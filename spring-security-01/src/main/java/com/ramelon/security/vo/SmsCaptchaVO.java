package com.ramelon.security.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/22 14:28
 */
@Data
public class SmsCaptchaVO implements Serializable {
    // 手机号
    private String phone;
    // 多少分钟后过期
    private Integer expire;
}