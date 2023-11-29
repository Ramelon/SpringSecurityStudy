package com.ramelon.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/27 15:28
 */
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class RegisteredClientController {

    private final RegisteredClientRepository registeredClientRepository;

    @GetMapping("/getOne/{clientId}")
    public Object getOne(@PathVariable("clientId") String clientId) {
        return registeredClientRepository.findByClientId(clientId);
    }

    @PostMapping("/save")
    public Object save(@RequestBody RegisteredClient client) {
        registeredClientRepository.save(client);
        return "操作成功";
    }

}