package com.example.xiedongdong.app02.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 从内存中加载图片
 * Created by xiedongdong on 16/6/25.
 */
public class BitmapFileCache {

    //根目录
    private static final String PATH= Environment.getExternalStorageState()+"/Geek";
    //private static File rootDir;
    private File rootDir=new File(PATH);
    private static BitmapFileCache bitmapFileCache=null;

    public BitmapFileCache(){
        //如果不存在根目录，创建
        if(! rootDir.exists()){
            rootDir.mkdir();
        }
    }

    //实例话
    public static BitmapFileCache getInstance(){
        if(bitmapFileCache==null){
            BitmapFileCache bitmapFileCache=new BitmapFileCache();
        }
        return bitmapFileCache;
    }


    /** 根据文件名获取Bitmap **/
//    public static Bitmap get(int hashCode) {
//        String[] files = rootDir.list(new MyFilenameFilter(hashCode));
//        if (files == null || files.length == 0) {
//            return null;
//        } else {
//            return BitmapFactory.decodeFile(PATH + "/" + files[0]);
//        }
//    }

    /** 文件名过滤器，把想要的文件过滤出来 **/
    private class MyFilenameFilter implements FilenameFilter {

        private int hashCode;

        public MyFilenameFilter(int hashCode) {
            this.hashCode = hashCode;
        }

        @Override
        public boolean accept(File dir, String filename) {
            return filename.equals(hashCode + ".jpg");
        }

    }
}
