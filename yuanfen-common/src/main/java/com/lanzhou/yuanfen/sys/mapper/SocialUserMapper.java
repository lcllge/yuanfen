package com.lanzhou.yuanfen.sys.mapper;

import com.lanzhou.yuanfen.sys.entity.SocialUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lcllge
 * @since 2020-05-18
 */
@Mapper
@Repository
public interface SocialUserMapper extends BaseMapper<SocialUser> {

}
