package com.project.www.controller;

import com.project.www.domain.CustomerVO;
import com.project.www.service.CustomerService;
import com.project.www.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer/*")
@Controller
public class CustomerController {
    private final CustomerService csv;
    private final MailService mailService;

    @GetMapping("/insert")
    public void insert() {}

    @PostMapping("/insert")
    public String insert(CustomerVO cvo){
        csv.insert(cvo);
        log.info("cvo값 체크 {}", cvo);
        return "index";
    }
    @GetMapping("/registerSelect")
    public void registerSelect(){}

    @GetMapping("/socialSelect")
    public void socialSelect(){}

    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail){
        int number = mailService.sendMail(mail);
        String num = "" + number;
        return num;
    }

    @GetMapping("/login")
    public void login(){
    }
    @ResponseBody
    @GetMapping("/check/{email}")
    public String checkEmail(@PathVariable("email")String email) {
        int isOk = csv.checkEmail(email);
        return isOk > 0 ? "1" : "0";
    }
    @ResponseBody
    @GetMapping("/checkN/{nickName}")
    public String checkNickName(@PathVariable("nickName")String nickName) {
        int isOk = csv.checkNickName(nickName);
        return isOk > 0 ? "1" : "0";
    }

}