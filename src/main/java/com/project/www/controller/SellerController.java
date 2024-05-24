package com.project.www.controller;

import com.project.www.domain.ProductDTO;
import com.project.www.domain.ProductVO;
import com.project.www.domain.SellerVO;
import com.project.www.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/seller/*")
@Controller

public class SellerController {

    private final SellerService ssv;
    //private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(SellerVO sellerVO){

        //log.info(">>>>>셀러브이오>>>{}",sellerVO);

        //Password 암호화
        //sellerVO.setPw(passwordEncoder.encode(sellerVO.getPw()));

        int isOk = ssv.register(sellerVO);

        return "/index";
    }

    @GetMapping("/productList")
    public void productList(Model model){
    //아이디 또는 이메일 a태그 또는 프린시펄로 받아오기

        String id = "dbscksdnd";

        List<ProductVO> list = ssv.getList(id);
        
        log.info(">>>>>상품 리스트 출력{}",list);

        model.addAttribute("list",list);
    }




}
