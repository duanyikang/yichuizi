package com.yichuizi.audiolibrary;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * 作者：段益康 on 2019/1/1 18:39
 * 描述：
 */
public class TomCatActivity extends Activity {

    private ServiceConnection mServiceConnection;
    private IAudioRecordeAidlInterface mIAudioRecordeAidlInterface;
    public static String[] MICROPHONE = {Manifest.permission.RECORD_AUDIO};
    public static String[] STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomcat);
        checkRecordPermission();
        startBindService();
        initView();
    }

    private void startBindService() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mIAudioRecordeAidlInterface = IAudioRecordeAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIAudioRecordeAidlInterface = null;
            }
        };

        Intent intent = new Intent(this, AudioRecordService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void initView() {
        mButton = findViewById(R.id.bt_tom);
        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //开始录音
                        if(mIAudioRecordeAidlInterface!=null){
                            try {
                                mButton.setText("接收数据中");
                                mIAudioRecordeAidlInterface.startAudioRecord();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        //播放录音
                        if( mIAudioRecordeAidlInterface!=null){
                            try {
                                mButton.setText("我可能是一只假的TOM猫");
                                System.out.println("我要的地址"+mIAudioRecordeAidlInterface.stopAudioRecord());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }


                return true;
            }
        });
    }

    public void checkRecordPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, MICROPHONE, 1);
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, STORAGE, 1);
            return;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceConnection != null) {
            unbindService(mServiceConnection);
        }
    }
}
