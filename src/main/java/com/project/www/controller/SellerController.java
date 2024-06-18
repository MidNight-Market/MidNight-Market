package com.project.www.controller;

import com.project.www.domain.ProductDTO;
import com.project.www.domain.ProductVO;
import com.project.www.domain.SellerVO;
import com.project.www.service.SellerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/seller/*")
@Controller
public class SellerController {

    private final SellerService ssv;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final HttpSession session;

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(SellerVO sellerVO){
        log.info("셀러객체 {}", sellerVO);
        //Password 암호화
        sellerVO.setPw(bCryptPasswordEncoder.encode(sellerVO.getPw()));

        int isOk = ssv.register(sellerVO);
        if(isOk > 0){
            return "/customer/success";
        }
        return "/index";
    }

    @GetMapping("/myRegisteredProduct")
    public void productList(@RequestParam(value = "id", required = false) String paramId){
        String sessionId = session.getAttribute("id") != null ? session.getAttribute("id").toString() : null;
        String id = paramId != null ? paramId : sessionId;
        if (id != null) {
        }
    }

    //내가등록한 상품 가져오기
    @ResponseBody
    @GetMapping("/getMyRegisteredProductList/{sellerId}")
    public List<ProductVO> getMyRegisteredProductList(@PathVariable(value = "sellerId") String sellerId){
        return ssv.getMyRegisteredProduct(sellerId);
    }



    @PutMapping (value = "/productQtyUpdate", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> productQtyUpdate(@RequestBody ProductVO productVO){

        log.info("변경될 수량값 확인>>>>{}", productVO);
        int isOk = ssv.productQtyUpdate(productVO);

        return isOk > 0 ? new ResponseEntity<String>("true", HttpStatus.OK) :
            new ResponseEntity<String>("false",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @GetMapping("/checkId/{id}")
    public String checkId(@PathVariable("id") String id){
        log.info("아이디 값{}", id);
        int isOk = ssv.checkId(id);
        if(isOk > 0){
            return "0";
        }
        return "1";
    }
    @ResponseBody
    @GetMapping("/checkShopName/{shopName}")
    public String checkShopName(@PathVariable("shopName") String shopName){
        int isOk = ssv.checkShopName(shopName);
        if(isOk > 0){
            return "0";
        }
        return "1";
    }

    @ResponseBody
    @GetMapping("/getList")
    public List<SellerVO> getList(){
        return ssv.getList();
    }

}
