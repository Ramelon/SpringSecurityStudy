package com.ramelon.security.handle;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/9 17:30
 */
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 返回JSON
        response.setContentType("application/json;charset=utf-8");
        // 状态码 400
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("code", 401);
        result.put("data", exception.getMessage());
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}