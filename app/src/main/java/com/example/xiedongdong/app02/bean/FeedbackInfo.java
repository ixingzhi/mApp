package com.example.xiedongdong.app02.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiedongdong on 16/7/15.
 */
public class FeedbackInfo extends BmobObject {
    private String username;
    private String mobilePhoneNumber;
    private String feedbackInfo;
    private String contactInfo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getFeedbackInfo() {
        return feedbackInfo;
    }

    public void setFeedbackInfo(String feedbackInfo) {
        this.feedbackInfo = feedbackInfo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
