package com.ramelon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ramelon.entity.UserBindThirdLogin;
import com.ramelon.mapper.UserBindThirdLoginMapper;
import com.ramelon.service.IUserBindThirdLoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzy
 * @since 2023-11-27
 */
@Service
public class UserBindThirdLoginServiceImpl extends ServiceImpl<UserBindThirdLoginMapper, UserBindThirdLogin> implements IUserBindThirdLoginService {

    @Override
    public List<UserBindThirdLogin> selectListByUserId(Long userId) {
        LambdaQueryWrapper<UserBindThirdLogin> query = Wrappers.lambdaQuery();
        query.eq(UserBindThirdLogin::getUserId,userId);
        return list(query);
    }

    @Override
    public UserBindThirdLogin selectOne(String type, String thirdUserId) {
        LambdaQueryWrapper<UserBindThirdLogin> query = Wrappers.lambdaQuery();
        query.eq(UserBindThirdLogin::getType,type);
        query.eq(UserBindThirdLogin::getUuid,thirdUserId);
        return getOne(query);
    }

    @Override
    @Transactional
    public void removeBind(String type, String userId) {
        LambdaQueryWrapper<UserBindThirdLogin> delete = Wrappers.lambdaQuery();
        delete.eq(UserBindThirdLogin::getType,type);
        delete.eq(UserBindThirdLogin::getUuid,userId);
        remove(delete);
    }
}
