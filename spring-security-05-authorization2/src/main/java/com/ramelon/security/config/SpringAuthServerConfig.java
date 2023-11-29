package com.ramelon.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.time.Duration;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/27 14:51
 */
@Configuration(proxyBeanMethods = false)
public class SpringAuthServerConfig {


    /**
     * 授权服务器 SecurityFilterChain
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());    // Enable OpenID Connect 1.0
        http
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/login"))
                )
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));
        return http.build();
    }

    private RegisteredClient createDefaultClient() {
        // 1. 创建客户端配置
        ClientSettings clientSettings = ClientSettings.builder()
                .requireAuthorizationConsent(true) // 需要授权同意，false表示自动授权（静默授权）
                .requireProofKey(false) // 是否需要需要验证密钥
                .settings(map -> { // 添加自定义配置项
                    map.put("user_info_url", "https://www.baidu.com/v1/user-info");
                })
                .build();
        // 2. 创建令牌配置
        TokenSettings tokenSettings = TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(30)) // 访问令牌有效期（默认5分钟）
                .refreshTokenTimeToLive(Duration.ofDays(1)) // 刷新令牌有效期（默认60分钟）
                .authorizationCodeTimeToLive(Duration.ofMinutes(10)) // 授权码有效期（默认5分钟）
                .build();
        // 3. 创建客户端
        return RegisteredClient.withId("123456") // ID
                .clientName("测试客户端")
                .clientId("client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8080/callback")
                .scope("user_info")
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();
    }

    /**
     * 客户端配置，基于数据库存储
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        jdbcRegisteredClientRepository.save(createDefaultClient());// 初始化时可以直接保存一个客户端到数据库中
        return jdbcRegisteredClientRepository;
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository clientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate,clientRepository);
    }
}