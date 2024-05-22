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
    private List<ProductImageDetailVO> imageList;

}
