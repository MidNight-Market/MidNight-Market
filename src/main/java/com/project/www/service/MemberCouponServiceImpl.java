package com.project.www.service;

import com.project.www.domain.MemberCouponVO;
import com.project.www.repository.MemberCouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService{
    private final MemberCouponMapper memberCouponMapper;

    @Override
    public int addCoupon(String customerId, String couponId) {
        return memberCouponMapper.addCoupon(customerId, couponId);
    }

    @Override
    public List<MemberCouponVO> getMemberCouponList(String customerId) {
        return memberCouponMapper.getMemberCouponList(customerId);
    }
}
