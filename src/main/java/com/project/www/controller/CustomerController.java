package com.project.www.controller;

import com.project.www.domain.CustomerVO;
import com.project.www.service.CustomerService;
import com.project.www.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer/*")
@Controller
public class CustomerController {
    private final CustomerService csv;
    /*private final PasswordEncoder passwordEncoder;*/
    private final MailService mailService;


    @GetMapping("/insert")
    public void insert() {}

    @PostMapping("/insert")
    public String insert(CustomerVO cvo){
        /*cvo.setCustomerPw(passwordEncoder.encode(cvo.getCustomerPw()));*/
        csv.insert(cvo);
        log.info("cvo값 체크 {}", cvo);
        return "index";
    }
    @ResponseBody
    @GetMapping("/{email}")
    public String getEmail(@PathVariable("email")String email) {
        log.info("email 체크 {}",email);
        return "";
    }
    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail){

        int number = mailService.sendMail(mail);

        String num = "" + number;

        return num;
    }

}
