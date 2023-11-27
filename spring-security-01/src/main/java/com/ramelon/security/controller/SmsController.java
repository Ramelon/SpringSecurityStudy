package com.ramelon.security.controller;

import cn.hutool.core.util.RandomUtil;
import com.ramelon.security.common.R;
import com.ramelon.security.vo.SmsCaptchaVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/22 14:29
 */
@Slf4j
@RestController
@RequestMapping("sms")
@RequiredArgsConstructor
public class SmsController {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 发送验证码接口
     */
    @GetMapping("/send/Captcha")
    public R<SmsCaptchaVO> msmCaptcha(@RequestParam String phone) {
        // 1. 获取到手机号
        log.info(phone + "请求获取验证码");
        // 2. 模拟调用短信平台获取验证码，以手机号为KEY，验证码为值，存入Redis，过期时间5分钟
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(phone, code, 60*5, TimeUnit.SECONDS);
        log.info(phone + "生成验证码："+code);
        // 3. 返回过期时间，方便前端提示多少时间内有效
        SmsCaptchaVO smsCaptchaVO=new SmsCaptchaVO();
        smsCaptchaVO.setPhone(phone);
        smsCaptchaVO.setExpire(5);
        return R.response(200,"短信验证码发送成功",smsCaptchaVO);
    }

}