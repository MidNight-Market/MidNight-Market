package com.project.www.service;

import com.project.www.domain.OrdersVO;
import com.project.www.domain.PaymentDTO;
import com.project.www.domain.ProductVO;
import com.project.www.repository.OrdersMapper;
import com.project.www.repository.PaymentMapper;
import com.project.www.repository.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentServiceImple implements PaymentService{

    private final PaymentMapper paymentMapper;
    private final ProductMapper productMapper;
    private final OrdersMapper ordersMapper;


    @Transactional
    @Override
    public String post(PaymentDTO paymentDTO) {

        ProductVO productVO = productMapper.getDetail(paymentDTO.getProductId());

        //재고 소진시 주문 취소
        if (productVO.getTotalQty() == 0){
            return "quantity_exhaustion";
        }

        //잔여개수보다 주문개수가 많을 경우 주문 취소
        if(productVO.getTotalQty() < paymentDTO.getQty()){
            return "excess_quantity"+productVO.getTotalQty();
        }

        //UID, customerId, payDescription, payPrice입력
        //상품설명 저장
        paymentDTO.setPayDescription(productVO.getName()+paymentDTO.getQty()+" 개");
        //결제할 가격 저장
        paymentDTO.setPayPrice( productVO.getDiscountPrice() * paymentDTO.getQty());

        OrdersVO ordersVO = new OrdersVO();
        ordersVO.setMerchantUid(paymentDTO.getMerchantUid());
        ordersVO.setCustomerId(paymentDTO.getCustomerId());
        ordersVO.setProductId(paymentDTO.getProductId());
        ordersVO.setQty(paymentDTO.getQty());
        ordersVO.setPayPrice(paymentDTO.getQty() * productVO.getDiscountPrice());

        paymentMapper.register(paymentDTO);
        ordersMapper.register(ordersVO);

        return "success";
    }

    @Override
    public PaymentDTO getMyPaymentProduct(String merchantUid) {
        
        //주문 정보도 가져와야함
        PaymentDTO paymentDTO = paymentMapper.getMyPaymentProduct(merchantUid);
        paymentDTO.setOrdersList(ordersMapper.getMyOrdersProduct(merchantUid));
        return paymentDTO;
    }
}
