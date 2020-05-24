package com.lanzhou.yuanfen.diary.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("article_tag")
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer articleKey;

    private Integer tagKey;


}
