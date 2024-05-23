package com.project.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {

    private long id;
    private String name;
    private long price;
    private long productCategoryId;
    private String description;
    private long totalQty;
    private long soldQty;
    private String registerDate;
    private String mainImage;
    private String thumbImage;
    private String sellerId;


}
