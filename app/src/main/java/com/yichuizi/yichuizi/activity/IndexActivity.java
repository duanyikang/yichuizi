package com.yichuizi.yichuizi.activity;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import com.yichuizi.audiolibrary.TomCatActivity;
import com.yichuizi.loginlibrary.annotation.LoginFilter;
import com.yichuizi.loglibrary.annotation.LogAroundFilter;
import com.yichuizi.videolibrary.SuperVideoActivity;
import com.yichuizi.videolibrary.opengl.OpenGLActivity;
import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.bean.BookBean;
import com.yichuizi.yichuizi.nativeclass.ChangeVoice;
import com.yichuizi.yichuizi.viewmodel.BookViewModel;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {
    private BookViewModel mBookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.startMethodTracing("guoguo");
        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        setContentView(R.layout.activity_index);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_video:
                startActivity(new Intent(IndexActivity.this, VideoPlayActivity.class));
                break;
            case R.id.bt_publish:
                startActivity(new Intent(IndexActivity.this, PublishActivity.class));
                break;

            case R.id.bt_net:
                getBookData();
                break;
            case R.id.bt_recyclerview:
                startActivity(new Intent(IndexActivity.this, RecyclerActivity.class));
                break;
            case R.id.bt_chatroom:
                startActivity(new Intent(IndexActivity.this, ChatActivity.class));
                break;
            case R.id.bt_flex:
                startActivity(new Intent(IndexActivity.this, LayoutAnimationActivity.class));
                break;
            case R.id.bt_mediacodec:
                startActivity(new Intent(IndexActivity.this, MediaCodecActivity.class));
                break;
            case R.id.bt_tomcat:
                startActivity(new Intent(IndexActivity.this, TomCatActivity.class));
                break;
            case R.id.bt_changevoice:
                startActivity(new Intent(IndexActivity.this, ChangeVoiceActivity.class));
                break;
            case R.id.bt_gl:
                startActivity(new Intent(IndexActivity.this, OpenGLActivity.class));
                break;
            case R.id.bt_supervideo:
                startActivity(new Intent(IndexActivity.this, MFWActivity.class));
                break;
        }
    }

    @LogAroundFilter(Log = "我点击请求接口了")
    @LoginFilter
    private void getBookData() {
        mBookViewModel.getBookData().observe(this, new Observer<BookBean>() {
            @Override
            public void onChanged(@Nullable BookBean bookBean) {
                if (bookBean != null) {
                    System.out.println("我要的:" + bookBean.getSummary());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Debug.stopMethodTracing();
    }
}
