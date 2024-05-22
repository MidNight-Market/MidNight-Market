package com.project.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SellerVO {

    private long sellerCode;
    private String sellerId;
    private String sellerPw;
    private String sellerName;

}
