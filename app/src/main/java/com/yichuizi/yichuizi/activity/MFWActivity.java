package com.yichuizi.yichuizi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.view.MFWRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者： duanyikang on 2018/12/17.
 * 描述：
 */
public class MFWActivity extends Activity {
    private MFWRecyclerView mfwRecyclerView;
    private List<String> mdata = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mfw_layout);
        mfwRecyclerView = findViewById(R.id.mfwrecycler);
        for (int i = 0; i < 50; i++) {
            mdata.add(String.valueOf(i));
        }
        mfwRecyclerView.setData(mdata);
    }

    private void learn(){
        //map<给你1，还给你2>
        //concat<Observable1(先读下缓存)如果缓存有，直接发射出来如果没有，则e.onComplete()，Observable2(请求网络)>
          //感觉就是串联起来，

        //flatMap<>
        //zip 合并多个Obersvale
        //interval
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {

            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
