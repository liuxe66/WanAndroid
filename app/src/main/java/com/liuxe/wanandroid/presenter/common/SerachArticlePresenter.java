package com.liuxe.wanandroid.presenter.common;

import android.os.Bundle;

import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.contract.common.SerachArticleContract;
import com.liuxe.wanandroid.model.common.SerachArticleModel;
import com.liuxe.wanandroid.ui.activity.common.WebLoadDetailsActivity;

import io.reactivex.functions.Consumer;

public class SerachArticlePresenter  extends SerachArticleContract.Presenter {

    public static SerachArticlePresenter newInstance(){
        return new SerachArticlePresenter();
    }
    @Override
    public void loadArticle(final String pageIndex, String key) {
        if (mIModel == null || mIView == null)
            return;
        mRxManager.register(mIModel.loadArticle(pageIndex,key).subscribe(new Consumer<BaseResponse<Articles>>() {
            @Override
            public void accept(BaseResponse<Articles> response) throws Exception {
                mIView.initArticle(response);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mIView.showNetworkError();
            }
        }));
    }

    @Override
    public void onArticleItemClick(final int position, Article article) {
        if (mIView == null)
            return;

        mRxManager.register(mIModel.recordItemIsRead(article.getId()+"").subscribe
                (new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            mIView.itemNotifyChanged(position);
                        }
                    }
                }));
        Bundle bundle = new Bundle();
        bundle.putString("title", article.getTitle());
        bundle.putString("url", article.getLink());
        mIView.startNewActivity(WebLoadDetailsActivity.class, bundle);
    }

    @Override
    public SerachArticleContract.IModel getIModel() {
        return SerachArticleModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
