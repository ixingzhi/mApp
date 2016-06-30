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
public class DeskTopCultrueFragment extends BaseFragment {
    private ListView lv_deskTopCultrue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_community_desktopculture,container,false);
        lv_deskTopCultrue= (ListView) view.findViewById(R.id.lv_deskTopCulture);

        return view;

    }
}
