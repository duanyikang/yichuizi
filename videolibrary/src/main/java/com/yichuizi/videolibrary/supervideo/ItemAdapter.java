package com.yichuizi.videolibrary.supervideo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yichuizi.videolibrary.R;
import com.yichuizi.videolibrary.bean.VideoBean;

import java.util.List;
import java.util.Random;

/**
 * 作者： duanyikang on 2019/1/2.
 * 描述：
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private List<VideoBean> mData;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
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
        return Integer.MAX_VALUE;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public ItemHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv);
        }
    }
}
