package com.liuxe.wanandroid.presenter.project;

import android.os.Bundle;

import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Tab;
import com.liuxe.wanandroid.contract.project.ProjectContract;
import com.liuxe.wanandroid.model.project.ProjectModel;
import com.liuxe.wanandroid.ui.activity.common.WebLoadDetailsActivity;

import java.util.List;

import io.reactivex.functions.Consumer;

public class ProjectPresenter extends ProjectContract.Presenter {

    public static ProjectPresenter newInstance(){
        return new ProjectPresenter();
    }

    @Override
    public void loadProjectTree() {
        if (mIView == null || mIModel == null)
            return;
        mRxManager.register(mIModel.getProjectTree().subscribe(new Consumer<BaseResponse<List<Tab>>>() {
            @Override
            public void accept(BaseResponse<List<Tab>> response) throws Exception {
                mIView.showProjectTree(response);
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
    public void loadProjectList(String pageIndex, String cid) {
        if (mIView == null || mIModel == null)
            return;
        mRxManager.register(mIModel.getProjectList(pageIndex,cid).subscribe(new Consumer<BaseResponse<Articles>>() {
            @Override
            public void accept(BaseResponse<Articles> response) throws Exception {
                mIView.showProjectList(response);
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
    public void loadMoreProjectList(String pageIndex, String cid) {
        if (mIView == null || mIModel == null)
            return;
        mRxManager.register(mIModel.getProjectList(pageIndex,cid).subscribe(new Consumer<BaseResponse<Articles>>() {
            @Override
            public void accept(BaseResponse<Articles> response) throws Exception {
                mIView.showMoreProjectList(response);
            }
        }));
    }

    @Override
    public void onArticleItemClick(final int position, Article article) {
        if (mIView == null)
            return;
        mRxManager.register(mIModel.recordItemIsRead(article.getId()+"").subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean){
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
    public ProjectContract.IModel getIModel() {
        return ProjectModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
