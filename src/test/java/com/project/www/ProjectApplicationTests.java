package com.project.www;

import com.project.www.domain.HelpVO;
import com.project.www.domain.ProductVO;
import com.project.www.repository.HelpMapper;
import com.project.www.repository.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private HelpMapper mapper;

	@Autowired
	private ProductMapper productMapper;

	@Test
	void contextLoads() {
		for(int i=0; i<100; i++){
			HelpVO hvo = HelpVO.builder()
					.id("작성자"+i)
					.title("제목입니다"+i)
					.content("내용입니다"+i)
					.build();

			mapper.insert(hvo);
		}
	}

	@Test
	void productInsert(){
		for(int i=0; i<100; i++){
			ProductVO productVO = ProductVO.builder()
					.name("[어보그로서리] 기장 연화리 가마솥 전복죽 230g (냉동)"+i)
					.price(10000)
					.discountPrice(10000)
					.productCategoryDetailId(3)
					.description("석류 착즙주스 1L"+i)
					.totalQty(7)
					.mainImage("/upload/2024\\06\\10/686f498b-5122-4681-81b0-75ad543281a5_main.jpg")
					.thumbImage("/upload/2024\\06\\10/686f498b-5122-4681-81b0-75ad543281a5_th_main.jpg")
					.sellerId("dbscksdnd")
					.build();

			productMapper.insert(productVO);
		}
	}

}
