package com.liuxe.wanandroid.ui.fragment.tpublic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.adapter.ArticleAdapter;
import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.contract.tpublic.PublicTabContract;
import com.liuxe.wanandroid.presenter.tpublic.PublicTabPresenter;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.fragment.BaseMvpFragment;
import com.yunxiaosheng.baselib.utils.ToastUtils;
import com.yunxiaosheng.baselib.widgets.VRecylerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PublicTabFragment extends BaseMvpFragment<PublicTabPresenter> implements PublicTabContract.IView {
    @BindView(R.id.tab_recyler)
    VRecylerView tabRecyler;

    private ArticleAdapter mAdapter;
    private List<Article> mList;
    private List<Article> mNewList;
    private String wxId;
    private int pageIndex = 0;

    public static PublicTabFragment newInstance(String wxId) {
        Bundle args = new Bundle();
        args.putString("wxId", wxId);
        PublicTabFragment fragment = new PublicTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        tabRecyler.showLoading();
        mPresenter.loadList(wxId, pageIndex + "");
    }

    public void scrollTop(){
        tabRecyler.getRecyclerView().smoothScrollToPosition(0);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return PublicTabPresenter.newInstance();
    }

    @Override
    public void showNetworkError() {
        tabRecyler.showError();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        wxId = (String) getArguments().get("wxId");

        mList = new ArrayList<>();
        mAdapter = new ArticleAdapter(R.layout.item_article_home, mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        tabRecyler.setLayoutManager(layoutManager);
        tabRecyler.setAdapter(mAdapter);
        mAdapter.openLoadAnimation();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onArticleItemClick(position, mList.get(position));
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMoreList(wxId, pageIndex + "");
            }
        }, tabRecyler.getRecyclerView());

    }

    @Override
    public void showWxlist(BaseResponse<Articles> response) {
        if (response.getErrorCode() == 0){
            mNewList = response.getData().getDatas();
            if (mNewList.size() == 0) {
                tabRecyler.showEmpty();
                return;
            }
            tabRecyler.hideNetworkStatus();
            pageIndex++;
            mList.addAll(mNewList);
            mAdapter.setNewData(mList);
        } else {
            ToastUtils.showToast(response.getErrorMsg());
        }
    }

    @Override
    public void showMoreList(BaseResponse<Articles> response) {
        if (response.getErrorCode() == 0){
            mNewList = response.getData().getDatas();
            if (mNewList.size() == 0){
                mAdapter.setEnableLoadMore(false);
                ToastUtils.showToast("已经到底部了！");
                return;
            }
            pageIndex++;
            mList.addAll(mNewList);
            mAdapter.setNewData(mList);
        } else {
            ToastUtils.showToast(response.getErrorMsg());
        }
    }

    @Override
    public void itemNotifyChanged(int position) {
        mAdapter.notifyItemChanged(position);
    }

}
