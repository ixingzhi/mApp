//package com.example.xiedongdong.app02.adapter;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//
//import com.example.xiedongdong.app02.R;
//import com.example.xiedongdong.app02.bean.News;
//import com.example.xiedongdong.app02.util.BitmapFileNet;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import cn.bmob.v3.BmobQuery;
//import cn.bmob.v3.listener.FindListener;
//
///**
// * Created by xiedongdong on 16/6/27.
// */
//public class ListViewSimple {
//    private ListView lv_new;
//    private ImageView img_title;
//
//    public void function(View view, final Context context){
//
//        lv_new=(ListView) view.findViewById(R.id.lv_new);
//        img_title=(ImageView)view.findViewById(R.id.img_title);
//
//
//        /**先从数据库中获取数据，在数组中存放数据**/
//        final News news=new News();
//        BmobQuery<News> query=new BmobQuery<News>();
//        query.setLimit(50);
//        query.order("-createdAt");
//        query.findObjects(context, new FindListener<News>() {
//            @Override
//            public void onSuccess(List<News> list) {
//                Log.e("ListViewSimple","查询数据成功");
//                List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
//
//                for(final News newsList:list){
//                    Log.e("Url",""+newsList.getImgTitleUrl());
//                    /**定义一个动态数组**/
//
//                    final Map<String,Object> map=new HashMap<String,Object>();
//                    map.put("tv_title",newsList.getTitle());
//                    map.put("tv_time",newsList.getCreatedAt());
//                    map.put("tv_fromUrl",newsList.getFrom());
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                map.put("img_title", BitmapFileNet.get(newsList.getImgTitleUrl()));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
//
//                    listItem.add(map);
//
//                    /**
//                     * *SimpleAdapter的参数说明
//                     * 第一个参数 表示访问整个android应用程序接口，基本上所有的组件都需要
//                     * 第二个参数表示生成一个Map(String ,Object)列表选项
//                     * 第三个参数表示界面布局的id  表示该文件作为列表项的组件
//                     * 第四个参数表示该Map对象的哪些key对应value来生成列表项
//                     * 第五个参数表示来填充的组件 Map对象key对应的资源一依次填充组件 顺序有对应关系
//                     * 注意的是map对象可以key可以找不到 但组件的必须要有资源填充  因为 找不到key也会返回null 其实就相当于给了一个null资源
//                     */
//                    SimpleAdapter simpleAdapter=new SimpleAdapter
//                            (context,listItem,R.layout.layout_community_item,new String[]{"tv_title","tv_time","img_title","tv_fromUrl"},
//                                    new int[]{R.id.tv_title,R.id.tv_time,R.id.img_title,R.id.tv_fromUrl});
//
//                    lv_new.setAdapter(simpleAdapter);
//
//                }
//
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Log.e("ListViewSimple","查询数据失败"+s);
//            }
//        });
//
//    }
//}
