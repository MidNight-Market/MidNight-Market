package com.project.www.service;

import com.project.www.domain.*;

import java.util.List;

public interface ProductService {
    int insert(ProductDTO productDTO);

    ProductDTO getDetail(String customerId, long id);
    
    ProductDTO getProductCategoryList();

    int slangPost(SlangVO slangVO);

    int slangDelete(SlangVO slangVO);

    List<ProductVO> getMySlangProduct(String customerId);

    List<ReviewVO> getReview(long id);

    int getTotalCount(ListPagingVO pgvo);

    List<ProductVO> getProductList(ListPagingVO pgvo);
}
