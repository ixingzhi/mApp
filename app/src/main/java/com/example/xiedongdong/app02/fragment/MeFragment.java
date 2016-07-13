package com.example.xiedongdong.app02.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseFragment;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.activity.AboutSoftActivity;
import com.example.xiedongdong.app02.activity.ChangePasswordActivity;
import com.example.xiedongdong.app02.activity.LoginActivity;
import com.example.xiedongdong.app02.activity.MoreFunctionActivity;
import com.example.xiedongdong.app02.activity.MyPostsActivity;
import com.example.xiedongdong.app02.activity.UserInfoActivity;
import com.example.xiedongdong.app02.bean.User;
import com.example.xiedongdong.app02.util.BitmapFileNet;
import com.example.xiedongdong.app02.util.CheckNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;

/**
 * Created by xiedongdong on 16/5/24.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {
    private final String PATH=Environment.getExternalStorageDirectory()+"/Geek/imageHead/crop_head_image.jpg";

    private ImageView img_headImg;
    private RelativeLayout rl_userInfo;
    private TextView tv_username;
    private Button btn_quitUsername;
    private LinearLayout ll_aboutSoft;
    private LinearLayout ll_changePassword;
    private LinearLayout ll_myPosts;
    private LinearLayout ll_myCollect;
    private LinearLayout ll_moreFunction;
    private TextView tv_currentUser;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_me,container,false);

        img_headImg=(ImageView)view.findViewById(R.id.img_headImg);

        rl_userInfo=(RelativeLayout)view.findViewById(R.id.rl_userInfo);
        rl_userInfo.setOnClickListener(this);

        tv_username=(TextView)view.findViewById(R.id.tv_username);

        btn_quitUsername=(Button)view.findViewById(R.id.btn_quitUsername);
        btn_quitUsername.setOnClickListener(this);

        ll_aboutSoft=(LinearLayout) view.findViewById(R.id.ll_aboutSoft);
        ll_aboutSoft.setOnClickListener(this);

        ll_changePassword=(LinearLayout) view.findViewById(R.id.ll_changePassword);
        ll_changePassword.setOnClickListener(this);

        ll_myPosts=(LinearLayout)view.findViewById(R.id.ll_myPosts);
        ll_myPosts.setOnClickListener(this);

        ll_myCollect=(LinearLayout)view.findViewById(R.id.ll_myCollect);
        ll_myCollect.setOnClickListener(this);

        ll_moreFunction=(LinearLayout)view.findViewById(R.id.ll_moreFunction);
        ll_moreFunction.setOnClickListener(this);

        tv_currentUser=(TextView)view.findViewById(R.id.tv_currentUser);

        /**
         * 从ShardPerferences中获取用户名信息
         */
        initData();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rl_userInfo:
                if(isLogin()){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                }
                break;
            case R.id.ll_myPosts:
                if(isLogin()){
                    showToast("未登录");
                }else{
                    startActivity(new Intent(getActivity(), MyPostsActivity.class));
                }
                break;
            case R.id.ll_myCollect:
                showToast("正在开发中");
                break;
            case R.id.ll_aboutSoft:
                startActivity(new Intent(getActivity(),AboutSoftActivity.class));
                break;
            case R.id.ll_changePassword:
                if(isLogin()){
                    showToast("未登录");
                }else{
                    startActivity(new Intent(getActivity(),ChangePasswordActivity.class));
                }
                break;
            case R.id.ll_moreFunction:
                if(isLogin()){
                    showToast("未登录");
                }else{
                    startActivity(new Intent(getActivity(),MoreFunctionActivity.class));
                }
                break;
            case R.id.btn_quitUsername:
                if(isLogin()){
                    showToast("未登录");
                }else{
                    logOut();
                    getActivity().finish();
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;

            default:
                break;

        }

    }

    /**
     * 初始化数据
     */
    private void initData() {
        final User userInfo=BmobUser.getCurrentUser(getContext(),User.class);
        if(userInfo!=null){
            initHeadImg();
            tv_username.setText(userInfo.getUsername());
            tv_username.setTextColor(Color.BLACK);
        }


    }

    /**
     * 初始化数据是获取头像
     */
    private void initHeadImg() {
        File headImgFile=new File(new String(PATH));
        if(headImgFile.exists()){
            Bitmap bitmap= BitmapFactory.decodeFile(PATH);
            img_headImg.setImageBitmap(bitmap);
            Log.e("headImage","本地获取图片");
        }else {

            final User user = BmobUser.getCurrentUser(getActivity(), User.class);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Bitmap bitmap = BitmapFileNet.get(user.getHeadImgUrl());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                img_headImg.setImageBitmap(bitmap);
                                //获取到头像后保存到本地
                                savePhotoToLocal(bitmap);
                                Log.e("headImage","网络获取图片");
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    /**
     * 保存头像图片到本地
     */
    private void savePhotoToLocal(Bitmap photo){

        //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Geek文件夹
        File f0 = new File(Environment.getExternalStorageDirectory()+"/Geek");
        if(!f0.exists()){
            f0.mkdir();
        }

        File f1=new File(Environment.getExternalStorageDirectory()+"/Geek/imageHead");
        if(! f1.exists()){
            f1.mkdir();
            Log.e("imageHead","创建文件夹成功");
        }

        //在根目录下面的Geek文件夹下 创建head_image.jpg文件
        File f = new File(Environment.getExternalStorageDirectory()+"/Geek/imageHead","crop_head_image.jpg");
        if(! f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream out = null;
        try {

            //打开输出流 将图片数据填入文件中
            out = new FileOutputStream(f);
            photo.compress(Bitmap.CompressFormat.PNG, 100, out);

            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * 退出用户
     */
    private void logOut() {

        BmobUser.logOut(getActivity());   //清除缓存用户
    }

    /**
     * 检测是否登录
     */
    public boolean isLogin() {
        User userInfo= BmobUser.getCurrentUser(getActivity(),User.class);
        //检查网络连接
        boolean isNetwork=new CheckNetwork(getActivity()).isOpenNetwork();

        if(userInfo==null && isNetwork){
            return true;
        }

        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isLogin()){
            initHeadImg();
        }

    }
}