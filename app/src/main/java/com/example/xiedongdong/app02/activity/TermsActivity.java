package com.example.xiedongdong.app02.activity;

import android.os.Bundle;
import android.widget.CheckBox;

import com.example.xiedongdong.app02.Base.BaseActivity;
import com.example.xiedongdong.app02.R;

/**
 * Created by xiedongdong on 16/6/15.
 */
public class TermsActivity extends BaseActivity {
    private CheckBox cb_agreeTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
    }

}
