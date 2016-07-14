package com.example.xiedongdong.app02.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiedongdong on 16/7/15.
 */
public class Collect extends BmobObject {
    private String userId;
    private String itemId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
