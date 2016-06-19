package com.example.xiedongdong.app02.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.fragment.CommunityFragment;
import com.example.xiedongdong.app02.fragment.HomeFragment;
import com.example.xiedongdong.app02.fragment.MeFragment;


/**
 * Created by xiedongdong on 16/5/24.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{
    //底部三个导航布局
    private LinearLayout ll_home;
    private LinearLayout ll_community;
    private LinearLayout ll_me;
    //三个Textview
    private TextView tv_home;
    private TextView tv_community;
    private TextView tv_me;
    //初始化三个Fragment
    private Fragment tab01;
    private Fragment tab02;
    private Fragment tab03;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();   //初始化页面
        initEvents();  //初始化事件
        onSelect(0);  //默认启动页面（首页）
    }

    private void initEvents() {
        ll_home.setOnClickListener(this);
        ll_community.setOnClickListener(this);
        ll_me.setOnClickListener(this);
    }

    private void initView() {
        ll_home=(LinearLayout)findViewById(R.id.ll_home);
        ll_community=(LinearLayout)findViewById(R.id.ll_community);
        ll_me=(LinearLayout)findViewById(R.id.ll_me);

        tv_home=(TextView)findViewById(R.id.tv_home);
        tv_community=(TextView)findViewById(R.id.tv_community);
        tv_me=(TextView)findViewById(R.id.tv_me);
    }

    @Override
    public void onClick(View view) {
        resetText();
        switch (view.getId()){
            case R.id.ll_home:
                onSelect(0); //要启动的页面，调用onSelect方法
                break;
            case R.id.ll_community:
                onSelect(1);
                break;
            case R.id.ll_me:
                onSelect(2);
                break;
            default:
                break;

        }
    }

    private void onSelect(int i) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        //隐藏Fragment,不然会出现叠加显示效果
        hideFragment(fragmentTransaction);

        switch (i){
            case 0:
                if(tab01==null){
                    tab01=new HomeFragment();    //tab01指向HomeFragment
                    fragmentTransaction.add(R.id.fl_content,tab01);  //将HomeFragment添加到活动中
                }else{
                    fragmentTransaction.show(tab01);
                }
                tv_home.setTextColor(Color.GREEN);
                break;
            case 1:
                if (tab02==null){
                    tab02=new CommunityFragment();
                    fragmentTransaction.add(R.id.fl_content,tab02);
                }else{
                    fragmentTransaction.show(tab02);
                }
                tv_community.setTextColor(Color.GREEN);
                break;
            case 2:
                if (tab03==null){
                    tab03=new MeFragment();
                    fragmentTransaction.add(R.id.fl_content,tab03);
                }else{
                    fragmentTransaction.show(tab03);
                }
                tv_me.setTextColor(Color.GREEN);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();  //提交事务
    }
    /**
     * 隐藏所有的Fragment
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if(tab01!=null){
            fragmentTransaction.hide(tab01);
        }
        if(tab02!=null){
            fragmentTransaction.hide(tab02);
        }
        if(tab03!=null){
            fragmentTransaction.hide(tab03);
        }

    }

    /**
     *
     * 重置所有的颜色为白色（默认颜色）
     */

    private void resetText() {
        tv_home.setTextColor(Color.WHITE);
        tv_community.setTextColor(Color.WHITE);
        tv_me.setTextColor(Color.WHITE);
    }


}
