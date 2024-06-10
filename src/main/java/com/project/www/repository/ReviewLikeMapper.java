package com.project.www.repository;

import com.project.www.domain.ReviewLikeVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewLikeMapper {

    ReviewLikeVO getReviewLike(long id);

    int registerLike(ReviewLikeVO reviewLikeVO);
}
