package com.ramelon.security.handle;

import com.ramelon.security.enums.ResultCodeEnum;
import com.ramelon.security.utils.R;
import com.ramelon.security.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/28 16:54
 */
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        ResponseUtils.buildResponse(response, R.response(ResultCodeEnum.ACCESS_DENIED, null), HttpStatus.FORBIDDEN);
    }

}