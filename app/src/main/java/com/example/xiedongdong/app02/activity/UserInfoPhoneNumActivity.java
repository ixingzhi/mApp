package com.example.xiedongdong.app02.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.User;
import com.example.xiedongdong.app02.util.TimeCount;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by xiedongdong on 16/6/21.
 */
public class UserInfoPhoneNumActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_cancel,tv_save,tv_currentPhoneNum,tv_sendSecurityCode;
    private EditText et_phoneNum,et_securityCode;

    private boolean blag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_userinfo_phonenum);
        initView();
        initEvent();
        initData();

    }

    private void initData() {
        User user=BmobUser.getCurrentUser(UserInfoPhoneNumActivity.this,User.class);
        tv_currentPhoneNum.setText(user.getMobilePhoneNumber());

    }

    private void initEvent() {
        tv_cancel.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_sendSecurityCode.setOnClickListener(this);
    }

    private void initView() {
        tv_cancel=(TextView)findViewById(R.id.tv_cancel);
        tv_save=(TextView)findViewById(R.id.tv_save);
        tv_currentPhoneNum=(TextView)findViewById(R.id.tv_currentPhoneNum);
        et_phoneNum=(EditText)findViewById(R.id.et_phoneNum);
        et_securityCode=(EditText)findViewById(R.id.et_securityCode);
        tv_sendSecurityCode=(TextView)findViewById(R.id.tv_sendSecurityCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                UserInfoPhoneNumActivity.this.finish();
                break;
            case R.id.tv_save:
                if(checkInfoFrom() && checkSecurityCode()){
                    upDataPhoneNum();
                }

                break;
            case R.id.tv_sendSecurityCode:
                if(checkInfoFrom()){
                    sendSecurityCode();
                }

                break;
            default:
                break;
        }

    }

    /**
     * 发送验证码前判断是否输入手机号
     * @return
     */
    private boolean checkInfoFrom() {
        String txt_phoneNum=et_phoneNum.getText().toString().trim();
        if(TextUtils.isEmpty(txt_phoneNum)){
            showToast("手机号不能为空");
            return false;
        }
        return true;
    }

    /**
     * 发送验证码
     */
    private void sendSecurityCode() {
        String txt_phoneNum=et_phoneNum.getText().toString().trim();

        if(!TextUtils.isEmpty(txt_phoneNum)){
            Bmob.requestSMSCode(UserInfoPhoneNumActivity.this, txt_phoneNum, "mAppSMS", new RequestSMSCodeListener() {
                @Override
                public void done(Integer smsId, BmobException ex) {
                    if(ex==null){
                        showToast("短信发送成功");
                        new TimeCount(UserInfoPhoneNumActivity.this,60000,1000,tv_sendSecurityCode).start();
                    }
                }
            });
        }
    }

    /**
     * 验证验证码是否正确
     */

    private boolean checkSecurityCode() {
        String txt_phoneNum = et_phoneNum.getText().toString().trim();
        String txt_securityCode = et_securityCode.getText().toString().trim();

        Bmob.verifySmsCode(UserInfoPhoneNumActivity.this, txt_phoneNum, txt_securityCode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.e("RegisterActivity","验证码验证成功");
                    blag=true;
                }else{
                    showToast("验证码错误");
                    Log.e("RegisterActivity","验证码错误");
                    blag=false;

                }
            }
        });
        return blag;
    }

    /**
     * 保存手机号
     */
    private void upDataPhoneNum() {
        String txt_phoneNum=et_phoneNum.getText().toString().trim();
        String txt_objectId=BmobUser.getCurrentUser(UserInfoPhoneNumActivity.this,User.class).getObjectId();
        User user=new User();
        user.setMobilePhoneNumber(txt_phoneNum);
        user.update(UserInfoPhoneNumActivity.this, txt_objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("修改手机号失败：",""+s);
            }
        });


    }
}
