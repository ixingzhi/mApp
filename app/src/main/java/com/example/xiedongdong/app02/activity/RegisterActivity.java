package com.example.xiedongdong.app02.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.po.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xiedongdong on 16/5/30.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText et_phoneNum;
    private EditText et_newPassword;
    private EditText et_usernmae;
    private EditText et_securityCode;
    private Button btn_sendSecurityCode;
    private Button btn_register;
    private TextView tv_existAccount;

    private final String ApplicationID="6df39e42d1f641e92cd618e2db9a22bf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Bmob.initialize(RegisterActivity.this,ApplicationID);

        initView();
        initEvent();
    }

    private void initEvent() {
        btn_sendSecurityCode.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_existAccount.setOnClickListener(this);

    }

    private void initView() {
        et_phoneNum=(EditText)findViewById(R.id.et_phoneNum);
        et_newPassword=(EditText)findViewById(R.id.et_newPassword);
        et_usernmae=(EditText)findViewById(R.id.et_userName);
        et_securityCode=(EditText)findViewById(R.id.et_securityCode);
        btn_sendSecurityCode=(Button)findViewById(R.id.btn_sendSecurityCode);
        btn_register=(Button)findViewById(R.id.btn_register);
        tv_existAccount=(TextView)findViewById(R.id.tv_existAccount);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sendSecurityCode:
                //发送验证码
                break;
            case R.id.btn_register:
                isRegister();
                break;
            case R.id.tv_existAccount:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                break;
            default:
                break;
        }

    }

    //判断注册信息
    public void isRegister() {
        String txt_phoneNum=et_phoneNum.getText().toString().trim();
        String txt_newPassword=et_newPassword.getText().toString().trim();
        String txt_username=et_usernmae.getText().toString().trim();

        if(!TextUtils.isEmpty(txt_phoneNum) && !TextUtils.isEmpty(txt_newPassword)){
            final User user=new User();
            user.setPhoneNum(txt_phoneNum);
            user.setPassword(txt_newPassword);
            user.setUsername(txt_username);

            user.save(RegisterActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(RegisterActivity.this,"注册失败："+s,Toast.LENGTH_SHORT).show();
                }

            });
        }

        return ;
    }
}
