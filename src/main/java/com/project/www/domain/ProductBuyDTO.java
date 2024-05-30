package com.project.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductBuyDTO {

    private String merchentUid;
    private String customerId;
    private long productId;
    private long payPrice;
    private String buyDate;
    private String address;
    private String status;
}
