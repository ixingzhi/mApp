package com.example.xiedongdong.app02.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.service.UserService;

/**
 * Created by xiedongdong on 16/6/2.
 */
public class ChangePasswordActivity extends Activity implements View.OnClickListener {
    private TextView tv_currentUser;
    private Button btn_commmit;
    private EditText et_oldPassword;
    private EditText et_changePassword_newPassword;
    private EditText et_changePassword_okNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_changepassword);

        tv_currentUser = (TextView) findViewById(R.id.tv_currentUser);
        tv_currentUser.setOnClickListener(this);

        btn_commmit = (Button) findViewById(R.id.btn_commit);
        btn_commmit.setOnClickListener(this);

        et_oldPassword=(EditText)findViewById(R.id.et_oldPassword);
        et_changePassword_newPassword=(EditText)findViewById(R.id.et_changePassword_newPassword);
        et_changePassword_okNewPassword=(EditText)findViewById(R.id.et_changePassword_okNewPassword);


        currentUserInfo();

    }

    private void currentUserInfo() {
        SharedPreferences pref = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");
        if (username.equals("")) {

        } else {
            tv_currentUser.setText(username);
        }

    }

    public boolean isLogin() {
        SharedPreferences pref = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");
        if (username.equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_currentUser:
                if (isLogin()) {
                    startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                }
                break;
            case R.id.btn_commit:
                if (checkFrom()) {
                    if(alterPassword()){
                        Toast.makeText(ChangePasswordActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                    }
                }
            default:
                break;
        }
    }


    private boolean checkFrom() {
        String txt_currentUser=(tv_currentUser).getText().toString().trim();
        String txt_oldPassword=(et_oldPassword).getText().toString().trim();
        String txt_newPassword=(et_changePassword_newPassword).getText().toString().trim();
        String txt_okNewPassword=(et_changePassword_okNewPassword).getText().toString().trim();

        if(isLogin()){
            Toast.makeText(ChangePasswordActivity.this,"未登录，登陆后修改",Toast.LENGTH_SHORT).show();
            return (false);
        }

        if(txt_oldPassword.equals("") || txt_newPassword.equals("") || txt_okNewPassword.equals("")){
            Toast.makeText(ChangePasswordActivity.this,"输入不合法",Toast.LENGTH_SHORT).show();
            return (false);
        }
        if(txt_newPassword.length()<4 && txt_okNewPassword.length()<4){
            Toast.makeText(ChangePasswordActivity.this,"密码长度不足4位",Toast.LENGTH_SHORT).show();
            return (false);
        }
        if(new UserService(ChangePasswordActivity.this).getUserByUsernamePassword(txt_currentUser,txt_oldPassword)){

        }else{
            Toast.makeText(ChangePasswordActivity.this,"原密码错误",Toast.LENGTH_SHORT).show();
            return (false);
        }
        if(txt_newPassword.equals(txt_okNewPassword)){

        }else{
            Toast.makeText(ChangePasswordActivity.this,"两次新密码输入不一致",Toast.LENGTH_SHORT).show();
            return (false);
        }

        return true;
    }

    private boolean alterPassword() {
        String txt_currentUser=(tv_currentUser).getText().toString().trim();
        String txt_newPassword=(et_changePassword_newPassword).getText().toString().trim();

        if(new UserService(ChangePasswordActivity.this).getUsernameByUpdatePassword(txt_newPassword,txt_currentUser)){
            return true;
        }

        return false;
    }



}
