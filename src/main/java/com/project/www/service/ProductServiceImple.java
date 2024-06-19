package com.project.www.service;

import com.project.www.domain.*;
import com.project.www.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImple implements ProductService {

    private final ProductMapper productMapper;
    private final ProductDetailImageMapper productDetailImageMapper;
    private final ProductCategoryMapper productCategoryMapper;
    private final ProductCategoryDetailMapper productCategoryDetailMapper;
    private final SlangMapper slangMapper;
    private final ReviewMapper reviewMapper;
    private final ReviewImageMapper reviewImageMapper;
    private final ReviewLikeMapper reviewLikeMapper;

    @Transactional
    @Override
    public int insert(ProductDTO productDTO) {

        int isOK = productMapper.insert(productDTO.getProductVO());


        long productId = productMapper.getProductId();

        log.info(">>>>>상품번호 마지막 가져오기>>>>>{}", productId);

        if (isOK > 0) {
            for (ProductDetailImageVO image : productDTO.getImageList()) {
                image.setProductId(productId);

                log.info(">>>>세부이미지 리스트>>>>>{}", image);

                productDetailImageMapper.insert(image);
            }
        }

        return isOK;
    }

    @Override
    public ProductDTO getDetail(String customerId, long id) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductVO(productMapper.getDetail(id));
        productDTO.setImageList(productDetailImageMapper.getDetail(id));
        productDTO.setPcdVO(productCategoryDetailMapper.getMyCategoryDetail(productDTO.getProductVO().getProductCategoryDetailId()));
        productDTO.setPcVO(productCategoryMapper.getMyCategory(productDTO.getPcdVO().getProductCategoryId()));
        productDTO.setSlangVO(slangMapper.getMySlang(customerId, id));

        return productDTO;
    }

    @Override
    public ProductDTO getProductCategoryList() {

        ProductDTO productDTO = new ProductDTO();

        productDTO.setPcList(productCategoryMapper.getList());
        productDTO.setPcdList(productCategoryDetailMapper.getList());


        return productDTO;
    }

    @Override
    public int slangPost(SlangVO slangVO) {
        return slangMapper.slangPost(slangVO);
    }

    @Override
    public int slangDelete(SlangVO slangVO) {
        return slangMapper.slangDelete(slangVO);
    }

    @Override
    public List<ProductVO> getMySlangProduct(String customerId) {

        List<SlangVO> slangVOS = slangMapper.getMySlangProductList(customerId);

        List<ProductVO> productVOS = new ArrayList<>();

        for (SlangVO svo : slangVOS) {
            productVOS.add(productMapper.getDetail(svo.getProductId()));
        }

        return productVOS;
    }

    @Override
    public List<ReviewVO> getReview(long id) {
        List<ReviewVO> rvo = reviewMapper.getReview(id);

        for (ReviewVO review : rvo) {
            review.setReviewImageVOList(reviewImageMapper.getReviewImgList(review.getId()));
            review.setReviewLikeVO(reviewLikeMapper.getReviewLike(review.getId()));
        }
        log.info("rvo >> {}", rvo);
        return rvo;
    }

    @Override
    public int getTotalCount(ListPagingVO pgvo) {
        return productMapper.getTotalCount(pgvo);
    }

    @Override
    public List<ProductVO> getProductList(ListPagingVO pgvo) {

        ProductDTO productDTO = ProductDTO.builder()
                .pcList(productCategoryMapper.getList())
                .pcdList(productCategoryDetailMapper.getList())
                .build();

        String type = pgvo.getType();
        String category = pgvo.getCategory();
        String categoryDetail = pgvo.getCategoryDetail();

        String description = "";

        if (type != null) {

            if (type.equals("best")) {
                description = "베스트상품";
            }

            if (type.equals("new")) {
                description = "신상품";
            }

            if (type.equals("sale")) {
                description = "세일상품";
            }
        }

        if(pgvo.getCategory() != null){

            for (ProductCategoryVO productCategoryVO : productDTO.getPcList()) {

                if(Long.toString(productCategoryVO.getId()).equals(category)){
                    description = productCategoryVO.getName();
                    break;
                }
            }
        }

        if(pgvo.getCategoryDetail() != null){

            for (ProductCategoryDetailVO productCategoryDetailVO : productDTO.getPcdList()) {

                if(Long.toString(productCategoryDetailVO.getId()).equals(categoryDetail)){
                    description = productCategoryDetailVO.getName();
                    break;
                }
            }
        }

        pgvo.setDescription(description);

        return productMapper.getList(pgvo);

    }
}





