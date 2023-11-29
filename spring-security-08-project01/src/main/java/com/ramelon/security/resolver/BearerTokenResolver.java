package com.ramelon.security.resolver;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/29 9:26
 */
@FunctionalInterface
public interface BearerTokenResolver {

    String resolve(HttpServletRequest request);
}
