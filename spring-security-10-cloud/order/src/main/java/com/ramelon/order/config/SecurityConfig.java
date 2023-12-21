package com.ramelon.order.config;

import com.ramelon.order.handler.JsonAccessDeniedHandler;
import com.ramelon.order.handler.JsonAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @description:
 * @author: xzy
 * @time: 2023/12/12 16:07
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // 配置所有的Http请求必须认证
        http.authorizeHttpRequests()
                .requestMatchers("/sms/send/Captcha", "sms/login").permitAll()
                .anyRequest().authenticated();
        // 关闭表单登录
        http.formLogin().disable();
        // 关闭 Basic认证
        http.httpBasic().disable();
        // 关闭 CSRF
        http.csrf().disable();
        // 异常配置
        http.exceptionHandling()
                .accessDeniedHandler(new JsonAccessDeniedHandler()) // 权限不足
                .authenticationEntryPoint(new JsonAuthenticationEntryPoint()); // 未登录认证入口
        return http.build();
    }
}
