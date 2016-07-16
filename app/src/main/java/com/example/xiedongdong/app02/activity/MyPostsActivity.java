package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.adapter.NewsListViewAdapter;
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
 * Created by xiedongdong on 16/7/1.
 */
public class MyPostsActivity extends BaseActivity implements View.OnClickListener{
    private ListView lv_myPosts;
    private NewsListViewAdapter adapter;

    private TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_myposts);

        lv_myPosts= (ListView) findViewById(R.id.lv_myPosts);
        tv_back= (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        /**先从数据库中获取数据，在数组中存放数据**/
        final User user= BmobUser.getCurrentUser(MyPostsActivity.this,User.class);

        BmobQuery<News> query=new BmobQuery<News>();
        query.setLimit(50);
        query.order("-createdAt");
        query.addWhereEqualTo("id",user.getObjectId());  //获得当前用户id与News数据库中的Id相同
        query.findObjects(MyPostsActivity.this, new FindListener<News>() {
            @Override
            public void onSuccess(List<News> list) {
                final ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

                for (final News newsList : list) {
                    /**定义一个动态数组**/

                    final HashMap<String, String> map = new HashMap<String, String>();
                    map.put(NewsListViewAdapter.KEY_TITLE, newsList.getTitle());
                    map.put(NewsListViewAdapter.KEY_FROM, newsList.getFrom());
                    map.put(NewsListViewAdapter.KEY_HEADIMG,newsList.getHeadImgUrl());
                    map.put(NewsListViewAdapter.KEY_USERNAME,newsList.getUsername());
                    map.put(NewsListViewAdapter.KEY_TIME,newsList.getCreatedAt());
                    map.put(NewsListViewAdapter.KEY_URL,newsList.getUrl());
                    map.put(NewsListViewAdapter.KEY_READCOUNT,newsList.getReadCount());
                    map.put(NewsListViewAdapter.KEY_TITLEIMG,newsList.getImgTitleUrl());
                    //获取用户名
                    map.put(NewsListViewAdapter.KEY_USERNAME,"我");

                    listItem.add(map);
                }

                adapter=new NewsListViewAdapter(MyPostsActivity.this,listItem);
                lv_myPosts.setAdapter(adapter);

                //listview点击事件
                lv_myPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {

                        String url=listItem.get(postion).get(NewsListViewAdapter.KEY_URL);
                        Log.e("MyPostsActivity",url);
                        Intent intent=new Intent();
                        intent.putExtra("Url",url);
                        intent.setClass(MyPostsActivity.this,NewsWebViewActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                Log.e("NewFragment", "查询数据失败:"+s);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }

    }
}
