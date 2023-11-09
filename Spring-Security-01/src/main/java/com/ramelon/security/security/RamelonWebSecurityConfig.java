package com.ramelon.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/8 18:23
 */
@Configuration
@EnableWebSecurity
public class RamelonWebSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // 配置所有的Http请求必须认证
        // 关闭 CSRF
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/login.html")
                .permitAll()
                .anyRequest()
                .authenticated())
                // 开启表单登录
                .formLogin((formLogin) -> formLogin.loginPage("/login.html")
                        // 自定义登录处理URL
                        .loginProcessingUrl("/custom/login")
                        // 自定义登录页面（注意要同步配置loginProcessingUrl）
                        .successForwardUrl("/index")
                        .failureForwardUrl("/login/failure"))
                // 开启Basic认证
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}