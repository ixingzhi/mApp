package com.example.xiedongdong.app02.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.xiedongdong.app02.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiedongdong on 16/6/27.
 */
public class ViewPagerAdapter extends PagerAdapter {
    public static final int NEW = 0;
    private Context context;
    private List<View> viewList;

    public ViewPagerAdapter(Context context,List<View> viewList){
        this.context = context;
        this.viewList=viewList;
    }
    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return viewList.size();
    }

    /**
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=viewList.get(position);
        switch (position){
            case NEW:
                new ListViewSimple().function(view,context);
                break;
            default:
                break;

        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(viewList.get(position));
    }



//        ListView lv_new=(ListView)view.findViewById(R.id.lv_new);
//        /**定义一个动态数组**/
//        List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
//
//        /**在数组中存放数据**/
//
//        for(int i=0;i<5;i++){
//            Map<String,Object> map=new HashMap<String,Object>();
//            map.put("tv_title","iphone7最新开箱视频,内涵段子，哈哈");
//            map.put("tv_time","2016-09-09 00:00");
//            map.put("img_title",R.mipmap.img_title3);
//            listItem.add(map);
//        }
//        /**
//         * *SimpleAdapter的参数说明
//         * 第一个参数 表示访问整个android应用程序接口，基本上所有的组件都需要
//         * 第二个参数表示生成一个Map(String ,Object)列表选项
//         * 第三个参数表示界面布局的id  表示该文件作为列表项的组件
//         * 第四个参数表示该Map对象的哪些key对应value来生成列表项
//         * 第五个参数表示来填充的组件 Map对象key对应的资源一依次填充组件 顺序有对应关系
//         * 注意的是map对象可以key可以找不到 但组件的必须要有资源填充  因为 找不到key也会返回null 其实就相当于给了一个null资源
//         */
//
//        SimpleAdapter simpleAdapter=new SimpleAdapter
//                (context,listItem,R.layout.layout_community_item,new String[]{"tv_title","tv_time","img_title"},
//                        new int[]{R.id.tv_title,R.id.tv_time,R.id.img_title});
//
//        lv_new.setAdapter(simpleAdapter);

}
