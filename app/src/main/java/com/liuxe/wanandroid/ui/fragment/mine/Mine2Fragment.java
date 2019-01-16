package com.liuxe.wanandroid.ui.fragment.mine;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuxe.wanandroid.R;
import com.yunxiaosheng.baselib.base.fragment.BaseFragment;
import com.yunxiaosheng.baselib.utils.BitmapUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class Mine2Fragment extends BaseFragment {
    @BindView(R.id.circle_photo)
    CircleImageView circlePhoto;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;


    public static Mine2Fragment newInstance() {
        return new Mine2Fragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine2;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        Bitmap rootBtimap = BitmapUtils.getBitmapFromDrawable(mContext, R.drawable.ic_photo);
        Bitmap rootBlurBitmap = BitmapUtils.getBlurBitmap(mContext, rootBtimap, 20);
        rlTop.setBackground(new BitmapDrawable(rootBlurBitmap));
    }


    @OnClick(R.id.circle_photo)
    public void onViewClicked() {

    }
}
