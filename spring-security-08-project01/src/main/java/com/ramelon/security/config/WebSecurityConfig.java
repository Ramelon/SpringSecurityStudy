package com.ramelon.security.config;

import com.ramelon.security.handle.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.ramelon.security.jwt.JwtAuthenticationConfigurer.jwtAuth;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/28 16:07
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // 认证
        http.authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated());
        // 开启表单认证
        http.formLogin(formLogin -> formLogin
                //.successHandler(new JsonAuthenticationSuccessHandler())
                .successHandler(new JwtTokenAuthenticationSuccessHandler())
                .failureHandler(new JsonAuthenticationFailureHandler()));
        // 异常
        http.exceptionHandling(exceptionHandle -> exceptionHandle
                .authenticationEntryPoint(new JsonAuthenticationEntryPoint())
                .accessDeniedHandler(new JsonAccessDeniedHandler()));
        // 注销登录
        http.logout(logout -> logout.logoutSuccessHandler(new JsonLogoutSuccessHandler())); //  自定义注销成功处理器
        http.csrf(AbstractHttpConfigurer::disable);
        http.apply(jwtAuth());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("user")
                .password("{noop}123456")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}