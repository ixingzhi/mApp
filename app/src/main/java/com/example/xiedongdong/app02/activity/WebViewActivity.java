package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;

/**
 * Created by xiedongdong on 16/7/14.
 */
public class WebViewActivity extends BaseActivity implements View.OnClickListener{
    private ImageView img_back;
    private WebView web_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        img_back=(ImageView)findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        Intent intent=getIntent();
        String url=intent.getStringExtra("url");


        web_content=(WebView)findViewById(R.id.web_content);
        web_content.setWebViewClient(new WebViewClient());
        web_content.getSettings().setJavaScriptEnabled(true);
        web_content.loadUrl(url);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
