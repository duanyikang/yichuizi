package com.yichuizi.loglibrary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yichuizi.loglibrary.annotation.LogAfterFilter;
import com.yichuizi.loglibrary.annotation.LogAfterReturnFilter;
import com.yichuizi.loglibrary.annotation.LogBeforeFilter;

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


    //@LogFilter(Log = "LogActivity 点击了")
    @Override
    public void onClick(View v) {
        three();
    }

    @LogAfterReturnFilter(Log = "执行到方法一了")
    public boolean one() {
        //dosomething
        System.out.println("mothed1执行7777777");
        return true;
    }


    @LogBeforeFilter(Log = "执行到方法二了 before")
    public boolean two() {
        //dosomething
        System.out.println("mothed2执行7777777");
        return true;
    }


    @LogAfterFilter(Log = "执行到方法三了 after")
    public boolean three() {
        //dosomething
        System.out.println("mothed3执行7777777");
        return true;
    }
}
