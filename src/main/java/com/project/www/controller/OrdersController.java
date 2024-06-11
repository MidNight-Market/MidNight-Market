package com.project.www.controller;

import com.project.www.domain.OrdersVO;
import com.project.www.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/orders/*")
public class OrdersController {

    private final OrdersService osv;

    @ResponseBody
    @GetMapping("/getMyPurchasedProductList/{customerId}")
    public List<OrdersVO> getMyPurchasedProductList(@PathVariable("customerId") String customerId){

        return osv.getMyPurchasedProductList(customerId);
    }

    @ResponseBody
    @GetMapping("/getMyFrequentPurchasesList/{customerId}")
    public List<OrdersVO> getMyFrequentPurchasesList(@PathVariable("customerId") String customerId){

        log.info("내가 자주 주문한 리스트에서 고객 아이디 확인>>>{}",customerId);
        return osv.getMyFrequentPurchasesList(customerId);
    }

}
