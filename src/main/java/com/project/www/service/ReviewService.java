package com.project.www.service;

import com.project.www.domain.ReviewDTO;
import com.project.www.domain.ReviewLikeVO;

public interface ReviewService {
    int register(ReviewDTO reviewDTO);

    String registerLike(ReviewLikeVO reviewLikeVO);
}
