package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.po.User;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xiedongdong on 16/5/29.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_account;
    private EditText et_password;
    private Button btn_login;
    private TextView tv_forgetPassword;
    private TextView tv_registerNewUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initEvent();
    }

    private void initEvent() {
        btn_login.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
        tv_registerNewUser.setOnClickListener(this);
    }

    private void initView() {
        et_account=(EditText)findViewById(R.id.et_account);
        et_password=(EditText)findViewById(R.id.et_password);
        btn_login=(Button)findViewById(R.id.btn_login);
        tv_forgetPassword=(TextView)findViewById(R.id.tv_forgetPassword);
        tv_registerNewUser=(TextView)findViewById(R.id.tv_registerNewUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                isLogin();
                break;
            case R.id.tv_forgetPassword:

                break;
            case R.id.tv_registerNewUser:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            default:
                break;


        }
    }

    /**
     *对登录信息进行处理
     *用手机号和密码判断进行登录
     */

    public void isLogin(){
        final String txt_account=et_account.getText().toString().trim();
        String txt_password=et_password.getText().toString().trim();

        if(!TextUtils.isEmpty(txt_account) && !TextUtils.isEmpty(txt_password)) {
            BmobUser bmobUser=new BmobUser();
            bmobUser.setMobilePhoneNumber(txt_account);
            bmobUser.setPassword(txt_password);

            BmobUser.loginByAccount(LoginActivity.this, txt_account, txt_password, new LogInListener<BmobUser>() {

                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if(bmobUser!=null){
                        showToast("登录成功");
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        LoginActivity.this.finish();
                    }else{
                        showToast("登录信息有误");
                    }
                }
            });

        }
    }
}
