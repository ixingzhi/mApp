package com.example.xiedongdong.app02.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private TextView tv_cancel,tv_save,tv_currentPhoneNum,tv_getSecurityCode;
    private EditText et_phoneNum,et_securityCode;

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
                    updataPhoneNum();
                    break;
                case CODE_ERROR:
                    progress.dismiss();
                    showToast("修改失败，验证码错误");
                    break;
                default:
                    break;

            }
        }
    };

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
        tv_getSecurityCode.setOnClickListener(this);
    }

    private void initView() {
        tv_cancel=(TextView)findViewById(R.id.tv_cancel);
        tv_save=(TextView)findViewById(R.id.tv_save);
        tv_currentPhoneNum=(TextView)findViewById(R.id.tv_currentPhoneNum);
        et_phoneNum=(EditText)findViewById(R.id.et_phoneNum);
        et_securityCode=(EditText)findViewById(R.id.et_securityCode);
        tv_getSecurityCode=(TextView)findViewById(R.id.tv_getSecurityCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                UserInfoPhoneNumActivity.this.finish();
                break;
            case R.id.tv_save:
                checkInfoFrom();
                break;
            case R.id.tv_getSecurityCode:
                getSecurityCode();

                break;
            default:
                break;
        }

    }

    /**
     * 获取验证码前判断是否输入手机号
     * @return
     */
    private void checkInfoFrom() {
        String txt_phoneNum=et_phoneNum.getText().toString().trim();
        String txt_SecurityCode=et_securityCode.getText().toString().trim();
        if(TextUtils.isEmpty(txt_phoneNum)){
            showToast("手机号不能为空");
        }
        else if(TextUtils.isEmpty(txt_SecurityCode)){
            showToast("验证码不能为空");
        }
        else{
            checkSecurityCode();
        }
    }

    /**
     * 发送验证码
     */
    private void getSecurityCode() {
        String txt_phoneNum=et_phoneNum.getText().toString().trim();

        if(!TextUtils.isEmpty(txt_phoneNum)){
            Bmob.requestSMSCode(UserInfoPhoneNumActivity.this, txt_phoneNum, "mAppSMS", new RequestSMSCodeListener() {
                @Override
                public void done(Integer smsId, BmobException ex) {
                    if(ex==null){
                        showToast("短信发送成功");
                        new TimeCount(UserInfoPhoneNumActivity.this,60000,1000,tv_getSecurityCode).start();
                    }
                }
            });
        }else{
            showToast("请填写手机号");
        }
    }

    /**
     * 验证验证码是否正确
     */

    private void checkSecurityCode() {
        String txt_phoneNum = et_phoneNum.getText().toString().trim();
        String txt_securityCode = et_securityCode.getText().toString().trim();

        //显示注册进度条，防止用户重复点击
        progress=new ProgressDialog(UserInfoPhoneNumActivity.this);
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.setMessage("正在修改中...");
        progress.show();

        Bmob.verifySmsCode(UserInfoPhoneNumActivity.this, txt_phoneNum, txt_securityCode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
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
     * 保存手机号
     */
    private void updataPhoneNum() {
        String txt_phoneNum=et_phoneNum.getText().toString().trim();
        String txt_objectId=BmobUser.getCurrentUser(UserInfoPhoneNumActivity.this,User.class).getObjectId();
        User user=new User();
        user.setMobilePhoneNumber(txt_phoneNum);
        user.update(UserInfoPhoneNumActivity.this, txt_objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                progress.dismiss();
                showToast("修改手机号成功");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("修改手机号失败：",""+s);
            }
        });


    }
}
