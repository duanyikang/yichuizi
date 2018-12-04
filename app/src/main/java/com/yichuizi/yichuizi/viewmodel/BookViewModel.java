package com.yichuizi.yichuizi.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LiveData;

import com.yichuizi.netlibrary.RxHttpUtils;
import com.yichuizi.netlibrary.interceptor.Transformer;
import com.yichuizi.netlibrary.observer.CommonObserver;
import com.yichuizi.yichuizi.api.YichuiziService;
import com.yichuizi.yichuizi.bean.BookBean;


/**
 * 作者： duanyikang on 2018/12/4.
 * 描述：
 */
public class BookViewModel extends ViewModel {
    private MutableLiveData<BookBean> mBookBean = new MutableLiveData<>();

    public LiveData<BookBean> getBookData() {
        if (mBookBean.getValue() == null) {
            loadBook();
        }
        return mBookBean;
    }

    private MutableLiveData<BookBean> loadBook() {
        RxHttpUtils
                .createApi(YichuiziService.class)
                .getBook()
                .compose(Transformer.<BookBean>switchSchedulers())
                .subscribe(new CommonObserver<BookBean>() {

                    @Override
                    protected boolean isHideToast() {
                        return false;
                    }

                    @Override
                    protected String setTag() {
                        return "tag1";
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BookBean bookBean) {
                        mBookBean.setValue(bookBean);
                        System.out.println("走到网络请求了");
                    }
                });
        return mBookBean;
    }
}
