package com.project.www.service;

import com.project.www.domain.CustomerVO;
import com.project.www.domain.OrdersVO;
import com.project.www.repository.CustomerMapper;
import com.project.www.repository.OrdersMapper;
import com.project.www.repository.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServiceImple implements OrdersService {

    private final OrdersMapper ordersMapper;
    private final ProductMapper productMapper;
    private final CustomerMapper customerMapper;

    @Transactional
    @Override
    public List<OrdersVO> getMyPurchasedProductList(String customerId) {

        List<OrdersVO> ordersVOList = ordersMapper.getMyPurchasedProductList(customerId);

        for (OrdersVO ordersVO : ordersVOList) {
            ordersVO.setProductVO(productMapper.getDetail(ordersVO.getProductId()));
        }
        return ordersVOList;
    }

    @Transactional
    @Override
    public List<OrdersVO> getMyFrequentPurchasesList(String customerId) {
        List<OrdersVO> ordersVOList = ordersMapper.getMyFrequentPurchasesList(customerId);

        for (OrdersVO ordersVO : ordersVOList) {
            ordersVO.setProductVO(productMapper.getDetail(ordersVO.getProductId()));
        }

        return ordersVOList;
    }

    @Override
    public List<OrdersVO> getMyWriteReviewList(String customerId) {
        List<OrdersVO> ordersVOList = ordersMapper.getMyWriteReviewList(customerId);

        for (OrdersVO ordersVO : ordersVOList) {
            ordersVO.setProductVO(productMapper.getDetail(ordersVO.getProductId()));
        }
        return ordersVOList;
    }

    @Override
    public List<OrdersVO> getList() {
        return ordersMapper.getList();
    }

    @Transactional
    @Override
    public String confirmOrderUpdate(OrdersVO ordersVO) {

        int isOk;

        try {
            ordersVO = ordersMapper.selectOne(ordersVO.getId());
            CustomerVO customerVO = customerMapper.selectOne(ordersVO.getCustomerId());

            boolean isMembership = customerVO.isMStatus();
            long point = customerVO.getPoint();
            long payPrice = ordersVO.getPayPrice();
            long addedPoints = 0;

            if(isMembership){ //멤버쉽이 있다면
                customerVO.setPoint(point + Math.round(payPrice * 0.05));
                addedPoints = Math.round(payPrice * 0.05);
            }

            if(!isMembership){//멤버쉼이 없다면
                customerVO.setPoint(point + Math.round(payPrice * 0.01));
                addedPoints = Math.round(payPrice * 0.01);
            }

            int ordersUpdateResult = ordersMapper.confirmOrderUpdate(ordersVO);
            int customerUpdateResult = customerMapper.confirmOrderUpdate(customerVO);

            if (ordersUpdateResult == 0 || customerUpdateResult == 0) {
                throw new RuntimeException("주문확정에 실패하였습니다.");
            }

            return "success/"+addedPoints;

        } catch (RuntimeException e) {
            e.printStackTrace();
            return "fail";
        }

    }
}
