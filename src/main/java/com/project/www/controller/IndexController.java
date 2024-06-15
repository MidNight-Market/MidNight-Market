package com.project.www.controller;

import com.project.www.config.oauth2.PrincipalDetails;
import com.project.www.config.oauth2.SellerPrincipalDetails;
import com.project.www.domain.ProductVO;
import com.project.www.service.BasketService;
import com.project.www.service.IndexService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class IndexController {

    private final IndexService isv;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BasketService bsv;

    @GetMapping("/")
    public String index(Model model,HttpServletResponse response, HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails principal, @AuthenticationPrincipal SellerPrincipalDetails sellerPrincipalDetails) {

        List<ProductVO> newProductList = isv.getIndexNewProductList();
        List<ProductVO> heavySoldList = isv.getIndexHeavySoldList();
        List<ProductVO> discountProductList = isv.getIndexDiscountProductList();
        Cookie del = new Cookie("url", null); // choiceCookieName(쿠키 이름)에 대한 값을 null로 지정
        del.setMaxAge(0); // 유효시간을 0으로 설정
        response.addCookie(del); // 응답 헤더에 추가해서 없어지도록 함

        if(sellerPrincipalDetails != null){
            HttpSession ses = request.getSession();
            ses.setAttribute("name", sellerPrincipalDetails.getShopName());
            ses.setAttribute("id", sellerPrincipalDetails.getUsername());
            ses.setAttribute("role", sellerPrincipalDetails.getAuthorities());
            ses.setAttribute("shopName", sellerPrincipalDetails.getShopName());
        }
        if (principal != null) {
            HttpSession ses = request.getSession();
            ses.setAttribute("name", principal.getNickName());
            ses.setAttribute("id", principal.getUsername());
            ses.setAttribute("role", principal.getAuthorities());
            ses.setAttribute("mStatus", principal.getMStatus());
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
