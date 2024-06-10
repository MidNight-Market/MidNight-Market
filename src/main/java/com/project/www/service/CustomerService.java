package com.project.www.service;

import com.project.www.domain.CustomerVO;

public interface CustomerService {
    int insert(CustomerVO cvo);

    int checkEmail(String email);

    int checkNickName(String nickName);

    String findNickName(String nickName);

    int findId(String id);

    void updateDefaultPw(String id, String pw);

    void updatePw(String id, String pw);
}
