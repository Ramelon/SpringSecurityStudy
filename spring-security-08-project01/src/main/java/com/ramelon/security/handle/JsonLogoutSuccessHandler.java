package com.ramelon.security.handle;

import com.ramelon.security.enums.ResultCodeEnum;
import com.ramelon.security.utils.R;
import com.ramelon.security.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/28 16:55
 */
public class JsonLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseUtils.buildResponse(response, R.response(ResultCodeEnum.LOGOUT_SUCCESS,null), HttpStatus.OK);
    }
}