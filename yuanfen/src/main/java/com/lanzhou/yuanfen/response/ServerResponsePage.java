package com.lanzhou.yuanfen.response;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * @version V1.0.0
 * @ClassName: {@link ServerResponsePage}
 * @Description: 后台返回分页DTO对象
 * @author: 兰州
 * @date: 2019/7/16 10:12
 * @Copyright:2019 All rights reserved.
 */
public class ServerResponsePage<T> implements Serializable {

    private static final long serialVersionUID = 8029494783945793535L;

    private long count;

    private Integer code;

    private String msg;

    private List<T> data;

    public ServerResponsePage() {
        this.msg = "success";
        this.code = 0;
    }

    public ServerResponsePage(long count, List<T> data) {
        this.code = 0;
        this.msg = "success";
        this.count = count;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        if (data == null || data.size() == 0) {
            this.code = 404;
            this.msg = "暂无数据";
        } else {
            this.count = data.size();
            this.data = data;
        }
    }

    /**
     * 设置IPage数据
     *
     * @param page
     */
    public void setIPage(IPage<T> page) {
        this.code = 200;
        this.msg = "SUCCESS";
        this.data = page.getRecords();
        this.count = page.getTotal();
    }

}
