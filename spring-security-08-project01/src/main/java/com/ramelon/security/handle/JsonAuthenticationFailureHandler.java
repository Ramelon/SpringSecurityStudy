package com.ramelon.security.handle;

import com.ramelon.security.utils.R;
import com.ramelon.security.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/28 16:53
 */
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseUtils.buildResponse(response, R.response(HttpStatus.UNAUTHORIZED.value(), exception.getLocalizedMessage(), null), HttpStatus.UNAUTHORIZED);
    }
}