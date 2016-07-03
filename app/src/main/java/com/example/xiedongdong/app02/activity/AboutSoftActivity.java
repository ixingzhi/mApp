package com.example.xiedongdong.app02.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;

/**
 * Created by xiedongdong on 16/6/2.
 */
public class AboutSoftActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_aboutsoft);

        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }

    }
}
