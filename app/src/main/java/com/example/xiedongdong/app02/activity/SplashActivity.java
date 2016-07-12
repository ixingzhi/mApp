package com.example.xiedongdong.app02.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goMainActivity();
            }
        },SPLASH_DELAY_MILLIS);

    }


    //前往首页
    private void goMainActivity() {

        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        this.finish();

    }

}
