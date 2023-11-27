package com.ramelon.service;

import com.ramelon.entity.UserBindThirdLogin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xzy
 * @since 2023-11-27
 */
public interface IUserBindThirdLoginService extends IService<UserBindThirdLogin> {

    /**
     * 根据用户ID查询所有第三方平台绑定关系
     *
     * @param userId 用户ID
     * @return 绑定
     */
    List<UserBindThirdLogin> selectListByUserId(Long userId);

    /**
     *  根据第三方平台类型 + 第三方用户ID 查询绑定的当前平台用户ID
     */
    UserBindThirdLogin selectOne(String type,String thirdUserId);

    /**
     *  删除绑定
     */
    void removeBind(String type,String userId);
}
