package com.lanzhou.yuanfen.diary.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanzhou.yuanfen.diary.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lcllge
 * @since 2020-05-23
 */
@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

}
