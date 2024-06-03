package com.project.www.service;

import com.project.www.domain.PaymentDTO;
import com.project.www.domain.ProductVO;
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

        

        return "성공";
    }
}
