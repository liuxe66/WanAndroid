package com.liuxe.wanandroid.ui.activity.common;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.liuxe.wanandroid.R;
import com.yunxiaosheng.baselib.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,"关于项目");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

}
