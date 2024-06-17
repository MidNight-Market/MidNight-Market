package com.project.www.service;

import com.project.www.domain.AddressVO;
import com.project.www.repository.AddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressMapper addressMapper;

    @Override
    public int register(AddressVO avo) {
        return addressMapper.register(avo);
    }

    @Override
    public List<AddressVO> getList() {
        return addressMapper.getList();
    }
}
