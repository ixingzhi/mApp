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
public class UserInfoAutographActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_cancel,tv_save;
    private EditText et_autograph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_userinfo_autograph);
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        tv_cancel=(TextView)findViewById(R.id.tv_cancel);
        tv_save=(TextView)findViewById(R.id.tv_save);
        et_autograph=(EditText)findViewById(R.id.et_autograph);
    }

    private void initEvent() {
        tv_cancel.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    private void initData() {
        User user= BmobUser.getCurrentUser(UserInfoAutographActivity.this,User.class);
        et_autograph.setText(user.getAutograph());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                UserInfoAutographActivity.this.finish();
                break;
            case R.id.tv_save:
                if(checkInfoFrom()){
                    saveName();
                }
                break;
            default:
                break;
        }

    }

    private boolean checkInfoFrom() {
        String txt_autograph=et_autograph.getText().toString().trim();

        if(txt_autograph.length()>30){
            showToast("输入字符须小于30个字符");
            return false;
        }
        return true;
    }

    private void saveName() {
        String txt_autograph=et_autograph.getText().toString().trim();
        String txt_objectId=BmobUser.getCurrentUser(UserInfoAutographActivity.this).getObjectId();
        User user=new User();
        user.setAutograph(txt_autograph);
        user.update(UserInfoAutographActivity.this, txt_objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("个性签名修改失败:",""+s);
            }
        });

    }
}
