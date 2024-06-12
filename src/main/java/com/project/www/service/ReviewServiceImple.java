package com.project.www.service;

import com.project.www.domain.ReviewDTO;
import com.project.www.domain.ReviewImageVO;
import com.project.www.domain.ReviewLikeVO;
import com.project.www.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImple implements ReviewService{

    private final ReviewMapper reviewMapper;
    private final OrdersMapper ordersMapper;
    private final ReviewImageMapper reviewImageMapper;
    private final ReviewLikeMapper reviewLikeMapper;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public int register(ReviewDTO reviewDTO) {

        log.info("리뷰DTO객체 확인>>>>{}", reviewDTO);

        int isOk = reviewMapper.register(reviewDTO.getReviewVO());
        log.info("아이디값 잘되나 확인>>{}",reviewDTO.getReviewVO().getId());
        if(isOk > 0){

            //파일이 있을 경우
            if(reviewDTO.getReviewImageVOList() != null){
                for(ReviewImageVO reviewImageVO : reviewDTO.getReviewImageVOList()){
                    reviewImageVO.setReviewId(reviewDTO.getReviewVO().getId());
                }

                reviewImageMapper.register(reviewDTO.getReviewImageVOList());

            }
            //리뷰 카운트 추가
            productMapper.reviewCountUpdate(reviewDTO.getReviewVO().getProductId());
            return ordersMapper.isReviewCommentUpdate(reviewDTO.getReviewVO());
        }

        return 0;
    }

    @Transactional
    @Override
    public int registerLike(ReviewLikeVO reviewLikeVO) {
        int isOK = reviewLikeMapper.registerLike(reviewLikeVO);
        return isOK;
    }

    @Transactional
    @Override
    public int deleteLike(ReviewLikeVO reviewLikeVO) {
        int isOK = reviewLikeMapper.deleteLike(reviewLikeVO);
        return isOK;
    }

    @Override
    public int getCount(String reviewId) {
       return reviewMapper.getCount(reviewId);
    }

    @Override
    public void update(ReviewLikeVO reviewLikeVO) {
        reviewMapper.update(reviewLikeVO);
    }

    @Override
    public void delete(ReviewLikeVO reviewLikeVO) {
        reviewMapper.delete(reviewLikeVO);
    }

    @Override
    public Boolean isExist(ReviewLikeVO reviewLikeVO) {
        return reviewLikeMapper.isExist(reviewLikeVO);
    }


}
