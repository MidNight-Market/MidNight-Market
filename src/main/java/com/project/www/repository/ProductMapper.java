package com.project.www.repository;

import com.project.www.domain.ProductVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    int insert(ProductVO productVO);

    long getProductCode();
}
