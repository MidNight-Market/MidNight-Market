package com.project.www.service;

import com.project.www.domain.BasketVO;

import java.util.List;

public interface BasketService {
    int register(BasketVO basketVO);

    BasketVO productDuplicationVerify(BasketVO basketVO);

    int productDuplicationUpdate(BasketVO basketVO);

    List<BasketVO> getMyBasket(String email);
}
