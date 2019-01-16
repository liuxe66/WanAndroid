package com.liuxe.wanandroid.ui.fragment.tpublic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Tab;
import com.liuxe.wanandroid.contract.tpublic.PublicContract;
import com.liuxe.wanandroid.presenter.tpublic.PublicPresenter;
import com.yunxiaosheng.baselib.adapter.FragmentAdapter;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.fragment.BaseMvpFragment;
import com.yunxiaosheng.baselib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PublicFragment extends BaseMvpFragment<PublicPresenter> implements PublicContract.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tab_public)
    TabLayout tabPublic;
    @BindView(R.id.vp_public)
    ViewPager vpPublic;
    @BindView(R.id.public_container)
    CoordinatorLayout publicContainer;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private List<Fragment> mFragmentList;

    public static PublicFragment newInstance() {
        return new PublicFragment();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mFragmentList = new ArrayList<>();
        mPresenter.loadTabList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_public;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mTvTitle.setText("公众号");
    }

    @Override
    public void showTab(BaseResponse<List<Tab>> response) {
        if (response.getErrorCode() == 0){
            for (int i = 0; i < response.getData().size(); i++) {
                tabPublic.addTab(tabPublic.newTab());
                mFragmentList.add(PublicTabFragment.newInstance(""+response.getData().get(i).getId()));
            }

            vpPublic.setAdapter(new FragmentAdapter(getChildFragmentManager(), mFragmentList));
            vpPublic.setCurrentItem(0);//要设置到viewpager.setAdapter后才起作用
            tabPublic.setupWithViewPager(vpPublic);
            tabPublic.setVerticalScrollbarPosition(0);
            //tlTabs.setupWithViewPager方法内部会remove所有的tabs，这里重新设置一遍tabs的text，否则tabs的text不显示
            for (int i = 0; i < response.getData().size(); i++) {
                tabPublic.getTabAt(i).setText(response.getData().get(i).getName());
            }

            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PublicTabFragment tabFragment = (PublicTabFragment) mFragmentList.get(vpPublic.getCurrentItem());
                    tabFragment.scrollTop();
                }
            });
        } else {
            ToastUtils.showToast(response.getErrorMsg());
        }
    }
    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return PublicPresenter.newInstance();
    }

    @Override
    public void showNetworkError() {

    }


}
