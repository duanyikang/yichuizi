package com.yichuizi.yichuizi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yichuizi.yichuizi.R;

/**
 * 作者： duanyikang on 2019/2/11.
 * 描述：
 */
public class MfwAdapter extends RecyclerView.Adapter<MfwAdapter.Holder> {
    int count=0;
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("我要的："+(count++));
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mfw_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
