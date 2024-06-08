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
					.name("물건"+i)
					.price(10000)
					.discountPrice(10000)
					.productCategoryDetailId(3)
					.description("설명"+i)
					.totalQty(7)
					.mainImage("/upload/2024\\06\\08/71783d88-8214-4519-8930-961656041572_main.jpg")
					.thumbImage("/upload/2024\\06\\08/71783d88-8214-4519-8930-961656041572_th_main.jpg")
					.sellerId("dbscksdnd")
					.build();

			productMapper.insert(productVO);
		}
	}

}
