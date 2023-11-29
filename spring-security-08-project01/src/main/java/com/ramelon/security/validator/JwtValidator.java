package com.ramelon.security.validator;

import java.io.IOException;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/29 9:48
 */
public interface JwtValidator {

    /**
     * 令牌校验，并返回用户唯一标识
     *
     * @param token 令牌字符串
     * @return 用户唯一标识
     */
    String validate(String token) throws IOException;

}
