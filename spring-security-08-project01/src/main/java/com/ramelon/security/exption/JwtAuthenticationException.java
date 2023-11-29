package com.ramelon.security.exption;

import org.springframework.security.core.AuthenticationException;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/29 9:19
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}