package com.example.xiedongdong.app02.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;

/**
 * Created by xiedongdong on 16/6/2.
 */
public class MoreFunctionActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_back;
    private LinearLayout ll_disappeared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_morefunction);

        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        ll_disappeared=(LinearLayout)findViewById(R.id.ll_disappeared);
        ll_disappeared.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
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
                /*
                执行删除用户，并清理缓存，返回到主页。
                 */
                if(isLogin()){
                    deleteUser();
                }
            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.show();
    }

    /**
     * 检测是否登录
     */
    public boolean isLogin() {
        User userInfo= BmobUser.getCurrentUser(MoreFunctionActivity.this,User.class);
        if(userInfo==null){
            showToast("未登录状态");
            return false;
        }
        return true;
    }
    /**
     * 删除用户
     */
    private void deleteUser() {
        final ProgressDialog progress=new ProgressDialog(MoreFunctionActivity.this);
        progress.setMessage("清除用户数据中...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        String txt_objectId= BmobUser.getCurrentUser(MoreFunctionActivity.this).getObjectId();
        User deleteUser=new User();
        deleteUser.setObjectId(txt_objectId);
        deleteUser.delete(MoreFunctionActivity.this, new DeleteListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            progress.setMessage("删除用户成功");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                progress.dismiss();
                BmobUser.logOut(MoreFunctionActivity.this);
                startActivity(new Intent(MoreFunctionActivity.this,MainActivity.class));
            }

            @Override
            public void onFailure(int i, String s) {
                progress.dismiss();
                showToast("删除失败");
            }
        });

    }


}
