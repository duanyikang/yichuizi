package com.yichuizi.loglibrary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yichuizi.loglibrary.annotation.LogFilter;

/**
 * 作者： duanyikang on 2018/12/2.
 * 描述：
 */
public class LogActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_layout);
    }


    @LogFilter(Log = "LogActivity 点击了")
    @Override
    public void onClick(View v) {
        one();
    }

    @LogFilter(Log = "执行到方法一了")
    public void one() {
        //dosomething
        System.out.println("mothed1执行7777777");
    }


}
