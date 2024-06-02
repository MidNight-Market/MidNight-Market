package com.project.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PaymentDTO {

    private String payUid;
    private String customerId;
    private String payDescription;
    private String payDate;
    private long payPrice;
    private String payMethod;
    private String address;
    private String status;
    //개수 상품아이디는 어떻게 할지? 그냥추가?
    private long productId;
    private long qty;
}
