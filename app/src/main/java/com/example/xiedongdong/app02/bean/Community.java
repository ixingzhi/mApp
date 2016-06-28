package com.example.xiedongdong.app02.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiedongdong on 16/6/28.
 */
public class Community extends BmobObject {
    private String title;
    private String headImgUrl;

    public Community(String title){
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
}
