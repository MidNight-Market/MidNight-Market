package com.project.www.service;

import com.project.www.domain.OrdersVO;
import com.project.www.repository.OrdersMapper;
import com.project.www.repository.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImple implements OrdersService {

    private final OrdersMapper ordersMapper;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public List<OrdersVO> getMyPurchasedProductList(String customerId) {

        List<OrdersVO> ordersVOList = ordersMapper.getMyPurchasedProductList(customerId);

        for(OrdersVO ordersVO : ordersVOList){
            ordersVO.setProductVO(productMapper.getDetail(ordersVO.getProductId()));
        }
        return ordersVOList;
    }

    @Transactional
    @Override
    public List<OrdersVO> getMyFrequentPurchasesList(String customerId) {
        List<OrdersVO> ordersVOList = ordersMapper.getMyFrequentPurchasesList(customerId);

        for(OrdersVO ordersVO : ordersVOList){
            ordersVO.setProductVO(productMapper.getDetail(ordersVO.getProductId()));
        }

        return ordersVOList;
    }

    @Override
    public List<OrdersVO> getMyWriteReviewList(String customerId) {
        List<OrdersVO> ordersVOList = ordersMapper.getMyWriteReviewList(customerId);

        for(OrdersVO ordersVO : ordersVOList){
            ordersVO.setProductVO(productMapper.getDetail(ordersVO.getProductId()));
        }
        return ordersVOList;
    }

    @Override
    public List<OrdersVO> getList() {
        return ordersMapper.getList();
    }
}
