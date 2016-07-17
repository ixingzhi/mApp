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

    String itemId=null;
    String title=null;
    String from=null;
    String headImgUrl=null;
    String username=null;
    String shareTime=null;
    String titleImageUrl=null;
    String readCount=null;
    String url=null;


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

        /**每次打开item时，传递过来item的信息，用于收藏**/
        Intent intent=getIntent();

        itemId=intent.getStringExtra("itemId");
        title=intent.getStringExtra("title");
        from=intent.getStringExtra("from");
        headImgUrl=intent.getStringExtra("headImgUrl");
        username=intent.getStringExtra("username");
        shareTime=intent.getStringExtra("shareTime");
        titleImageUrl=intent.getStringExtra("titleImageUrl");
        readCount=intent.getStringExtra("readCount");
        url=intent.getStringExtra("url");


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
        collect.setTitle(title);
        collect.setFrom(from);
        collect.setHeadImgUrl(headImgUrl);
        collect.setUsername(username);
        collect.setShareTime(shareTime);
        collect.setTitleImageUrl(titleImageUrl);
        collect.setReadCount(readCount);
        collect.setUrl(url);

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
