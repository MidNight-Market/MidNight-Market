package com.project.www.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDTO {

    private List<ProductCategoryVO> pcList;
    private List<ProductCategoryDetailVO> pcdList;

}
