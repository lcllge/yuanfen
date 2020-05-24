package com.lanzhou.yuanfen.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lanzhou.yuanfen.sys.entity.User;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * mybatis plus自动填充策略
 * 需要在对应字段填充对应的注解@TableField(fill = FieldFill.INSERT)
 * {@link com.baomidou.mybatisplus.annotation.TableField}
 * {@link com.baomidou.mybatisplus.annotation.FieldFill}
 * <p>
 * setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
 * metaObject为固定传入,
 * fieldName为字段名,
 * fieldVal为值,
 * 此方法会判断是否存在该字段,存在则填充设定值,该字段为实体类字段
 * <p>
 *
 * @author jun-wqh
 * @date 2018/12/1
 */
@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            MyUserDetails details = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = details.getUser();
            setFieldValByName("createBy", user.getUserKey(), metaObject);
        } catch (Exception e) {
            setFieldValByName("createBy", 0L, metaObject);
            System.out.println("无法获取用户， 添加或更新时此警告可以忽略 ！");
        } finally {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
            setFieldValByName("isDel", false, metaObject);
            setFieldValByName("isJob", false, metaObject);
            setFieldValByName("disable", false, metaObject);
            setFieldValByName("isDefault", 0, metaObject);
            setFieldValByName("enable", 1, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            MyUserDetails details = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = details.getUser();
            if (user != null) {
                setFieldValByName("updateBy", user.getUserKey(), metaObject);
            }
        } catch (Exception e) {
            setFieldValByName("updateBy", 0L, metaObject);
            System.out.println("无法获取用户， 添加或更新时此警告可以忽略 ！");
        } finally {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
        }
    }


    private User getUserByToken() {
        return new User();
    }

}