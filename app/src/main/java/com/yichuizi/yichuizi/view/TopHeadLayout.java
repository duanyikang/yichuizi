package com.yichuizi.yichuizi.view;


import android.animation.ObjectAnimator;

import android.animation.ValueAnimator;
import android.content.Context;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yichuizi.yichuizi.R;
import com.yichuizi.yichuizi.adapter.TabAdapter;
import com.yichuizi.yichuizi.util.DensityUtil;

/**
 * 作者： duanyikang on 2019/1/10.
 * 描述： 一个edittext 一个recyclerview
 */
public class TopHeadLayout extends FrameLayout {
    private EditText mEditTextSearch;
    private ImageView mImageViewMessage, mImageViewBackTop;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mConstraintLayout;
    private TabAdapter mTabAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public TopHeadLayout(Context context) {
        super(context, null);
    }

    public TopHeadLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initView();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_tophand_layout, this, true);
    }

    private void initView() {
        mEditTextSearch = findViewById(R.id.edit_search);
        mImageViewMessage = findViewById(R.id.iv_message);
        mRecyclerView = findViewById(R.id.recycler);
        mImageViewBackTop = findViewById(R.id.iv_back_top);
        mConstraintLayout = findViewById(R.id.rootlayout);
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mTabAdapter = new TabAdapter();
        mRecyclerView.setAdapter(mTabAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    public void startAnim() {
        mEditTextSearch.setHint("搜索");
        final ConstraintSet applyConstraintSet = new ConstraintSet();
        applyConstraintSet.clone(mConstraintLayout);
        applyConstraintSet.clear(mEditTextSearch.getId(), ConstraintSet.END);


        ValueAnimator animator = ValueAnimator.ofInt(mEditTextSearch.getWidth(), DensityUtil.dip2px(getContext(), 100)).setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                applyConstraintSet.constrainWidth(mEditTextSearch.getId(), (Integer) valueAnimator.getAnimatedValue());
                applyConstraintSet.applyTo(mConstraintLayout);
                beginDelayedTransition(mConstraintLayout);
            }
        });
        animator.start();


//        setMyApha(true);


    }


    public void endAnim() {
        mEditTextSearch.setHint("我是长搜索真心的666");
        ConstraintSet applyConstraintSet = new ConstraintSet();
        applyConstraintSet.clone(mConstraintLayout);
        applyConstraintSet.connect(mEditTextSearch.getId(), ConstraintSet.END, mImageViewMessage.getId(), ConstraintSet.START);
        applyConstraintSet.constrainWidth(mEditTextSearch.getId(), ConstraintSet.MATCH_CONSTRAINT);
        applyConstraintSet.applyTo(mConstraintLayout);
        beginDelayedTransition(mConstraintLayout);
        setMyApha(false);
    }

    private void beginDelayedTransition(ViewGroup view) {
        AutoTransition transitionSet = new AutoTransition();
        transitionSet.setDuration(0);

        TransitionManager.beginDelayedTransition(view, transitionSet);
    }


    private void setMyApha(boolean start) {
        if (start) {
            ObjectAnimator.ofFloat(mRecyclerView, "Alpha", 0f, 1f).setDuration(800).start();
        } else {
            ObjectAnimator.ofFloat(mRecyclerView, "Alpha", 1, 0f).setDuration(500).start();

        }
    }
}
