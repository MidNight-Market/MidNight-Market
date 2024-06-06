package com.project.www.controller;

import com.project.www.domain.ReviewVO;
import com.project.www.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/review/*")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService rsv;

    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody ReviewVO reviewVO){
        log.info(">>>>리뷰객체 확인>>>{}",reviewVO);
        int isOk = rsv.register(reviewVO);
        return isOk > 0 ? "register_success" : "register_fail";
    }

}
