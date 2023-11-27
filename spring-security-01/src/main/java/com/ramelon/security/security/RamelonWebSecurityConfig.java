package com.ramelon.security.security;

import cn.hutool.json.JSONUtil;
import com.ramelon.security.filter.CaptchaVerifyFilter;
import com.ramelon.security.handle.JsonAuthenticationFailureHandler;
import com.ramelon.security.handle.JsonAuthenticationSuccessHandler;
import com.ramelon.security.handle.JsonLogoutSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.ramelon.security.sms.SmsLoginConfigurer.smsLogin;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/8 18:23
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class RamelonWebSecurityConfig {

    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    private final StringRedisTemplate stringRedisTemplate;

    // 基于数据库存储，并且每次自动登录后修改Cookie
    @Bean
    public RememberMeServices rememberMeServices() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl(); // 数据库存储令牌
        tokenRepository.setDataSource(dataSource);
        // create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)
        // tokenRepository.setCreateTableOnStartup(true); //启动时自动创建表结构
        // return tokenRepository;
        return new PersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(), userDetailsService, tokenRepository);
    }

    /*@Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // 配置所有的Http请求必须认证
        // 关闭 CSRF
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/generateCaptcha", "/sms/send/Captcha")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                // 开启表单登录
                .formLogin((formLogin) -> formLogin
                                .successHandler(new JsonAuthenticationSuccessHandler())
                                .failureHandler(new JsonAuthenticationFailureHandler())
                                //.loginPage("/login.html")
                                // 自定义登录处理URL
                                //.loginProcessingUrl("/custom/login")
                        // 自定义登录页面（注意要同步配置loginProcessingUrl）
                        //.successForwardUrl("/index")
                        //.failureForwardUrl("/login/failure")
                )
                // 开启Basic认证
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .logout((logout) -> logout.logoutSuccessHandler(new JsonLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true))
                // 用户最大会话数为 1，后面的登陆就会自动踢掉前面的登陆
                .sessionManagement(session -> session.sessionFixation(
                                        SessionManagementConfigurer.SessionFixationConfigurer::changeSessionId
                                )
                                .maximumSessions(1)
                                // 当前已登录时，阻止其他登录
                                //.maxSessionsPreventsLogin(true)
                                .expiredSessionStrategy(event -> {
                                    HttpServletResponse response = event.getResponse();
                                    response.setContentType("application/json;charset=utf-8"); // 返回JSON
                                    response.setStatus(HttpStatus.BAD_REQUEST.value());  // 状态码
                                    Map<String, Object> result = new HashMap<>(); // 返回结果
                                    result.put("msg", "当前会话已失效");
                                    result.put("code", 401);
                                    response.getWriter().write(JSONUtil.toJsonStr(result));
                                })
                )
                // 验证码
                //.addFilterBefore(new CaptchaVerifyFilter(new JsonAuthenticationFailureHandler(), stringRedisTemplate), UsernamePasswordAuthenticationFilter.class)
                .rememberMe(remember -> remember.rememberMeServices(rememberMeServices()));
        return http.build();
    }*/

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(author ->
                author.requestMatchers("/sms/send/Captcha")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(form ->
                        form.successHandler(new JsonAuthenticationSuccessHandler())
                                .failureHandler(new JsonAuthenticationFailureHandler()))
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        http.apply(smsLogin()
                .successHandler(new JsonAuthenticationSuccessHandler())
                .failureHandler(new JsonAuthenticationFailureHandler())
                .phoneParameter("phone")
                .smsCodeParameter("smsCode"));
        return http.build();
    }

//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    Sm4PasswordEncoder passwordEncoder() {
//        return new Sm4PasswordEncoder("1234567812345678");
//    }

    @Bean
    @SuppressWarnings("deprecation")
    PasswordEncoder passwordEncoder() {
        // 当前需升级到哪种算法 （实际开发需要在配置文件中读取）
        String encodingId = "bcrypt";
        // 添加算法支持
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("ldap", new LdapShaPasswordEncoder());
        encoders.put("MD4", new Md4PasswordEncoder());
        encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_5());
        encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v4_1());
        encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_2());
        encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        // 添加自定义密码编码器
        encoders.put("SM4", new Sm4PasswordEncoder("1234567812345678"));
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}