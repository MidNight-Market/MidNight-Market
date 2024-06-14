package com.project.www.repository;

import com.project.www.domain.PaymentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    int register(PaymentDTO paymentDTO);

    PaymentDTO getMyPaymentProduct(String merchantUid);

    int paySuccessUpdate(PaymentDTO paymentDTO);

    int refundUpdate(PaymentDTO paymentDTO);

    void deletePendingPaymentOrders();
}
