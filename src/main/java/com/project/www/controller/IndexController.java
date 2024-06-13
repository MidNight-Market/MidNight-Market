package com.project.www.controller;

import com.project.www.config.oauth2.PrincipalDetails;
import com.project.www.config.oauth2.SellerPrincipalDetails;
import com.project.www.domain.ProductVO;
import com.project.www.service.BasketService;
import com.project.www.service.IndexService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class IndexController {

    private final IndexService isv;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BasketService bsv;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails principal, @AuthenticationPrincipal SellerPrincipalDetails sellerPrincipalDetails) {

        List<ProductVO> newProductList = isv.getIndexNewProductList();
        List<ProductVO> heavySoldList = isv.getIndexHeavySoldList();
        List<ProductVO> discountProductList = isv.getIndexDiscountProductList();
        if(sellerPrincipalDetails != null){
            HttpSession ses = request.getSession();
            ses.setAttribute("name", sellerPrincipalDetails.getShopName());
            ses.setAttribute("id", sellerPrincipalDetails.getUsername());
            ses.setAttribute("role", sellerPrincipalDetails.getAuthorities());
        }
        if (principal != null) {
            HttpSession ses = request.getSession();
            ses.setAttribute("name", principal.getNickName());
            ses.setAttribute("id", principal.getUsername());
            ses.setAttribute("role", principal.getAuthorities());
            ses.setAttribute("mStatus", principal.getMStatus());
            int basketTotalCount = bsv.getBasketTotalCount(principal.getUsername());
            ses.setAttribute("basketTotalCount", basketTotalCount);
            if(principal.getPassword() != null){
                if(bCryptPasswordEncoder.matches("resetPw",principal.getPassword())){
                    model.addAttribute("pwReset", 1);
                }
            }
        }
        model.addAttribute("newProductList", newProductList);
        model.addAttribute("heavySoldList", heavySoldList);
        model.addAttribute("discountProductList", discountProductList);
        return "index";
    }


}
