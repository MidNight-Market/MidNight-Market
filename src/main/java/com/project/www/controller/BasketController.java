package com.project.www.controller;

import com.project.www.domain.BasketVO;
import com.project.www.domain.ProductVO;
import com.project.www.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basket/*")
@RequiredArgsConstructor
@Slf4j
public class BasketController {

    private final BasketService bsv;

    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody BasketVO basketVO){

        //상품의 중복 검사
        BasketVO productDuplicationVerifyVO = bsv.productDuplicationVerify(basketVO);

        log.info("상품 중복 검사  : {}",productDuplicationVerifyVO);
        log.info("장바구니 객체 확인>>>>{}",basketVO);

        int isOk = 0;
        
        //장바구니에 똑같은 상품이 없을 시에
        if(productDuplicationVerifyVO == null){
            isOk = bsv.register(basketVO);
        }else{
            isOk = bsv.productDuplicationUpdate(basketVO);
        }

        return  isOk > 0 ? "success" : "fail";
    }

    @GetMapping("/myBasket")
    public String myBasket(Model model){

        //현재 로그인한 이메일을 가져와야함
        String email = "oco0217@gmail.com";
        
        List<BasketVO> myBasket = bsv.getMyBasket(email);

        log.info("내 장바구니 리스트 가져온거 : {}",myBasket);
        model.addAttribute("myBasket",myBasket);

        return "/product/myBasket";
    }


}
