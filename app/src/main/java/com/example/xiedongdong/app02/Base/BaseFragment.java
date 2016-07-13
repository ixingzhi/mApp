package com.example.xiedongdong.app02.Base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.xiedongdong.app02.Bmob.BmobUserManager;

import cn.bmob.v3.Bmob;

/**
 * Created by xiedongdong on 16/6/15.
 */
public class BaseFragment extends Fragment {
    Toast mToast;
    BmobUserManager userManager;
    private final String ApplicationID="6df39e42d1f641e92cd618e2db9a22bf";

    public BaseFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(getActivity(),ApplicationID);
        userManager=BmobUserManager.getInstance(getActivity());
    }

    /**
     * 打印Toast
     */

    public void showToast(final String text){
        if(mToast==null){
            mToast=Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(text);
        }
        mToast.show();

    }

    public void showToast(final int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

}
