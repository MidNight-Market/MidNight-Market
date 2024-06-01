package com.project.www.config.oauth2;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.project.www.config.oauth2.provider.GoogleUserInfo;
import com.project.www.config.oauth2.provider.KakaoUserInfo;
import com.project.www.config.oauth2.provider.NaverUserInfo;
import com.project.www.config.oauth2.provider.OAuth2UserInfo;
import com.project.www.domain.CustomerVO;
import com.project.www.repository.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private CustomerMapper customerMapper;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}",oAuth2User.getAttributes());
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = null;
        if(provider.equals("google")){
            log.info("구글 로그인");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        } else if (provider.equals("kakao")) {
            log.info("카카오 로그인");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("naver")) {
            log.info("네이버 로그인");
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        }
        String providerId = oAuth2UserInfo.getProviderId();
        String id = oAuth2UserInfo.getEmail();
        String nickName= oAuth2UserInfo.getName();
        String role = "ROLE_USER";
        log.info("provider {}", provider);
        log.info("providerId {}", providerId);
        log.info("nickName {}", nickName);
        log.info("id {}", id);
        CustomerVO cvo = customerMapper.findByUserName(providerId);
        CustomerVO existCvo = new CustomerVO();
        if(cvo == null) {
            existCvo.setId(id);
            existCvo.setNickName(nickName);
            existCvo.setProvider(provider);
            existCvo.setProviderId(providerId);
            existCvo.setRole(role);
            customerMapper.insert(existCvo);
        }else{
            existCvo = cvo;
        }
        return new PrincipalDetails(existCvo, oAuth2User.getAttributes());
    }
}
