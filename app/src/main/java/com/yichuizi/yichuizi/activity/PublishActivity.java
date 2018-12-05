package com.yichuizi.yichuizi.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.nativeclass.Publisher;
import com.yichuizi.yichuizi.view.PublishSurfaceView;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者： duanyikang on 2018/12/5.
 * 描述：
 */
public class PublishActivity extends Activity implements SurfaceHolder.Callback {
    private PublishSurfaceView sv;
    private int screenWidth = 1280;
    private int screenHeight = 720;
    private SurfaceHolder mHolder;
    private Camera mCamera;
    boolean isPreview = false; // 是否在浏览中
    private String url = "rtmp://172.30.5.220:1935/live/movie";
    //采集到每帧数据时间
    long previewTime = 0;
    //开始编码时间
    long encodeTime = 0;

    boolean isPlaying = false;

    Button btn_start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    isPlaying = false;
                    btn_start.setText("start");
                } else {
                    isPlaying = true;
                    btn_start.setText("stop");
                }
            }
        });
        init();
    }

    private void init() {
        Publisher.init(this);
        sv = findViewById(R.id.sv);
        mHolder = sv.getHolder();
        mHolder.addCallback(this);
        Publisher.getInstance().initVideo(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Publisher.getInstance().close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera();
            if (mHolder != null) {
                setStartPreview(mCamera, mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private Camera getCamera() {
        Camera camera;
        try {
            //打开相机，默认为后置，可以根据摄像头ID来指定打开前置还是后置
            camera = Camera.open(1);
            if (camera != null && !isPreview) {
                try {
                    Camera.Parameters parameters = camera.getParameters();
                    //对拍照参数进行设置

                    parameters.setPreviewSize(screenWidth, screenHeight); // 设置预览照片的大小
                    parameters.setPreviewFpsRange(30000, 30000);
                    parameters.setPictureFormat(ImageFormat.NV21); // 设置图片格式
                    parameters.setPictureSize(screenWidth, screenHeight); // 设置照片的大小
                    camera.setParameters(parameters);
                    //指定使用哪个SurfaceView来显示预览图片
                    camera.setPreviewDisplay(sv.getHolder()); // 通过SurfaceView显示取景画面
                    camera.setPreviewCallback(new StreamIt()); // 设置回调的类
                    camera.startPreview(); // 开始预览
                    //Camera.takePicture()方法进行拍照
                    camera.autoFocus(null); // 自动对焦
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isPreview = true;
            }
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
            Toast.makeText(this, "无法获取前置摄像头", Toast.LENGTH_LONG);
        }
        return camera;
    }

    public static void followScreenOrientation(Context context, Camera camera) {
        final int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            camera.setDisplayOrientation(90);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }


    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            followScreenOrientation(this, camera);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    ExecutorService executor = Executors.newSingleThreadExecutor();

    public class StreamIt implements Camera.PreviewCallback {
        @Override
        public void onPreviewFrame(final byte[] data, Camera camera) {
            if (isPlaying) {
                long endTime = System.currentTimeMillis();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        encodeTime = System.currentTimeMillis();
                        Publisher.getInstance().onFrameCallback(data);
                    }
                });
                previewTime = endTime;
            }

        }
    }


}