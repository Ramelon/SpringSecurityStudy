package com.ramelon.security.controller;

import com.ramelon.security.enums.ResultCodeEnum;
import com.ramelon.security.utils.R;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/28 16:13
 */
@RestController
@RequestMapping
public class TestController {

    @PostAuthorize("hasRole('admin')")
    @GetMapping("/test")
    public R<String> test(){
        return R.response(ResultCodeEnum.SUCCESS, null);
    }
}