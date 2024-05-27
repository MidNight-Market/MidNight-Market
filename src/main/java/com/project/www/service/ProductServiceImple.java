package com.project.www.service;

import com.project.www.domain.ProductDTO;
import com.project.www.domain.ProductDetailImageVO;
import com.project.www.repository.ProductCategoryDetailMapper;
import com.project.www.repository.ProductCategoryMapper;
import com.project.www.repository.ProductDetailImageMapper;
import com.project.www.repository.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImple implements ProductService{

    private final ProductMapper productMapper;
    private final ProductDetailImageMapper productDetailImageMapper;
    private final ProductCategoryMapper productCategoryMapper;
    private final ProductCategoryDetailMapper productCategoryDetailMapper;

    @Transactional
    @Override
    public int insert(ProductDTO productDTO) {

        int isOK = productMapper.insert(productDTO.getProductVO());


        long productId = productMapper.getProductId();

        log.info(">>>>>상품번호 마지막 가져오기>>>>>{}",productId);

        if(isOK > 0){
            for(ProductDetailImageVO image : productDTO.getImageList()){
                image.setProductId(productId);

        log.info(">>>>세부이미지 리스트>>>>>{}",image);

                productDetailImageMapper.insert(image);
            }
        }

        return isOK;
    }

    @Override
    public ProductDTO getDetail(long id) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductVO(productMapper.getDetail(id));
        productDTO.setImageList(productDetailImageMapper.getDetail(id));

        return productDTO;
    }

    @Override
    public ProductDTO getProductCategoryList() {

        ProductDTO productDTO = new ProductDTO();

        productDTO.setPcList(productCategoryMapper.getList());
        productDTO.setPcdList(productCategoryDetailMapper.getList());

        return productDTO;
    }
}
