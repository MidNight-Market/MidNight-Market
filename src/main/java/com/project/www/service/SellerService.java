package com.project.www.service;

import com.project.www.domain.ProductVO;
import com.project.www.domain.SellerVO;

import java.util.List;

public interface SellerService {
    int register(SellerVO sellerVO);


    List<ProductVO> getList(String id);
}
