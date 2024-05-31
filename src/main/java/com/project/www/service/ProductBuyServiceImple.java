package com.project.www.service;

import com.project.www.domain.ProductBuyDTO;
import com.project.www.repository.ProductBuyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductBuyServiceImple implements ProductBuyService{

    private final ProductBuyMapper productBuyMapper;

    @Override
    public int register(ProductBuyDTO productBuyDTO) {
        return productBuyMapper.register(productBuyDTO);
    }
}
