package com.yichuizi.yichuizi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.view.MFWRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： duanyikang on 2018/12/17.
 * 描述：
 */
public class MFWActivity extends Activity {
    private MFWRecyclerView mfwRecyclerView;
    private List<String> mdata = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mfw_layout);
        mfwRecyclerView = findViewById(R.id.mfwrecycler);
        for (int i = 0; i < 50; i++) {
            mdata.add(String.valueOf(i));
        }
        mfwRecyclerView.setData(mdata);
    }
}
