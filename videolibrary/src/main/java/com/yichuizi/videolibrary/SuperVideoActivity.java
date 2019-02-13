package com.yichuizi.videolibrary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yichuizi.videolibrary.bean.VideoBean;
import com.yichuizi.videolibrary.view.MainRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： duanyikang on 2019/1/2.
 * 描述：
 */
public class SuperVideoActivity extends Activity {
    private MainRecyclerView mRecyclerViewMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_supervideo_layout);
        initView();
        initData();
    }

    private void initData() {
        List<List<VideoBean>> mSumData = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<VideoBean> mItemData = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                VideoBean videoBean = new VideoBean();
                mItemData.add(videoBean);
            }
            mSumData.add(mItemData);
        }
        if (mRecyclerViewMain != null) {
            mRecyclerViewMain.addData(mSumData);
        }
    }

    private void initView() {
        mRecyclerViewMain = findViewById(R.id.rl_main);
    }
}
