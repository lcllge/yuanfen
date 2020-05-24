package com.lanzhou.yuanfen.diary.service.impl;

import com.lanzhou.yuanfen.diary.entity.Tag;
import com.lanzhou.yuanfen.diary.mapper.TagMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanzhou.yuanfen.diary.service.ITagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lcllge
 * @since 2020-05-23
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<Tag> randomSelect(Integer labelLimit) {
        return tagMapper.randomGetTags(labelLimit);
    }
}
