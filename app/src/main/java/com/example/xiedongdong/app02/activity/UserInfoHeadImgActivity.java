package com.example.xiedongdong.app02.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;


/**
 * Created by xiedongdong on 16/6/22.
 */
public class UserInfoHeadImgActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_back;
    private ImageView img_headImgSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_userinfoheadimg);

        initView();
        initSetListener();
    }

    private void initSetListener() {
        tv_back.setOnClickListener(this);
        img_headImgSource.setOnClickListener(this);
    }

    private void initView() {
        tv_back=(TextView)findViewById(R.id.tv_back);
        img_headImgSource=(ImageView)findViewById(R.id.img_headimgsource);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.img_headimgsource:
                showDialog();
                break;
            default:
                break;
        }

    }

    /**
     * 显示选择图片来源
     */
    private void showDialog() {

        AlertDialog dialog=new AlertDialog.Builder(this).create();  //创建一个Dialog
        View view=getLayoutInflater().inflate(R.layout.me_userinfoheadimg_source,null);  //自定义布局
        dialog.setView(view,0,0,0,0);  //把自定义布局添加到dialog中，从第二个参数开始分别表示填充内容与边缘之间的像素 左上右下。
        dialog.show();  //一定要在dialog，show之后在设置dialog的参数，不然会不会显示

        int width=getWindowManager().getDefaultDisplay().getWidth();  //获取当前设备的显示宽度
        WindowManager.LayoutParams params=dialog.getWindow().getAttributes();  //得到这个dialog界面的参数对象
        params.width= width;   //设置显示宽度和屏幕宽度相同
        params.height= WindowManager.LayoutParams.WRAP_CONTENT;//设置dialog的高度为包裹内容。
        params.gravity= Gravity.BOTTOM;  //设置重心为显示到最下面

        dialog.getWindow().setAttributes(params);// 将设置的内容与dialog绑定


    }
}
