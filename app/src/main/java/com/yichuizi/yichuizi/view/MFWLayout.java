package com.yichuizi.yichuizi.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者： duanyikang on 2018/12/17.
 * 描述：
 */
public class MFWLayout extends LinearLayout {
    private static final int MAX_NUMBER = 3;
    private static final int MESSAGE_REMOVE_VIEW = 1;
    private static final int MESSAGE_ADD_VIEW = 2;
    private LayoutTransition mLayoutTransition;
    private List<String> mData = new ArrayList<>();
    private List<View> mCurrentViewList = new ArrayList<>();
    private int mLastPosition;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case MESSAGE_REMOVE_VIEW:
                    removeFirstView();
                    break;
                case MESSAGE_ADD_VIEW:
                    addNextView();
                    break;
            }
            return true;
        }
    });

    public MFWLayout(Context context) {
        super(context, null);
    }

    public MFWLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setData(List<String> mData) {
        //这里判断下重复
        this.mData = mData;
        if (this.mData.size() > 3) {
            //如果所给出的数据大于3个则需要循环
            mHandler.sendEmptyMessageDelayed(MESSAGE_REMOVE_VIEW, 3000);
        }
        addStartView();
    }

    private void init() {
        mLayoutTransition = new LayoutTransition();
        setLayoutTransition(mLayoutTransition);
        setTransition();
        inijiadeshuju();
    }

    private void inijiadeshuju() {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            temp.add(String.valueOf(i));
        }
        setData(temp);
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void setTransition() {
        /**
         * view出现时 view自身的动画效果
         */

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(null, "TranslationX", DensityUtil.dip2px(getContext(), 5), 0F).
                setDuration(mLayoutTransition.getDuration(LayoutTransition.APPEARING));
        ObjectAnimator animator11 = ObjectAnimator.ofFloat(null, "Alpha", 0F, 1F).
                setDuration(mLayoutTransition.getDuration(LayoutTransition.APPEARING));
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animator1, animator11);
        mLayoutTransition.setAnimator(LayoutTransition.APPEARING, animatorSet1);

        /**
         * view 消失时，view自身的动画效果
         */
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(null, "TranslationX", 0F, -DensityUtil.dip2px(getContext(), 10)).
                setDuration(mLayoutTransition.getDuration(LayoutTransition.APPEARING));
        ObjectAnimator animator22 = ObjectAnimator.ofFloat(null, "Alpha", 1F, 0F).
                setDuration(mLayoutTransition.getDuration(LayoutTransition.APPEARING));
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animator2, animator22);
        mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, animatorSet2);

        PropertyValuesHolder pvhLeft =
                PropertyValuesHolder.ofInt("left", 0, 1);
        PropertyValuesHolder pvhTop =
                PropertyValuesHolder.ofInt("top", 0, 1);
        PropertyValuesHolder pvhRight =
                PropertyValuesHolder.ofInt("right", 0, 1);
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 1);


        /**
         * view出现时，导致整个布局改变的动画
         */
        PropertyValuesHolder animator3 = PropertyValuesHolder.ofFloat("scaleX", 1F, 2F, 1F);
        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhTop, pvhRight, pvhBottom, animator3).
                setDuration(mLayoutTransition.getDuration(LayoutTransition.CHANGE_APPEARING));
        changeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View view = (View) ((ObjectAnimator) animation).getTarget();
                view.setScaleX(1.0f);
            }
        });
        mLayoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);


        /**
         * view消失，导致整个布局改变时的动画
         */
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.5f, 2f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation =
                PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2);
        final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation).
                setDuration(mLayoutTransition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        changeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View view = (View) ((ObjectAnimator) animation).getTarget();
                view.setScaleX(1.0f);
            }
        });
        mLayoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);

    }

    /**
     * 添加起始view
     */
    private void addStartView() {
        for (int i = 0; i < Math.min(mData.size(), MAX_NUMBER); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.iv_video);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(getContext(), 20), DensityUtil.dip2px(getContext(), 20));
            params.setMargins(i == 0 ? 0 : -DensityUtil.dip2px(getContext(), 4), 0, 0, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setZ(MAX_NUMBER - i);
            }
            mCurrentViewList.add(imageView);
            addView(imageView, params);
            mLastPosition = i;
        }
    }

    /**
     * 添加一个view
     */
    private void addNextView() {
        mLastPosition++;
        if (mLastPosition >= mData.size()) {
            mLastPosition = 0;
        }
        //mData.get(mLastPosition)//这个是头像地址
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.iv_video);
        LayoutParams params = new LayoutParams(DensityUtil.dip2px(getContext(), 20), DensityUtil.dip2px(getContext(), 20));
        params.setMargins(-DensityUtil.dip2px(getContext(), 4), 0, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setZ(1);
        }
        mCurrentViewList.add(imageView);
        addView(imageView, params);
        mHandler.sendEmptyMessageDelayed(MESSAGE_REMOVE_VIEW, 3000);
    }

    /**
     * 删除第一个view
     */
    private void removeFirstView() {
        View view = getChildAt(0);
        removeView(view);
        mCurrentViewList.remove(0);
        for (int i = 0; i < mCurrentViewList.size(); i++) {
            View view1 = mCurrentViewList.get(i);
            if (i == 0) {
                LayoutParams params = (LayoutParams) view1.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view1.setZ(MAX_NUMBER - i);
            }
        }
        mHandler.sendEmptyMessageDelayed(MESSAGE_ADD_VIEW, mLayoutTransition.getDuration(LayoutTransition.APPEARING));
    }
}
