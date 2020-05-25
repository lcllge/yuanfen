package com.lanzhou.yuanfen.controller;

import com.lanzhou.yuanfen.diary.entity.Article;
import com.lanzhou.yuanfen.diary.service.IArticleService;
import com.lanzhou.yuanfen.response.ServerResponsePage;
import com.lanzhou.yuanfen.response.ServerResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    @PostMapping("addArticle")
    public ServerResponseResult addArticle(Article article) {
        boolean save = articleService.save(article);
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
        return new ServerResponsePage();
    }


}
