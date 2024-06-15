package com.project.www.repository;

import com.project.www.domain.CustomerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    int insert(CustomerVO cvo);

    int checkEmail(String email);

    int checkNickName(String nickName);

    String findNickName(String nickName);

    int findId(String id);

    void updateDefaultPw(String id, String pw);

    void updatePw(String id, String pw);

    CustomerVO findByUserName(String providerId);

    List<CustomerVO> getNickName(String customerId);

    List<CustomerVO> getList();

    int confirmOrderUpdate(CustomerVO ordersVO);

    CustomerVO selectOne(String customerId);
}
