package com.yichuizi.yichuizi.nativeclass;

import android.view.Surface;

/**
 * 作者： duanyikang on 2018/9/21.
 * 邮箱： 574281045@qq.com
 * 描述： 播放器
 */

public class Player {
    static {
        System.loadLibrary("native-lib");
    }
    public native void play(String url, Surface surface);
}