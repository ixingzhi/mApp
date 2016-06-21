package com.example.xiedongdong.app02.activity;

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
 * Created by xiedongdong on 16/6/21.
 */
public class UserInfoEmailActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_cancel,tv_save;
    private EditText et_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_userinfo_email);
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        tv_cancel=(TextView)findViewById(R.id.tv_cancel);
        tv_save=(TextView)findViewById(R.id.tv_save);
        et_email=(EditText)findViewById(R.id.et_email);
    }

    private void initEvent() {
        tv_cancel.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    private void initData() {
        User user= BmobUser.getCurrentUser(UserInfoEmailActivity.this,User.class);
        if(user!=null){
            et_email.setText(user.getEmail());
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                UserInfoEmailActivity.this.finish();
                break;
            case R.id.tv_save:
                if(checkInfoFrom()){
                    upDataEmail();
                }
                break;
            default:
                break;
        }

    }

    private boolean checkInfoFrom() {
        String txt_email=et_email.getText().toString().trim();
        if(TextUtils.isEmpty(txt_email)){
            showToast("名字不能为空");
            return false;
        }
        return true;
    }

    private void upDataEmail() {
        String txt_email=et_email.getText().toString().trim();
        String txt_objectId=BmobUser.getCurrentUser(UserInfoEmailActivity.this).getObjectId();
        User user=new User();
        user.setEmail(txt_email);
        user.update(UserInfoEmailActivity.this, txt_objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                UserInfoEmailActivity.this.finish();
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("邮箱修改失败:"+s);
                Log.e("邮箱修改失败:",""+s);
            }
        });

    }
}
