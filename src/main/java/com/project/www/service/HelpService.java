package com.project.www.service;

import com.project.www.domain.HelpVO;

import java.util.List;

public interface HelpService {
    List<HelpVO> getList();

    int register(HelpVO hvo);

    HelpVO getDetail(long hno);

    int modify(HelpVO hvo);

    int delete(long hno);

    void replyRegister(HelpVO hvo);
}
