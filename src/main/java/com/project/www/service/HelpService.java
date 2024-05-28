package com.project.www.service;

import com.project.www.domain.HelpVO;

import java.util.List;

public interface HelpService {
    List<HelpVO> getList();

    int register(HelpVO hvo);
}
