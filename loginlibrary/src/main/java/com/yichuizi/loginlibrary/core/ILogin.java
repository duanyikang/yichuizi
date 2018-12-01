package com.yichuizi.loginlibrary.core;

import android.content.Context;

/**
 * 作者： duanyikang on 2018/11/30.
 * 描述：
 */
public interface ILogin {
    void login(Context context, int login);

    boolean isLogin(Context context);

    void loginOut(Context context);
}
