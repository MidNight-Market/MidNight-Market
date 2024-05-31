package com.project.www.controller;

import com.project.www.domain.ProductBuyDTO;
import com.project.www.service.ProductBuyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/productBuy/*")
public class ProductBuyController {

    private final ProductBuyService psv;

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody ProductBuyDTO productBuyDTO){
        log.info("상품판매VO 확인{}", productBuyDTO);

        int isOk = psv.register(productBuyDTO);

        return "1";
    }


}
