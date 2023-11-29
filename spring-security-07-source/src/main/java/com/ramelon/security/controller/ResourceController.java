package com.ramelon.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/28 15:21
 */
@RestController
@RequestMapping
public class ResourceController {

    @GetMapping("/resource")
    public String resource(){
        return "访问到了resource资源 ";
    }
}