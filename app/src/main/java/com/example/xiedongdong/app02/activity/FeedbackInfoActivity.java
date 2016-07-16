package com.example.xiedongdong.app02.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.FeedbackInfo;
import com.example.xiedongdong.app02.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xiedongdong on 16/7/15.
 */
public class FeedbackInfoActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_back;
    private TextView tv_commit;
    private EditText et_feedbackInfo;
    private EditText et_contactInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_aboutsoft_userfeedback);

        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        tv_commit=(TextView)findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(this);
        et_feedbackInfo=(EditText)findViewById(R.id.et_feedbackInfo);
        et_contactInfo=(EditText)findViewById(R.id.et_contactInfo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_commit:
                //提交反馈信息
                if(checkInfoFrom()){
                    commitFeedbackInfo();
                }
                finish();
                break;
            default:
                break;
        }
    }

    private boolean checkInfoFrom() {
        String txt_feedbackInfo=et_feedbackInfo.getText().toString().trim();
        if(TextUtils.isEmpty(txt_feedbackInfo)){
            showToast("反馈信息不能为空");
            return false;
        }

        return true;
    }

    private void commitFeedbackInfo() {

        String txt_feedbackInfo=et_feedbackInfo.getText().toString().trim();
        String txt_contactInfo=et_contactInfo.getText().toString().trim();

        FeedbackInfo feedbackInfo=new FeedbackInfo();
        feedbackInfo.setFeedbackInfo(txt_feedbackInfo);
        feedbackInfo.setContactInfo(txt_contactInfo);

        feedbackInfo.save(FeedbackInfoActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                showToast("提交信息成功");
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("提交信息失败");
            }
        });

    }
}
