package com.yichuizi.yichuizi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.adapter.MfwAdapter;

/**
 * 作者： duanyikang on 2018/12/17.
 * 描述：
 */
public class MFWActivity extends Activity {

    private RecyclerView recycler1;
    private RecyclerView recycler2;
    private MfwAdapter mfwAdapter1, mfwAdapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mfw_layout);
        initView();
    }

    private void initView() {
       recycler1 =findViewById(R.id.my_list);
        recycler2 =findViewById(R.id.recycler2);

        recycler1.setNestedScrollingEnabled(true);



       recycler1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
       recycler2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mfwAdapter1=new MfwAdapter();
        mfwAdapter2=new MfwAdapter();
        recycler1.setAdapter(mfwAdapter1);

       recycler2.setAdapter(mfwAdapter2);

    }
}
