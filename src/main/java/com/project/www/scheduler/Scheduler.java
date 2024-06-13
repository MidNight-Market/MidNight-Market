package com.project.www.scheduler;

import com.project.www.domain.OrdersVO;
import com.project.www.repository.OrdersMapper;
import com.project.www.repository.PaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
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
       int row = ordersMapper.markAsDelivered();
       log.info("로우확인>>>{}",row);
       //업데이트를하면 업데이트된 개수 받아와서
        //배송완료 Count Limit 0, low 아이디 값 List로 받아오기
       
        //배송완료되면 주문자에게 알림 추가
    }

//    //10분마다 결제 대기인 상품 삭제
//    @Scheduled(fixedRate = 600000)
//    public void deletePendingPaymentProducts (){
//        paymentMapper.deletePendingPaymentOrders();
//    }

}
