package com.project.www.repository;

import com.project.www.domain.ProductDetailImageVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailImageMapper {

     void insert(ProductDetailImageVO image);
}
