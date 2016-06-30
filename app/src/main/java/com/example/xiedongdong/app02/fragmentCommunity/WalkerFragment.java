package com.example.xiedongdong.app02.fragmentCommunity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.xiedongdong.app02.Base.BaseFragment;
import com.example.xiedongdong.app02.R;

/**
 * Created by xiedongdong on 16/6/30.
 */
public class WalkerFragment extends BaseFragment {
    private ListView lv_walker;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_community_walker,container,false);

        lv_walker= (ListView) view.findViewById(R.id.lv_walker);
        return view;
    }
}
