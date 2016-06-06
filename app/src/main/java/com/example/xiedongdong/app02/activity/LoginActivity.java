package com.example.xiedongdong.app02.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.po.User;
import com.example.xiedongdong.app02.service.UserService;

/**
 * Created by xiedongdong on 16/5/29.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private Button btn_login;
    private TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tv_register=(TextView)findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                checkLogin();

                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;

            default:
                break;
        }

    }

    private void checkLogin() {
        String txt_username=((EditText)findViewById(R.id.et_userName)).getText().toString().trim();
        String txt_password=((EditText)findViewById(R.id.et_password)).getText().toString().trim();


        if(new UserService(LoginActivity.this).getUserByUsernamePassword(txt_username,txt_password)){
            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            /**
             * 存储用户的登录名信息,登录后从我的页面获取登录用户的用户名
             */
            SharedPreferences.Editor editor=getSharedPreferences("user",MODE_PRIVATE).edit();
            editor.putString("username",txt_username);
            editor.commit();

            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            LoginActivity.this.finish();
        }else{
            Toast.makeText(LoginActivity.this,"登录信息有误或未注册",Toast.LENGTH_SHORT).show();
        }
    }
}
