package com.yichuizi.videolibrary.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yichuizi.videolibrary.opengl.OneGlSurfaceView;

/**
 * 作者： duanyikang on 2019/1/2.
 * 描述：
 */
public class OpenGLActivity extends Activity {
    private OneGlSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new OneGlSurfaceView(this);
        setContentView(mGLSurfaceView);
    }
}
