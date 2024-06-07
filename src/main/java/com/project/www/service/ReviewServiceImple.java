package com.project.www.service;

import com.project.www.domain.ReviewVO;
import com.project.www.repository.OrdersMapper;
import com.project.www.repository.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImple implements ReviewService{

    private final ReviewMapper reviewMapper;
    private final OrdersMapper ordersMapper;

    @Transactional
    @Override
    public int register(ReviewVO reviewVO) {
        int isOk = reviewMapper.register(reviewVO);

        if(isOk > 0){
            return ordersMapper.isReviewCommentUpdate(reviewVO);
        }

        return 0;
    }
}
