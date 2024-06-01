package com.project.www.config.oauth2;

import com.project.www.domain.CustomerVO;
import com.project.www.repository.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private CustomerMapper customerMapper;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading user 1{}", userRequest.getAccessToken().getTokenValue());
        log.info("Loading user 2{}", userRequest.getClientRegistration());
        log.info("Loading user 3{}", super.loadUser(userRequest).getAttributes());

        OAuth2User oauth2User =super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId(); //social
        String providerId = oauth2User.getAttribute("sub");
        String nickName = oauth2User.getAttribute("name");
        String id = oauth2User.getAttribute("email");
        String role = "ROLE_USER";
        CustomerVO cvo = customerMapper.findByUserName(id);
        if(cvo == null) {
            CustomerVO newCVO = new CustomerVO();
            newCVO.setId(id);
            newCVO.setNickName(nickName);
            newCVO.setProvider(provider);
            newCVO.setProviderId(providerId);
            newCVO.setRole(role);
            customerMapper.insert(newCVO);
        }
        return new PrincipalDetails(cvo, oauth2User.getAttributes());
    }
}
