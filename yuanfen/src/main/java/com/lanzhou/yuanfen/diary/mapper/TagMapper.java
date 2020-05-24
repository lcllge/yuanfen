package com.lanzhou.yuanfen.diary.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanzhou.yuanfen.diary.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lcllge
 * @since 2020-05-23
 */
@Mapper
@Repository
public interface TagMapper extends BaseMapper<Tag> {


    /**
     * 随机查询数据
     * @param labelLimit
     * @return
     */
    @Select("SELECT  *  FROM  tag ORDER BY rand() LIMIT #{labelLimit}")
    List<Tag> randomGetTags(@Param("labelLimit") Integer labelLimit);


}
