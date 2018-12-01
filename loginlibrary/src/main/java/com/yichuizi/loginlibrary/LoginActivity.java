package com.yichuizi.loginlibrary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yichuizi.loginlibrary.annotation.LoginFilter;

/**
 * 作者： duanyikang on 2018/11/30.
 * 描述：
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @LoginFilter
    @Override
    public void onClick(View v) {

    }
}
