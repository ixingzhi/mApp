package com.example.xiedongdong.app02.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.po.User;


import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xiedongdong on 16/5/29.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText et_account;
    private EditText et_password;
    private Button btn_login;
    private TextView tv_forgetPassword;
    private TextView tv_registerNewUser;

    private final String ApplicationID="6df39e42d1f641e92cd618e2db9a22bf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Bmob.initialize(LoginActivity.this,ApplicationID);
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

    //对登录信息进行处理
    public void isLogin() {
        final String txt_account=et_account.getText().toString().trim();
        String txt_password=et_password.getText().toString().trim();

        if(!TextUtils.isEmpty(txt_account) && !TextUtils.isEmpty(txt_password)){

            BmobQuery<User> userQuery=new BmobQuery<>();
            userQuery.addWhereEqualTo("phoneNum",txt_account);
            userQuery.addWhereEqualTo("password",txt_password);

            userQuery.findObjects(LoginActivity.this, new FindListener<User>() {
                @Override
                public void onSuccess(List<User> userList) {
                    if(userList!=null && userList.size()>0){
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        /**
                         * 存储用户的登录名信息,登录后从我的页面获取登录用户的用户名
                         */
                        SharedPreferences.Editor editor=getSharedPreferences("user",MODE_PRIVATE).edit();
                        editor.putString("username",txt_account);
                        editor.commit();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        LoginActivity.this.finish();
                    }else {
                        Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(int i, String s) {

                    Toast.makeText(LoginActivity.this,"登录失败："+i+s,Toast.LENGTH_SHORT).show();
                }

            });

        }

    }
}
