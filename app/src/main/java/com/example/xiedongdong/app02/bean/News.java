package com.example.xiedongdong.app02.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiedongdong on 16/6/28.
 */
public class News extends BmobObject {
    private String id;
    private String title;
    private String url;
    private String from;
    private String messageType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
