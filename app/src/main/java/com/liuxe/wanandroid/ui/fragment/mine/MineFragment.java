package com.liuxe.wanandroid.ui.fragment.mine;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.liuxe.wanandroid.R;
import com.yunxiaosheng.baselib.base.fragment.BaseFragment;
import com.yunxiaosheng.baselib.utils.BitmapUtils;
import com.yunxiaosheng.baselib.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MineFragment extends BaseFragment {

    @BindView(R.id.mine_head_bg)
    ImageView mineHeadBg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;

    private int statusBarHeight;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        statusBarHeight = StatusBarUtils.getStatusBarHeight(mContext);
        CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.height = statusBarHeight+layoutParams.height;

        toolbar.setPadding(0,statusBarHeight,0,0);
        Bitmap rootBtimap = BitmapUtils.getBitmapFromDrawable(mContext,R.drawable.ic_photo);
        Bitmap rootBlurBitmap = BitmapUtils.getBlurBitmap(mContext,rootBtimap,20);
        mineHeadBg.setImageBitmap(rootBlurBitmap);

    }

}
