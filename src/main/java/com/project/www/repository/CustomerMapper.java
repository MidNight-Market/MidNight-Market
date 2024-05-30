package com.project.www.repository;

import com.project.www.domain.CustomerVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    void insert(CustomerVO cvo);

    int checkEmail(String email);

    int checkNickName(String nickName);

    String findNickName(String nickName);

    int findId(String id);

    void updateDefaultPw(String id);

    void updatePw(String id, String pw);
}
