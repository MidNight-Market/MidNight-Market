package com.project.www.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberCouponMapper {

    int addCoupon(String customerId, String couponId);
}