package com.ramelon.security.service;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Set;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/30 16:12
 */
public interface OidcUserInfoService {

    OidcUserInfo loadUser(String username, Set<String> scopes);
}