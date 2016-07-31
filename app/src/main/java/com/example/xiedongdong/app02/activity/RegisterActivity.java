package com.example.xiedongdong.app02.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.User;
import com.example.xiedongdong.app02.util.TimeCount;


import cn.bmob.v3.Bmob;
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
    private TextView tv_getSecurityCode;
    private CheckBox cb_agreeTerms;
    private TextView tv_terms;
    private Button btn_register;
    private TextView tv_existAccount;

    //验证码正确
    private static final int CODE_RIGHT=0;
    //验证码错误
    private static final int CODE_ERROR=1;

    ProgressDialog progress;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CODE_RIGHT:
                    Log.e("code","验证码正确");
                    //验证码正确，注册账号
                    register();
                    break;
                case CODE_ERROR:
                    progress.dismiss();
                    showToast("注册失败，验证码错误");
                    break;
                default:
                    break;

            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initEvent();

        cb_agreeTerms.setChecked(true);
    }

    private void initEvent() {
        tv_getSecurityCode.setOnClickListener(this);
        tv_terms.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_existAccount.setOnClickListener(this);

    }

    private void initView() {
        et_phoneNum=(EditText)findViewById(R.id.et_phoneNum);
        et_newPassword=(EditText)findViewById(R.id.et_newPassword);
        et_usernmae=(EditText)findViewById(R.id.et_userName);
        et_securityCode=(EditText)findViewById(R.id.et_securityCode);
        tv_getSecurityCode=(TextView) findViewById(R.id.tv_getSecurityCode);
        cb_agreeTerms=(CheckBox)findViewById(R.id.cb_agreeTerms);
        tv_terms=(TextView)findViewById(R.id.tv_terms);
        btn_register=(Button)findViewById(R.id.btn_register);
        tv_existAccount=(TextView)findViewById(R.id.tv_existAccount);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_getSecurityCode:
                //获取验证码
                getSecurityCode();
                break;
            case R.id.btn_register:
                checkInfoFrom();
                break;
            case R.id.tv_terms:
                startActivity(new Intent(RegisterActivity.this,TermsActivity.class));
                break;
            case R.id.tv_existAccount:
                finish();
                break;
            default:
                break;
        }

    }

    /**
     * 获取验证码
     */

    private void getSecurityCode() {
        String txt_phoneNum=et_phoneNum.getText().toString().trim();

        if(!TextUtils.isEmpty(txt_phoneNum)){
            Bmob.requestSMSCode(RegisterActivity.this, txt_phoneNum, "mAppSMS", new RequestSMSCodeListener() {
                @Override
                public void done(Integer smsId, BmobException ex) {
                    if(ex==null){
                        showToast("短信发送成功");
                        new TimeCount(RegisterActivity.this,60000,1000,tv_getSecurityCode).start();
                    }
                }
            });
        }else{
            showToast("请输入手机号");
        }

    }
    /**
     * 验证验证码是否正确
     */

    private void checkSecurityCode() {
        String txt_phoneNum = et_phoneNum.getText().toString().trim();
        String txt_securityCode = et_securityCode.getText().toString().trim();

        //显示注册进度条，防止用户重复点击注册
        progress=new ProgressDialog(RegisterActivity.this);
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.setMessage("正在注册中...");
        progress.show();

        Bmob.verifySmsCode(RegisterActivity.this, txt_phoneNum, txt_securityCode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //验证码正确，注册账号
                    handler.sendEmptyMessage(CODE_RIGHT);
                    Log.e("RegisterActivity","验证码验证成功");
                }else{
                    handler.sendEmptyMessage(CODE_ERROR);
                    Log.e("RegisterActivity","验证码错误");

                }
            }
        });

    }

    /**
     * 检查输入信息是否正确
     * @return
     */
    private void checkInfoFrom() {
        String txt_phoneNum = et_phoneNum.getText().toString().trim();
        String txt_newPassword = et_newPassword.getText().toString().trim();
        String txt_username = et_usernmae.getText().toString().trim();
        String txt_securityCode = et_securityCode.getText().toString().trim();

        if (TextUtils.isEmpty(txt_phoneNum)){

            showToast("请完善信息");
        }
        else if(TextUtils.isEmpty(txt_newPassword)){
            showToast("请完善信息");
        }
        else if(txt_newPassword.length()<6){
            showToast("密码需大于等于6位");
        }
        else if(TextUtils.isEmpty(txt_username)) {
            showToast("请完善信息");
        }
        else if(txt_username.length()<2){
            showToast("用户名需大于2个字符");
        }
        else if(TextUtils.isEmpty(txt_securityCode)) {
            showToast("请获取验证码");
        }
        else if (!cb_agreeTerms.isChecked()) {
            showToast("未同意条款");
        }
        else {
            //最后检查验证码是否正确，需要在子线程冲检查。
            checkSecurityCode();

        }

    }



    /**
     * 注册
     */
    public void register(){
        String txt_phoneNum = et_phoneNum.getText().toString().trim();
        String txt_username = et_usernmae.getText().toString().trim();
        String txt_password = et_newPassword.getText().toString().trim();

        User user=new User();
        user.setMobilePhoneNumber(txt_phoneNum);
        user.setPassword(txt_password);
        user.setUsername(txt_username);
        user.setSex("未知");
        user.setLocation("江苏无锡");
        user.setAutograph("无");
        user.setHeadImgUrl("");
        user.setHeadImgFileUrl("");

        user.signUp(RegisterActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                progress.dismiss();
                showToast("注册成功");
                finish();
                Log.d("RegisterActivity","注册成功");
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("注册失败:"+s);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

