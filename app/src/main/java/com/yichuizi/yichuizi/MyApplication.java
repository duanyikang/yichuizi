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
        LoginManager.getInstance().init(this,iLogin);
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

    ILogin iLogin=new ILogin() {
        @Override
        public void login(Context context, int login) {
            System.out.println("2222222");
            startActivity(new Intent(context, LoginActivity.class));
        }

        @Override
        public boolean isLogin(Context context) {
            System.out.println("1111111");
            return true;
        }

        @Override
        public void loginOut(Context context) {

        }
    };
}
