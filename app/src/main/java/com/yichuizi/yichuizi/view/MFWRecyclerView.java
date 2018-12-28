package com.yichuizi.yichuizi.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;


public class MFWRecyclerView extends RecyclerView {
    private List<String> sumdata = new ArrayList<>();
    private List<String> mCurrentdata = new ArrayList<>();
    private int mCurrentPosition;
    private int maxNumOneRow = 3;
    private MyAdapter myAdapter;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            startAnim();
            return true;
        }
    });


    public MFWRecyclerView(Context context) {
        super(context, null);
    }

    public MFWRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        myAdapter = new MyAdapter();
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        setAdapter(myAdapter);
        addItemDecoration(new MyItemDecoration());
    }

    private void startAnim() {
        System.out.println("我要的");
       // mCurrentdata.remove(0);
        mCurrentPosition++;
        if (mCurrentPosition >= sumdata.size()) {
            mCurrentPosition = 0;
        }
        mCurrentdata.add(sumdata.get(mCurrentPosition));
       // myAdapter.notifyItemRemoved(2);
        myAdapter.notifyItemInserted(0);
        mHandler.sendEmptyMessageDelayed(1, 5000);
    }

    public void setData(List<String> data) {
        this.sumdata = data;
        if (myAdapter != null) {
            myAdapter.notifyDataSetChanged();
        }

        for (int i = 0; i < Math.min(sumdata.size(), maxNumOneRow); i++) {
            mCurrentdata.add(sumdata.get(i));
            mCurrentPosition = i;
        }


        if (sumdata.size() > maxNumOneRow) {
            mHandler.sendEmptyMessageDelayed(1, 5000);
        }
    }

    class MyAdapter extends Adapter<MyHolder> {

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mCurrentdata.size();
        }
    }

    class MyHolder extends ViewHolder {
        private ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    class MyItemDecoration extends ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            super.getItemOffsets(outRect, view, parent, state);


            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.right = -DensityUtil.dip2px(getContext(), 20);
            }
        }
    }
}
