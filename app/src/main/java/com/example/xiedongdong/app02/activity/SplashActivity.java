package com.example.xiedongdong.app02.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.xiedongdong.app02.R;

/**
 * Created by xiedongdong on 16/5/29.
 */
public class SplashActivity extends Activity {
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

    private void goMainActivity() {

        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        this.finish();

    }
}
