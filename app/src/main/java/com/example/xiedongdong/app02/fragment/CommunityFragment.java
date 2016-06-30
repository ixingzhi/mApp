package com.example.xiedongdong.app02.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseFragment;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.User;
import com.example.xiedongdong.app02.fragmentCommunity.DisassemblyFragment;
import com.example.xiedongdong.app02.fragmentCommunity.NewFragment;


import cn.bmob.v3.BmobUser;

/**
 * Created by xiedongdong on 16/5/24.
 */
public class CommunityFragment extends BaseFragment implements View.OnClickListener{
    private ImageView img_btn_add;
    private TextView tv_new;
    private TextView tv_disassembly;
    private TextView tv_openBox;
    private TextView tv_walker;
    private TextView tv_deskTopCulture;

    private Fragment tab01;
    private Fragment tab02;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        img_btn_add = (ImageView) view.findViewById(R.id.img_btn_add);
        tv_new = (TextView) view.findViewById(R.id.tv_new);
        tv_disassembly = (TextView) view.findViewById(R.id.tv_disassembly);
        tv_openBox = (TextView) view.findViewById(R.id.tv_openBox);
        tv_walker = (TextView) view.findViewById(R.id.tv_walker);
        tv_deskTopCulture = (TextView) view.findViewById(R.id.tv_deskTopCulture);

        //vp_communityPager=(ViewPager)view.findViewById(R.id.vp_communityPager);

        img_btn_add.setOnClickListener(this);
        tv_new.setOnClickListener(this);
        tv_disassembly.setOnClickListener(this);
        tv_openBox.setOnClickListener(this);
        tv_walker.setOnClickListener(this);
        tv_deskTopCulture.setOnClickListener(this);

        onSelect(0);//默认进入最新页面

        return view;

    }

    @Override
    public void onClick(View view) {
        setDefaultTextColor();
        switch (view.getId()){
            case R.id.tv_new:
                onSelect(0);
                break;
            case R.id.tv_disassembly:
                onSelect(1);
                break;
            default:
                break;

        }

    }

    private void onSelect(int i) {

        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        //隐藏Fragment,不然会出现叠加显示效果
        hideFragment(fragmentTransaction);
        switch(i){
            case 0:
                if(tab01==null){
                    tab01=new NewFragment();    //tab01指向NewFragment
                    fragmentTransaction.add(R.id.fl_content_news,tab01);  //将NewFragment添加到活动中
                }else{
                    fragmentTransaction.show(tab01);
                }
                tv_new.setTextColor(Color.RED);
                break;
            case 1:
                if(tab02==null){
                    tab02=new DisassemblyFragment();
                    fragmentTransaction.add(R.id.fl_content_news,tab02);
                }else{
                    fragmentTransaction.show(tab02);
                }
                tv_disassembly.setTextColor(Color.RED);
                break;
            default:
                break;

        }


        fragmentTransaction.commit();  //提交事务, 最痛心的一句代码，少写了，调试了一天，Fuck！

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

    }

    /**每次点击时恢复默认字体颜色**/
    private void setDefaultTextColor() {
        tv_new.setTextColor(Color.GRAY);
        tv_disassembly.setTextColor(Color.GRAY);
        tv_openBox.setTextColor(Color.GRAY);
        tv_walker.setTextColor(Color.GRAY);
        tv_deskTopCulture.setTextColor(Color.GRAY);
    }


    public boolean isLogin() {
        User user = BmobUser.getCurrentUser(getActivity(), User.class);
        if (user == null) {
            showToast("未登录");
            return false;
        }
        return true;
    }

}