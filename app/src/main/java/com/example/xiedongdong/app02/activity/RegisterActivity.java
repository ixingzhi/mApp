package com.example.xiedongdong.app02.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.po.User;
import com.example.xiedongdong.app02.service.UserService;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.InsertListener;

/**
 * Created by xiedongdong on 16/5/30.
 */
public class RegisterActivity extends Activity implements View.OnClickListener{
    private Button btn_okRegister;
    private final String ApplicationID="6df39e42d1f641e92cd618e2db9a22bf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化Bmob数据服务
        Bmob.initialize(this,ApplicationID);

        initView();
    }

    private void initView() {
        btn_okRegister=(Button)findViewById(R.id.btn_okRegister);
        btn_okRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_okRegister:
                if(checkForm()){
                    BmobinsertUserInfo();
                }
                break;
            default:
                break;
        }

    }


    private boolean checkForm() {

        //获取输入的信息
        String txt_newUsername=((EditText)findViewById(R.id.et_newUsername)).getText().toString().trim();
        String txt_newPassword=((EditText)findViewById(R.id.et_newPassword)).getText().toString().trim();
        String txt_okPassword=((EditText)findViewById(R.id.et_okPassword)).getText().toString().trim();
        String txt_email=((EditText)findViewById(R.id.et_email)).getText().toString().trim();

        if(new UserService(RegisterActivity.this).getUserByUsername(txt_newUsername)){
            Toast.makeText(RegisterActivity.this,"用户名已被注册",Toast.LENGTH_LONG).show();
            return (false);
        }

        if(txt_newUsername.length()<4 || txt_newPassword.length()<4 || txt_okPassword.length()<4){
            Toast.makeText(this,"注册信息不完善或用户名密码没有大余4个字符",Toast.LENGTH_LONG).show();
            return (false);
        }

        if(txt_newPassword.equals(txt_okPassword)){

        }else
        {
            Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_LONG).show();
            return (false);
        }

        return (true);

    }

    private void BmobinsertUserInfo() {
        //获取输入的信息
        String txt_newUsername=((EditText)findViewById(R.id.et_newUsername)).getText().toString().trim();
        String txt_newPassword=((EditText)findViewById(R.id.et_newPassword)).getText().toString().trim();
        String txt_email=((EditText)findViewById(R.id.et_email)).getText().toString().trim();


        User user=new User();
        user.setUsername(txt_newUsername);
        user.setPassword(txt_newPassword);
        user.setEmail(txt_email);

        user.signUp(this, new InsertListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(String s) {
                Toast.makeText(RegisterActivity.this,"注册shibai:"+s,Toast.LENGTH_SHORT).show();

            }
        });







    }

}
