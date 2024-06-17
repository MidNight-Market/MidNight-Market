package com.project.www.service;

import com.project.www.repository.MemberCouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService{
    private final MemberCouponMapper memberCouponMapper;

    @Override
    public int addCoupon(String customerId, String couponId) {
        return memberCouponMapper.addCoupon(customerId, couponId);
    }
}
