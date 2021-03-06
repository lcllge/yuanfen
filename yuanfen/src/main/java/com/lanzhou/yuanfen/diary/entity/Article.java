package com.lanzhou.yuanfen.diary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanzhou.yuanfen.config.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author lcllge
 * @since 2020-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("article")
public class Article extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "article_key", type = IdType.AUTO)
    private Long articleKey;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签
     */
    private String tags;

    /**
     * 查看次数
     */
    private Integer views;

    /**
     * 是否公开, 0: 不公开 1: 公开
     */
    private Boolean publicity;

    @TableField(exist = false)
    private List<Tag> tagList;

}
