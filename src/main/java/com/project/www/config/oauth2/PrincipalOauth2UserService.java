package com.project.www.config.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading user 1{}", userRequest.getAccessToken().getTokenValue());
        log.info("Loading user 2{}", userRequest.getClientRegistration());
        log.info("Loading user 3{}", super.loadUser(userRequest).getAttributes());
        return super.loadUser(userRequest);
    }
}
