package com.yichuizi.yichuizi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.yichuizi.yichuizi.R;

import java.lang.ref.WeakReference;

/**
 * 作者： duanyikang on 2018/9/21.
 * 邮箱： 574281045@qq.com
 * 描述： 闪屏页
 */

public class SplashActivity extends Activity {
    private static final long STOP_TIME = 2000;
    private static final int JUMP_TYPE = 1;
    private MyHandler myHandler = new MyHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myHandler.sendEmptyMessageDelayed(JUMP_TYPE, STOP_TIME);
    }


    class MyHandler extends Handler {
        private WeakReference<Activity> weakReference;

        public MyHandler(Activity mActivity) {
            weakReference = new WeakReference<>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity mActivity = weakReference.get();
            if (mActivity != null) {
                startActivity(new Intent(mActivity, IndexActivity.class));
                finish();
            }
        }
    }
}
