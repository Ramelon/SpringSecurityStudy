package com.ramelon.security.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.ramelon.security.common.R;
import com.ramelon.security.vo.CaptchaVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/22 11:01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final StringRedisTemplate stringRedisTemplate;

    @GetMapping("/generateCaptcha")
    @ResponseBody
    public R<CaptchaVO> getCaptcha(HttpServletResponse response) {
        // 定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        String code = lineCaptcha.getCode();// 验证码
        log.info("生成验证码：" + lineCaptcha.getCode());
        String imageBase64 = lineCaptcha.getImageBase64Data(); // 验证码图片BASE64
        // 创建验证码对象
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setId(UUID.randomUUID().toString());
        captchaVO.setBase64(imageBase64);
        // 缓存验证码，10分钟有效
        stringRedisTemplate.opsForValue().set(captchaVO.getId(), code, 10, TimeUnit.MINUTES);
        return R.response(200, "生成验证码成功", captchaVO);
    }

}