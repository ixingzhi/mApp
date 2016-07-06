package com.example.xiedongdong.app02.fragmentCommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.xiedongdong.app02.Base.BaseFragment;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.activity.NewsWebView;
import com.example.xiedongdong.app02.adapter.NewsListViewAdapter;
import com.example.xiedongdong.app02.bean.News;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xiedongdong on 16/6/30.
 */
public class WalkerFragment extends BaseFragment {

    private ListView lv_walker;
    private NewsListViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_community_walker,container,false);

        lv_walker= (ListView) view.findViewById(R.id.lv_walker);

        /**先从数据库中获取数据，在数组中存放数据**/

        BmobQuery<News> query=new BmobQuery<News>();
        query.setLimit(50);
        query.order("-createdAt");
        query.addWhereEqualTo("messageType","玩客");
        query.findObjects(getActivity(), new FindListener<News>() {
            @Override
            public void onSuccess(List<News> list) {
                final ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

                for (final News newsList : list) {
                    /**定义一个动态数组**/

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(NewsListViewAdapter.KEY_TITLE, newsList.getTitle());
                    map.put(NewsListViewAdapter.KEY_FROM, newsList.getFrom());
                    map.put(NewsListViewAdapter.KEY_HEADIMG,newsList.getHeadImgUrl());
                    map.put(NewsListViewAdapter.KEY_USERNAME,newsList.getUsername());
                    map.put(NewsListViewAdapter.KEY_TIME,newsList.getCreatedAt());
                    map.put(NewsListViewAdapter.KEY_URL,newsList.getUrl());
                    map.put(NewsListViewAdapter.KEY_TITLEIMG,newsList.getImgTitleUrl());

                    listItem.add(map);
                }

                adapter=new NewsListViewAdapter(getActivity(),listItem);
                lv_walker.setAdapter(adapter);

                lv_walker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {

                        String url=listItem.get(postion).get(NewsListViewAdapter.KEY_URL);

                        Intent intent=new Intent();
                        intent.putExtra("Url",url);
                        intent.setClass(getActivity(),NewsWebView.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                Log.e("WalkerFragment", "查询数据失败:"+s);
            }
        });


        return view;
    }
}
