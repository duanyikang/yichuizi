package com.yichuizi.yichuizi.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yichuizi.yichuizi.R;

import chat.Chat;
import chat.ReceiveMessage;

/**
 * 作者： duanyikang on 2018/12/10.
 * 描述：
 */
public class ChatActivity extends Activity {
    private EditText mEditTextInput;
    private Button mButtonSend;
    private TextView mTextViewContent;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_layout);
        initView();
        System.out.println("我要的");
        Chat.initNet();
        Chat.initNickName("用户"+Build.MODEL);
        Chat.setCallBack(new ReceiveMessage() {
            @Override
            public void call(final String s) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewContent.setText(s);
                    }
                });
            }
        });
    }

    private void initView() {
        mTextViewContent = findViewById(R.id.content);
        mEditTextInput = findViewById(R.id.edt_input);
        mButtonSend = findViewById(R.id.bt_send);
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mEditTextInput.getText().toString())) {
                    Chat.sendMessag(mEditTextInput.getText().toString());
                    mTextViewContent.setText(mEditTextInput.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Chat.close();
        handler.removeCallbacksAndMessages(null);
    }
}
