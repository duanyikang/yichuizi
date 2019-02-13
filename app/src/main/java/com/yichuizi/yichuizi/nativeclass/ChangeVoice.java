package com.yichuizi.yichuizi.nativeclass;

/**
 * 作者： duanyikang on 2019/2/13.
 * 描述：
 */
public class ChangeVoice {
    static {
        System.loadLibrary("native-lib");
    }
    //音效类型
    public static final int MODE_NORMAL = 0;
    public static final int MODE_LUOLI = 1;
    public static final int MODE_DASHU = 2;
    public static final int MODE_JINGSONG = 3;
    public static final int MODE_GAOGUAI = 4;
    public static final int MODE_KONGLING = 5;

    public native static int paySound(String path, int type);
}
