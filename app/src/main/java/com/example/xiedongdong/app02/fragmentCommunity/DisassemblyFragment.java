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
import com.example.xiedongdong.app02.activity.NewsWebViewActivity;
import com.example.xiedongdong.app02.adapter.NewsListViewAdapter;
import com.example.xiedongdong.app02.bean.News;
import com.example.xiedongdong.app02.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xiedongdong on 16/6/30.
 */
public class DisassemblyFragment extends BaseFragment {

    private ListView lv_disassembly;
    private NewsListViewAdapter adapter;

    //用户ID
    private String id=null;
    //阅读量
    private String readCount=null;

    //文章编号
    private String itemId=null;

    private int count=0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_community_disassembly,container,false);

        lv_disassembly= (ListView) view.findViewById(R.id.lv_disassembly);

        BmobQuery<News> query=new BmobQuery<News>();
        query.setLimit(50);
        query.order("-createdAt");
        query.addWhereEqualTo("messageType","拆解");
        query.findObjects(getActivity(), new FindListener<News>() {
            @Override
            public void onSuccess(List<News> list) {
                final ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

                for (final News newsList : list) {
                    /**定义一个动态数组**/

                    final HashMap<String, String> map = new HashMap<String, String>();

                    map.put(NewsListViewAdapter.KEY_ITEMID,newsList.getObjectId());
                    map.put(NewsListViewAdapter.KEY_TITLE, newsList.getTitle());
                    map.put(NewsListViewAdapter.KEY_FROM, newsList.getFrom());
                    map.put(NewsListViewAdapter.KEY_HEADIMG,newsList.getHeadImgUrl());
                    map.put(NewsListViewAdapter.KEY_USERNAME,newsList.getUsername());
                    map.put(NewsListViewAdapter.KEY_TIME,newsList.getCreatedAt());
                    map.put(NewsListViewAdapter.KEY_URL,newsList.getUrl());
                    map.put(NewsListViewAdapter.KEY_READCOUNT,newsList.getReadCount());
                    map.put(NewsListViewAdapter.KEY_TITLEIMG,newsList.getImgTitleUrl());

                    listItem.add(map);
                }

                adapter=new NewsListViewAdapter(getActivity(),listItem);
                lv_disassembly.setAdapter(adapter);
                lv_disassembly.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {

                        String itemId=listItem.get(postion).get(NewsListViewAdapter.KEY_ITEMID);
                        String title=listItem.get(postion).get(NewsListViewAdapter.KEY_TITLE);
                        String from=listItem.get(postion).get(NewsListViewAdapter.KEY_FROM);
                        String headImgUrl=listItem.get(postion).get(NewsListViewAdapter.KEY_HEADIMG);
                        String username=listItem.get(postion).get(NewsListViewAdapter.KEY_USERNAME);
                        String shareTime=listItem.get(postion).get(NewsListViewAdapter.KEY_TIME);  //分享时间
                        String titleImageUrl=listItem.get(postion).get(NewsListViewAdapter.KEY_TITLEIMG);
                        readCount=listItem.get(postion).get(NewsListViewAdapter.KEY_READCOUNT);
                        String url=listItem.get(postion).get(NewsListViewAdapter.KEY_URL);

                        //每次点击，阅读量加1
                        addReadCount();

                        Intent intent=new Intent();

                        intent.putExtra("itemId",itemId);
                        intent.putExtra("title",title);
                        intent.putExtra("from",from);
                        intent.putExtra("headImgUrl",headImgUrl);
                        intent.putExtra("username",username);
                        intent.putExtra("shareTime",shareTime);
                        intent.putExtra("titleImageUrl",titleImageUrl);
                        intent.putExtra("readCount",readCount);
                        intent.putExtra("url",url);

                        intent.setClass(getActivity(),NewsWebViewActivity.class);
                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onError(int i, String s) {
                Log.e("DisassemblyFragment", "查询数据失败:"+s);
            }
        });


        return view;
    }
    private void addReadCount() {

        //先将数据库中的String类型转换成int类型，再加一个点击量
        count=Integer.valueOf(readCount)+1;
        //在将int类型转换成String类型，上传到数据库中
        readCount=Integer.toString(count);

        //上传点击量到数据库中
        News news=new News();
        news.setReadCount(readCount);
        news.update(getActivity(),id, new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.e("readCount","阅读量加1");
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });

    }
}
