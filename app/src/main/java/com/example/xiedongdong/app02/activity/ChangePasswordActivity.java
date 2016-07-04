package com.example.xiedongdong.app02.activity;

import android.app.ProgressDialog;
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
import com.example.xiedongdong.app02.util.TimeCount;


import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by xiedongdong on 16/6/2.
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_back;
    private TextView tv_commit;
    private TextView tv_currentUser;
    private TextView tv_phoneNumber;
    private EditText et_newPassword;
    private EditText et_securityCode;
    private TextView tv_sendSecurityCode;

    boolean blag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_changepassword);

        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(this);

        tv_currentUser = (TextView) findViewById(R.id.tv_currentUser);
        tv_currentUser.setOnClickListener(this);

        tv_phoneNumber=(TextView) findViewById(R.id.tv_phoneNumber);

        et_newPassword=(EditText)findViewById(R.id.et_newPassword);

        et_securityCode=(EditText)findViewById(R.id.et_securityCode);

        tv_sendSecurityCode=(TextView)findViewById(R.id.tv_sendSecurityCode);
        tv_sendSecurityCode.setOnClickListener(this);


        initData();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_currentUser:
                if (isLogin()) {
                    startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                }
                break;
            case R.id.tv_sendSecurityCode:
                sendSecurityCode();
                break;
            case R.id.tv_commit:
                if (checkFrom()==true) {
                    Log.e("ChangePasswordActivity","通过验证，进入修改密码");
                    alterPassword();
                }
            default:
                break;
        }
    }

    /**
     * 初始化数据,读取用户信息
     */
    private void initData() {
        BmobUser userInfo = BmobUser.getCurrentUser(ChangePasswordActivity.this);
        if (userInfo != null) {
            tv_currentUser.setText(userInfo.getUsername());
            tv_phoneNumber.setText(userInfo.getMobilePhoneNumber());
        }
    }

    /**
     * 从缓存中检测是否有用户登录
     */

    public boolean isLogin() {
        BmobUser userInfo = BmobUser.getCurrentUser(ChangePasswordActivity.this);
        if (userInfo == null) {
            return true;
        }
        return false;
    }

    /**
     * 发送验证码
     */

    private void sendSecurityCode() {
        String txt_phoneNum=tv_phoneNumber.getText().toString().trim();

        if(!TextUtils.isEmpty(txt_phoneNum)){
            Bmob.requestSMSCode(ChangePasswordActivity.this, txt_phoneNum, "mAppSMS", new RequestSMSCodeListener() {

                @Override
                public void done(Integer smsId, BmobException ex) {
                    if(ex==null){
                        showToast("短信发送成功");
                        new TimeCount(ChangePasswordActivity.this,60000,1000,tv_sendSecurityCode).start();
                    }
                }
            });
        }

    }
    /**
     * 验证验证码是否正确
     */

    private boolean checkSecurityCode() {
        String txt_phoneNum = tv_phoneNumber.getText().toString().trim();
        String txt_securityCode = et_securityCode.getText().toString().trim();

        Bmob.verifySmsCode(ChangePasswordActivity.this, txt_phoneNum, txt_securityCode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.e("ChangePasswordActivity","验证码验证成功");
                    blag=true;
                }else{
                    Log.e("ChangePasswordActivity","验证码错误");
                    blag=false;

                }
            }
        });
        return blag;
    }


    /**
     * 检查信息来源
     */

    private boolean checkFrom() {
        String txt_phoneNumber=(tv_phoneNumber).getText().toString().trim();
        String txt_newPassword = (et_newPassword).getText().toString().trim();
        String txt_securityCode=(et_securityCode).getText().toString().trim();

        Log.d("blag",""+blag);

        /**
         * &&和&都是表示与，区别是&&只要满足第一个条件，后面条件就不再判断。而&要对所有的条件都进行判断。
         */
        if (isLogin()) {
            showToast("未登录，登陆后修改");
            return (false);
        }if (TextUtils.isEmpty(txt_phoneNumber)){
            showToast("信息未完善");
            return (false);
        }if (TextUtils.isEmpty(txt_newPassword)){
            showToast("信息未完善");
            return (false);
        }if (TextUtils.isEmpty(txt_securityCode)){
            showToast("未填写验证码");
            return (false);
        }if (txt_newPassword.length()<7){
            showToast("新密码须大于7个字符");
            return (false);
        }if (checkSecurityCode()==false){
            showToast("验证码错误");
            return (false);
        }

        return true;
    }

    /**
     * 修改密码
     */

    private void alterPassword() {

        String txt_newPassword=(et_newPassword).getText().toString().trim();
        String txt_ObjectId=BmobUser.getCurrentUser(ChangePasswordActivity.this).getObjectId();
        BmobUser bmobUser=new BmobUser();
        bmobUser.setPassword(txt_newPassword);
        bmobUser.update(ChangePasswordActivity.this,txt_ObjectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                showToast("修改密码成功");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("修改密码失败"+s);
            }
        });

    }



}
