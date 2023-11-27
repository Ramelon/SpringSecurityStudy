package com.ramelon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ramelon.entity.User;
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
