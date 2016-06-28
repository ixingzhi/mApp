package com.example.xiedongdong.app02.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.News;
import com.example.xiedongdong.app02.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xiedongdong on 16/6/28.
 */
public class PublishNewsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_back;
    private TextView tv_publish;
    private EditText et_title;
    private EditText et_url;
    private EditText et_from;
    private RadioButton rbtn_disassembly,rbtn_openBox,rbtn_walker,rbtn_deskTopCulture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_add_publish);

        initView();
        initSetListener();
    }

    private void initView() {
        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_publish=(TextView)findViewById(R.id.tv_publish);
        et_title=(EditText)findViewById(R.id.et_title);
        et_url=(EditText)findViewById(R.id.et_url);
        et_from=(EditText)findViewById(R.id.et_from);
        rbtn_disassembly=(RadioButton) findViewById(R.id.rbtn_disassembly);
        rbtn_openBox=(RadioButton) findViewById(R.id.rbtn_openBox);
        rbtn_walker=(RadioButton) findViewById(R.id.rbtn_walker);
        rbtn_deskTopCulture=(RadioButton) findViewById(R.id.rbtn_deskTopCulture);

    }

    private void initSetListener() {
        tv_back.setOnClickListener(this);
        tv_publish.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_publish:
                if(checkFrom()){
                    publishNews();;
                }
                break;
            default:
                break;
        }

    }

    //检查输入信息来源
    private boolean checkFrom() {
        String txt_title=et_title.getText().toString().trim();
        String txt_url=et_url.getText().toString().trim();
        String txt_from=et_from.getText().toString().trim();

        if(TextUtils.isEmpty(txt_title)){
            showToast("标题不能为空");
            return false;
        }
        if(TextUtils.isEmpty(txt_url)){
            showToast("网址不能为空");
            return false;
        }
        if(TextUtils.isEmpty(txt_from)){
            showToast("来自不能为空");
            return false;
        }
        if(txt_title.length()>25){
            showToast("标题不能大于25个字符");
            return false;
        }
        if(txt_from.length()>14){
            showToast("来自不能大于14个字符");
            return false;
        }
        if(!rbtn_disassembly.isChecked() && !rbtn_openBox.isChecked() && !rbtn_walker.isChecked() &&
                !rbtn_deskTopCulture.isChecked()){
            showToast("没有选择分类");
            return false;
        }


        return true;
    }
    /**发布消息**/
    private void publishNews() {
        User user= BmobUser.getCurrentUser(PublishNewsActivity.this,User.class);
        String txt_objectId=user.getObjectId();

        String txt_title=et_title.getText().toString().trim();
        String txt_url=et_url.getText().toString().trim();
        String txt_from=et_from.getText().toString().trim();

        String txt_messageType=null;
        if(rbtn_disassembly.isChecked()){
            txt_messageType=rbtn_disassembly.getText().toString().trim();
        }
        if(rbtn_openBox.isChecked()){
            txt_messageType=rbtn_openBox.getText().toString().trim();
        }
        if(rbtn_walker.isChecked()){
            txt_messageType=rbtn_walker.getText().toString().trim();
        }
        if(rbtn_deskTopCulture.isChecked()){
            txt_messageType=rbtn_deskTopCulture.getText().toString().trim();
        }

        News news=new News();
        news.setId(txt_objectId);
        news.setTitle(txt_title);
        news.setUrl(txt_url);
        news.setFrom(txt_from);
        news.setMessageType(txt_messageType);

        news.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                showToast("发送消息成功");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("发送消息失败:"+s);
            }
        });



    }
}
