package com.liuxe.wanandroid.ui.activity.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.adapter.ArticleAdapter;
import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.contract.common.SerachArticleContract;
import com.liuxe.wanandroid.presenter.common.SerachArticlePresenter;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.activity.BaseMvpActivity;
import com.yunxiaosheng.baselib.utils.LogUtils;
import com.yunxiaosheng.baselib.utils.ToastUtils;
import com.yunxiaosheng.baselib.widgets.VRecylerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SerachArticleActivity extends BaseMvpActivity<SerachArticleContract.Presenter> implements SerachArticleContract.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.v_recylerview)
    VRecylerView vRecylerview;

    private ArticleAdapter mArticleAdapter;
    private List<Article> mArticleList;
    private String keyword;
    private int pageIndex = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        keyword = getIntent().getStringExtra("keyword");
        initTitleBar(toolbar,keyword);

        mArticleList = new ArrayList<>();
        mArticleAdapter = new ArticleAdapter(R.layout.item_article_home,mArticleList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        vRecylerview.setLayoutManager(layoutManager);
        vRecylerview.setAdapter(mArticleAdapter);

        vRecylerview.showLoading();
        mPresenter.loadArticle(pageIndex+"",keyword);

        vRecylerview.setOnErrorListener(new VRecylerView.OnErrorListener() {
            @Override
            public void setOnError() {
                mPresenter.loadArticle(pageIndex+"",keyword);
            }
        });

        mArticleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadArticle(pageIndex+"",keyword);
            }
        },vRecylerview.getRecyclerView());

        mArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onArticleItemClick(position,mArticleList.get(position));
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_serach_article;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return SerachArticlePresenter.newInstance();
    }

    @Override
    public void showNetworkError() {
        vRecylerview.showError();
    }

    @Override
    public void initArticle(BaseResponse<Articles> response) {
        if (response.getErrorCode() != 0){
            ToastUtils.showToast(response.getErrorMsg());
        } else {
            mArticleList.addAll(response.getData().getDatas());
            if (mArticleList.size() == 0){
                vRecylerview.showEmpty();
            } else {
                vRecylerview.hideNetworkStatus();
                LogUtils.e("mArticleList:"+mArticleList.size());
                pageIndex++;
                mArticleAdapter.setNewData(mArticleList);
            }
        }
    }

    @Override
    public void itemNotifyChanged(int position) {
        mArticleAdapter.notifyItemChanged(position);
    }
}
