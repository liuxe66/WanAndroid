package com.liuxe.wanandroid.presenter.home;

import android.os.Bundle;

import com.liuxe.banner.Banner;
import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BannerData;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Collection;
import com.liuxe.wanandroid.contract.home.HomeContract;
import com.liuxe.wanandroid.model.home.HomeModel;
import com.liuxe.wanandroid.ui.activity.common.WebLoadDetailsActivity;
import com.orhanobut.logger.Logger;
import com.yunxiaosheng.baselib.utils.LogUtils;
import com.yunxiaosheng.baselib.utils.ToastUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HomePresenter extends HomeContract.Presenter {

    public static HomePresenter newInstance() {
        return new HomePresenter();
    }

    @Override
    public void loadBanner() {
        if (mIModel == null || mIView == null) {
            return;
        }
        mRxManager.register(mIModel.getBanner().subscribe(new Consumer<BaseResponse<List<BannerData>>>() {
            @Override
            public void accept(BaseResponse<List<BannerData>> baseResponse) throws Exception {
                mIView.showBanner(baseResponse);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView != null) {
                    if (mIView.isVisiable()) {
                        mIView.showNetworkError();
                    }
                }
            }
        }));
    }

    @Override
    public void loadArticle(String pageIndex) {
        if (mIModel == null || mIView == null) {
            return;
        }
        mRxManager.register(mIModel.getArticle(pageIndex).subscribe(new Consumer<BaseResponse<Articles>>() {
            @Override
            public void accept(BaseResponse<Articles> articlesBaseResponse) throws Exception {
                mIView.showArticle(articlesBaseResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView != null) {
                    if (mIView.isVisiable()) {
                        mIView.showNetworkError();
                    }
                }
            }
        }));
    }

    @Override
    public void loadMoreArticle(String pageIndex) {
        if (mIModel == null || mIView == null) {
            return;
        }
        mRxManager.register(mIModel.getArticle(pageIndex).subscribe(new Consumer<BaseResponse<Articles>>() {
            @Override
            public void accept(BaseResponse<Articles> articlesBaseResponse) throws Exception {
                mIView.loadMoreArticle(articlesBaseResponse);
            }
        }));
    }

    @Override
    public void onBannerItemClick(int position, BannerData banner) {
        if (mIView == null)
            return;
        Bundle bundle = new Bundle();
        bundle.putString("title", banner.getTitle());
        bundle.putString("url", banner.getUrl());
        mIView.startNewActivity(WebLoadDetailsActivity.class, bundle);
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
    public HomeContract.IModel getIModel() {
        return HomeModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
