package com.yichuizi.yichuizi.nativeclass;

import android.content.Context;

/**
 * 作者： duanyikang on 2018/12/5.
 * 描述：
 */
public class Publisher {
    private static Publisher mInstance;

    public static void init(Context context) {
        mInstance = new Publisher();
    }

    public static Publisher getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("FFmpegHandle must init fist");
        }
        return mInstance;
    }

    static {
        System.loadLibrary("native-lib");
    }

    public native int initVideo(String url);

    public native int onFrameCallback(byte[] buffer);

    public native int close();
}
