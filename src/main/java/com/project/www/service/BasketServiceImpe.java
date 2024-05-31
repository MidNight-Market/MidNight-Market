package com.project.www.service;

import com.project.www.domain.BasketVO;
import com.project.www.repository.BasketMapper;
import com.project.www.repository.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpe implements BasketService{

private final BasketMapper basketMapper;
private final ProductMapper productMapper;

    @Override
    public BasketVO productDuplicationVerify(BasketVO basketVO) {
        return basketMapper.productDuplicationVerify(basketVO);
    }


    @Override
    public int register(BasketVO basketVO) {
        return basketMapper.register(basketVO);
    }

    @Override
    public int productDuplicationUpdate(BasketVO basketVO) {
        return basketMapper.productDuplicationUpdate(basketVO);
    }

    @Override
    public List<BasketVO> getMyBasket(String email) {

        List<BasketVO> myBasket = basketMapper.getMyBasket(email);

        for(BasketVO bvo : myBasket){
            bvo.setProductVO(productMapper.getDetail(bvo.getProductId()));
        }

        return myBasket;
    }


}
