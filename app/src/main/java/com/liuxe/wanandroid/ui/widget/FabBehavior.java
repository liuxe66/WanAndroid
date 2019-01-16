package com.liuxe.wanandroid.ui.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

import com.yunxiaosheng.baselib.utils.LogUtils;

public class FabBehavior extends CoordinatorLayout.Behavior<View> {

    AnimatorSet mShowAnimatorSet,mHideAnimatorSet;

    public FabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;//表示只接受垂直方向的滑动
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //dy是我们手指滑动的垂直方向的值，child为设置了此Behaviour的View
        if (dy > 10) {//向上滑为正值, 隐藏
            hide(child);
        } else if (dy < -10) {//向下滑为负值，显示
            show(child);
        }
    }

    private void show(View child) {
        if (mShowAnimatorSet == null){
            ObjectAnimator showAnimator1 = ObjectAnimator.ofFloat(child, "scaleX", 0f, 1f);
            ObjectAnimator showAnimator2 = ObjectAnimator.ofFloat(child, "scaleY", 0f, 1f);
            mShowAnimatorSet = new AnimatorSet();
            mShowAnimatorSet.play(showAnimator1).with(showAnimator2);
            mShowAnimatorSet.setInterpolator(new FastOutSlowInInterpolator());
            mShowAnimatorSet.setDuration(200);
        }

        if(!mShowAnimatorSet.isRunning() && child.getScaleY() == 0f){
            mShowAnimatorSet.start();
        }

    }

    private void hide(View child) {
        if (mHideAnimatorSet == null){
            ObjectAnimator showAnimator1 = ObjectAnimator.ofFloat(child, "scaleX", 1f, 0f);
            ObjectAnimator showAnimator2 = ObjectAnimator.ofFloat(child, "scaleY", 1f, 0f);
            mHideAnimatorSet = new AnimatorSet();
            mHideAnimatorSet.play(showAnimator1).with(showAnimator2);
            mHideAnimatorSet.setInterpolator(new FastOutSlowInInterpolator());
            mHideAnimatorSet.setDuration(200);
        }

        if(!mHideAnimatorSet.isRunning() && child.getScaleY() == 1f){
            mHideAnimatorSet.start();
        }

    }
}
