package com.project.www.service;

import com.project.www.domain.PaymentDTO;

public interface PaymentService {
    String post(PaymentDTO paymentDTO);

    PaymentDTO getMyPaymentProduct(String merchantUid);
}
