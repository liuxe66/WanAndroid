package com.liuxe.wanandroid.ui.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liuxe.wanandroid.R;
import com.yunxiaosheng.baselib.base.fragment.BaseFragment;

public class MineRootFragment  extends BaseFragment {
    public static MineRootFragment newInstance(){
        return new MineRootFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_root_mine;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (findChildFragment(MineFragment.class) == null) {
            loadRootFragment(R.id.fl_root, Mine2Fragment.newInstance());
        }
    }
}
