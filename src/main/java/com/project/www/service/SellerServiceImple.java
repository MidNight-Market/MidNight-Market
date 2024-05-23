package com.project.www.service;

import com.project.www.domain.SellerVO;
import com.project.www.repository.SellerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class SellerServiceImple implements SellerService{

    private final SellerMapper sellerMapper;

    @Override
    public int register(SellerVO sellerVO) {
        return sellerMapper.register(sellerVO);
    }

    @Override
    public Object getList(String id) {
        return null;
    }
}
