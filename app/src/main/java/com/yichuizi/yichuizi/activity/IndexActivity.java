package com.yichuizi.yichuizi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
//
import com.yichuizi.loginlibrary.annotation.LoginFilter;
import com.yichuizi.loglibrary.LogActivity;
import com.yichuizi.loglibrary.annotation.LogAroundFilter;
import com.yichuizi.netlibrary.RxHttpUtils;
import com.yichuizi.netlibrary.interceptor.Transformer;
import com.yichuizi.netlibrary.observer.CommonObserver;
import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.api.YichuiziService;
import com.yichuizi.yichuizi.bean.BookBean;

import static com.yichuizi.netlibrary.utils.ToastUtils.showToast;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_video:
                startActivity(new Intent(IndexActivity.this,VideoPlayActivity.class));
                break;
            case R.id.iv_publish:
                getBookData();
                break;
            case R.id.iv_log:
                startActivity(new Intent(IndexActivity.this,LogActivity.class));
                break;
        }
    }
    @LogAroundFilter(Log = "我去请求接口了                                                                                                                                                                                                                                       ")
    @LoginFilter
    private void getBookData() {
        RxHttpUtils
                .createApi(YichuiziService.class)
                .getBook()
                .compose(Transformer.<BookBean>switchSchedulers())
                .subscribe(new CommonObserver<BookBean>() {

                    //默认false   隐藏onError的提示
                    @Override
                    protected boolean isHideToast() {
                        return false;
                    }

                    //tag下的一组或一个请求，用来处理一个页面的所以请求或者某个请求
                    //设置一个tag就行就可以取消当前页面所有请求或者某个请求了
                    @Override
                    protected String setTag() {
                        return "tag1";
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BookBean bookBean) {
                        String s = bookBean.getSummary();

                        showToast(s);
                    }
                });
    }
}
