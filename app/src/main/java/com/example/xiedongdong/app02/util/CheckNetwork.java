package com.example.xiedongdong.app02.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by xiedongdong on 16/7/13.
 */
public class CheckNetwork {
    private Context context;

    public CheckNetwork(Context context){
        this.context=context;
    }

    public boolean isOpenNetwork(){
        ConnectivityManager connManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connManager.getActiveNetworkInfo();

        if(networkInfo!=null){
            //获得网络链接类型
            int networkType=networkInfo.getType();

            //WIFI连接
            if(ConnectivityManager.TYPE_WIFI==networkType){
                Log.e("networkType","WIFI");
                return true;
            }
            //MOBILE连接
            else if(ConnectivityManager.TYPE_MOBILE==networkType){
                Log.e("networkType","MOBILE");
                return true;
            }

        }

        Toast.makeText(context,"没有网络连接",Toast.LENGTH_SHORT).show();

        return false;
    }
}
