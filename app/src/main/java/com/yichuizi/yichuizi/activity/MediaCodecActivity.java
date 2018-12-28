package com.yichuizi.yichuizi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.yichuizi.yichuizi.media.SimplePlayer;

/**
 * 作者： duanyikang on 2018/12/28.
 * 描述：
 */
public class MediaCodecActivity extends Activity implements SurfaceHolder.Callback {
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private SimplePlayer mSimplePlayer;
    private String mVideoUrl = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1.mp4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new SurfaceView(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        setContentView(mSurfaceView);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSimplePlayer = new SimplePlayer(surfaceHolder.getSurface(), mVideoUrl);
        mSimplePlayer.play();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSimplePlayer.destroy();
    }
}
