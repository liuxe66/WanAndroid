package com.liuxe.wanandroid.presenter.tpublic;

import android.os.Bundle;

import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Collection;
import com.liuxe.wanandroid.contract.tpublic.PublicTabContract;
import com.liuxe.wanandroid.model.tpublic.PublicTabModel;
import com.liuxe.wanandroid.ui.activity.common.WebLoadDetailsActivity;

import io.reactivex.functions.Consumer;

public class PublicTabPresenter extends PublicTabContract.Preaenter {

    public static PublicTabPresenter newInstance(){
        return new PublicTabPresenter();
    }

    @Override
    public void loadList(String wxId, String pageIndex) {
        if (mIModel == null || mIView == null){
            return;
        }
        mRxManager.register(mIModel.getWxlistData(wxId,pageIndex).subscribe(new Consumer<BaseResponse<Articles>>() {
            @Override
            public void accept(BaseResponse<Articles> response) throws Exception {
                mIView.showWxlist(response);
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
    public void loadMoreList(String wxId, String pageIndex) {
        if (mIModel == null || mIView == null){
            return;
        }
        mRxManager.register(mIModel.getWxlistData(wxId,pageIndex).subscribe(new Consumer<BaseResponse<Articles>>() {
            @Override
            public void accept(BaseResponse<Articles> response) throws Exception {
                mIView.showMoreList(response);
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
    public PublicTabContract.IModel getIModel() {
        return PublicTabModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
