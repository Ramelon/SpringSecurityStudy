package org.ramelon.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.RequestContextFilter;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/22 16:55
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(author ->
                        author.requestMatchers("/resources/**", "/signup", "/about")
                                .permitAll()
                                .requestMatchers("/user/**").hasRole("ADMIN")
                                .requestMatchers("/db/**").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') and hasRole('DBA')"))
                                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                // 设置令牌的存储方式为Cookie
                .csrf(csrfConfigurer -> csrfConfigurer
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()) // 令牌处理器
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())); // 设置令牌的存储方式为Cookie
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager();
        // 系统管理员
        detailsManager.createUser(User.withUsername("sysadmin").password("{noop}123456").roles("ADMIN").build());
        // 普通用户
        detailsManager.createUser(User.withUsername("user").password("{noop}123456").roles("USER").build());
        return detailsManager;
    }

    @Bean
    @ConditionalOnMissingBean({ RequestContextListener.class, RequestContextFilter.class })
    RequestContextFilter requestContextFilter() {
        return new OrderedRequestContextFilter();
    }

}
