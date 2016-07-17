package com.example.xiedongdong.app02.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.Version;
import com.example.xiedongdong.app02.util.CheckNetwork;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by xiedongdong on 16/5/29.
 * 欢迎页面
 */
public class SplashActivity extends BaseActivity {
    //欢迎界面睡眠时间
    private static final long SPLASH_DELAY_MILLIS=2000;
    //根目录
    private final String HEADIMG_PATH=Environment.getExternalStorageDirectory()+"/Geek/imageHead/crop_head_image.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //检查是否有网络连接
        new CheckNetwork(this).isOpenNetwork();
        //判断应用是否是首次启动
        judgeFirstStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goMainActivity();
            }
        },SPLASH_DELAY_MILLIS);

    }

    private void judgeFirstStart() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("startData", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun)
        {
            Log.e("启动次数", "该软件是第一次运行");
            editor.putBoolean("isFirstRun", false);
            editor.commit();

            //如果是第一次启动，判断下有没有以前用户遗留下的数据，有，则清空。
            clearOldData();
        } else
        {
            Log.e("启动次数", "该软件不是第一次运行");
        }
    }

    private void clearOldData() {
        File delHeadImg=new File(HEADIMG_PATH);

        if(delHeadImg.isFile()){
            delHeadImg.delete();
            Log.e("oldData","删除旧头像成功");
        }

    }


    //前往首页
    private void goMainActivity() {

        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        this.finish();

    }

}
