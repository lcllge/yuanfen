package com.lanzhou.yuanfen.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanzhou.yuanfen.config.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author lcllge
 * @since 2019-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_key", type = IdType.AUTO)
    private Long userKey;

    private String avatar;

    private String code;

    private String smsCode;

    private String emlCode;

    private String email;

    private String mobile;

    private String username;

    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 省份
     */
    private String province;

    /**
     * 省份
     */
    private String city;

    /**
     * 出身年份
     */
    private LocalDateTime birthDate;


    private LocalDateTime lastLoginTime;

}
