package com.example.xiedongdong.app02.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xiedongdong.app02.Base.BaseFragment;
import com.example.xiedongdong.app02.activity.MoreVideoActivity;
import com.example.xiedongdong.app02.activity.NewsWebViewActivity;
import com.example.xiedongdong.app02.activity.WebViewActivity;
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

    //智能家居
    private ImageView img_smartHome;
    //出行方式
    private ImageView img_tripMode;
    //玩客系列
    private ImageView img_walkerSerial;
    //生活健康
    private ImageView img_lifeHealth;
    //更多视频
    private LinearLayout ll_moreVideo;
    //视屏栏目
    private LinearLayout ll_video;
    //极客工厂
    private LinearLayout ll_factory;

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

        imageAdapter=new ImagePagerAdapter(getActivity(),imageList);
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


        img_smartHome=(ImageView)view.findViewById(R.id.img_smartHome);
        img_smartHome.setOnClickListener(this);

        img_tripMode=(ImageView)view.findViewById(R.id.img_tripMode);
        img_tripMode.setOnClickListener(this);

        img_walkerSerial=(ImageView)view.findViewById(R.id.img_walkerSerial);
        img_walkerSerial.setOnClickListener(this);

        img_lifeHealth=(ImageView)view.findViewById(R.id.img_lifeHealth);
        img_lifeHealth.setOnClickListener(this);


        ll_moreVideo=(LinearLayout)view.findViewById(R.id.ll_moreVideo);
        ll_moreVideo.setOnClickListener(this);

        ll_video=(LinearLayout)view.findViewById(R.id.ll_video);
        ll_video.setOnClickListener(this);

        ll_factory=(LinearLayout)view.findViewById(R.id.ll_factory);
        ll_factory.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_smartHome:
                String smartHomeUrl="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=2653189866&idx=3" +
                        "&sn=87ff6142b32fe8dfdf537bb8a04fdc82&scene=0&key=77421cf58af4a65390263c189e57be2" +
                        "2b15ce6a5d340ae05ab1d8ce6f2d80ddf4610c2c987b7584792e4b302161f2572&ascene=7&uin=MjA" +
                        "zMzU5NTQyMQ%3D%3D&devicetype=iPhone+OS10.0&version=16031610&nettype=WIFI&fontScal" +
                        "e=100&pass_ticket=DGQHy%2FI%2F6JOvZoFSrzmyBpbPRzDKgFAEmpGjjBGKnj%2BgEztO9phZm2L090t0pXZb";
                throwUrlOpenWeb(smartHomeUrl);
                break;
            case R.id.img_tripMode:
                String tripModeUrl="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=2653189945&idx" +
                        "=3&sn=15ca098f03e58f27ee972498d476b8f4&scene=0&key=77421cf58af4a653c451917feef7f" +
                        "91ccab3cd1c8bb9ec8e233188d88df8ca6b564fa8dc350bfaa2dcfd0032c56c74f6&ascene=7&uin=" +
                        "MjAzMzU5NTQyMQ%3D%3D&devicetype=iPhone+OS10.0&version=16031610&nettype=WIFI&fontS" +
                        "cale=100&pass_ticket=DGQHy%2FI%2F6JOvZoFSrzmyBpbPRzDKgFAEmpGjjBGKnj%2BgEztO9phZm2L090t0pXZb";
                throwUrlOpenWeb(tripModeUrl);
                break;
            case R.id.img_walkerSerial:
                String walkerSerialUrl="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=2653189925&id" +
                        "x=1&sn=eff72435e519aefcc1997e0a838cccfd&scene=0&key=77421cf58af4a6538bf280b764159c769" +
                        "ae72f390d2923b6111a9da53896a4a73df0b14ea655ffb3293827ec768e3b1b&ascene=7&uin=MjAzMzU5NTQ" +
                        "yMQ%3D%3D&devicetype=iPhone+OS10.0&version=16031610&nettype=WIFI&fontScale=100&pass_ticke" +
                        "t=DGQHy%2FI%2F6JOvZoFSrzmyBpbPRzDKgFAEmpGjjBGKnj%2BgEztO9phZm2L090t0pXZb";
                throwUrlOpenWeb(walkerSerialUrl);
                break;
            case R.id.img_lifeHealth:
                String lifeHealthUrl="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=207106897&idx=1&sn=014" +
                        "0b73277f06e62fa3c567e16301712&scene=20&key=77421cf58af4a6538a79853fe45059906d1f3a64fa1d05cf1c6" +
                        "43a9316cdec4eea81bf53047df60820f95180a50e3b2a&ascene=7&uin=MjAzMzU5NTQyMQ%3D%3D&devicetype=i" +
                        "Phone+OS10.0&version=16031610&nettype=WIFI&fontScale=100&pass_ticket=DGQHy%2FI%2F6JOvZoFSrzmyBpbP" +
                        "RzDKgFAEmpGjjBGKnj%2BgEztO9phZm2L090t0pXZb";
                throwUrlOpenWeb(lifeHealthUrl);
                break;
            case R.id.ll_moreVideo:
                startActivity(new Intent(getActivity(), MoreVideoActivity.class));
                break;
            case R.id.ll_video:
                String videoUrl="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=402252485&idx=" +
                        "1&sn=7574cc930102772e7ade4736cf072e28&scene=21&uin=MjAzMzU5NTQyMQ%3D%3D&key" +
                        "=77421cf58af4a653804cbbac0318f72c45da03690602edcb01aa67031ea65c8d51af3985e5a5b2" +
                        "59a18069284c4021e0&devicetype=iPhone+OS10.0&version=16031610&lang=zh_CN&nettype" +
                        "=3G+&fontScale=100&pass_ticket=DGQHy%2FI%2F6JOvZoFSrzmyBpbPRzDKgFAEmpGjjBGKnj%2B" +
                        "gEztO9phZm2L090t0pXZb";
                throwUrlOpenWeb(videoUrl);
                break;
            case R.id.ll_factory:
                showToast("正在开发中，敬请期待");
                break;
            default:
                break;
        }

    }

    //通过网页链接打开相应的网页
    public void throwUrlOpenWeb(String url){
        Intent intent=new Intent();
        intent.putExtra("url",url);
        intent.setClass(getActivity(),WebViewActivity.class);
        startActivity(intent);

    }


}