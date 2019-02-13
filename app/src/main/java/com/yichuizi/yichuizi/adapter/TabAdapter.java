package com.yichuizi.yichuizi.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.yichuizi.yichuizi.R;

/**
 * 作者： duanyikang on 2019/1/10.
 * 描述：
 */
public class TabAdapter extends RecyclerView.Adapter<TabAdapter.TabHolder> {

    private int fuckPosition;

    @NonNull
    @Override
    public TabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TabHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tab_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TabHolder holder, int position) {
        if (fuckPosition == position) {
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f);
            Animator a = ObjectAnimator.ofPropertyValuesHolder(holder.itemView, pvhX, pvhY).setDuration(300);
            a.setInterpolator(new AccelerateDecelerateInterpolator());
            a.start();
        }
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public void startAnim(int position) {
        if(this.fuckPosition==position){
            return;
        }
        this.fuckPosition = position;
        notifyItemChanged(position);
    }

    public class TabHolder extends RecyclerView.ViewHolder {
        public TabHolder(View itemView) {
            super(itemView);
        }
    }
}
