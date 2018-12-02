package com.yichuizi.yichuizi;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.yichuizi.loginlibrary.LoginActivity;
import com.yichuizi.loginlibrary.core.ILogin;
import com.yichuizi.loginlibrary.core.LoginManager;
import com.yichuizi.netlibrary.RxHttpUtils;

/**
 * 作者： duanyikang on 2018/9/21.
 * 邮箱： 574281045@qq.com
 * 描述：
 */

public class MyApplication extends Application {
    private boolean islogin = false;//先假装下

    @Override
    public void onCreate() {
        super.onCreate();
        iniLogin();
        iniNet();
    }

    /**
     * 初始化AOP登陆
     */
    private void iniLogin() {
        LoginManager.getInstance().init(this, iLogin);
    }

    /**
     * 初始化网络模块
     */
    private void iniNet() {
        RxHttpUtils
                .getInstance()
                .init(this)
                .config()
                .setBaseUrl("https://api.douban.com/");
    }

    ILogin iLogin = new ILogin() {
        @Override
        public void login(Context context, int login) {
            switch (login) {
                case 0:
                    //普通需要登陆
                    startActivity(new Intent(context, LoginActivity.class));
                    break;
                case 1:
                    //可能是需要一个对话框
                    break;
            }
            islogin=true;
        }

        @Override
        public boolean isLogin(Context context) {
            return islogin;
        }

        @Override
        public void loginOut(Context context) {
            islogin = false;
        }
    };
}
