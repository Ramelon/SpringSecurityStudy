package com.ramelon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ramelon.entity.User;
import com.ramelon.mapper.UserMapper;
import com.ramelon.service.IUserService;
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
