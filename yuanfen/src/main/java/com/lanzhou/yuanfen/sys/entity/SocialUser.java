package com.lanzhou.yuanfen.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanzhou.yuanfen.config.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lcllge
 * @since 2020-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("social_user")
public class SocialUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "social_key", type = IdType.AUTO)
    private Long socialKey;

    private String nickname;

    private String avatar;

    private String openId;

    private String province;

    private String city;

    private String year;

    private String gender;

    private Integer level;

    private Boolean vip;

    private Long userKey;

}
