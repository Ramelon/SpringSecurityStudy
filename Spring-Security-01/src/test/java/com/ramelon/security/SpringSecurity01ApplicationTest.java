package com.ramelon.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ramelon.security.entity.User;
import com.ramelon.security.service.IUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringSecurity01ApplicationTest {
    @Autowired
    IUserService userService;

    @Test
    @DisplayName("根据用户名查询用户")
    void testMp() {
        User admin = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserName, "admin"));
        System.out.println(admin);
    }

    @Test
    @DisplayName("插入用户数据")
    void insertUserTest() {
        User user = new User();
        user.setUserName("admin");
        // 使用加密：{bcrypt} 中的算法，可以从配置文件读取
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        user.setLoginName("admin");
        user.setPhone("13688888888");
        userService.save(user);
    }
}