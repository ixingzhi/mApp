package com.example.xiedongdong.app02.communityListView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.adapter.NewsListViewAdapter;
import com.example.xiedongdong.app02.bean.News;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xiedongdong on 16/6/29.
 */
public class NewsListView extends BaseActivity {

    public static final String KEY_TITLE="title";
    public static final String KEY_FROM="from";
    public static final String KEY_HEADIMG="headImg";
    public static final String KEY_TIME="time";
    public static final String KEY_TITLEIMG="titleImg";

    private ListView listView;
    private NewsListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_new);



        /**先从数据库中获取数据，在数组中存放数据**/
        BmobQuery<News> query=new BmobQuery<News>();
        query.setLimit(50);
        query.order("-createdAt");
        query.findObjects(this, new FindListener<News>() {
            @Override
            public void onSuccess(List<News> list) {
                Log.e("ListViewSimple", "查询数据成功");
                List<Map<String, String>> listItem = new ArrayList<Map<String, String>>();

                for (final News newsList : list) {
                    Log.e("Url", "" + newsList.getImgTitleUrl());
                    /**定义一个动态数组**/

                    final Map<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, newsList.getTitle());
                    map.put(KEY_FROM, newsList.getFrom());
                    map.put(KEY_TIME,newsList.getCreatedAt());

                    listItem.add(map);
                }

                listView= (ListView) findViewById(R.id.lv_news);
                listView.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}