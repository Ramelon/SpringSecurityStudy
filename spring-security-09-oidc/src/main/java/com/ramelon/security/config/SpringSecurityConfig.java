package com.ramelon.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/27 15:21
 */
@Configuration(proxyBeanMethods = false)
public class SpringSecurityConfig {

    /**
     * Spring Security SecurityFilterChain 认证配置
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login","/bootstrap.min.css","/login.css","/error","/callback")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(formLogin -> formLogin.loginPage("/login").loginProcessingUrl("/login"))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * 内存存储用户
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails userDetails = User.withUsername("user")
                .password("{noop}123456")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}