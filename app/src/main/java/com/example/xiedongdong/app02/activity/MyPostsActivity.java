package com.example.xiedongdong.app02.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

/**
 * Created by xiedongdong on 16/7/1.
 */
public class MyPostsActivity extends BaseActivity {
    private ListView lv_myPosts;
    private NewsListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_myposts);

        lv_myPosts= (ListView) findViewById(R.id.lv_myPosts);

        /**先从数据库中获取数据，在数组中存放数据**/
        User user= BmobUser.getCurrentUser(MyPostsActivity.this,User.class);

        BmobQuery<News> query=new BmobQuery<News>();
        query.setLimit(50);
        query.order("-createdAt");
        query.addWhereEqualTo("id",user.getObjectId());  //获得当前用户id与News数据库中的Id相同
        query.findObjects(MyPostsActivity.this, new FindListener<News>() {
            @Override
            public void onSuccess(List<News> list) {
                ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

                for (final News newsList : list) {
                    /**定义一个动态数组**/

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(NewsListViewAdapter.KEY_TITLE, newsList.getTitle());
                    map.put(NewsListViewAdapter.KEY_FROM, newsList.getFrom());
                    map.put(NewsListViewAdapter.KEY_TIME,newsList.getCreatedAt());

                    listItem.add(map);
                }

                adapter=new NewsListViewAdapter(MyPostsActivity.this,listItem);
                lv_myPosts.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("NewFragment", "查询数据失败:"+s);
            }
        });

    }
}
