package com.project.www.controller;

import com.project.www.domain.PaymentDTO;
import com.project.www.service.PaymentService;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment/*")
public class PaymentController {

    private final PaymentService psv;

    @ResponseBody
    @PostMapping("/post")
    public String paymentPost(@RequestBody PaymentDTO paymentDTO){
        log.info("결제DTO확인 ->>>{}",paymentDTO);

        return psv.post(paymentDTO);
    }

    @GetMapping("/receipt")
    public String receipt(){
        return "product/receipt";
    }

    @GetMapping("/order")
    public String order(){
        return "product/order";
    }

}