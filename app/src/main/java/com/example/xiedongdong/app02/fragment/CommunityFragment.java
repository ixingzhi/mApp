package com.example.xiedongdong.app02.fragment;

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
import com.example.xiedongdong.app02.activity.PublishNewsActivity;
import com.example.xiedongdong.app02.bean.User;
import com.example.xiedongdong.app02.fragmentCommunity.DeskTopCultrueFragment;
import com.example.xiedongdong.app02.fragmentCommunity.DisassemblyFragment;
import com.example.xiedongdong.app02.fragmentCommunity.EvaluationFragment;
import com.example.xiedongdong.app02.fragmentCommunity.InfoFragment;
import com.example.xiedongdong.app02.fragmentCommunity.MediaFragment;
import com.example.xiedongdong.app02.fragmentCommunity.NewFragment;
import com.example.xiedongdong.app02.fragmentCommunity.OpenBoxFragment;
import com.example.xiedongdong.app02.fragmentCommunity.WalkerFragment;


import cn.bmob.v3.BmobUser;

/**
 * Created by xiedongdong on 16/5/24.
 */
public class CommunityFragment extends BaseFragment implements View.OnClickListener{
    private ImageView img_btn_add;
    private TextView tv_new;
    private TextView tv_evaluation;
    private TextView tv_info;
    private TextView tv_disassembly;
    private TextView tv_openBox;
    private TextView tv_walker;
    private TextView tv_media;
    private TextView tv_deskTopCulture;

    private Fragment tab01;
    private Fragment tab02;
    private Fragment tab03;
    private Fragment tab04;
    private Fragment tab05;
    private Fragment tab06;
    private Fragment tab07;
    private Fragment tab08;

    private int pagerNum=1;  //设置默认页码，恢复进入activity时用


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        img_btn_add = (ImageView) view.findViewById(R.id.img_btn_add);
        tv_new = (TextView) view.findViewById(R.id.tv_new);
        tv_info= (TextView) view.findViewById(R.id.tv_info);
        tv_evaluation= (TextView) view.findViewById(R.id.tv_evaluation);
        tv_disassembly = (TextView) view.findViewById(R.id.tv_disassembly);
        tv_openBox = (TextView) view.findViewById(R.id.tv_openBox);
        tv_walker = (TextView) view.findViewById(R.id.tv_walker);
        tv_media= (TextView) view.findViewById(R.id.tv_media);
        tv_deskTopCulture = (TextView) view.findViewById(R.id.tv_deskTopCulture);


        img_btn_add.setOnClickListener(this);
        tv_new.setOnClickListener(this);
        tv_info.setOnClickListener(this);
        tv_evaluation.setOnClickListener(this);
        tv_disassembly.setOnClickListener(this);
        tv_openBox.setOnClickListener(this);
        tv_media.setOnClickListener(this);
        tv_walker.setOnClickListener(this);
        tv_deskTopCulture.setOnClickListener(this);

        onSelect(pagerNum);//默认进入最新页面

        return view;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.img_btn_add:
                if(isLogin()){
                    startActivity(new Intent(getActivity(), PublishNewsActivity.class));
                }
                break;
            case R.id.tv_new:
                setDefaultTextColor();
                onSelect(1);
                break;
            case R.id.tv_info:
                setDefaultTextColor();
                onSelect(2);
                break;
            case R.id.tv_evaluation:
                setDefaultTextColor();
                onSelect(3);
                break;
            case R.id.tv_disassembly:
                setDefaultTextColor();
                onSelect(4);
                break;
            case R.id.tv_openBox:
                setDefaultTextColor();
                onSelect(5);
                break;
            case R.id.tv_walker:
                setDefaultTextColor();
                onSelect(6);
                break;
            case R.id.tv_media:
                setDefaultTextColor();
                onSelect(7);
                break;
            case R.id.tv_deskTopCulture:
                setDefaultTextColor();
                onSelect(8);
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
            case 1:
                if(tab01==null){
                    tab01=new NewFragment();    //tab01指向NewFragment
                    fragmentTransaction.add(R.id.fl_content_news,tab01);  //将NewFragment添加到活动中
                }else{
                    fragmentTransaction.show(tab01);
                }
                tv_new.setTextColor(Color.RED);
                pagerNum=1;
                break;
            case 2:
                if(tab02==null){
                    tab02=new InfoFragment();
                    fragmentTransaction.add(R.id.fl_content_news,tab02);
                }else{
                    fragmentTransaction.show(tab02);
                }
                tv_info.setTextColor(Color.RED);
                pagerNum=2;
                break;
            case 3:
                if(tab03==null){
                    tab03=new EvaluationFragment();
                    fragmentTransaction.add(R.id.fl_content_news,tab03);
                }else{
                    fragmentTransaction.show(tab03);
                }
                tv_info.setTextColor(Color.RED);
                pagerNum=3;
                break;
            case 4:
                if(tab04==null){
                    tab04=new DisassemblyFragment();
                    fragmentTransaction.add(R.id.fl_content_news,tab04);
                }else{
                    fragmentTransaction.show(tab04);
                }
                tv_disassembly.setTextColor(Color.RED);
                pagerNum=4;
                break;
            case 5:
                if(tab05==null){
                    tab05=new OpenBoxFragment();
                    fragmentTransaction.add(R.id.fl_content_news,tab05);
                }else{
                    fragmentTransaction.show(tab05);
                }
                tv_openBox.setTextColor(Color.RED);
                pagerNum=5;
                break;
            case 6:
                if(tab06==null){
                    tab06=new WalkerFragment();
                    fragmentTransaction.add(R.id.fl_content_news,tab06);
                }else{
                    fragmentTransaction.show(tab06);
                }
                tv_walker.setTextColor(Color.RED);
                pagerNum=6;
                break;
            case 7:
                if(tab07==null){
                    tab07=new MediaFragment();
                    fragmentTransaction.add(R.id.fl_content_news,tab07);
                }else{
                    fragmentTransaction.show(tab07);
                }
                tv_media.setTextColor(Color.RED);
                pagerNum=7;
                break;
            case 8:
                if(tab08==null){
                    tab08=new DeskTopCultrueFragment();
                    fragmentTransaction.add(R.id.fl_content_news,tab08);
                }else{
                    fragmentTransaction.show(tab08);
                }
                tv_deskTopCulture.setTextColor(Color.RED);
                pagerNum=8;
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
        if(tab03!=null){
            fragmentTransaction.hide(tab03);
        }
        if(tab04!=null){
            fragmentTransaction.hide(tab04);
        }
        if(tab05!=null){
            fragmentTransaction.hide(tab05);
        }
        if(tab06!=null){
            fragmentTransaction.hide(tab06);
        }
        if(tab07!=null){
            fragmentTransaction.hide(tab07);
        }
        if(tab08!=null){
            fragmentTransaction.hide(tab08);
        }

    }

    /**每次点击时恢复默认字体颜色**/
    private void setDefaultTextColor() {
        tv_new.setTextColor(Color.GRAY);
        tv_info.setTextColor(Color.GRAY);
        tv_evaluation.setTextColor(Color.GRAY);
        tv_disassembly.setTextColor(Color.GRAY);
        tv_openBox.setTextColor(Color.GRAY);
        tv_walker.setTextColor(Color.GRAY);
        tv_media.setTextColor(Color.GRAY);
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

    @Override
    public void onResume() {
        super.onResume();
        onSelect(pagerNum);
    }
}