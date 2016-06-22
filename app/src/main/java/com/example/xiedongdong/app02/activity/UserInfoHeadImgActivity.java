package com.example.xiedongdong.app02.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by xiedongdong on 16/6/22.
 */
public class UserInfoHeadImgActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_back;
    private ImageView img_headImgSource;

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 600;
    private static int output_Y = 600;

    private ImageView headImage = null;

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

        final AlertDialog dialog=new AlertDialog.Builder(this).create();  //创建一个Dialog
        View view=getLayoutInflater().inflate(R.layout.me_userinfoheadimg_source,null);  //自定义布局
        dialog.setView(view,0,0,0,0);  //把自定义布局添加到dialog中，从第二个参数开始分别表示填充内容与边缘之间的像素 左上右下。
        dialog.show();  //一定要在dialog，show之后在设置dialog的参数，不然会不会显示

        //int width=getWindowManager().getDefaultDisplay().getWidth();  //获取当前设备的显示宽度
        WindowManager.LayoutParams params=dialog.getWindow().getAttributes();  //得到这个dialog界面的参数对象
        params.width= WindowManager.LayoutParams.MATCH_PARENT;   //设置显示宽度和屏幕宽度相同
        params.height= WindowManager.LayoutParams.WRAP_CONTENT;//设置dialog的高度为包裹内容。
        params.gravity= Gravity.BOTTOM;  //设置重心为显示到最下面

        dialog.getWindow().setAttributes(params);// 将设置的内容与dialog绑定

        /**
         * 设置里面的内容监听
         */
        RelativeLayout rl_photograph=(RelativeLayout)view.findViewById(R.id.rl_photograph);
        rl_photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseHeadImageFromCameraCapture();

            }
        });

        RelativeLayout rl_getPhoto=(RelativeLayout)view.findViewById(R.id.rl_getPhoto);
        rl_getPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.rl_getPhoto){
                    //从本地相册选取图片作为头像
                    choseHeadImageFromGallery();
                }
            }
        });

        RelativeLayout rl_calcel=(RelativeLayout)view.findViewById(R.id.rl_cancel);
        rl_calcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.rl_cancel){
                    dialog.dismiss();
                }
            }
        });





    }

    /**
     * 判断有没有SD卡的工具方法
     * @return
     */
    private boolean hasSdcard() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }


    /**
     * 启动相机拍照作为头像
     */
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //首先判断有没有SD卡
        if(hasSdcard()){
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }
        startActivityForResult(intentFromCapture,CODE_CAMERA_REQUEST);
    }


    /**
     * 从本地相册获取照片
     */
    private void choseHeadImageFromGallery() {

        Intent intentFromGallery=new Intent();
        //设置文件类型
        intentFromGallery.setType("image/*");
        //选择图片
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        //获取到图片，将当前的数据返回到上一个Activity中
        startActivityForResult(intentFromGallery,CODE_GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {//取消
            showToast("取消");
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST://如果是来自本地的
                cropRawPhoto(intent.getData());//直接裁剪图片
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    showToast("没有SDCard!");
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);//设置图片框
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        //把裁剪的数据填入里面

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            headImage.setImageBitmap(photo);

            //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Ask文件夹
            File nf = new File(Environment.getExternalStorageDirectory()+"/Ask");
            nf.mkdir();

            //在根目录下面的ASk文件夹下 创建okkk.jpg文件
            File f = new File(Environment.getExternalStorageDirectory()+"/Ask", "okkk.jpg");

            FileOutputStream out = null;
            try {

                //打开输出流 将图片数据填入文件中
                out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);

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
    }
}
