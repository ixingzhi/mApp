package com.example.xiedongdong.app02.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xiedongdong on 16/6/25.
 */
public class BitmapFileNet {

    public static Bitmap get(String address) throws IOException {
        URL url=new URL(address);
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        //获取服务器返回来的流
        InputStream is=conn.getInputStream();
        Bitmap bitmap=BitmapFactory.decodeStream(is);
        is.close();

        return bitmap;

    }
}

