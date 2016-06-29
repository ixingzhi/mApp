package com.example.xiedongdong.app02.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.communityListView.NewsListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xiedongdong on 16/6/29.
 */
public class NewsListViewAdapter extends BaseAdapter {
    private Context context;
    private static LayoutInflater inflater=null;
    private ArrayList<HashMap<String,String>> data;

    public NewsListViewAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int postion, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null){
            vi=inflater.inflate(R.layout.layout_community_item,null);
        }

        TextView tv_title= (TextView) vi.findViewById(R.id.tv_title); //标题
        TextView tv_from= (TextView) vi.findViewById(R.id.tv_from);//来自
        ImageView img_headImg= (ImageView) vi.findViewById(R.id.img_headImg);//用户头像
        TextView tv_time= (TextView) vi.findViewById(R.id.tv_time);//时间
        ImageView img_title= (ImageView) vi.findViewById(R.id.img_title);//标题头像

        HashMap<String,String> news=new HashMap<>();
        news=data.get(postion);

        //设置Listview相关的值

        tv_title.setText(news.get(NewsListView.KEY_TITLE));
        tv_from.setText(news.get(NewsListView.KEY_FROM));

        tv_time.setText(news.get(NewsListView.KEY_TIME));





        return vi;
    }
}
