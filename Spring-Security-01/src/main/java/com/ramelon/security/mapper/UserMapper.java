package com.ramelon.security.mapper;

import com.ramelon.security.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xzy
 * @since 2023-11-08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
