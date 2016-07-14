package com.example.xiedongdong.app02.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.adapter.NewsListViewAdapter;
import com.example.xiedongdong.app02.bean.Collect;
import com.example.xiedongdong.app02.bean.News;
import com.example.xiedongdong.app02.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by xiedongdong on 16/7/15.
 */
public class MyCollectActivity extends BaseActivity implements View.OnClickListener{

    private ListView lv_myCollect;
    private NewsListViewAdapter adapter;
    private TextView tv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_mycollect);

        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        lv_myCollect=(ListView)findViewById(R.id.lv_myCollect);

        User userInfo= BmobUser.getCurrentUser(MyCollectActivity.this,User.class);

        BmobQuery<Collect> query=new BmobQuery<>();
        query.addWhereEqualTo("userId",userInfo.getObjectId());
        query.findObjects(MyCollectActivity.this, new FindListener<Collect>() {
            @Override
            public void onSuccess(List<Collect> list) {
                final ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
                for(Collect itemIdList:list){
                    String itemId=itemIdList.getItemId();

                    BmobQuery<News> query=new BmobQuery<News>();
                    query.getObject(MyCollectActivity.this, itemId, new GetListener<News>() {
                        @Override
                        public void onSuccess(News news) {

                            final HashMap<String, String> map = new HashMap<String, String>();
                            map.put(NewsListViewAdapter.KEY_ID,news.getObjectId());
                            map.put(NewsListViewAdapter.KEY_TITLE, news.getTitle());
                            map.put(NewsListViewAdapter.KEY_FROM, news.getFrom());
                            map.put(NewsListViewAdapter.KEY_TIME,news.getCreatedAt());
                            map.put(NewsListViewAdapter.KEY_URL,news.getUrl());
                            map.put(NewsListViewAdapter.KEY_READCOUNT,news.getReadCount());
                            map.put(NewsListViewAdapter.KEY_TITLEIMG,news.getImgTitleUrl());

                            //加载用户名和用户头像
                            String userId=news.getId();
                            BmobQuery<User> queryUser=new BmobQuery<User>();
                            queryUser.getObject(MyCollectActivity.this, userId, new GetListener<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    map.put(NewsListViewAdapter.KEY_USERNAME,user.getUsername());
                                    map.put(NewsListViewAdapter.KEY_HEADIMG,user.getHeadImgUrl());
                                }

                                @Override
                                public void onFailure(int i, String s) {

                                }
                            });

                            listItem.add(map);
                        }





                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }

                adapter=new NewsListViewAdapter(MyCollectActivity.this,listItem);
                lv_myCollect.setAdapter(adapter);

            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
