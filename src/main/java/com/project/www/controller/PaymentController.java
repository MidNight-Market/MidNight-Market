package com.project.www.controller;

import com.project.www.domain.PaymentDTO;
import com.project.www.service.ImportService;
import com.project.www.service.PaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.POST;

import java.io.IOException;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment/*")
public class PaymentController {

    private final PaymentService psv;
    private final ImportService importService;

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

    @PostMapping("/order")
    public String order(@RequestParam("merchantUid") String merchantUid, Model model){
        log.info("주문페이지 잘 오나 확인>>>>{}",merchantUid);

        //나의 결제 상품 가져오기
        PaymentDTO paymentDTO = psv.getMyPaymentProduct(merchantUid);

        model.addAttribute("paymentDTO",paymentDTO);
        return "product/order";
    }

    //사전검증
    @ResponseBody
    @PostMapping("/prepare")
    public void prepare(@RequestBody PaymentDTO paymentDTO)
    throws IamportResponseException, IOException {
        log.info("사전검증 데이터 잘들어온지 확인<>>>>>>{}",paymentDTO);
        importService.postPrepare(paymentDTO);
    }
}