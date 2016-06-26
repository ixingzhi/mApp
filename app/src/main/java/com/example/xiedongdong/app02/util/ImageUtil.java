package com.example.xiedongdong.app02.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by xiedongdong on 16/6/25.
 */
public class ImageUtil {

    private static ImageUtil imageUtil=null;


    public static ImageUtil getInstance(){
        if(imageUtil==null){
            ImageUtil imageUtil=new ImageUtil();
        }
        return imageUtil;
    }

    public void loadImage(String url, ImageView image){
//        /**先从内从中获取图片**/
//        Bitmap bitmap=findBitmapFromCache(url.hashCode());
//        if(bitmap==null){
//            /**再从网络从中获取图片**/
//            bitmap=findBitmapFromNet(url.hashCode());
//        }
//
//        if(bitmap!=null){
//            image.setImageBitmap(bitmap);
//        }
//
//    }
//    /**从内从中获取图片**/
//    private Bitmap findBitmapFromCache(int hashCode) {
//
//        return BitmapFileCache.get(hashCode);
//    }
//
//    /**从网络从中获取图片**/
//    private Bitmap findBitmapFromNet(int hashCode) {
//        return BitmapFileNet.get(hashCode);
    }

}
