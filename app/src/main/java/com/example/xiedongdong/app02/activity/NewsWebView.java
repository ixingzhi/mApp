package com.example.xiedongdong.app02.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.xiedongdong.app02.R;

/**
 * Created by xiedongdong on 16/6/12.
 */
public class NewsWebView extends Activity implements View.OnClickListener{
    android.webkit.WebView web_news;

    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_webview);

        img_back=(ImageView)findViewById(R.id.img_back) ;
        img_back.setOnClickListener(this);

        Intent intent=getIntent();
        String url=intent.getStringExtra("Url");

        web_news=(android.webkit.WebView)findViewById(R.id.web_news);
        web_news.setWebViewClient(new WebViewClient());
        web_news.getSettings().setJavaScriptEnabled(true);
        web_news.loadUrl(url);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }

    }
}
