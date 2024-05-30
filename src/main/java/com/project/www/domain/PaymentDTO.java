package com.project.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PaymentDTO {

    private String impUid;
    private String merchantUid;
    private String buyerName;
    private String name;
    private long paidAmount;

}
