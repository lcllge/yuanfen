package com.lanzhou.yuanfen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanzhou.yuanfen.diary.entity.Tag;
import com.lanzhou.yuanfen.diary.service.ITagService;
import com.lanzhou.yuanfen.response.ServerResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created on 2020/5/24 11:12
 * Code By LanZhou
 *
 * @author LanZhou
 */
@RestController
@RequestMapping("/tag/")
public class TagController {

    @Resource
    private ITagService tagService;

    /**
     * 添加标签
     *
     * @param name
     * @return
     */
    @PostMapping("addTag")
    public ServerResponseResult addTag(@RequestParam("name") String name) {
        Tag exist = tagService.getOne(new QueryWrapper<Tag>().eq("name", name));
        if (exist == null) {
            Tag tag = new Tag();
            tag.setName(name);
            boolean save = tagService.save(tag);
            return save ? ServerResponseResult.success(tag) : ServerResponseResult.fail("保存失败");
        } else {
            return ServerResponseResult.fail("标签已经存在!");
        }
    }

    /**
     * 换一批推荐标签
     *
     * @return
     */
    @PostMapping("switchTags")
    public ServerResponseResult switchTags(@RequestParam("labelLimit") Integer labelLimit) {
        List<Tag> tags = tagService.randomSelect(labelLimit);
        return ServerResponseResult.success(tags);
    }


}
