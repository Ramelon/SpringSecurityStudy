package com.ramelon.security.handle;

import cn.hutool.json.JSONUtil;
import com.ramelon.security.enums.ResultCodeEnum;
import com.ramelon.security.utils.R;
import com.ramelon.security.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/28 16:25
 */
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * @param request        请求
     * @param response       响应
     * @param authentication 成功认证的用户信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseUtils.buildResponse(response, R.response(ResultCodeEnum.AUTHENTICATION_SUCCESS, authentication), HttpStatus.OK);
    }
}