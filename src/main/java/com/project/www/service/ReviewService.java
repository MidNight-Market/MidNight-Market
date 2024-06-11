package com.project.www.service;

import com.project.www.domain.ReviewDTO;
import com.project.www.domain.ReviewLikeVO;

public interface ReviewService {
    int register(ReviewDTO reviewDTO);

    int registerLike(ReviewLikeVO reviewLikeVO);

    int deleteLike(ReviewLikeVO reviewLikeVO);

    int getCount(String reviewId);

    void update(ReviewLikeVO reviewLikeVO);

    void delete(ReviewLikeVO reviewLikeVO);

    Boolean isExist(ReviewLikeVO reviewLikeVO);
}
