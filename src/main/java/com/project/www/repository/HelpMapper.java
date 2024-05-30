package com.project.www.repository;

import com.project.www.domain.HelpVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HelpMapper {

    List<HelpVO> getList();

    int insert(HelpVO hvo);

    HelpVO getDetail(long hno);

    int modify(HelpVO hvo);

    int delete(long hno);

    void replyRegister(HelpVO hvo);

    void replyUpdate(HelpVO hvo);
}
