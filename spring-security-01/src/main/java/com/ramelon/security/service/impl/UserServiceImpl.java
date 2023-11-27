package com.ramelon.security.service.impl;

import com.ramelon.security.entity.User;
import com.ramelon.security.mapper.UserMapper;
import com.ramelon.security.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzy
 * @since 2023-11-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
