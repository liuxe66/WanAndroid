package com.liuxe.wanandroid.ui.fragment.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.liuxe.wanandroid.R;
import com.yunxiaosheng.baselib.base.fragment.BaseFragment;
import com.yunxiaosheng.baselib.utils.StatusBarUtils;

import butterknife.BindView;

public class ProjectRootFragment extends BaseFragment {
    @BindView(R.id.view_status)
    View mViewStatus;

    private int statusBarHeight;

    public static ProjectRootFragment newInstance() {
        return new ProjectRootFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_root_project;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        statusBarHeight = StatusBarUtils.getStatusBarHeight
                (mContext);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mViewStatus.getLayoutParams();
        // 控件的高强制设成状态栏高度，重新定义AppBarLayout的高度
        layoutParams.height = statusBarHeight;
        if (findChildFragment(ProjectFragment.class) == null) {
            loadRootFragment(R.id.fl_root, ProjectFragment.newInstance());
        }
    }
}
