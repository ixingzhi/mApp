package com.example.xiedongdong.app02.fragmentCommunity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xiedongdong on 16/6/30.
 */
public class DisassemblyFragment extends BaseFragment {

    private ListView lv_disassembly;
    private NewsListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_comminity_disassembly,container,false);

        lv_disassembly= (ListView) view.findViewById(R.id.lv_disassembly);

        BmobQuery<News> query=new BmobQuery<News>();
        query.setLimit(50);
        query.order("-createdAt");
        query.addWhereEqualTo("messageType","拆解");
        query.findObjects(getActivity(), new FindListener<News>() {
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

                adapter=new NewsListViewAdapter(getActivity(),listItem);
                lv_disassembly.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("DisassemblyFragment", "查询数据失败:"+s);
            }
        });


        return view;
    }
}
