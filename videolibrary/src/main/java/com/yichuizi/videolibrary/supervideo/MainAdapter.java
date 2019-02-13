package com.yichuizi.videolibrary.supervideo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yichuizi.videolibrary.R;
import com.yichuizi.videolibrary.bean.VideoBean;
import com.yichuizi.videolibrary.view.ItemRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： duanyikang on 2019/1/2.
 * 描述：
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
    private List<List<VideoBean>> mMainData;

    public void addData(List<List<VideoBean>> mMainData) {
        this.mMainData = mMainData;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        holder.mItemRecyclerView.addData(mMainData.get(position));
    }

    @Override
    public int getItemCount() {
        if (mMainData == null) {
            return 0;
        }
        return mMainData.size();
    }

    class MainHolder extends RecyclerView.ViewHolder {
        private ItemRecyclerView mItemRecyclerView;

        public MainHolder(View itemView) {
            super(itemView);
            mItemRecyclerView = itemView.findViewById(R.id.rl_item);
        }
    }
}
