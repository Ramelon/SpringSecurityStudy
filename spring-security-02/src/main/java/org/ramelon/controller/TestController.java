package org.ramelon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/22 16:43
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public Object test() {
        return "Hello Security";
    }
}