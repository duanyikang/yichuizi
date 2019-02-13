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

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： duanyikang on 2019/1/2.
 * 描述：
 */
public class ItemRecyclerView extends RecyclerView {
    private List<VideoBean> mData;
    private ItemAdapter mItemAdapter;

    public ItemRecyclerView(Context context) {
        super(context, null);
    }

    public ItemRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void addData(List<VideoBean> mData) {
        this.mData = mData;
    }

    private void init() {
        mData = new ArrayList<>();
        mItemAdapter = new ItemAdapter();
        setAdapter(mItemAdapter);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        new PagerSnapHelper().attachToRecyclerView(this);
    }
}
