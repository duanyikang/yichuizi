package com.yichuizi.yichuizi.bean;

import android.graphics.drawable.Drawable;

/**
 * 作者： duanyikang on 2018/12/13.
 * 描述：
 */
public class WanFaBean {
    private String icon;
    private String text;
    private String color;
    private Drawable mDrawable;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Drawable getmDrawable() {
        return mDrawable;
    }

    public void setmDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }
}
