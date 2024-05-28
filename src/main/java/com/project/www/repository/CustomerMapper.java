package com.project.www.repository;

import com.project.www.domain.CustomerVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    void insert(CustomerVO cvo);

    int checkEmail(String email);
}
