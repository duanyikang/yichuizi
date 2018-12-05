package com.yichuizi.yichuizi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 作者： duanyikang on 2018/12/5.
 * 描述：
 */
public class PublishSurfaceView extends SurfaceView {
    private SurfaceHolder mHolder;

    public PublishSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHolder=getHolder();
    }
}
