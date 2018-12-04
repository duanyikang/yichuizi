package com.yichuizi.yichuizi.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yichuizi.yichuizi.R;

import java.util.Random;

/**
 * 作者： duanyikang on 2018/12/4.
 * 描述：
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainHolder> {

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        holder.mImageView.setBackgroundColor(Color.parseColor("#" + r + g + b));
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class MainHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public MainHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_bg);
        }
    }
}
