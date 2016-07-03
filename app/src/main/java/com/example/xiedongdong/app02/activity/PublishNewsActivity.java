package com.example.xiedongdong.app02.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.bean.News;
import com.example.xiedongdong.app02.bean.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by xiedongdong on 16/6/28.
 */
public class PublishNewsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_back;
    private TextView tv_publish;
    private EditText et_title;
    private EditText et_url;
    private EditText et_from;
    private RadioGroup grp01,grp02;
    private RadioButton rbtn_info,rbtn_evaluation,rbtn_disassembly,rbtn_openBox,rbtn_walker,rbtn_media,rbtn_deskTopCulture;
    private Button btn_selectPicture;
    private ImageView img_title;

    private Bitmap photo=null;

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;

    private final String PATH=Environment.getExternalStorageDirectory()+"/Geek/imageTitle.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_add_publish);

        initView();
        initSetListener();
    }

    private void initView() {
        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_publish=(TextView)findViewById(R.id.tv_publish);

        et_title=(EditText)findViewById(R.id.et_title);
        et_url=(EditText)findViewById(R.id.et_url);
        et_from=(EditText)findViewById(R.id.et_from);

        grp01=(RadioGroup)findViewById(R.id.grp01);
        grp02=(RadioGroup)findViewById(R.id.grp02);


        rbtn_info= (RadioButton) findViewById(R.id.rbtn_info);
        rbtn_evaluation=(RadioButton)findViewById(R.id.rbtn_evaluation);
        rbtn_disassembly=(RadioButton) findViewById(R.id.rbtn_disassembly);
        rbtn_openBox=(RadioButton) findViewById(R.id.rbtn_openBox);
        rbtn_walker=(RadioButton) findViewById(R.id.rbtn_walker);
        rbtn_media=(RadioButton)findViewById(R.id.rbtn_media);
        rbtn_deskTopCulture=(RadioButton) findViewById(R.id.rbtn_deskTopCulture);

        btn_selectPicture=(Button)findViewById(R.id.btn_selectPicture);
        img_title=(ImageView)findViewById(R.id.img_title);

    }

    private void initSetListener() {
        tv_back.setOnClickListener(this);
        tv_publish.setOnClickListener(this);
        btn_selectPicture.setOnClickListener(this);

        rbtn_info.setOnClickListener(this);
        rbtn_evaluation.setOnClickListener(this);
        rbtn_disassembly.setOnClickListener(this);
        rbtn_openBox.setOnClickListener(this);
        rbtn_walker.setOnClickListener(this);
        rbtn_media.setOnClickListener(this);
        rbtn_deskTopCulture.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.btn_selectPicture:
                selectPicture();
                break;
            case R.id.tv_publish:
                if(checkFrom()){
                    publishNews();
                }
                break;
            /**一下选择为RadioButton避免多选解决方案**/
            case R.id.rbtn_info:
                grp02.clearCheck();
                break;
            case R.id.rbtn_evaluation:
                grp02.clearCheck();
                break;
            case R.id.rbtn_disassembly:
                grp02.clearCheck();
                break;
            case R.id.lv_openBox:
                grp02.clearCheck();
                break;
            case R.id.rbtn_walker:
                grp01.clearCheck();
                break;
            case R.id.rbtn_media:
                grp01.clearCheck();
                break;
            case R.id.rbtn_deskTopCulture:
                grp01.clearCheck();
                break;
            default:
                break;
        }

    }

    /**选择图片**/
    private void selectPicture() {
        //从本地选择照片
        choseHeadImageFromGallery();
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

        Log.e("resultCode",""+resultCode);
        Log.e("requestCode",""+requestCode);


        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {//取消
            showToast("取消");
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST://如果是来自本地的
                cropRawPhoto(intent.getData());//直接裁剪图片
                break;
            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);//提取保存裁剪之后的图片数据，并设置头像部分的View,并上传头像至数据库中
                }
                break;
            default:
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
     * 提取保存裁剪之后的图片数据
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");
            img_title.setImageBitmap(photo);

            //新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Geek文件夹
            File nf = new File(Environment.getExternalStorageDirectory()+"/Geek");
            nf.mkdir();

            //在根目录下面的Geek文件夹下 创建imageTitle.jpg文件
            File f = new File(PATH);

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
    }

    //检查输入信息来源
    private boolean checkFrom() {
        String txt_title=et_title.getText().toString().trim();
        String txt_url=et_url.getText().toString().trim();
        String txt_from=et_from.getText().toString().trim();

        if(TextUtils.isEmpty(txt_title)){
            showToast("标题不能为空");
            return false;
        }
        if(TextUtils.isEmpty(txt_url)){
            showToast("网址不能为空");
            return false;
        }
        if(TextUtils.isEmpty(txt_from)){
            showToast("来自不能为空");
            return false;
        }
        if(txt_title.length()>25){
            showToast("标题不能大于25个字符");
            return false;
        }
        if(txt_from.length()>14){
            showToast("来自不能大于14个字符");
            return false;
        }
        if(!rbtn_info.isChecked() && !rbtn_disassembly.isChecked() && !rbtn_openBox.isChecked() && !rbtn_walker.isChecked() &&
               !rbtn_media.isChecked() && !rbtn_deskTopCulture.isChecked()){
            showToast("没有选择分类");
            return false;
        }


        return true;
    }
    /**发布消息**/
    private void publishNews() {
        User user= BmobUser.getCurrentUser(PublishNewsActivity.this,User.class);
        final String txt_objectId=user.getObjectId();

        final String txt_title=et_title.getText().toString().trim();
        final String txt_url=et_url.getText().toString().trim();
        final String txt_from=et_from.getText().toString().trim();

        String txt_messageType=null;
        if(rbtn_info.isChecked()){
            txt_messageType=rbtn_info.getText().toString().trim();
        }
        if(rbtn_disassembly.isChecked()){
            txt_messageType=rbtn_disassembly.getText().toString().trim();
        }
        if(rbtn_openBox.isChecked()){
            txt_messageType=rbtn_openBox.getText().toString().trim();
        }
        if(rbtn_walker.isChecked()){
            txt_messageType=rbtn_walker.getText().toString().trim();
        }
        if(rbtn_media.isChecked()){
            txt_messageType=rbtn_media.getText().toString().trim();
        }
        if(rbtn_deskTopCulture.isChecked()){
            txt_messageType=rbtn_deskTopCulture.getText().toString().trim();
        }



        final BmobFile imgTitleFile=new BmobFile(new File(PATH));
        final String finalTxt_messageType = txt_messageType;
        imgTitleFile.upload(this, new UploadFileListener() {
            @Override
            public void onSuccess() {


                News news=new News();
                news.setId(txt_objectId);
                news.setTitle(txt_title);
                news.setUrl(txt_url);
                news.setFrom(txt_from);
                news.setMessageType(finalTxt_messageType);
                news.setImgTitleUrl(imgTitleFile.getFileUrl(PublishNewsActivity.this));
                news.save(PublishNewsActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        showToast("发送消息成功");
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        showToast("发送消息失败:"+s);
                    }
                });

            }

            @Override
            public void onFailure(int i, String s) {
                showToast("没有选择图片"+s);
            }
        });






    }
}
