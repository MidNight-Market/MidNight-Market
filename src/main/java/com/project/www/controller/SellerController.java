package com.project.www.controller;

import com.project.www.domain.ProductVO;
import com.project.www.domain.SellerVO;
import com.project.www.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/seller/*")
@Controller

public class SellerController {

    private final SellerService ssv;

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(SellerVO sellerVO){

        //log.info(">>>>>셀러브이오>>>{}",sellerVO);

        int isOk = ssv.register(sellerVO);

        return "/index";
    }



}
