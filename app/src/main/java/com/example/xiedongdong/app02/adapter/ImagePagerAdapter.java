package com.example.xiedongdong.app02.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.xiedongdong.app02.activity.NewsWebViewActivity;

import java.util.List;

/**
 * Created by xiedongdong on 16/5/25.
 * 图片适配器
 */
public class ImagePagerAdapter extends PagerAdapter{
    /**
     * Return the number of views available.
     */
    private Context context;
    private List<View> imageList;


    public ImagePagerAdapter(Context context,List imageList){
        this.context=context;
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
                        String url0="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=2653189811&idx=1&sn=355b6114df7d1ee0004817e26c1fdea0&scene=0&key=77421cf58af4a6538ba4dd546c80cb7895ca56f28f90654c4643860d962bd8a7c9f7aba2326fc4b97457cb584d7833db&ascene=7&uin=MjAzMzU5NTQyMQ%3D%3D&devicetype=iPhone+OS9.3.2&version=16031610&nettype=3G+&fontScale=100&pass_ticket=zbs%2FJvhT38MbFgN9AY1arvJjEL9Zvc7nuvj94KPuMrVPCWcOo4MJnf8KS71jhycA";
                        throwUrlOpenWeb(url0);
                        break;
                    case 1:
                        String url1="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=2653189748&idx=1&sn=4c917e59b647c8b01c2895e7d1a3e13c&scene=0&key=77421cf58af4a65363de94331e39ce8aa4cf9d6de1c43d8bb4dfe13da633d8b0e9c42f5db888c277837dee89e3efde3b&ascene=7&uin=MjAzMzU5NTQyMQ%3D%3D&devicetype=iPhone+OS9.3.2&version=16031610&nettype=3G+&fontScale=100&pass_ticket=zbs%2FJvhT38MbFgN9AY1arvJjEL9Zvc7nuvj94KPuMrVPCWcOo4MJnf8KS71jhycA";
                        throwUrlOpenWeb(url1);
                        break;
                    case 2:
                        String url2="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=2653189627&idx=1&sn=8a6a78430d9d36e5bea5c56736118231&scene=0&key=77421cf58af4a653b24ceea3fc8977a9d7f5417aab2f794b054bf8af2594452583c170a2905c6b29f3443bb9b4ab5504&ascene=7&uin=MjAzMzU5NTQyMQ%3D%3D&devicetype=iPhone+OS9.3.2&version=16031610&nettype=3G+&fontScale=100&pass_ticket=zbs%2FJvhT38MbFgN9AY1arvJjEL9Zvc7nuvj94KPuMrVPCWcOo4MJnf8KS71jhycA";
                        throwUrlOpenWeb(url2);
                        break;
                    //一加手机评测
                    case 3:
                        String url3="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=2653189866&idx=1&sn=8eb9a89d6ddc9a828ae8c6a3a57cf854&scene=0&key=77421cf58af4a6534eefc8ef135f2677943aca1dad63c590484718af78982c6bb0505cd5cd42b36faaa7271b657337a0&ascene=7&uin=MjAzMzU5NTQyMQ%3D%3D&devicetype=iPhone+OS9.3.2&version=16031610&nettype=3G+&fontScale=100&pass_ticket=zbs%2FJvhT38MbFgN9AY1arvJjEL9Zvc7nuvj94KPuMrVPCWcOo4MJnf8KS71jhycA";
                        throwUrlOpenWeb(url3);
                        break;
                    default:
                        break;

                }
            }
        });

        container.addView(view);
        return view;
    }

    //通过网页链接打开相应的网页
    public void throwUrlOpenWeb(String url) {
        Intent intent = new Intent();
        intent.putExtra("Url", url);
        intent.setClass(context, NewsWebViewActivity.class);
        context.startActivity(intent);
    }


}
