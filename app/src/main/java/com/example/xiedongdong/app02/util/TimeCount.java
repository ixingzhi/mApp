package com.example.xiedongdong.app02.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import com.example.xiedongdong.app02.R;

/**
 * Created by xiedongdong on 16/6/16.
 */
public class TimeCount extends CountDownTimer {
    private Activity mActivity;
    private TextView tv_btn;

    /**
     *
     * @param millisInFuture 总时间
     * @param countDownInterval 倒计时渐变时间
     */
    public TimeCount(Activity mActivity,long millisInFuture, long countDownInterval,TextView tv_btn) {
        super(millisInFuture, countDownInterval);
        this.mActivity=mActivity;
        this.tv_btn=tv_btn;
    }

    /**
     * 计时时显示
     * @param millisUntilFinished  倒计时时间
     */
    @Override
    public void onTick(long millisUntilFinished) {
        tv_btn.setClickable(false); // 倒计时过程中不能点击
        tv_btn.setText(millisUntilFinished/1000+" 秒后重新发送");// 设置倒计时时间
        tv_btn.setBackground(mActivity.getResources().getDrawable(R.mipmap.background_gray));

        SpannableString spannableString = new SpannableString(tv_btn.getText().toString());  //获取按钮上的文字
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        /**
         * public void setSpan(Object what, int start, int end, int flags) {
         * 主要是start跟end，start是起始位置,无论中英文，都算一个。
         * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
         */
        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        tv_btn.setText(spannableString);
        tv_btn.setText(spannableString);

    }

    /**
     * 计时完成时显示
     */

    @Override
    public void onFinish() {
        tv_btn.setText("重新获取");
        tv_btn.setClickable(true);  //重新获得点击
        tv_btn.setBackground(mActivity.getResources().getDrawable(R.mipmap.background_blue));

    }
}
