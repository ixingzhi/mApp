package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.util.TimeCount;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by xiedongdong on 16/5/30.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_phoneNum;
    private EditText et_newPassword;
    private EditText et_usernmae;
    private EditText et_securityCode;
    private TextView tv_sendSecurityCode;
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
        tv_sendSecurityCode.setOnClickListener(this);
        tv_terms.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_existAccount.setOnClickListener(this);

    }

    private void initView() {
        et_phoneNum=(EditText)findViewById(R.id.et_phoneNum);
        et_newPassword=(EditText)findViewById(R.id.et_newPassword);
        et_usernmae=(EditText)findViewById(R.id.et_userName);
        et_securityCode=(EditText)findViewById(R.id.et_securityCode);
        tv_sendSecurityCode=(TextView) findViewById(R.id.tv_sendSecurityCode);
        cb_agreeTerms=(CheckBox)findViewById(R.id.cb_agreeTerms);
        tv_terms=(TextView)findViewById(R.id.tv_terms);
        btn_register=(Button)findViewById(R.id.btn_register);
        tv_existAccount=(TextView)findViewById(R.id.tv_existAccount);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_sendSecurityCode:
                //发送验证码
                sendSecurityCode();
                break;
            case R.id.btn_register:
                if(checkInfoFrom()){

                }
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

    /**
     * 发送验证码
     */

    private void sendSecurityCode() {
        String txt_phoneNum=et_phoneNum.getText().toString().trim();
        new TimeCount(RegisterActivity.this,60000,1000,tv_sendSecurityCode).start();

        if(!TextUtils.isEmpty(txt_phoneNum)){
            Bmob.requestSMSCode(RegisterActivity.this, txt_phoneNum, "mAppSMS", new RequestSMSCodeListener() {
                @Override
                public void done(Integer smsId, BmobException ex) {
                    if(ex==null){
                        showToast("短信发送成功");

                    }
                }
            });
        }else{
            showToast("请输入手机号");
        }

    }

    private boolean checkInfoFrom() {
        String txt_phoneNum=et_phoneNum.getText().toString().trim();
        String txt_newPassword=et_newPassword.getText().toString().trim();
        String txt_username=et_usernmae.getText().toString().trim();
        String txt_securityCode=et_securityCode.getText().toString().trim();

        /**
         * 验证手机验证码是否正确
         */
        if(!TextUtils.isEmpty(txt_securityCode)){
            Bmob.verifySmsCode(RegisterActivity.this, txt_phoneNum, txt_securityCode, new VerifySMSCodeListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        Log.e("RegisterActivity","验证码验证成功");
                    }else{
                        showToast("验证码错误"+e);
                    }
                }
            });
        }
        else if(cb_agreeTerms.isChecked()){

        }

        else if(!TextUtils.isEmpty(txt_phoneNum) && !TextUtils.isEmpty(txt_newPassword)){
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
        }

        else{
            showToast("请完善信息并同意条款");
            return (false);
        }



        return true;
    }



}

