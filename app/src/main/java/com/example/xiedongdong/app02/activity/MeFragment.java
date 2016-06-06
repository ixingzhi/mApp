package com.example.xiedongdong.app02.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiedongdong.app02.R;

/**
 * Created by xiedongdong on 16/5/24.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
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
        View view=inflater.inflate(R.layout.me_pager,container,false);

        tv_username=(TextView)view.findViewById(R.id.tv_username);
        tv_username.setOnClickListener(this);

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
        readUserInfo();

        return view;
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_username:
                if(isLogin()){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else{

                }
                break;
            case R.id.ll_myPosts:
                Toast.makeText(getContext(),"正在开发中",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_myCollect:
                Toast.makeText(getContext(),"正在开发中",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_aboutSoft:
                startActivity(new Intent(getActivity(),AboutSoftActivity.class));
                break;
            case R.id.ll_changePassword:
                startActivity(new Intent(getActivity(),ChangePasswordActivity.class));
                break;
            case R.id.ll_moreFunction:
                startActivity(new Intent(getActivity(),MoreFunctionActivity.class));
                break;
            case R.id.btn_quitUsername:
                if(isLogin()){
                    Toast.makeText(getContext(),"未登录",Toast.LENGTH_SHORT).show();
                }else{
                    clearUserInfo();
                    getActivity().finish();
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;

            default:
                break;

        }

    }

    private void readUserInfo() {
        SharedPreferences pref=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username=pref.getString("username","");
        if(username.equals("")){

        }else{
            tv_username.setText(username);
        }

    }

    private void clearUserInfo() {
        SharedPreferences pref=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        pref.edit().clear().commit();
    }

    public boolean isLogin() {
        SharedPreferences pref=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username=pref.getString("username","");
        if(username.equals("")){
            return true;
        }

        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
