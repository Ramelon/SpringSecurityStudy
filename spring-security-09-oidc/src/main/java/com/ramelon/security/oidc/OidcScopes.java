package com.ramelon.security.oidc;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/30 16:12
 */
public class OidcScopes {
    /**
     * OIDC必传作用域，表示开启OIDC身份验证请求
     */
    public static final String OPENID = "openid";

    /**
     * 表示获取用户的基本信息：name, family_name, given_name, middle_name, nickname, preferred_username,
     * profile, picture, website, gender, birthdate, zoneinfo, locale, updated_at
     */
    public static final String PROFILE = "profile";

    /**
     * 表示获取用户的邮箱
     */
    public static final String EMAIL = "email";

    /**
     * 表示获取用户的地址
     */
    public static final String ADDRESS = "address";

    /**
     * 表示获取用户的手机号
     */
    public static final String PHONE = "phone";
}