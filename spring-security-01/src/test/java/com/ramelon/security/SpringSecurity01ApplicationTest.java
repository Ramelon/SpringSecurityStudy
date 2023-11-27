package com.ramelon.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ramelon.security.entity.User;
import com.ramelon.security.service.IUserService;
import com.ramelon.security.util.SpringContextUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
//        User user = new User();
//        user.setUserName("admin");
//        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
//        user.setLoginName("admin");
//        user.setPhone("13688888888");
//        userService.save(user);
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User user = new User();
        user.setUserName("ty");
        // 使用加密：{bcrypt} 中的算法，可以从配置文件读取
        String encode = passwordEncoder.encode("123456");
        user.setPassword(encode);
        user.setLoginName("ty");
        user.setPhone("13688888888");
        userService.save(user);
    }

    @Test
    @DisplayName("插入用户数据")
    void insertUserDecodeTest() {
        User user = new User();
        user.setUserName("bcrypt");
        // 使用 bcrypt 加密
        user.setPassword(new BCryptPasswordEncoder().encode("{bcrypt}123456"));
        user.setLoginName("bcrypt");
        user.setPhone("13688888888");
        userService.save(user);


        User user2 = new User();
        user2.setUserName("argon2");
        // 使用 argon2 加密
        Argon2PasswordEncoder arg2SpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 65536, 10);
        user2.setPassword(arg2SpringSecurity.encode("{argon2}123456"));
        user2.setLoginName("argon2");
        user2.setPhone("13688888888");
        userService.save(user2);
    }

    @Test
    @DisplayName("插入SM4用户")
    public void insertSM4Test() {
        User user = new User();
        user.setUserName("md5");
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        PasswordEncoder passwordEncoder = (PasswordEncoder) applicationContext.getBean("passwordEncoder");
        // 使用加密：{sm4} 中的算法，可以从配置文件读取
        user.setPassword(passwordEncoder.encode("123456"));
        user.setLoginName("md5");
        user.setPhone("13688888888");
        userService.save(user);
    }

}