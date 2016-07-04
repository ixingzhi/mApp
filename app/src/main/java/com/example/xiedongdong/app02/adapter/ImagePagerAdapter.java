package com.example.xiedongdong.app02.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiedongdong on 16/5/25.
 * 图片适配器
 */
public class ImagePagerAdapter extends PagerAdapter{
    /**
     * Return the number of views available.
     */
    private List<View> imageList;


    public ImagePagerAdapter(List imageList){

       this.imageList=imageList;
    }

    /**
     * 获取数量
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 判断出去的view是否等于进来的view 如果为true直接复用
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0==arg1;
    }
    /**
     * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来就是position ,android 最多加载3个
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        //container.removeView(imageList.get(position));

    }

    /**
     *创建一个VIEW
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= imageList.size();
        if (position<0){
            position = imageList.size()+position;
        }

        View view=imageList.get(position);

        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }

        final int finalPosition = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(finalPosition){
                    case 0:
                        Log.e("ImagePagerAdapter",""+finalPosition);
                        break;
                    case 1:
                        Log.e("ImagePagerAdapter",""+finalPosition);
                        break;
                    case 2:
                        Log.e("ImagePagerAdapter",""+finalPosition);
                        break;
                    case 3:
                        Log.e("ImagePagerAdapter",""+finalPosition);
                        break;
                    default:
                        break;

                }
            }
        });

        container.addView(view);
        return view;
    }



}
