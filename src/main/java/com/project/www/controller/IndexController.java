package com.project.www.controller;

import com.project.www.domain.ProductVO;
import com.project.www.service.IndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class IndexController {

    private final IndexService isv;

    @GetMapping("/")
    public String index(Model model){
        List<ProductVO> newProductList = isv.getNewProductList();
        List<ProductVO> heavySoldList = isv.getHeavySoldList();
        List<ProductVO> discountProductList = isv.getDiscountProductList();


        model.addAttribute("newProductList", newProductList);
        model.addAttribute("heavySoldList", heavySoldList);
        model.addAttribute("discountProductList", discountProductList);

        return "index";
    }

    @GetMapping("/loginPopup")
    public void loginPopup(Model model){}

}
