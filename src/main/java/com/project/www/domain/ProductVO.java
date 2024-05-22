package com.project.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {

    private long productCode;
    private String productName;
    private long productPrice;
    private long productCategoryCode;
    private String productDescription;
    private long productTotalQuantity;
    private long productSoldQuantity;
    private String productRegisterDate;
    private String mainImageLink;
    private String thumbnailImageLink;
    private long sellerCode;

}
