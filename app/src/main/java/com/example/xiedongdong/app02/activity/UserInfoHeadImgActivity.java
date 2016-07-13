package com.example.xiedongdong.app02.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * Created by xiedongdong on 16/6/22.
 */
public class UserInfoHeadImgActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_back;
    private ImageView img_headImgSource;
    private ImageView img_headImg;

    //启动相机拍照后的存储原图路径
    private static String CAMERA_PHOTO_PATH=Environment.getExternalStorageDirectory()+"Geek/cameraPhoto";
    //启动相机拍完照后的名字
    private static final String CAMERA_PHOTO_NAME = "head_image.jpg";

    //裁剪后图片保存路径
    private final String CROP_IMAGE_PATH=Environment.getExternalStorageDirectory()+"/Geek/imageHead" ;
    /* 裁剪后的头像文件名 */
    private static final String CROP_IMAGE_NAME = "crop_head_image.jpg";
    //裁剪完后文件
    private static String PATH=Environment.getExternalStorageDirectory()+"/Geek/imageHead/crop_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 600;
    private static int output_Y = 600;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_userinfoheadimg);

        initView();
        initSetListener();
        initData();
    }

    private void initData() {
        /**初始化头像的方法**/
        initHeadImg();
    }

    private void initHeadImg() {
        File headImgFile=new File(new String(PATH));
        //先从本地获取
        if(headImgFile.exists()){
            Bitmap bitmap= BitmapFactory.decodeFile(PATH);
            img_headImg.setImageBitmap(bitmap);
        }
    }

    private void initSetListener() {
        tv_back.setOnClickListener(this);
        img_headImgSource.setOnClickListener(this);
    }

    private void initView() {
        tv_back=(TextView)findViewById(R.id.tv_back);
        img_headImgSource=(ImageView)findViewById(R.id.img_headImgSource);
        img_headImg=(ImageView)findViewById(R.id.img_headImg);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.img_headImgSource:
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
        View view=getLayoutInflater().inflate(R.layout.layout_me_userinfoheadimgsource,null);  //自定义布局
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
                dialog.dismiss();

            }
        });

        RelativeLayout rl_getPhoto=(RelativeLayout)view.findViewById(R.id.rl_getPhoto);
        rl_getPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从本地相册选取图片作为头像
                choseHeadImageFromGallery();
                dialog.dismiss();
            }
        });

        RelativeLayout rl_calcel=(RelativeLayout)view.findViewById(R.id.rl_cancel);
        rl_calcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

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
        Log.e(">>","启动相机成功");
        if(hasSdcard()){
            File file=new File(CAMERA_PHOTO_PATH);
            if(! file.exists()){
                file.mkdir();
                Log.e(">>","创建文件夹成功");
            }

            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(CAMERA_PHOTO_PATH, CAMERA_PHOTO_NAME)));
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

            case CODE_CAMERA_REQUEST: //图片来自相机
                if (hasSdcard()) {
                    File tempFile = new File(
                            CAMERA_PHOTO_PATH,
                            CAMERA_PHOTO_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    showToast("没有SDCard!");
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);//提取保存裁剪之后的图片数据，并设置头像部分的View,并上传头像至数据库中
                    deleteOldHeadImgFile();//上传头像后，删除原来的就头像文件。
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**上传头像后，删除原来的就头像文件**/
    private void deleteOldHeadImgFile() {
        User user=BmobUser.getCurrentUser(UserInfoHeadImgActivity.this,User.class);
        BmobFile file=new BmobFile();
        file.setUrl(user.getHeadImgFileUrl());
        file.delete(UserInfoHeadImgActivity.this, new DeleteListener() {
            @Override
            public void onSuccess() {
                Log.d("UserInfoHeadImgActivity","删除旧头像文件成功");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.d("UserInfoHeadImgActivity","删除旧头像文件失败:"+s);
            }
        });
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
     * 提取保存裁剪之后的图片数据，并设置头像部分的View,并上传头像至数据库中
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            img_headImg.setImageBitmap(photo);

            /**保存Bitmap图片到本地**/

            savePhotoToLocal(photo);

            /**上传头像至数据库中**/

            upLoad(PATH);

        }
    }

    /**
     * 保存裁剪头像图片到本地
     */
    private void savePhotoToLocal(Bitmap photo){

        //在根目录下面的Geek文件夹下 创建head_image.jpg文件
        File f = new File(CROP_IMAGE_PATH,CROP_IMAGE_NAME);
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

    /**上传头像至数据库中**/
    private void upLoad(String path) {
        final BmobFile headImgFile=new BmobFile(new File(path));
        final String txt_objectId=BmobUser.getCurrentUser(UserInfoHeadImgActivity.this).getObjectId();
        headImgFile.uploadblock(this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                User user=new User();
                user.setHeadImgUrl(headImgFile.getFileUrl(UserInfoHeadImgActivity.this));  //上传头像的Url到输入库中
                user.setHeadImgFileUrl(headImgFile.getUrl());  //上传头像文件（Bmob中）的命名到数据库中，用于上传新头像，删除旧头像。
                user.update(UserInfoHeadImgActivity.this, txt_objectId, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Log.i("headImgUrl","上传头像Url成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.e("headImgUrl","上传头像Url失败"+s);
                    }
                });

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        initHeadImg();
    }
}
