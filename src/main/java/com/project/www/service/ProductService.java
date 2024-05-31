package com.project.www.service;

import com.project.www.domain.BasketVO;
import com.project.www.domain.ProductDTO;

import java.util.List;

public interface ProductService {
    int insert(ProductDTO productDTO);

    ProductDTO getDetail(long id);


    ProductDTO getProductCategoryList();

}
