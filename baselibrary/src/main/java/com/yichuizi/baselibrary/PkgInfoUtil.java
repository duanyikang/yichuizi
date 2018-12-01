package com.yichuizi.baselibrary;

import android.content.Context;

/**
 * 作者： duanyikang on 2018/11/27.
 * 邮箱： 574281045@qq.com
 * 描述：
 */
public class PkgInfoUtil {
    static {
        System.loadLibrary("pkginfo-lib");
    }

    /**
     * 获取签名
     * @return
     */
    public native String getPkgName();

    /**
     * 获取包名
     * @return
     */
    public native String getSign(Context context);

    /**
     * 获取签名包名MD5
     * @return
     */
    public native String getPSMd5(String str);

}
