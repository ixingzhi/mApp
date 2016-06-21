package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xiedongdong on 16/6/20.
 */
public class UserInfoNameActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_userInfoTitle_cancel,tv_userInfoTitle_name,tv_userInfoTitle_save;
    private EditText et_userInfo_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_userinfo_name);
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        tv_userInfoTitle_cancel=(TextView)findViewById(R.id.tv_userInfoTitle_cancel);
        tv_userInfoTitle_name=(TextView)findViewById(R.id.tv_userInfoTitle_name);
        tv_userInfoTitle_save=(TextView)findViewById(R.id.tv_userInfoTitle_save);
        et_userInfo_name=(EditText)findViewById(R.id.et_userinfo_name);
    }

    private void initEvent() {
        tv_userInfoTitle_cancel.setOnClickListener(this);
        tv_userInfoTitle_save.setOnClickListener(this);
    }

    private void initData() {
        User user= BmobUser.getCurrentUser(UserInfoNameActivity.this,User.class);
        if(user!=null){
            et_userInfo_name.setText(user.getUsername());
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_userInfoTitle_cancel:
                UserInfoNameActivity.this.finish();
                break;
            case R.id.tv_userInfoTitle_save:
                if(checkInfoFrom()){
                    saveName();
                }
                break;
            default:
                break;
        }

    }

    private boolean checkInfoFrom() {
        String txt_name=et_userInfo_name.getText().toString().trim();
        if(TextUtils.isEmpty(txt_name)){
            showToast("名字不能为空");
            return false;
        }
        if(txt_name.length()<2){
            showToast("名字不能少于两个字符");
            return false;
        }
        return true;
    }

    private void saveName() {
        String txt_name=et_userInfo_name.getText().toString().trim();
        String txt_objectId=BmobUser.getCurrentUser(UserInfoNameActivity.this).getObjectId();
        User user=new User();
        user.setUsername(txt_name);
        user.update(UserInfoNameActivity.this, txt_objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                UserInfoNameActivity.this.finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("名字修改失败:",""+s);
            }
        });

    }
}
