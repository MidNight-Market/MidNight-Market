package com.project.www.controller;

import com.project.www.domain.CustomerVO;
import com.project.www.service.CustomerService;
import com.project.www.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer/*")
@Controller
public class CustomerController {
    private final CustomerService csv;
    private final MailService mailService;

    @GetMapping("/insert")
    public void insert() {}

    @PostMapping("/insert")
    public String insert(CustomerVO cvo){
        csv.insert(cvo);
        log.info("cvo값 체크 {}", cvo);
        return "index";
    }
    @GetMapping("/loginSelect")
    public void loginSelect(){}
    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail){
        log.info("이메일 들어옴 {}",mail);
        int number = mailService.sendMail(mail);
        String num = "" + number;
        return num;
    }

/*    @GetMapping("/login")
    public String login(){
        return "login";
    }*/

/*    @GetMapping("/index")
    public @ResponseBody String kakao(String code){//Data를 리턴해주는 컨트롤 함수
        // POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        // 이 때 필요한 라이브러리가 RestTemplate, 얘를 쓰면 http 요청을 편하게 할 수 있음
        RestTemplate rt = new RestTemplate();

        // HTTP POST를 요청할 때 보내는 데이터(body)를 설명해주는 헤더도 만들어 같이 보내줘야 함
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); //요청 데이터 타입

        // body 데이터를 담을 오브젝트인 MultiValueMap
        // body는 보통 key, value의 쌍으로 이루어지기 때문에 자바에서 제공해주는 MultiValueMap 타입을 사용
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "a294a7364c26a8ffe88bcdd7b325bfd4");
        params.add("redirect_uri", "http://localhost:8090/index");
        params.add("code", code);

        // 요청하기 위해 헤더(Header)와 데이터(Body)를 합침
        // kakaoTokenRequest는 데이터(Body)와 헤더(Header)를 Entity가 됨
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // POST 방식으로 Http 요청한 후 response 변수의 응답을 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
                HttpMethod.POST, // 요청할 방식
                kakaoTokenRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );


        return response.getBody();
    }*/
}
