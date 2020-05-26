package com.lanzhou.yuanfen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanzhou.yuanfen.config.MyUserDetails;
import com.lanzhou.yuanfen.diary.entity.Article;
import com.lanzhou.yuanfen.diary.entity.Tag;
import com.lanzhou.yuanfen.diary.service.IArticleService;
import com.lanzhou.yuanfen.diary.service.ITagService;
import com.lanzhou.yuanfen.response.ServerResponsePage;
import com.lanzhou.yuanfen.response.ServerResponseResult;
import org.springframework.security.core.context.SecurityContextHolder;
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
     * 获取公开的文章展示文章
     *
     * @return
     */
    @PostMapping("getDisableArticle")
    public ServerResponseResult getDisableArticle() {
        MyUserDetails authentication = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userKey = authentication.getUser().getUserKey();
        List<Article> articleList = articleService.list(new QueryWrapper<Article>().eq("publicity", true).or(qw -> {
            qw.eq("create_by", userKey);
        }).orderByDesc("update_time", "create_time"));
        setPrivateOrPublic(articleList, userKey);
        return ServerResponseResult.success(articleList);
    }


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
     * 删除文章
     *
     * @param articleKey
     * @return
     */
    @PostMapping("delArticle")
    public ServerResponseResult delArticle(@RequestParam("articleKey") String articleKey) {
        boolean save = articleService.removeById(articleKey);
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


    /**
     * 设置公开的还是私有的
     *
     * @param records
     */
    private void setPrivateOrPublic(List<Article> records, Long userKey) {
        for (Article record : records) {
            if (record.getCreateBy() != null) {
                record.setPublicity(userKey.longValue() == record.getCreateBy().longValue());
            } else {
                record.setPublicity(true);
            }
        }
    }


}
