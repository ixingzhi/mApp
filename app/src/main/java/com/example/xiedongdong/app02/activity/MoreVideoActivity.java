package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;

/**
 * Created by xiedongdong on 16/7/17.
 */
public class MoreVideoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;

    private ImageView img_moreVideo01;
    private ImageView img_moreVideo02;
    private ImageView img_moreVideo03;
    private ImageView img_moreVideo04;
    private ImageView img_moreVideo05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_morevideo);

        img_back=(ImageView)findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        img_moreVideo01= (ImageView) findViewById(R.id.img_moreVideo01);
        img_moreVideo01.setOnClickListener(this);

        img_moreVideo02= (ImageView) findViewById(R.id.img_moreVideo02);
        img_moreVideo02.setOnClickListener(this);

        img_moreVideo03= (ImageView) findViewById(R.id.img_moreVideo03);
        img_moreVideo03.setOnClickListener(this);

        img_moreVideo04= (ImageView) findViewById(R.id.img_moreVideo04);
        img_moreVideo04.setOnClickListener(this);

        img_moreVideo05= (ImageView) findViewById(R.id.img_moreVideo05);
        img_moreVideo05.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.img_moreVideo01:
                String url01="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=401188726&idx=1&sn" +
                        "=4ed51fccbf0f338d4350e41883743f47&scene=21&uin=MjAzMzU5NTQyMQ%3D%3D&key=7742" +
                        "1cf58af4a653a90e7a65a48b9ba24aa88d7491f0a3baa6db81eeefcbda6b05d17d661ce03bcb6" +
                        "aab21ef8b958867&devicetype=iPhone+OS10.0&version=16031610&lang=zh_CN&nettype=3" +
                        "G+&fontScale=100&pass_ticket=DGQHy%2FI%2F6JOvZoFSrzmyBpbPRzDKgFAEmpGjjBGKnj%2Bg" +
                        "EztO9phZm2L090t0pXZb";
                throwUrlOpenWeb(url01);
                break;
            case R.id.img_moreVideo02:
                String url02="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=505705381&idx=1&sn=" +
                        "af9a4629be25e8f1bcadff98f81eb325&scene=18&uin=MjAzMzU5NTQyMQ%3D%3D&key=77421cf5" +
                        "8af4a6530b0114fd830ae829a42e3d26df2f588805f118e607d0aa159877c93c7e8260ee0a51b4afe7" +
                        "9281cd&devicetype=iPhone+OS10.0&version=16031610&lang=zh_CN&nettype=3G+&fontScale=" +
                        "100&pass_ticket=DGQHy%2FI%2F6JOvZoFSrzmyBpbPRzDKgFAEmpGjjBGKnj%2BgEztO9phZm2L090t0pXZb";
                throwUrlOpenWeb(url02);
                break;
            case R.id.img_moreVideo03:
                String url03="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=505705258&idx=1&sn=11db05774" +
                        "72103756f4f72849dece934&scene=18&uin=MjAzMzU5NTQyMQ%3D%3D&key=77421cf58af4a653588ce9" +
                        "442aaa669cb813aae8a6dfa0dcfa78631d49613277cb11f19db451ca4ef921c86c4a202575&devicetype=iP" +
                        "hone+OS10.0&version=16031610&lang=zh_CN&nettype=WIFI&fontScale=100&pass_ticket=DGQHy%2FI%" +
                        "2F6JOvZoFSrzmyBpbPRzDKgFAEmpGjjBGKnj%2BgEztO9phZm2L090t0pXZb";
                throwUrlOpenWeb(url03);
                break;
            case R.id.img_moreVideo04:
                String url04="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=505705864&idx=1&sn=7aaae7fec" +
                        "d2b473e0cbaef670fefa1c9&scene=18&uin=MjAzMzU5NTQyMQ%3D%3D&key=77421cf58af4a6538c1346db33" +
                        "ffc05b6920a54e3c6128ae77bd30102ee08cd725b1ee93136cd2550d805e23678e0744&devicetype=iPhone" +
                        "+OS10.0&version=16031610&lang=zh_CN&nettype=WIFI&fontScale=100&pass_ticket=DGQHy%2FI%2F6" +
                        "JOvZoFSrzmyBpbPRzDKgFAEmpGjjBGKnj%2BgEztO9phZm2L090t0pXZb";
                throwUrlOpenWeb(url04);
                break;
            case R.id.img_moreVideo05:
                String url05="https://mp.weixin.qq.com/s?__biz=MjM5NzYwNjk2Mg==&mid=505705279&idx=1&sn=1bd01fed" +
                        "e35faf5b7a7583d8f02f8e2d&scene=18&uin=MjAzMzU5NTQyMQ%3D%3D&key=77421cf58af4a6530abd0efe5f" +
                        "5e2d5425d65ea36c4c6a1b1796510bc8acb04aa35eb4317d39f2f344ad368633038331&devicetype=iPhone" +
                        "+OS10.0&version=16031610&lang=zh_CN&nettype=WIFI&fontScale=100&pass_ticket=DGQHy%2FI%2F6J" +
                        "OvZoFSrzmyBpbPRzDKgFAEmpGjjBGKnj%2BgEztO9phZm2L090t0pXZb";
                throwUrlOpenWeb(url05);
                break;
            default:
                break;
        }

    }

    //通过网页链接打开相应的网页
    public void throwUrlOpenWeb(String url){
        Intent intent=new Intent();
        intent.putExtra("url",url);
        intent.setClass(MoreVideoActivity.this,WebViewActivity.class);
        startActivity(intent);

    }
}
