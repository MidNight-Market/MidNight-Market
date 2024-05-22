package com.project.www.controller;

import com.project.www.domain.CustomerVO;
import com.project.www.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/register")
    public void register() {}

    @PostMapping("/register")
    public String register(CustomerVO cvo){

        return null;
    }
}
