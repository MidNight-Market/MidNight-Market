package com.project.www.service;

import com.project.www.domain.ProductDTO;
import com.project.www.domain.SlangVO;

public interface ProductService {
    int insert(ProductDTO productDTO);

    ProductDTO getDetail(String customerId, long id);


    ProductDTO getProductCategoryList();

    int slangPost(SlangVO slangVO);

    int slangDelete(SlangVO slangVO);
}
