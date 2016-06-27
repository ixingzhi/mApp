package com.example.xiedongdong.app02.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xiedongdong on 16/6/27.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> viewList;

    public ViewPagerAdapter(List<View> viewList){
        this.viewList=viewList;
    }
    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return viewList.size();
    }

    /**
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=viewList.get(position);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
