package com.liuxe.wanandroid.ui.fragment.mine;

import android.content.Intent;
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
import com.liuxe.wanandroid.constant.SputilsKey;
import com.liuxe.wanandroid.ui.activity.common.AboutActivity;
import com.liuxe.wanandroid.ui.activity.common.WebLoadDetailsActivity;
import com.yunxiaosheng.baselib.base.fragment.BaseFragment;
import com.yunxiaosheng.baselib.config.DBConfig;
import com.yunxiaosheng.baselib.utils.BitmapUtils;
import com.yunxiaosheng.baselib.utils.DBUtils;
import com.yunxiaosheng.baselib.utils.SpUtils;

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
    @BindView(R.id.url)
    TextView url;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.clear)
    TextView clear;



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

    @OnClick({R.id.url, R.id.about,R.id.clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.url:
                Bundle bundle = new Bundle();
                bundle.putString("title", "玩安卓项目");
                bundle.putString("url", "https://github.com/liuxe66/WanAndroid");
                mActivity.startActivity(WebLoadDetailsActivity.class,bundle);
                break;
            case R.id.about:
                Intent about = new Intent(mContext,AboutActivity.class);
                startActivity(about);
                break;
            case R.id.clear:
                SpUtils.remove(mContext,SputilsKey.islogin);
                SpUtils.remove(mContext,SputilsKey.username);
                SpUtils.remove(mContext,SputilsKey.passwoed);
                DBUtils.getDB(mContext).clearAll(DBConfig.TABLE_ARTICLE);
                DBUtils.getDB(mContext).clearAll(DBConfig.TABLE_PROJECT);
                break;
        }
    }
}
