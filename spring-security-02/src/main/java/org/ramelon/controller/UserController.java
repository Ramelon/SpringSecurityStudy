package org.ramelon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/22 17:00
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/save")
    String save() {
        return "save";
    }

    @GetMapping("/del")
    String del() {
        return "del";
    }

    @GetMapping("/update")
    String update() {
        return "update";
    }

    @GetMapping("/list")
    String list() {
        return "list";
    }
}