package com.ramelon.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/23 16:20
 */
@Controller
public class UserController {

    @GetMapping("/user-info")
    @ResponseBody
    public Object code() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}