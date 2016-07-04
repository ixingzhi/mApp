package com.example.xiedongdong.app02.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseFragment;
import com.example.xiedongdong.app02.activity.NewsWebView;
import com.example.xiedongdong.app02.adapter.ImagePagerAdapter;
import com.example.xiedongdong.app02.adapter.ImageHandler;
import com.example.xiedongdong.app02.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiedongdong on 16/5/24.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{
    //图片适配器
    private ImagePagerAdapter imageAdapter;
    //存放图片的数组
    private List<View> imageList;
    //ViewPager
    //private static final String LOG_TAG = "HomeFragment";
    public ImageHandler handler = new ImageHandler(new WeakReference<HomeFragment>(this));
    public android.support.v4.view.ViewPager viewPager;

    private ImageView iv_chuizi;

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        //初始化界面

        viewPager=(ViewPager)view.findViewById(R.id.vp_content);
        View iv01=View.inflate(view.getContext(),R.layout.home_viewpager_img01,null);
        View iv02=View.inflate(view.getContext(),R.layout.home_viewpager_img02,null);
        View iv03=View.inflate(view.getContext(),R.layout.home_viewpager_img03,null);
        View iv04=View.inflate(view.getContext(),R.layout.home_viewpager_img04,null);

        imageList=new ArrayList<>();
        imageList.add(iv01);
        imageList.add(iv02);
        imageList.add(iv03);
        imageList.add(iv04);

        imageAdapter=new ImagePagerAdapter(imageList);
        //绑定适配器
        viewPager.setAdapter(imageAdapter);
        //给viewpager页面设置页面改变监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //配合Adapter的currentItem字段进行设置。

            /**
             * 这个方法有一个参数position，代表"哪个"页面被选中。当用手指滑动翻页的时候，
             * 如果翻动成功了（滑动的距离够长），手指抬起来就会立即执行这个方法
             */
            @Override
            public void onPageSelected(int arg0) {
                handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, arg0, 0));

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            //覆写该方法实现轮播效果的暂停和恢复
            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });

        //默认在中间，使用户看不到边界
        //viewPager.setCurrentItem(Integer.MAX_VALUE/2);
        //改成：
        //默认在中间附近，使用户看不到边界
        int mid = Integer.MAX_VALUE/2;
        //初始显示第一个
        viewPager.setCurrentItem(mid - mid/imageList.size());

        //开始轮播效果
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);


        iv_chuizi=(ImageView)view.findViewById(R.id.img_smartHome);
        iv_chuizi.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_smartHome:
                String url="http://www.zealer.com";
                throwUrlOpenWeb(url);
                break;
            default:
                break;
        }

    }

    //通过网页链接打开相应的网页
    public void throwUrlOpenWeb(String url){
        Intent intent=new Intent();
        intent.putExtra("Url",url);
        intent.setClass(getActivity(),NewsWebView.class);
        startActivity(intent);

    }


}