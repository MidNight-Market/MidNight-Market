package com.project.www.service;

import com.project.www.domain.ProductDTO;

public interface ProductService {
    int insert(ProductDTO productDTO);

    ProductDTO getDetail(long id);


    ProductDTO getProductCategoryList();
}
