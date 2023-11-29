package com.ramelon.security.validator;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.ramelon.security.constants.AuthenticationConstants;
import com.ramelon.security.exption.JwtAuthenticationException;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/29 9:48
 */
public class HutoolJwtValidator implements JwtValidator{
    @Override
    public String validate(String token) {
        // 验签
        boolean verify = JWTUtil.verify(token, AuthenticationConstants.JWT_KEY.getBytes());
        if (!verify) {
            throw new JwtAuthenticationException("非法令牌");
        }
        // 过期
        final JWT jwt = JWTUtil.parseToken(token);
        String expireTimeString = String.valueOf(jwt.getPayload("expire_time"));
        long expireTime = Long.parseLong(expireTimeString);
        if (System.currentTimeMillis() > expireTime) {
            throw new JwtAuthenticationException("令牌已失效");
        }
        // 返回
        return (String) jwt.getPayload("username");
    }
}