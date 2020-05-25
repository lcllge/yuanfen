package com.lanzhou.yuanfen.diary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lanzhou.yuanfen.diary.entity.Tag;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lcllge
 * @since 2020-05-23
 */
public interface ITagService extends IService<Tag> {

    /**
     * 随机查询
     * @param labelLimit
     * @return
     */
    List<Tag> randomSelect(Integer labelLimit);
}
