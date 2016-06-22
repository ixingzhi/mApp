package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.User;

import cn.bmob.v3.BmobUser;


/**
 * Created by xiedongdong on 16/6/19.
 */
public class UserInfo extends BaseActivity implements View.OnClickListener{
    private TextView tv_userInfo_name,tv_userInfo_sex,tv_userInfo_phoneNum,
            tv_userInfo_email,tv_userInfo_location,tv_userInfo_autograph;
    private RelativeLayout rl_userInfo_head,rl_userInfo_name,rl_userInfo_sex,rl_userInfo_phoneNum,
            rl_userInfo_email,rl_userInfo_location,rl_userInfo_autograph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_userinfo);
        initView();
        initEvent();
        initData();

    }

    private void initView() {
        tv_userInfo_name=(TextView)findViewById(R.id.tv_userInfo_name);
        tv_userInfo_sex=(TextView)findViewById(R.id.tv_userInfo_sex);
        tv_userInfo_phoneNum=(TextView)findViewById(R.id.tv_userInfo_phoneNum);
        tv_userInfo_email=(TextView)findViewById(R.id.tv_userInfo_email);
        tv_userInfo_location=(TextView)findViewById(R.id.tv_userInfo_location);
        tv_userInfo_autograph=(TextView)findViewById(R.id.tv_userInfo_autograph);

        rl_userInfo_head=(RelativeLayout)findViewById(R.id.rl_userInfo_head);
        rl_userInfo_name=(RelativeLayout)findViewById(R.id.rl_userInfo_name);
        rl_userInfo_sex=(RelativeLayout)findViewById(R.id.rl_userInfo_sex);
        rl_userInfo_phoneNum=(RelativeLayout)findViewById(R.id.rl_userInfo_phoneNum);
        rl_userInfo_email=(RelativeLayout)findViewById(R.id.rl_userInfo_email);
        rl_userInfo_location=(RelativeLayout)findViewById(R.id.rl_userInfo_location);
        rl_userInfo_autograph=(RelativeLayout)findViewById(R.id.rl_userInfo_autograph);

    }

    private void initEvent() {
        rl_userInfo_head.setOnClickListener(this);
        rl_userInfo_name.setOnClickListener(this);
        rl_userInfo_sex.setOnClickListener(this);
        rl_userInfo_phoneNum.setOnClickListener(this);
        rl_userInfo_email.setOnClickListener(this);
        rl_userInfo_location.setOnClickListener(this);
        rl_userInfo_autograph.setOnClickListener(this);
    }


    /**
     * 初始化时获取用户数据
     */
    private void initData() {
        User user=BmobUser.getCurrentUser(UserInfo.this,User.class);
        if(user!=null){
            tv_userInfo_name.setText(user.getUsername());
            Log.d("name",""+user.getUsername());
            tv_userInfo_sex.setText(user.getSex());
            tv_userInfo_phoneNum.setText(user.getMobilePhoneNumber());
            tv_userInfo_email.setText(user.getEmail());
            tv_userInfo_location.setText(user.getLocation());
            tv_userInfo_autograph.setText(user.getAutograph());
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.rl_userInfo_head:
                startActivity(new Intent(UserInfo.this,UserInfoHeadImgActivity.class));
                break;
            case R.id.rl_userInfo_name:
                startActivity(new Intent(UserInfo.this,UserInfoNameActivity.class));
                break;
            case R.id.rl_userInfo_sex:
                startActivity(new Intent(UserInfo.this,UserInfoSexActivity.class));
                break;
            case R.id.rl_userInfo_phoneNum:
                startActivity(new Intent(UserInfo.this,UserInfoPhoneNumActivity.class));
                break;
            case R.id.rl_userInfo_email:
                startActivity(new Intent(UserInfo.this,UserInfoEmailActivity.class));
                break;
            case R.id.rl_userInfo_location:

                break;
            case R.id.rl_userInfo_autograph:
                startActivity(new Intent(UserInfo.this,UserInfoAutographActivity.class));
                break;
            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        User user=BmobUser.getCurrentUser(UserInfo.this,User.class);
        tv_userInfo_name.setText(user.getUsername());
        tv_userInfo_sex.setText(user.getSex());
        tv_userInfo_phoneNum.setText(user.getMobilePhoneNumber());
        tv_userInfo_email.setText(user.getEmail());
        tv_userInfo_location.setText(user.getLocation());
        tv_userInfo_autograph.setText(user.getAutograph());

    }
}
