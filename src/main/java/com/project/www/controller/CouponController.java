package com.project.www.controller;

import com.project.www.domain.MemberCouponVO;
import com.project.www.service.CouponService;
import com.project.www.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/coupon/*")
@RequiredArgsConstructor
@Slf4j
public class CouponController {
    private final CouponService csv;
    private final MemberCouponService mcsv;

    @ResponseBody
    @GetMapping("/add/{customerId}/{couponId}")
    public String addCoupon(@PathVariable("customerId") String customerId, @PathVariable("couponId") String couponId) {
        int isOk = mcsv.addCoupon(customerId, couponId);
        return isOk > 0 ? "1" : "0";
    }


    @ResponseBody
    @GetMapping("/search/{couponCode}")
    public long searchCoupon(@PathVariable("couponCode")String couponCode) {
        log.info("쿠폰이름 {}", couponCode);
        long couponId = csv.search(couponCode);
        log.info("있나{}", couponCode);
        if(couponId > 0){
            return couponId;
        }else{
            return -1;
        }
    }
}

