package com.ramelon.security.handle;

import com.ramelon.security.enums.ResultCodeEnum;
import com.ramelon.security.utils.R;
import com.ramelon.security.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * @description: 当用户未登录时访问任意接口，由LoginUrlAuthenticationEntryPoint重定向到默认登录页，需要自定义实现AuthenticationEntryPoint接口，返回JSON数据：
 * @author: xzy
 * @time: 2023/11/28 16:02
 */
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ResponseUtils.buildResponse(response, R.response(ResultCodeEnum.NOT_AUTHENTICATION, null), HttpStatus.UNAUTHORIZED);
    }
}