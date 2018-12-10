package com.yichuizi.yichuizi.nativeclass;

/**
 * 作者： duanyikang on 2018/12/6.
 * 描述：
 */
public class WaterText {
    static {
        System.loadLibrary("native-lib");
    }
    public native void addWatermark(int argc,String[] argv);
}
