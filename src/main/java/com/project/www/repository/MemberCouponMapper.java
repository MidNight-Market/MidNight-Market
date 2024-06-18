package com.project.www.repository;

import com.project.www.domain.MemberCouponVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberCouponMapper {

    int addCoupon(String customerId, String couponId);

    List<MemberCouponVO> getMemberCouponList(String customerId);
}
