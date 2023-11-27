package com.ramelon.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/22 11:19
 */
public class CaptchaVerifyException extends AuthenticationException {

    public CaptchaVerifyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CaptchaVerifyException(String msg) {
        super(msg);
    }
}