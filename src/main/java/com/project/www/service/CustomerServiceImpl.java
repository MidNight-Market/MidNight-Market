package com.project.www.service;

import com.project.www.domain.CustomerVO;
import com.project.www.repository.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerMapper customerMapper;

    @Override
    public void insert(CustomerVO cvo) {
        customerMapper.insert(cvo);
    }

    @Override
    public int checkEmail(String email) {
        int isOk = customerMapper.checkEmail(email);
        return isOk;
    }
}
