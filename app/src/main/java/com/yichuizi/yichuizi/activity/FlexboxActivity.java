package com.yichuizi.yichuizi.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.bean.WanFaBean;
import com.yichuizi.yichuizi.util.DensityUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * 作者： duanyikang on 2018/12/13.
 * 描述：
 */
public class FlexboxActivity extends Activity {
    private FlexboxLayout mFlexboxLayout;
    private String str = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544680950248&di=dea3a2e048673cfb4c0ef88e27095bc8&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsports%2Ftransform%2Fw650h390%2F20180122%2FWuTs-fyqwiqi2098972.jpg";
    private List<WanFaBean> data = new ArrayList();
    private DownloadImageTask downloadImageTask = new DownloadImageTask();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox_layout);
        mFlexboxLayout = findViewById(R.id.flexbox);
        //为了实验初始化假数据
        for (int i = 0; i < 50; i++) {
            WanFaBean wanFaBean = new WanFaBean();
            wanFaBean.setIcon(str);
            wanFaBean.setText("你好压我是标题" + i);
            data.add(wanFaBean);
        }
        for (int i = 0; i < data.size(); i++) {
            downloadImageTask.execute(data.get(i));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downloadImageTask.cancel(true);
    }

    private class DownloadImageTask extends AsyncTask<WanFaBean, Void, WanFaBean> {
        protected WanFaBean doInBackground(WanFaBean... beans) {
            return loadImageFromNetwork(beans[0]);
        }

        protected void onPostExecute(WanFaBean result) {
            TextView textView = new TextView(FlexboxActivity.this);
            textView.setText(result.getText());
            Drawable drawable = result.getmDrawable();
            drawable.setBounds(0, 0, DensityUtil.dip2px(getApplicationContext(), 11), DensityUtil.dip2px(getApplicationContext(), 11));
            textView.setCompoundDrawables(drawable, null, null, null);
            mFlexboxLayout.addView(textView);
        }
    }

    private WanFaBean loadImageFromNetwork(WanFaBean bean) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(new URL(bean.getIcon()).openStream(), "image.jpg");
            bean.setmDrawable(drawable);
        } catch (IOException e) {

        }
        return bean;
    }

}
