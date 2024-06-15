package com.project.www.controller;

import com.project.www.domain.*;
import com.project.www.handler.FileHandler;
import com.project.www.handler.ListPagingHandler;
import com.project.www.service.ProductService;
import com.project.www.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductController {

    private final ProductService psv;
    private final FileHandler fileHandler;
    private final ReviewService rsv;

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
    public void detail(@RequestParam("id")long id, Model model, HttpSession httpSession){

        //프린시팔로 현재 사용중인 아이디 받아야 한다
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("여기확인{}",auth.getPrincipal());
        boolean isAuthenticated;
        isAuthenticated = !auth.getPrincipal().equals("anonymousUser");
        model.addAttribute("isAuthenticated", isAuthenticated); // 모델에 추가
        String customerId = (String)httpSession.getAttribute("id");
        log.info("프린시팔 아이디 확인>>>{}",customerId);
        log.info(">>>>Product Detail Id 확인>>>>{}",id);

        ProductDTO productDTO = psv.getDetail(customerId ,id);
        log.info(">>>DTO확인>>>>{}",productDTO);

        List<ReviewVO> rvo = psv.getReview(id);
        model.addAttribute("rvo",rvo);

        model.addAttribute("productDTO",productDTO);
        model.addAttribute("customerId",customerId);
        model.addAttribute("productId",id);


    }

    @ResponseBody
    @PostMapping("/slang/{customerId}/{productId}")
    public String slangPost(@PathVariable("customerId")String customerId, @PathVariable("productId")long productId){

        log.info("찜하기 테스트 잘 연결됌{} , {}", productId,customerId);
        int isOk = psv.slangPost(new SlangVO(customerId, productId));
        return isOk > 0 ? "post_success" : "post_fail";
    }

    @ResponseBody
    @DeleteMapping("/slang/{customerId}/{productId}")
    public String slangDelete(@PathVariable("customerId")String customerId, @PathVariable("productId")long productId){

        //log.info("찜하기 테스트 잘 연결됌");
        int isOk = psv.slangDelete(new SlangVO(customerId, productId));
        return isOk > 0 ? "delete_success" : "delete_fail";
    }


    //상품 리스트 페이지
    @GetMapping("/list")
    public void list(Model model, ListPagingVO pgvo){

        int totalCount = psv.getTotalCount(pgvo);
        log.info("pgvo확인>>>{}",pgvo);

        log.info("토탈카운트 확인: >>{}",totalCount);

        List<ProductVO> list = psv.getProductList(pgvo);
        ListPagingHandler ph = new ListPagingHandler(pgvo,totalCount);

        model.addAttribute("list", list);
        model.addAttribute("ph",ph);

    }


    @GetMapping("/product/detailPopup")
    public String detailPopup() {
        return "product/detailPopup"; //
    }

    @GetMapping("/mySlang")
    public void mySlang(){}

    @ResponseBody
    @GetMapping("/getMySlangProductList/{customerId}")
    public List<ProductVO> getMySlangProduct(@PathVariable("customerId")String customerId){
        log.info(">>>>내가찜한품목 고객아이디 확인>>>{}",customerId);
        return psv.getMySlangProduct(customerId);
    }

    @ResponseBody
    @PostMapping("/isExist")
    public String isExist(@RequestBody ReviewLikeVO reviewLikeVO){
        Boolean isExist = rsv.isExist(reviewLikeVO);
        log.info("isExist값 {}",isExist);
        if(isExist){
            return "있음";
        }
        return "없음";
    }


    @ResponseBody
    @PostMapping("/reviewLikeRegister")
    public String reviewRegister(@RequestBody ReviewLikeVO reviewLikeVO){
        log.info("reviewLikeVO 확인 @register@ {}",reviewLikeVO);
        int isOK = rsv.registerLike(reviewLikeVO);
        String id = String.valueOf(reviewLikeVO.getReviewId());
        if (isOK > 0) {
            log.info("Test1");
            rsv.update(reviewLikeVO);
            int count = rsv.getCount(id);
            return "등록성공/"+count;
        } else {
            log.info("Test2");
            int count = rsv.getCount(id);
            return "등록실패/"+count;
        }
    }
    @ResponseBody
    @DeleteMapping("/reviewLikeRegister")
    public String reviewLikeRegister(@RequestBody  ReviewLikeVO reviewLikeVO) {
        log.info("reviewLikeVO 확인 @delete@ {}",reviewLikeVO);
        int isOK = rsv.deleteLike(reviewLikeVO);
        String id = String.valueOf(reviewLikeVO.getReviewId());
        if (isOK > 0) {
            log.info("Test3");
            rsv.delete(reviewLikeVO);
            int count = rsv.getCount(id);
            return "삭제성공/"+count;
        } else {
            log.info("Test4");
            int count = rsv.getCount(id);
            return "삭제실패/"+count;
        }
    }
}