package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.Collect;
import com.example.xiedongdong.app02.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xiedongdong on 16/6/12.
 */
public class NewsWebViewActivity extends BaseActivity implements View.OnClickListener{
    android.webkit.WebView web_news;

    private ImageView img_back;
    private TextView tv_collect;
    private TextView tv_comments;
    private TextView tv_praise;

    private String itemId=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_webview);

        img_back=(ImageView)findViewById(R.id.img_back) ;
        img_back.setOnClickListener(this);

        tv_collect=(TextView)findViewById(R.id.tv_collect);
        tv_collect.setOnClickListener(this);

        tv_comments=(TextView)findViewById(R.id.tv_comments);
        tv_comments.setOnClickListener(this);

        tv_praise=(TextView)findViewById(R.id.tv_praise);
        tv_praise.setOnClickListener(this);

        Intent intent=getIntent();
        String url=intent.getStringExtra("Url");
        itemId=intent.getStringExtra("ItemId");

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
            case R.id.tv_collect:
                //上传至数据库中
                if(isLogin()){
                    showToast("请登录后收藏");
                }else{
                    saveCollect();
                }
                break;
            case R.id.tv_comments:
                showToast("权限不足");
                break;
            case R.id.tv_praise:
                showToast("权限不足");
                break;
            default:
                break;
        }

    }

    private void saveCollect() {
        User userInfo= BmobUser.getCurrentUser(NewsWebViewActivity.this,User.class);
        String txt_objectId=userInfo.getObjectId();

        Collect collect=new Collect();
        collect.setUserId(txt_objectId);
        collect.setItemId(itemId);

        collect.save(NewsWebViewActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                showToast("收藏成功");
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });


    }

    /**
     * 检测是否登录
     */
    public boolean isLogin() {
        User userInfo= BmobUser.getCurrentUser(NewsWebViewActivity.this,User.class);
        if(userInfo==null){
            return true;
        }
        return false;
    }
}
