package com.ramelon.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * @description:
 * @author: xzy
 * @time: 2023/12/12 16:00
 */
@Configuration
public class SpringSessionConfig {

    @Bean
    public HttpSessionIdResolver sessionIdResolver() {
        // X-Auth-Token
        HeaderHttpSessionIdResolver xAuthTokenResolver = HeaderHttpSessionIdResolver.xAuthToken();
        // Authentication-Info
        HeaderHttpSessionIdResolver authenticationInfoResolver = HeaderHttpSessionIdResolver.authenticationInfo();
        // Authorization
        HeaderHttpSessionIdResolver customResolver = new HeaderHttpSessionIdResolver("Authorization");
        return xAuthTokenResolver;
    }

}
