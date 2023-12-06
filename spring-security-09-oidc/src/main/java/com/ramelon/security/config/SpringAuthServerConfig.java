package com.ramelon.security.config;

import com.ramelon.security.service.OidcUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.time.Duration;
import java.util.Set;
import java.util.function.Function;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/30 16:09
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SpringAuthServerConfig {

    private final OidcUserInfoService oidcUserInfoService;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // http://localhost:8080/oauth2/authorize?client_id=client&scope=openid email&state=123456&response_type=code&redirect_uri=http://127.0.0.1:8080/callback
        // 授权服务器配置类
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        authorizationServerConfigurer.authorizationEndpoint(endpoint -> endpoint.consentPage("/oauth2/consent"));
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        // 创建用户信息映射器
        Function<OidcUserInfoAuthenticationContext, OidcUserInfo> userInfoMapper = (context) -> {
            OidcUserInfoAuthenticationToken authentication = context.getAuthentication();
            JwtAuthenticationToken principal = (JwtAuthenticationToken) authentication.getPrincipal();
            Set<String> scopes = context.getAuthorization().getAuthorizedScopes();
            return oidcUserInfoService.loadUser(principal.getName(), scopes);
        };
        // 配置用户信息查询
        authorizationServerConfigurer.oidc((oidc) -> oidc
                .userInfoEndpoint((userInfo) -> userInfo.userInfoMapper(userInfoMapper)));
        // 安全配置
        http.securityMatcher(endpointsMatcher)
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(resource -> resource.jwt(Customizer.withDefaults()))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
                .apply(authorizationServerConfigurer);
        return http.build();
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

    private RegisteredClient createDefaultClient() {
        // 1. 创建客户端配置
        ClientSettings clientSettings = ClientSettings.builder()
                .requireAuthorizationConsent(true) // 需要授权同意，false表示自动授权（静默授权）
                .requireProofKey(false) // 是否需要需要验证密钥
                .build();
        // 2. 创建令牌配置
        TokenSettings tokenSettings = TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(30)) // 访问令牌有效期（默认5分钟）
                .refreshTokenTimeToLive(Duration.ofDays(1)) // 刷新令牌有效期（默认60分钟）
                .authorizationCodeTimeToLive(Duration.ofMinutes(10)) // 授权码有效期（默认5分钟）
                .build();
        // 3. 创建客户端
        return RegisteredClient.withId("654321") // ID
                .clientName("测试客户端")
                .clientId("client2")
                .clientSecret("{noop}secret2")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8091/callback")
                .scope("user_info")
                .scope(OidcScopes.OPENID) // OIDC
                .scope(OidcScopes.PROFILE)
                .scope(OidcScopes.EMAIL)
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();
    }

    /**
     * OIDC授权时，根据授权范围查询对应的OidcUserInfo信息，并封装到ID令牌的声明中：
     * @param oidcUserInfoService
     * @return
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(OidcUserInfoService oidcUserInfoService) {
        return (context) -> {
            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                OidcUserInfo userInfo = oidcUserInfoService.loadUser(
                        context.getPrincipal().getName(),context.getAuthorizedScopes());
                context.getClaims().claims(claims ->
                        claims.putAll(userInfo.getClaims()));
            }
        };
    }

}