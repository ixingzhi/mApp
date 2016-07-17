package com.example.xiedongdong.app02.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;
import com.example.xiedongdong.app02.adapter.NewsListViewAdapter;
import com.example.xiedongdong.app02.bean.Collect;
import com.example.xiedongdong.app02.bean.News;
import com.example.xiedongdong.app02.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by xiedongdong on 16/7/15.
 */
public class MyCollectActivity extends BaseActivity implements View.OnClickListener{

    private ListView lv_myCollect;
    private NewsListViewAdapter adapter;
    private TextView tv_back;

    private static final int DELETE=0;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_mycollect);

        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        lv_myCollect=(ListView)findViewById(R.id.lv_myCollect);

        User userInfo= BmobUser.getCurrentUser(MyCollectActivity.this,User.class);

        BmobQuery<Collect> query=new BmobQuery<Collect>();
        query.setLimit(50);
        query.order("-shareTime");
        query.addWhereEqualTo("userId",userInfo.getObjectId());
        query.findObjects(MyCollectActivity.this, new FindListener<Collect>() {
            @Override
            public void onSuccess(List<Collect> list) {
                final ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

                for (final Collect newsList : list) {
                    /**定义一个动态数组**/

                    final HashMap<String, String> map = new HashMap<String, String>();

                    map.put(NewsListViewAdapter.KEY_ITEMID, newsList.getObjectId());
                    map.put(NewsListViewAdapter.KEY_TITLE, newsList.getTitle());
                    map.put(NewsListViewAdapter.KEY_FROM, newsList.getFrom());
                    map.put(NewsListViewAdapter.KEY_HEADIMG, newsList.getHeadImgUrl());
                    map.put(NewsListViewAdapter.KEY_USERNAME, newsList.getUsername());
                    map.put(NewsListViewAdapter.KEY_TIME, newsList.getShareTime());
                    map.put(NewsListViewAdapter.KEY_URL, newsList.getUrl());
                    map.put(NewsListViewAdapter.KEY_READCOUNT, newsList.getReadCount());
                    map.put(NewsListViewAdapter.KEY_TITLEIMG, newsList.getTitleImageUrl());

                    listItem.add(map);
                }

                adapter = new NewsListViewAdapter(MyCollectActivity.this, listItem);
                lv_myCollect.setAdapter(adapter);

                //listview点击事件
                lv_myCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {

                        String url=listItem.get(postion).get(NewsListViewAdapter.KEY_URL);
                        Log.e("MyPostsActivity",url);
                        Intent intent=new Intent();
                        intent.putExtra("url",url);
                        intent.setClass(MyCollectActivity.this,NewsWebViewActivity.class);
                        startActivity(intent);
                    }
                });

                lv_myCollect.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int postion, long l) {
                        //获取到要删除的item id
                        String itemId=listItem.get(postion).get(NewsListViewAdapter.KEY_ITEMID);
                        //显示删除对话框
                        showDeleteDialog(itemId);

                        handler=new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                //从listItem中删除选中的postion
                                listItem.remove(postion);
                                //动态更新listview
                                adapter.notifyDataSetChanged();
                            }
                        };


                        return true;
                    }
                });

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    private void showDeleteDialog(final String itemId) {

        final AlertDialog dialog = new AlertDialog.Builder(this).create();  //创建一个Dialog
        View view = getLayoutInflater().inflate(R.layout.layout_cancelcollect_news_item, null);  //自定义布局
        dialog.setView(view, 0, 0, 0, 0);  //把自定义布局添加到dialog中，从第二个参数开始分别表示填充内容与边缘之间的像素 左上右下。
        dialog.show();  //一定要在dialog，show之后在设置dialog的参数，不然会不会显示

        //int width=getWindowManager().getDefaultDisplay().getWidth();  //获取当前设备的显示宽度
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();  //得到这个dialog界面的参数对象
        params.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置显示宽度和屏幕宽度相同
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;//设置dialog的高度为包裹内容。
        params.gravity = Gravity.BOTTOM;  //设置重心为显示到最下面

        dialog.getWindow().setAttributes(params);// 将设置的内容与dialog绑定

        RelativeLayout rl_cancelCollect= (RelativeLayout) view.findViewById(R.id.rl_cancelCollect);
        rl_cancelCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //从数据库中删除item
                Collect news=new Collect();
                news.setObjectId(itemId);
                news.delete(MyCollectActivity.this, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        showToast("取消收藏成功");
                        dialog.dismiss();
                        handler.sendEmptyMessage(DELETE);
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

            }
        });
        RelativeLayout rl_cancel= (RelativeLayout) view.findViewById(R.id.rl_cancel);
        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
