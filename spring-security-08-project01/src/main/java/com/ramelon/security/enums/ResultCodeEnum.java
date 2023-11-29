package com.ramelon.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/28 15:45
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultCodeEnum {

    FAIL(-1, "操作失败"),
    SUCCESS(200, "操作成功"),
    LOGOUT_SUCCESS(200, "注销成功"),
    AUTHENTICATION_SUCCESS(200, "登录成功"),
    NOT_AUTHENTICATION(401, "未认证请求"), JWT_TOKEN_FORMAT_ERROR(401, "令牌格式错误"),
    JWT_TOKEN_VERIFY_FAIL(401, "非法令牌"),
    JWT_TOKEN_EXPIRED(401, "令牌已失效"),
    ACCESS_DENIED(403, "权限不足，访问被拒绝");

    private int code;

    private String msg;

}