package com.example.xiedongdong.app02.adapter;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiedongdong on 16/6/27.
 */
public class ListViewSimple extends BaseActivity {
    private ListView lv_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_new);
        lv_new=(ListView)findViewById(R.id.lv_new);

        /**定义一个动态数组**/
        List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();

        /**在数组中存放数据**/

        for(int i=0;i<5;i++){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("tv_title","iphone7最新开箱视频");
            map.put("tv_fromUrl","www.huyou.com");
            map.put("tv_time","2016-09-09 00:00");
            map.put("img_title",R.mipmap.img_title3);
        }

        /**
         * *SimpleAdapter的参数说明
         * 第一个参数 表示访问整个android应用程序接口，基本上所有的组件都需要
         * 第二个参数表示生成一个Map(String ,Object)列表选项
         * 第三个参数表示界面布局的id  表示该文件作为列表项的组件
         * 第四个参数表示该Map对象的哪些key对应value来生成列表项
         * 第五个参数表示来填充的组件 Map对象key对应的资源一依次填充组件 顺序有对应关系
         * 注意的是map对象可以key可以找不到 但组件的必须要有资源填充  因为 找不到key也会返回null 其实就相当于给了一个null资源
         */

        SimpleAdapter simpleAdapter=new SimpleAdapter
                (this,listItem,R.layout.layout_community_item,new String[]{"tv_title","tv_fromUrl","tv_time","img_title"},
                        new int[]{R.id.tv_title,R.id.tv_fromUrl,R.id.tv_time,R.id.img_title});

        lv_new.setAdapter(simpleAdapter);
    }
}
