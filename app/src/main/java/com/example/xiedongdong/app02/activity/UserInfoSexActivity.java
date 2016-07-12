package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xiedongdong on 16/6/20.
 */
public class UserInfoSexActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_selectMan,rl_selectWoman,rl_selectUnknown;
    private TextView tv_man,tv_woman,tv_unknown,
                     tv_selectMan,tv_selectWoman,tv_selectUnknown;
    private TextView tv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_userinfo_sex);

        initView();
        initEvent();
        initData();
    }

    private void initView() {
        rl_selectMan=(RelativeLayout)findViewById(R.id.rl_selectman);
        rl_selectWoman=(RelativeLayout)findViewById(R.id.rl_selectwoman);
        rl_selectUnknown=(RelativeLayout)findViewById(R.id.rl_selectunknown);
        tv_man=(TextView)findViewById(R.id.tv_man);
        tv_woman=(TextView)findViewById(R.id.tv_woman);
        tv_unknown=(TextView)findViewById(R.id.tv_unknown);
        tv_selectMan=(TextView)findViewById(R.id.tv_selectman);
        tv_selectWoman=(TextView)findViewById(R.id.tv_selectwoman);
        tv_selectUnknown=(TextView)findViewById(R.id.tv_selectunknown);

        tv_back=(TextView)findViewById(R.id.tv_back);
    }

    private void initEvent() {
        rl_selectMan.setOnClickListener(this);
        rl_selectWoman.setOnClickListener(this);
        rl_selectUnknown.setOnClickListener(this);

        tv_back.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        User user=BmobUser.getCurrentUser(UserInfoSexActivity.this,User.class);
        String txt_sex=user.getSex();
        if(txt_sex.equals("男")){
            tv_selectMan.setTextColor(Color.GREEN);
        }
        if(txt_sex.equals("女")){
            tv_selectWoman.setTextColor(Color.GREEN);
        }
        if(txt_sex.equals("未知")){
            tv_selectUnknown.setTextColor(Color.GREEN);
        }
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.rl_selectman:
                //每次选择开始初始化选择的颜色为灰色
                initSelect();
                saveSexMan();
                break;
            case R.id.rl_selectwoman:
                //每次选择开始初始化选择的颜色为灰色
                initSelect();
                saveSexWoman();
                break;
            case R.id.rl_selectunknown:
                //每次选择开始初始化选择的颜色为灰色
                initSelect();
                SaveSexUnknown();
                break;
            case R.id.tv_back:
                UserInfoSexActivity.this.finish();
                break;
            default:
                break;

        }
    }

    private void initSelect() {
        tv_selectMan.setTextColor(Color.GRAY);
        tv_selectWoman.setTextColor(Color.GRAY);
        tv_selectUnknown.setTextColor(Color.GRAY);
    }

    private void SaveSexUnknown() {
        String txt_objectId=BmobUser.getCurrentUser(UserInfoSexActivity.this).getObjectId();
        String txt_unknown=tv_unknown.getText().toString().trim();
        User user=new User();
        user.setSex(txt_unknown);
        user.update(UserInfoSexActivity.this, txt_objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.d("sex:","保存性别（未知）成功");
                tv_selectUnknown.setTextColor(Color.GREEN);
            }
            @Override
            public void onFailure(int i, String s) {
                Log.d("sex:","保存性别失败"+s);
            }
        });
    }

    private void saveSexWoman() {
        String txt_objectId=BmobUser.getCurrentUser(UserInfoSexActivity.this).getObjectId();
        String txt_woman=tv_woman.getText().toString().trim();
        User user=new User();
        user.setSex(txt_woman);
        user.update(UserInfoSexActivity.this, txt_objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.d("sex:","保存性别（女）成功");
                tv_selectWoman.setTextColor(Color.GREEN);
            }
            @Override
            public void onFailure(int i, String s) {
                Log.d("sex:","保存性别失败"+s);
            }
        });
    }

    private void saveSexMan() {
        String txt_objectId=BmobUser.getCurrentUser(UserInfoSexActivity.this).getObjectId();
        String txt_man=tv_man.getText().toString().trim();
        User user=new User();
        user.setSex(txt_man);
        user.update(UserInfoSexActivity.this, txt_objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.d("sex:","保存性别（男）成功");
                tv_selectMan.setTextColor(Color.GREEN);
            }
            @Override
            public void onFailure(int i, String s) {
                Log.d("sex:","保存性别失败"+s);
            }
        });
    }
}
