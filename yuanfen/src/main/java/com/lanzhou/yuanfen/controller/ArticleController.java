package com.lanzhou.yuanfen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanzhou.yuanfen.diary.entity.Article;
import com.lanzhou.yuanfen.diary.entity.Tag;
import com.lanzhou.yuanfen.diary.service.IArticleService;
import com.lanzhou.yuanfen.diary.service.ITagService;
import com.lanzhou.yuanfen.response.ServerResponsePage;
import com.lanzhou.yuanfen.response.ServerResponseResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2020/5/24 11:12
 * Code By LanZhou
 *
 * @author LanZhou
 */
@RestController
@RequestMapping("/article/")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    @Resource
    private ITagService tagService;

    /**
     * 添加文章/修改文章
     *
     * @param article
     * @return
     */
    @PostMapping("addOrUpdateArticle")
    public ServerResponseResult addOrUpdateArticle(Article article) {
        boolean save = articleService.saveOrUpdate(article);
        return save ? ServerResponseResult.success() : ServerResponseResult.fail();
    }


    /**
     * 添加文章
     *
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @PostMapping("pageGetArticle")
    public ServerResponsePage pageGetArticle(@RequestParam("pageSize") Integer pageSize,
                                             @RequestParam("pageIndex") Integer pageIndex) {
        ServerResponsePage<Article> responsePage = new ServerResponsePage<>();
        IPage<Article> iPage = new Page<>(pageIndex, pageSize);
        IPage<Article> page = articleService.page(iPage, new QueryWrapper<Article>().orderByDesc("update_time", "create_time"));
        setTags(page.getRecords());
        responsePage.setIPage(page);
        return responsePage;
    }

    /**
     * 配置标签文字
     *
     * @param records
     */
    private void setTags(List<Article> records) {
        for (Article record : records) {
            String tags = record.getTags();
            if (!StringUtils.isEmpty(tags)) {
                String tag = tags.substring(0, tags.length() - 1);
                ArrayList<String> tagId = new ArrayList<>(Arrays.asList(tag.split(",")));
                List<Tag> tagList = tagService.list(new QueryWrapper<Tag>().in("tag_key", tagId));
                record.setTagList(tagList);
            }
        }
    }


}
