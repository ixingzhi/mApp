package com.example.xiedongdong.app02.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.Version;
import com.example.xiedongdong.app02.util.CheckNetwork;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by xiedongdong on 16/6/2.
 */
public class AboutSoftActivity extends BaseActivity implements View.OnClickListener{

    //下载中
    private static final int DOWNLOAD=0;
    //下载完成
    private static final int DOWNLOAD_FINISH=1;
    //下载路径
    private String mSavePath;
    //记录进度条的位置
    private int progress=0;
    //下载对话窗
    private ProgressDialog mDownloadDialog;
    //下载取消,默认取消
    private boolean cancelUpdate=false;

    //保存从数据库中获得的版本信息。
    private HashMap<String,String> map;
    //数据库中应用程序版本
    private String serverCode=null;
    //数据库中应用程序更新下载链接
    private String downloadLink=null;
    //数据库中获取更新内容
    private String updateContent=null;
    //数据库中获得网页下载链接
    private String url=null;

    Handler mHandler=new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what){
                case DOWNLOAD:
                    //实时更新下载进度
                    mDownloadDialog.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    installApk();
                    break;
                default:
                    break;

            }
        }

    };


    private TextView tv_back;
    private TextView tv_version;
    private Button btn_checkUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_aboutsoft);

        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        tv_version=(TextView)findViewById(R.id.tv_version);

        btn_checkUpdate= (Button) findViewById(R.id.btn_checkUpdate);
        btn_checkUpdate.setOnClickListener(this);

        //从程序中读取版本号
        tv_version.setText(getVersion());
        //获得数据库中的版本号
        getServerUpdate();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.btn_checkUpdate:
                //判断有没有网络
                boolean isNetwork=new CheckNetwork(this).isOpenNetwork();
                if(isNetwork){
                    checkUpadte();
                }
                break;
            default:
                break;
        }

    }

    private void checkUpadte() {

        if(isUpdate()){
            showNoticeDialog();
        }else {
            showToast("已是最新版本");
        }
    }

    /**
     * 获得当前程序的版本号
     */

    public String getVersion() {
        //获得PackageManager的实例
        PackageManager packageManager=getPackageManager();
        //getPackageName()是获取程序的包名，0代表获取版本信息
        PackageInfo packageInfo=null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.versionName;
    }

    /**
     * 获得服务器中存储的版本编号，下载链接
     * @return
     */
    public void getServerUpdate() {
        BmobQuery<Version> query=new BmobQuery<Version>();
        query.getObject(AboutSoftActivity.this, "YjU4FFFJ", new GetListener<Version>() {
            @Override
            public void onSuccess(Version version) {
                serverCode=version.getVersionNumber();
                downloadLink=version.getDownloadLink();
                updateContent=version.getUpdateContent();
                url=version.getUrl();

            }

            @Override
            public void onFailure(int i, String s) {
            }
        });
    }

    //检查是否更新
    public boolean isUpdate() {

        //获得本地应用的版本号，serversion为全局变量
        String versionCode=getVersion();

        if(! versionCode.equals(serverCode)){
            return true;
        }

        return false;
    }

    /**
     * 显示提示更新对话窗
     */
    private void showNoticeDialog() {
        //构建对话窗
        final AlertDialog.Builder dialog=new AlertDialog.Builder(AboutSoftActivity.this);
        dialog.setTitle("版本升级");
        dialog.setMessage("检测到有新的版本，请及时更新");
        dialog.setCancelable(false);

        dialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                //显示下载资源
                showDownloadResourcesDialog();


            }
        });
        dialog.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * 选择下载资源
     */
    private void showDownloadResourcesDialog(){

        //构建对话窗
        final AlertDialog.Builder dialog=new AlertDialog.Builder(AboutSoftActivity.this);
        dialog.setTitle("下载资源");
        dialog.setCancelable(true);

        dialog.setPositiveButton("网页下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                //向跳转的网页传递网址
                Intent intent=new Intent();
                intent.putExtra("Url",url);
                intent.setClass(AboutSoftActivity.this,WebViewActivity.class);
                startActivity(intent);

            }
        });
        dialog.setNegativeButton("直接下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                //显示下载对话窗
                showDownloadDialog();

            }
        });

        dialog.show();

    }


    /**
     * 显示下载对话窗
     */

    private void showDownloadDialog() {
        mDownloadDialog=new ProgressDialog(AboutSoftActivity.this);
        mDownloadDialog.setTitle("正在下载更新中");
        mDownloadDialog.setMessage(updateContent);
        mDownloadDialog.setCancelable(false);
        mDownloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //设置进度条是否明确
        mDownloadDialog.setIndeterminate(false);
       // mDownloadDialog.setProgress(100);


        mDownloadDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                //取消下载，从网络获取下载数据中断
                cancelUpdate=true;
            }
        });
        mDownloadDialog.show();

        //下载Apk文件
        downLoaadApk();

    }

    private class CancelButtonListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int i) {
            dialog.dismiss();
            //取消下载，从网络获取下载数据中断
            cancelUpdate=true;
        }
    }

    /**
     * 下载Apk文件
     */
    private void downLoaadApk() {
        //启动新线程下载文件
        new DownloadApkThread().start();

    }


    //启动新线程下载安装包文件

    private class DownloadApkThread extends Thread {
        @Override
        public void run() {
            try{

                //获得下载链接
                String downloadUrl=downloadLink;

                URL url=new URL(downloadUrl);

                //创建连接
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.connect();
                //获取文件大小
                int fileLength=conn.getContentLength();


                //创建输入流
                InputStream is = conn.getInputStream();

                //创建下载目录
                mSavePath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Geek/update";
                File file=new File(mSavePath);
                if(! file.exists()){
                    file.mkdir();
                }

                File apkFile=new File(mSavePath,"Geek");
                //输出流
                FileOutputStream fos=new FileOutputStream(apkFile);

                //创建缓存
                byte buf[]=new byte[1024];

                int count=0;

                //写入到文件中
                do{
                    //流中读取到的数据放倒缓存中
                    int numRead=is.read(buf);
                    count+=numRead;
                    //计算进度条位置
                    progress=(int) (((float)count/fileLength)*100);
                    // 下载中
                    mHandler.sendEmptyMessage(DOWNLOAD);

                    if(numRead<=0){
                        //下载完成
                        mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                        break;
                    }

                    //写入文件
                    fos.write(buf,0,numRead);


                }while (!cancelUpdate); //取消下载


                //关闭流，先开后关
                fos.close();
                is.close();




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //关闭下载对话窗
            mDownloadDialog.dismiss();

        }

    }
    //安装Apk文件
    private void installApk() {
        File apkFile = new File(mSavePath,"Geek");
        if(! apkFile.exists()){
            return;
        }

        // 通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新从服务器获取数据
        getServerUpdate();
    }


}
