package com.project.www.controller;

import com.project.www.domain.ProductCategoryDTO;
import com.project.www.domain.ProductDTO;
import com.project.www.domain.ProductVO;
import com.project.www.handler.FileHandler;
import com.project.www.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public void register(Model model){

        ProductCategoryDTO pcDTO = psv.getProductCategoryDTO();

        log.info("카테고리 DTO 리스트 출력{} ",pcDTO);

        model.addAttribute("pcDTO",pcDTO);


    }

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

    @GetMapping("/detail")
    public void detail(@RequestParam("id")long id, Model model){

        //log.info(">>>>Product Detail Id 확이>>>>{}",id);
        ProductDTO productDTO = psv.getDetail(id);

        log.info(">>>DTO확인>>>>{}",productDTO);

        model.addAttribute("productDTO",productDTO);

    }

    //Editer Test Code
    @GetMapping("/edit")
    public void edit(){}



}
