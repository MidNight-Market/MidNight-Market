package com.project.www.repository;

import com.project.www.domain.ProductBuyDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductBuyMapper {
    int register(ProductBuyDTO productBuyDTO);
}
