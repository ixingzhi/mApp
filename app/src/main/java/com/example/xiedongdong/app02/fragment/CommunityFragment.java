package com.example.xiedongdong.app02.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseFragment;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiedongdong on 16/5/24.
 */
public class CommunityFragment extends BaseFragment implements View.OnClickListener{
    private TextView tv_new;
    private TextView tv_disassembly;
    private TextView tv_openBox;
    private TextView tv_walker;
    private TextView tv_deskTopCulture;


    private ViewPagerAdapter viewPagerAdapter;
    private List<View> viewList;
    private ViewPager vp_communityPager;
    private View view1,view2,view3,view4,view5;   //5个添加的页面

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_community,container,false);

        tv_new=(TextView)view.findViewById(R.id.tv_new);
        tv_disassembly=(TextView)view.findViewById(R.id.tv_disassembly);
        tv_openBox=(TextView)view.findViewById(R.id.tv_openBox);
        tv_walker=(TextView)view.findViewById(R.id.tv_walker);
        tv_deskTopCulture=(TextView)view.findViewById(R.id.tv_deskTopCulture);

        vp_communityPager=(ViewPager)view.findViewById(R.id.vp_communityPager);


        view1=View.inflate(getActivity(),R.layout.community_new,null);
        view2=View.inflate(getActivity(),R.layout.community_disassembly,null);
        view3=View.inflate(getActivity(),R.layout.community_openbox,null);
        view4=View.inflate(getActivity(),R.layout.community_walker,null);
        view5=View.inflate(getActivity(),R.layout.community_desktopculture,null);

        viewList=new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);

        viewPagerAdapter=new ViewPagerAdapter(viewList);
        vp_communityPager.setAdapter(viewPagerAdapter);

        //initSetListener();

        tv_new.setOnClickListener(this);
        tv_disassembly.setOnClickListener(this);
        tv_openBox.setOnClickListener(this);
        tv_walker.setOnClickListener(this);
        tv_deskTopCulture.setOnClickListener(this);

        //Viewpager滑动事件
        vp_communityPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setDefaultTextColor();
                switch (position){
                    case 0:
                        tv_new.setTextColor(Color.RED);
                        break;
                    case 1:
                        tv_disassembly.setTextColor(Color.RED);
                        break;
                    case 2:
                        tv_openBox.setTextColor(Color.RED);
                        break;
                    case 3:
                        tv_walker.setTextColor(Color.RED);
                        break;
                    case 4:
                        tv_deskTopCulture.setTextColor(Color.RED);
                        break;
                    default:
                        break;

                }

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        setDefaultTextColor();
        switch (view.getId()){
            case R.id.tv_new:
                vp_communityPager.setCurrentItem(0);
                tv_new.setTextColor(Color.RED);
                break;
            case R.id.tv_disassembly:
                vp_communityPager.setCurrentItem(1);
                tv_disassembly.setTextColor(Color.RED);
                break;
            case R.id.tv_openBox:
                vp_communityPager.setCurrentItem(2);
                tv_openBox.setTextColor(Color.RED);
                break;
            case R.id.tv_walker:
                vp_communityPager.setCurrentItem(3);
                tv_walker.setTextColor(Color.RED);
                break;
            case R.id.tv_deskTopCulture:
                vp_communityPager.setCurrentItem(4);
                tv_deskTopCulture.setTextColor(Color.RED);
                break;

            default:
                break;
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
}