package com.project.www.service;

import com.project.www.domain.HelpVO;
import com.project.www.repository.HelpMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class HelpServiceImpl implements HelpService {
    private final HelpMapper helpMapper;

    @Override
    public List<HelpVO> getList() {
        return helpMapper.getList();
    }

    @Override
    public int register(HelpVO hvo) {
        return helpMapper.insert(hvo);
    }

}
