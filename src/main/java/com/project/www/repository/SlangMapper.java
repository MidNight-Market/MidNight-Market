package com.project.www.repository;

import com.project.www.domain.SlangVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SlangMapper {
    SlangVO getMySlang(@Param("customerId") String customerId, @Param("productId") long productId);

    int slangPost(SlangVO slangVO);

    int slangDelete(SlangVO slangVO);
}
