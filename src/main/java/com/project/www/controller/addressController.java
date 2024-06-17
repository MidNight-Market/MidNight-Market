package com.project.www.controller;

import com.project.www.domain.AddressVO;
import com.project.www.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/address/*")
@RequiredArgsConstructor
@Slf4j
public class addressController {
    private final AddressService asv;
    private int isOK;

    @GetMapping("/register")
    public void register() {}

    @PostMapping("/register")
    public String register(AddressVO avo) {
        log.info("진입test");
        log.info("avo >>@@@ {}", avo);
        isOK = asv.register(avo);
        return "/payment/addrModifyPopup";
    }

    @ResponseBody
    @GetMapping("/list")
    public List<AddressVO> list() {
        List<AddressVO> list = asv.getList();
        log.info("list >>@@@ {}", list);
        return list;
    }

}

