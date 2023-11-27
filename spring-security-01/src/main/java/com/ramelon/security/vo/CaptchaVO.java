package com.ramelon.security.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/22 11:00
 */
@Data
public class CaptchaVO implements Serializable {
    // 唯一ID
    private String id;
    // 验证码图片 Base64
    private String base64;
}