package com.project.www.repository;

import com.project.www.domain.ReviewLikeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewLikeMapper {

    List<ReviewLikeVO> getReviewLike(long id);

    int registerLike(ReviewLikeVO reviewLikeVO);

    int deleteLike(ReviewLikeVO reviewLikeVO);

    Boolean isExist(ReviewLikeVO reviewLikeVO);
}
