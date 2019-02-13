package com.yichuizi.videolibrary.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yichuizi.videolibrary.bean.VideoBean;
import com.yichuizi.videolibrary.supervideo.ItemAdapter;
import com.yichuizi.videolibrary.supervideo.MainAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： duanyikang on 2019/1/2.
 * 描述：
 */
public class MainRecyclerView extends RecyclerView {
    private List<List<VideoBean>> mData;
    private MainAdapter mMainAdapter;

    public MainRecyclerView(Context context) {
        super(context, null);
    }

    public MainRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void addData(List<List<VideoBean>> mData) {
        if (mMainAdapter != null) {
            mMainAdapter.addData(mData);
            mMainAdapter.notifyDataSetChanged();
        }
    }

    private void init() {
        mData = new ArrayList<>();
        mMainAdapter = new MainAdapter();
        setAdapter(mMainAdapter);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        new PagerSnapHelper().attachToRecyclerView(this);
    }
}
