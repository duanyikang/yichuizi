package com.yichuizi.yichuizi.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import com.yichuizi.yichuizi.nativeclass.Player;

/**
 * Created by 斗战圣佛 on 2018/9/20.
 */
public class MyVideoView extends SurfaceView {
    Player util;
    Surface surface;
    public MyVideoView(Context context) {
        this(context,null);
    }
    public MyVideoView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        getHolder().setFormat(PixelFormat.RGBA_8888);
        surface= getHolder().getSurface();
        util=new Player();
    }
    /**
     * 开始播放
     * @param videoPath
     */
    public void startPlay(final String videoPath){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("MyVideoView","------>>调用native方法");
                util.play(videoPath,surface);
            }
        }).start();
    }
}
