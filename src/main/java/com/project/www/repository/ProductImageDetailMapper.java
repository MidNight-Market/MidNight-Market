package com.project.www.repository;

import com.project.www.domain.ProductImageDetailVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductImageDetailMapper {
    void insert(ProductImageDetailVO image);
}
