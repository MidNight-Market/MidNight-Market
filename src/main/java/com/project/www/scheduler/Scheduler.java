package com.project.www.scheduler;

import com.project.www.repository.OrdersMapper;
import com.project.www.repository.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Scheduler {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    //2분마다 주문확정, 또는 주문완료시 상태를 배송완료로 변경하는 스케쥴러
    @Scheduled(fixedRate = 120000) //2분마다
    public void markAsDelivered() {
        ordersMapper.markAsDelivered();
        //배송완료되면 주문자에게 알림 추가
    }

    //10분마다 결제 대기인 상품 삭제
    @Scheduled(fixedRate = 600000)
    public void deletePendingPaymentProducts (){
        paymentMapper.deletePendingPaymentOrders();
    }

}
