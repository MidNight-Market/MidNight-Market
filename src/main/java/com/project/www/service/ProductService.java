package com.project.www.service;

import com.project.www.domain.ProductDTO;
import com.project.www.domain.ProductVO;

public interface ProductService {
    int insert(ProductDTO productDTO);

    ProductDTO getDetail(long id);
}
