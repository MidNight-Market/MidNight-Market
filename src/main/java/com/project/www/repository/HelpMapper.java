package com.project.www.repository;

import com.project.www.domain.HelpVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HelpMapper {

    List<HelpVO> getList();

    int insert(HelpVO hvo);
}
