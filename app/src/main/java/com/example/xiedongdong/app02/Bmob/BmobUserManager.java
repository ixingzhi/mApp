package com.example.xiedongdong.app02.Bmob;

import android.content.Context;

import com.example.xiedongdong.app02.po.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by xiedongdong on 16/6/15.
 */
public class BmobUserManager {
    public static BmobUserManager instance;
    Context context;

    public static BmobUserManager getInstance(Context context){
        if(instance==null){
            synchronized (BmobUserManager.class) {
                if(instance==null){
                    instance=new BmobUserManager();
                }
                instance.init(context);
            }
        }
        return instance;
    }
    private void init(Context c){
        this.context=c;
    }

    /**
     * 获取当前用户 getCurrentUser
     */
    public User getCurrentUser(){
        return BmobUser.getCurrentUser(context,User.class);
    }

    /**
     * 获取当前登录用户 用户名 getCurrentUsername
     */
    public String getCurrentUsername(){
        return getCurrentUser().getUsername();
    }

    /**
     * 退出登录
     */
    public void logout(){
        BmobUser.logOut(context);
    }
}
