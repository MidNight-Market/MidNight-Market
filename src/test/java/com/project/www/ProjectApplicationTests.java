package com.project.www;

import com.project.www.domain.HelpVO;
import com.project.www.repository.HelpMapper;
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

	@Test
	void contextLoads() {
		for(int i=0; i<200; i++){
			HelpVO hvo = HelpVO.builder()
					.id("test"+i)
					.title("test"+i)
					.content("test"+i)
					.build();

			mapper.insert(hvo);
		}
	}

}
