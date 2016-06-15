package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.po.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xiedongdong on 16/5/30.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_phoneNum;
    private EditText et_newPassword;
    private EditText et_usernmae;
    private EditText et_securityCode;
    private Button btn_sendSecurityCode;
    private CheckBox cb_agreeTerms;
    private TextView tv_terms;
    private Button btn_register;
    private TextView tv_existAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initEvent();

        cb_agreeTerms.setChecked(true);
    }

    private void initEvent() {
        btn_sendSecurityCode.setOnClickListener(this);
        tv_terms.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_existAccount.setOnClickListener(this);

    }

    private void initView() {
        et_phoneNum=(EditText)findViewById(R.id.et_phoneNum);
        et_newPassword=(EditText)findViewById(R.id.et_newPassword);
        et_usernmae=(EditText)findViewById(R.id.et_userName);
        et_securityCode=(EditText)findViewById(R.id.et_securityCode);
        btn_sendSecurityCode=(Button)findViewById(R.id.btn_sendSecurityCode);
        cb_agreeTerms=(CheckBox)findViewById(R.id.cb_agreeTerms);
        tv_terms=(TextView)findViewById(R.id.tv_terms);
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
            case R.id.tv_terms:
                startActivity(new Intent(RegisterActivity.this,TermsActivity.class));
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

        if(!TextUtils.isEmpty(txt_phoneNum) && !TextUtils.isEmpty(txt_newPassword) && cb_agreeTerms.isChecked()){
            BmobUser bmobUser=new BmobUser();
            bmobUser.setMobilePhoneNumber(txt_phoneNum);
            bmobUser.setPassword(txt_username);
            bmobUser.setUsername(txt_username);

            bmobUser.signUp(RegisterActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    showToast("注册成功");
                }

                @Override
                public void onFailure(int i, String s) {
                    showToast("注册失败:"+i+":"+s);
                }
            });
        }else{
            showToast("请完善信息并同意条款");
        }

        return ;
    }
}
