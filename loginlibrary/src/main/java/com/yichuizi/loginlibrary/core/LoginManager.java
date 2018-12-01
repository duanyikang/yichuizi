package com.yichuizi.loginlibrary.core;

import android.content.Context;

/**
 * 作者： duanyikang on 2018/11/30.
 * 描述：
 */
public class LoginManager {
    private static LoginManager instance;
    private Context applicationContext;

    private LoginManager() {}

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context, ILogin iLogin) {
        applicationContext = context.getApplicationContext();
        LoginAssistant.getInstance().setApplicationContext(context);
        LoginAssistant.getInstance().setiLogin(iLogin);
    }

    /**
     * 单点登录，验证token失效的统一接入入口
     */
    public void serverTokenInvalidation(int userDefine) {
        LoginAssistant.getInstance().serverTokenInvalidation(userDefine);
    }
}
