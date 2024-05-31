package com.project.www.controller;

import com.project.www.domain.BasketVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket/*")
@Slf4j
public class BasketController {

    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody BasketVO basketVO){

        

        return  "0";

    }


}
