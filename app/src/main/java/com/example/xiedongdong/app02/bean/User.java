package com.example.xiedongdong.app02.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by xiedongdong on 16/6/1.
 */
public class User extends BmobUser {
    private String sex;
    private String location;
    private String autograph;

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
