package com.project.www.config;

import com.project.www.config.oauth2.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService PrincipalDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/seller/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .formLogin(login -> login
                        .usernameParameter("id")
                        .passwordParameter("pw")
                        .loginPage("/loginPopup")
                        .loginProcessingUrl("/loginPopup")
                        .defaultSuccessUrl("/customer/info").permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/"))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/loginPopup")
                        .defaultSuccessUrl("/", true) // OAuth2 로그인 성공 시 리디렉션할 URL
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(PrincipalDetailsService)));

        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws  Exception{
        return  authenticationConfiguration.getAuthenticationManager();
    }


}