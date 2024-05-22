package com.project.www.controller;

import com.project.www.domain.CustomerVO;
import com.project.www.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer/*")
@Controller
public class CustomerController {
    private final CustomerService csv;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/insert")
    public void insert() {}

    @PostMapping("/insert")
    public String insert(CustomerVO cvo){
        cvo.setCustomerPw(passwordEncoder.encode(cvo.getCustomerPw()));
        csv.insert(cvo);
        log.info("cvo값 체크 {}", cvo);
        return "index";
    }
}
