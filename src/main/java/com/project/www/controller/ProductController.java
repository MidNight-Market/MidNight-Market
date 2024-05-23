package com.project.www.controller;

import com.project.www.domain.ProductDTO;
import com.project.www.domain.ProductVO;
import com.project.www.handler.FileHandler;
import com.project.www.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductController {

    private final ProductService psv;
    private final FileHandler fileHandler;

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(ProductVO productVO,
                           @RequestParam(name = "file",required = false)MultipartFile file,
                           @RequestParam(name = "files",required = false)MultipartFile[] files){

        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductVO(fileHandler.uploadFile(file,productVO));
        productDTO.setImageList(fileHandler.uploadFiles(files,productVO));

        log.info(">>>프로덕트DTO{}",productDTO);

       int isOk = psv.insert(productDTO);




        return "/index";
    }


}
