package com.example.xiedongdong.app02.Base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.xiedongdong.app02.Bmob.BmobUserManager;

import cn.bmob.v3.Bmob;

/**
 * Created by xiedongdong on 16/6/15.
 */
public class BaseActivity extends Activity {
    Toast mToast;
    BmobUserManager userManager;
    private final String ApplicationID="6df39e42d1f641e92cd618e2db9a22bf";
    public BaseActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(getApplicationContext(),ApplicationID);
        userManager=BmobUserManager.getInstance(this);
    }

    /**
     * 打印Toast
     */

    public void showToast(final String text){
        if(mToast==null){
            mToast= Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(text);
        }
        mToast.show();

    }

    public void showToast(final int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_LONG);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }
}
