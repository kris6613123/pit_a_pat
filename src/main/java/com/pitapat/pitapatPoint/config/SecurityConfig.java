package com.pitapat.pitapatPoint.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import static org.bouncycastle.asn1.crmf.SinglePubInfo.web;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity( prePostEnabled = true, securedEnabled = true )
public class SecurityConfig {

//    private final AuthenticationFailureHandler customLoginFailHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll() // /login 경로는 모든 사용자에게 허용
                .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
                .and()
                .anonymous().principal("anonymousUser").authorities("ANONYMOUS")
                .and()
                .formLogin()
                .usernameParameter("id")
                .passwordParameter("pwd")
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
//                .failureHandler(customLoginFailHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and().build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations()
                );
    }
}
