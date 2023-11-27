package com.ramelon.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xzy
 * @since 2023-11-08
 */
@Data
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 登录用户名称
     */
    private String loginName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 部门-组织ID
     */
    private Integer organizationId;

    /**
     * 用户状态
     */
    private Boolean state;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 注释 
     */
    private String remark;
}
