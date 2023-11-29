package com.ramelon.security.resolver;

import com.ramelon.security.exption.JwtAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/29 9:26
 */
public class DefaultBearerJwtTokenResolver implements BearerTokenResolver{

    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);

    private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

    public String resolve(final HttpServletRequest request) {
        return resolveFromAuthorizationHeader(request);
    }

    public void setBearerTokenHeaderName(String bearerTokenHeaderName) {
        this.bearerTokenHeaderName = bearerTokenHeaderName;
    }

    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(this.bearerTokenHeaderName);
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            throw new JwtAuthenticationException("令牌格式错误");
        }
        return matcher.group("token");
    }

}