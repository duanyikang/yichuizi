package com.yichuizi.yichuizi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.view.MyVideoView;

/**
 * 作者： duanyikang on 2018/9/21.
 * 邮箱： 574281045@qq.com
 * 描述： 播放器
 */

public class VideoPlayActivity extends Activity {
    private MyVideoView mMyVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mMyVideoView=findViewById(R.id.video_view);
        mMyVideoView.startPlay("http://vjs.zencdn.net/v/oceans.mp4");
    }
}
