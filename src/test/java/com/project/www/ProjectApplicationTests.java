package com.project.www;

import com.project.www.domain.HelpVO;
import com.project.www.domain.NoticeVO;
import com.project.www.domain.ProductVO;
import com.project.www.repository.HelpMapper;
import com.project.www.repository.NoticeMapper;
import com.project.www.repository.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectApplicationTests {

    @Autowired
    private HelpMapper helpmapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Test
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            HelpVO hvo = HelpVO.builder()
                    .id("작성자" + i)
                    .title("제목입니다" + i)
                    .content("내용입니다" + i)
                    .build();

            helpmapper.insert(hvo);
        }
    }

    @Test
    void productInsert() {
        for (int i = 0; i < 100; i++) {
            ProductVO productVO = ProductVO.builder()
                    .name("[어보그로서리] 기장 연화리 가마솥 전복죽 230g (냉동)" + i)
                    .price(100)
                    .discountPrice(100)
                    .productCategoryDetailId(3)
                    .description("석류 착즙주스 1L" + i)
                    .totalQty(7)
                    .mainImage("/upload/2024\\06\\10/686f498b-5122-4681-81b0-75ad543281a5_main.jpg")
                    .thumbImage("/upload/2024\\06\\10/686f498b-5122-4681-81b0-75ad543281a5_th_main.jpg")
                    .sellerId("dbscksdnd")
                    .build();

            productMapper.insert(productVO);
        }
    }

    //상품 할인율 랜덤으로 50개 넣기 메서드
    @Test
    void productRandomSaleInsertMethod() {

        Set<Integer> uniqueNumbers = new HashSet<>(); //중복안되게 set으로 생성
        Random random = new Random(); //랜덤 객체 생성

        while (uniqueNumbers.size() < 50) { //set사이즈가 50을 초과하면 종료
            int randomNumber = random.nextInt(273) + 1; // 1 273까지 난수 생성
            uniqueNumbers.add(randomNumber);
        }

        for (int number : uniqueNumbers) {

            int[] possibleNumbers = {10, 20, 30, 40, 50}; //할인율 배열로저장
            int randomIndex = random.nextInt(possibleNumbers.length); //배열 index 난수 생성
            int randomSaleNumbers = possibleNumbers[randomIndex]; //할인율 난수 생성

            ProductVO productVO = ProductVO.builder()
                    .id(number)
                    .discountRate(randomSaleNumbers)
                    .build();

			productMapper.updateProductDiscountRate(productVO);
        }
    }

}

//@Test
//void insert() {
//    for (int i = 0; i < 100; i++) {
//        NoticeVO nvo = NoticeVO.builder()
//                .title("제목입니다" + i)
//                .content("내용입니다" + i)
//                .build();
//
//        noticeMapper.register(nvo);
//    }
//}


