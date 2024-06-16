package com.project.www.service;

import com.project.www.domain.BasketVO;
import com.project.www.domain.OrdersVO;
import com.project.www.domain.PaymentDTO;
import com.project.www.domain.ProductVO;
import com.project.www.repository.BasketMapper;
import com.project.www.repository.OrdersMapper;
import com.project.www.repository.PaymentMapper;
import com.project.www.repository.ProductMapper;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PaymentServiceImple implements PaymentService {

    private final ImportService importService;
    private final PaymentMapper paymentMapper;
    private final ProductMapper productMapper;
    private final OrdersMapper ordersMapper;
    private final BasketMapper basketMapper;


    @Transactional
    @Override
    public String post(PaymentDTO paymentDTO) {

        //주문하려는 상품 객체 가져오기
        ProductVO productVO = productMapper.getDetail(paymentDTO.getProductId());

        long productQuantity = productVO.getTotalQty(); //상품 수량
        long paymentQuantity = paymentDTO.getQty(); //결제할 수량
        long originalPayment = productVO.getPrice() * paymentQuantity; //기존 결제가격
        long discountPayment = productVO.getDiscountPrice() * paymentQuantity; //할인된 결제가격

        //재고 소진시 주문 취소
        if (productQuantity == 0) {
            return "quantity_exhaustion";
        }

        //잔여개수보다 주문개수가 많을 경우 주문 취소
        if (productQuantity < paymentQuantity) {
            return "excess_quantity" + productVO.getTotalQty();
        }

        try {
            //결제 정보 객체에 저장
            paymentDTO.setPayDescription(productVO.getName() + paymentQuantity + "개"); //상품결제 설명 저장
            paymentDTO.setOriginalPrice(originalPayment); //할인되기 전 기존가격 저장 (할인금액 구하기 위해)
            paymentDTO.setPayPrice(discountPayment); //실제 결제가격 저장

            //주문정보 저장
            OrdersVO orders = OrdersVO.builder()
                    .merchantUid(paymentDTO.getMerchantUid())
                    .customerId(paymentDTO.getCustomerId())
                    .productId(paymentDTO.getProductId())
                    .qty(paymentQuantity)
                    .payPrice(discountPayment)
                    .build();

            int isOk = 1;
            isOk *= paymentMapper.register(paymentDTO); // 결제 정보 등록
            isOk *= ordersMapper.register(orders); // 주문 정보 등록
            isOk *= productMapper.orderUpdate(orders); // 상품 수량 업데이트
            //여기에 알림 로직 추가

            if (isOk == 0) {
                throw new RuntimeException("Failed to process payment and order.");
            }

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @Transactional
    @Override
    public PaymentDTO getMyPaymentProduct(String merchantUid) {

        PaymentDTO paymentDTO = paymentMapper.getMyPaymentProduct(merchantUid); //결제정보
        paymentDTO.setOrdersList(ordersMapper.getMyOrdersProduct(merchantUid)); //주문정보들 (장바구니 정보일경우 주문정보 여러개)

        //상품정보 가져오기 (이미지)
        paymentDTO.getOrdersList().forEach(orders -> {
            orders.setProductVO(productMapper.getDetail(orders.getProductId()));
        });

        return paymentDTO;
    }

    @Transactional
    @Override
    public String basketPost(PaymentDTO paymentDTO) {
        try {
            String productName = "";
            long productCount = 0;
            long originalPrice = 0;
            long payPrice = 0;

            // 내 장바구니 리스트
            List<BasketVO> basketList = basketMapper.getMyBasket(paymentDTO.getCustomerId());
            List<ProductVO> productList = new ArrayList<>();

            for (BasketVO bsv : basketList) {
                ProductVO productVO = productMapper.getDetail(bsv.getProductId());//상품 정보 리스트에 저장

                if (bsv.getQty() > productVO.getTotalQty()) {//수량 초과이면 저장되게 하지 못하게 막아야함
                    return "excess_quantity";
                }

                if (bsv.getQty() != 0) {//상단에 이름 저장하기위해 수량이 0이 아닌 상품의 이름을 가져옴
                    productName = productVO.getName();
                    productCount += 1;
                }
                originalPrice += bsv.getQty() * productVO.getPrice();
                payPrice += bsv.getQty() * productVO.getDiscountPrice();

                //리스트에 저장
                productList.add(productVO);
            }

            paymentDTO.setPayDescription(productName + (productCount <= 1 ? paymentDTO.getQty() + "개" : " 외 (" + productCount + " 개)"));
            paymentDTO.setOriginalPrice(originalPrice);
            paymentDTO.setPayPrice(payPrice);

            int isOk = paymentMapper.register(paymentDTO);

            for (int i = 0; i < basketList.size(); i++) {
                if (basketList.get(i).getQty() != 0) {//장바구니에 있는 상품 개수가 0이면 결제에 추가 안돼게
                    OrdersVO ordersVO = OrdersVO.builder()
                            .merchantUid(paymentDTO.getMerchantUid())  //상품고유번호
                            .customerId(paymentDTO.getCustomerId()) //고객아이디
                            .productId(basketList.get(i).getProductId()) //상품번호
                            .qty(basketList.get(i).getQty()) //구매하려는 상품 개수
                            .payPrice(basketList.get(i).getQty() * productList.get(i).getDiscountPrice()) //구매하려는 상품 가격
                            .build();

                    BasketVO basketVO = BasketVO.builder()//주문이 들어가면 기존에 있던 장바구니 목록 삭제
                            .customerId(ordersVO.getCustomerId())
                            .productId(ordersVO.getProductId())
                            .build();

                    isOk *= basketMapper.delete(basketVO);
                    isOk *= ordersMapper.register(ordersVO); // 주문 정보 등록
                    isOk *= productMapper.orderUpdate(ordersVO); //상품의 quantity 도 주문한 갯수만큼 수정
                }
            }

            if (isOk == 0) {
                throw new RuntimeException("Failed to process payment and order.");
            }

            return "post_success";
        } catch (Exception e) {
            e.printStackTrace();
            return "post_fail";
        }
    }


    @Transactional
    @Override
    public int paySuccessUpdate(PaymentDTO paymentDTO) {
        try {
            int isOk = paymentMapper.paySuccessUpdate(paymentDTO);
            isOk *= ordersMapper.paySuccessUpdate(paymentDTO.getMerchantUid());
            return isOk;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("paySuccessUpdate 중 오류 발생");
            return -1;
        }
    }


    @Transactional
    @Override
    public String refundUpdate(OrdersVO ordersVO) {

        try {
            ordersVO = ordersMapper.selectOne(ordersVO.getId()); // 환불할 주문 객체 가져오기
            PaymentDTO paymentDTO = paymentMapper.getMyPaymentProduct(ordersVO.getMerchantUid()); // 부분 환불될 결제 금액 가져오기
            ProductVO productVO = ProductVO.builder()
                    .totalQty(ordersVO.getQty()) // 환불될 상품 수량
                    .id(ordersVO.getProductId())  // 환불될 상품 번호
                    .build();

            String merchantUid = paymentDTO.getMerchantUid(); // 결제 주문 번호
            long amount = ordersVO.getPayPrice(); // 환불 금액
            BigDecimal checksum = BigDecimal.valueOf(paymentDTO.getPayPrice() - ordersVO.getPayPrice()); // 환불하고 남은 금액 (부분 환불)

            importService.refundPaymentByMerchantUid(merchantUid, amount, checksum);

            // 환불시 결제 DB 업데이트
            paymentDTO.setPayPrice(paymentDTO.getPayPrice() - ordersVO.getPayPrice()); // 결제하고 남은 금액
            ordersVO.setPayPrice(amount); // 결제한 금액

            int isOk = paymentMapper.refundUpdate(paymentDTO);
            isOk *= ordersMapper.refundUpdate(ordersVO);
            isOk *= productMapper.rollbackRefundQuantity(productVO);

            if (isOk == 0) {
                throw new RuntimeException("환불 처리에 실패했습니다.");
            }
            DecimalFormat df = new DecimalFormat("#,###");
            return df.format(ordersVO.getPayPrice())+"원이 정상적으로 환불되었습니다.";
        } catch (IamportResponseException | IOException | RuntimeException e) {
            log.error("환불 실패 : {}", e.getMessage());
            return "Refund failed :" + e.getMessage();
        }


    }
}
