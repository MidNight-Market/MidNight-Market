package com.project.www.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String header = request.getHeader("Referer");
        log.info("체크해바{}", header);
        if (header != null && header.contains("returnUrl=")) {
            String returnUrl = header.substring(header.indexOf("returnUrl=") + "returnUrl=".length());
            log.info("주소체크해바{}", returnUrl);
            if (!returnUrl.isEmpty()) {
                log.info("여기들어옴");
                getRedirectStrategy().sendRedirect(request, response, returnUrl);
                return; // 처리 후 메서드 종료
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

