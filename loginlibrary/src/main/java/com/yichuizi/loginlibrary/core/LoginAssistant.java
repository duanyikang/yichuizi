package com.yichuizi.loginlibrary.core;

import android.content.Context;

/**
 * 作者： duanyikang on 2018/11/30.
 * 描述：
 */
public class LoginAssistant {
    private ILogin iLogin;
    private Context applicationContext;

    private LoginAssistant() {
    }

    private static LoginAssistant instance;

    public static LoginAssistant getInstance() {
        if (instance == null) {
            synchronized (LoginAssistant.class) {
                if (instance == null) {
                    instance = new LoginAssistant();
                }
            }
        }
        return instance;
    }

    public ILogin getiLogin() {
        return iLogin;
    }

    public void setiLogin(ILogin iLogin) {
        this.iLogin = iLogin;
    }


    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void serverTokenInvalidation(int userDefine) {
        if (iLogin == null)
            return;
        iLogin.loginOut(applicationContext);
        iLogin.login(applicationContext, userDefine);
    }
}
