package com.project.www.controller;

import com.project.www.domain.BasketVO;
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

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductController {

    private final ProductService psv;
    private final FileHandler fileHandler;
    
    //상품 등록 페이지 이동
    @GetMapping("/register")
    public void register(Model model){

        ProductDTO productDTO = psv.getProductCategoryList();

        model.addAttribute("productDTO",productDTO);


    }

    //상품등록
    @PostMapping("/register")
    public String register(ProductVO productVO,
                           @RequestParam(name = "file",required = false)MultipartFile file,
                           @RequestParam(name = "files",required = false)MultipartFile[] files){

        //처음 가격 입력시 할인율이 0이기 떄문에 할인가격 == 처음가격
        productVO.setDiscountPrice(productVO.getPrice());

        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductVO(fileHandler.uploadFile(file,productVO));
        productDTO.setImageList(fileHandler.uploadFiles(files,productVO));



        log.info(">>>프로덕트DTO{}",productDTO);

       int isOk = psv.insert(productDTO);
       

        return "redirect:/";
    }

    //상품 상세페이지 
    @GetMapping("/detail")
    public void detail(@RequestParam("id")long id, Model model){

        //log.info(">>>>Product Detail Id 확이>>>>{}",id);
        ProductDTO productDTO = psv.getDetail(id);

        log.info(">>>DTO확인>>>>{}",productDTO);

        model.addAttribute("productDTO",productDTO);

    }


    //상품 리스트 페이지
    @GetMapping("/list")
    public void list(Model model){
        
    }

    
    
}
