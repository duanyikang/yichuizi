package com.yichuizi.yichuizi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.adapter.MainRecyclerAdapter;

/**
 * 作者： duanyikang on 2018/12/4.
 * 描述：
 */
public class RecyclerActivity extends Activity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private MainRecyclerAdapter mMainRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_layout);
        iniView();
    }

    private void iniView() {
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mMainRecyclerAdapter=new MainRecyclerAdapter();
        mRecyclerView.setAdapter(mMainRecyclerAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
//        LinearSnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onClick(View view) {
        mMainRecyclerAdapter.notifyDataSetChanged();
    }
}
