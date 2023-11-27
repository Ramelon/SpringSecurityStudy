package org.ramelon.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/23 11:16
 */
@Slf4j
@Controller
public class ThymeleafController {

    @GetMapping("/index")
    public String index() {
        return "transfer";
    }

    @ResponseBody
    @PostMapping("/transfer")
    public String permit(String name, HttpServletRequest request) {
        // 1. 模拟存储中查找令牌
        String saveToken = "f1929cc0-a11a-4fa6-876b-c0fc5a7677d3";
        // 2. 解析请求消息头中的令牌
        String csrfToken = request.getHeader("X-XSRF-TOKEN");
        // 3. 没有令牌、令牌不匹配时访问被拒绝
        if (StrUtil.isEmpty(csrfToken) || !saveToken.equals(csrfToken)) {
            throw new AccessDeniedException("访问被拒绝");
        }
        log.info("给" + name + "转账[1000000]越南盾");
        return "转账成功";
    }

}