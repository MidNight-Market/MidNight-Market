package com.project.www.repository;

import com.project.www.domain.SellerVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SellerMapper {
    int register(SellerVO sellerVO);
}
