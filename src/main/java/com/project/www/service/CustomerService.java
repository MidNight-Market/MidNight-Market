package com.project.www.service;

import com.project.www.domain.CustomerVO;

public interface CustomerService {
    void insert(CustomerVO cvo);

    int checkEmail(String email);

    int checkNickName(String nickName);

    String findNickName(String nickName);
}
