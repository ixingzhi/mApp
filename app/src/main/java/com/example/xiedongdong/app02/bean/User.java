package com.example.xiedongdong.app02.bean;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by xiedongdong on 16/6/1.
 */
public class User extends BmobUser {
    private String headImgUrl;
    private String headImgFileUrl;
    private String sex;
    private String location;
    private String autograph;

    public String getHeadImgFileUrl() {
        return headImgFileUrl;
    }

    public void setHeadImgFileUrl(String headImgFileUrl) {
        this.headImgFileUrl = headImgFileUrl;
    }
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }
}
