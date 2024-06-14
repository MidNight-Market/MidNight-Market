package com.project.www.service;

import com.project.www.domain.CouponVO;
import com.project.www.repository.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService{

    private final CouponMapper couponMapper;

    @Override
    public int insert(CouponVO cvo) {
        return couponMapper.insert(cvo);
    }

    @Override
    public List<CouponVO> getList() {
        return couponMapper.getList();
    }
}
