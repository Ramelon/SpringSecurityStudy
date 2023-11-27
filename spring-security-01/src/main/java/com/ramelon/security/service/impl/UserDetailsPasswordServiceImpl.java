package com.ramelon.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ramelon.security.entity.User;
import com.ramelon.security.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/10 15:26
 */
@Service
@RequiredArgsConstructor
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {

    private final IUserService userService;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        // 升级密码
        LambdaUpdateWrapper<User> queryWrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getUserName, user.getUsername())
                .set(User::getPassword,newPassword);
        userService.update(queryWrapper);
        return user;
    }
}