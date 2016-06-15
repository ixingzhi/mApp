package com.example.xiedongdong.app02.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;

/**
 * Created by xiedongdong on 16/6/2.
 */
public class MoreFunctionActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout ll_disappeared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_morefunction);

        ll_disappeared=(LinearLayout)findViewById(R.id.ll_disappeared);
        ll_disappeared.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_disappeared:
                alertDialog();
                break;
            default:
                break;
        }

    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(MoreFunctionActivity.this);
        dialog.setTitle("重要的事说一遍！");
        dialog.setMessage("将会彻底删除该用户的所有信息，包括注册信息！");
        //设置该对话窗是否可取消
        dialog.setCancelable(false);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.show();
    }
}
