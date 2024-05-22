package com.project.www.service;

import com.project.www.domain.ProductDTO;
import com.project.www.domain.ProductImageDetailVO;
import com.project.www.repository.ProductImageDetailMapper;
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
    private final ProductImageDetailMapper productImageDetailMapper;

    @Transactional
    @Override
    public int insert(ProductDTO productDTO) {
        int isOK = productMapper.insert(productDTO.getProductVO());

        long productCode = productMapper.getProductCode();

        if(isOK > 0){
            for(ProductImageDetailVO image : productDTO.getImageList()){
                image.setProductCode(productCode);
                productImageDetailMapper.insert(image);
            }
        }
        return isOK;
    }
}
