package com.ramelon.security.utils;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/28 15:46
 */
public class ResponseUtils {
    public static void buildResponse(HttpServletResponse response, Object result, HttpStatus httpStatus) throws IOException {
        response.setContentType("application/json;charset=utf-8"); // 返回JSON
        response.setStatus(httpStatus.value());  // 状态码
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}