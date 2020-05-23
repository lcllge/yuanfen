package com.lanzhou.yuanfen.diary.service.impl;

import com.lanzhou.yuanfen.diary.entity.Article;
import com.lanzhou.yuanfen.diary.mapper.ArticleMapper;
import com.lanzhou.yuanfen.diary.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lcllge
 * @since 2020-05-23
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
