package com.ramelon.security.controller;

import jakarta.annotation.Resource;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: xzy
 * @time: 2023/12/1 15:54
 */
@Controller
@RequestMapping
@SessionAttributes("authorizationRequest")
public class ThymeleafPageController {

    @Resource
    public RegisteredClientRepository registeredClientRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/oauth2/consent", method = RequestMethod.GET)
    public String consent(@RequestParam String scope,
                          @RequestParam String client_id,
                          @RequestParam String state,
                          Model model) {
        // http://localhost:8080/oauth2/authorize?client_id=client&scope=user_info&state=123456&response_type=code&redirect_uri=http://127.0.0.1:8080/callback
        // 1. 根据客户端ID查询客户端信息
        RegisteredClient client = registeredClientRepository.findByClientId(client_id);
        // 2. Scope 范围值
        String[] scopes = scope.trim().split(" ");
        for (String str : scopes) {
            // Scope 对应的中文描述，实际开发需要从数据库中查询，这里直接写死
            // 查询.....
        }
        Map<String, String> scopeMap = new HashMap<>();
        scopeMap.put("user_info", "用户基础信息");
        //scopeMap.put("email", "邮箱");
        //  scopeMap.put("openid", "openid");
        // 3. 设置属性，页面获取
        model.addAttribute("clientName", client.getClientName());// 客户端名称
        model.addAttribute("scopeMap", scopeMap);
        model.addAttribute("client_id", client_id);
        model.addAttribute("state", state);
        return "consent";
    }
}
