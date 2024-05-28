package com.project.www.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private ProductVO productVO;
    private List<ProductDetailImageVO> imageList;
    private List<ProductCategoryVO> pcList;
    private List<ProductCategoryDetailVO> pcdList;

}
