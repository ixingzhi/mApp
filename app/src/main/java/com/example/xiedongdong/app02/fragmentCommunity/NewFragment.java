package com.example.xiedongdong.app02.fragmentCommunity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.xiedongdong.app02.Base.BaseFragment;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.adapter.NewsListViewAdapter;
import com.example.xiedongdong.app02.bean.News;
import com.example.xiedongdong.app02.util.BitmapFileNet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xiedongdong on 16/6/30.
 */
public class NewFragment extends BaseFragment {

    public static final String KEY_TITLE="title";
    public static final String KEY_FROM="fromUrl";
    public static final String KEY_HEADIMG="headImg";
    public static final String KEY_TIME="time";
    public static final String KEY_TITLEIMG="titleImg";

    private ListView listView;
    private NewsListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_community_new,container,false);

        listView= (ListView) view.findViewById(R.id.lv_new);

        /**先从数据库中获取数据，在数组中存放数据**/

        BmobQuery<News> query=new BmobQuery<News>();
        query.setLimit(50);
        query.order("-createdAt");
        query.findObjects(getActivity(), new FindListener<News>() {
            @Override
            public void onSuccess(List<News> list) {
                ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

                for (final News newsList : list) {
                    Log.e("Url", "" + newsList.getImgTitleUrl());
                    /**定义一个动态数组**/

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, newsList.getTitle());
                    map.put(KEY_FROM, newsList.getFrom());
                    map.put(KEY_TIME,newsList.getCreatedAt());

                    listItem.add(map);
                }

                adapter=new NewsListViewAdapter(getActivity(),listItem);
                listView.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("ListViewSimple", "查询数据失败:"+s);
            }
        });

        return view;

    }


}
