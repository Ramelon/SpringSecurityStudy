package com.ramelon.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/28 15:22
 */
@Configuration
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                .anyRequest().authenticated()
        ).oauth2ResourceServer(resource -> resource.jwt(Customizer.withDefaults()));
        return http.build();
    }

}