package com.liuxe.wanandroid.presenter.common;

import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.HotKey;
import com.liuxe.wanandroid.contract.common.SerachContract;
import com.liuxe.wanandroid.model.common.SerachModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public class SerachPresenter extends SerachContract.Presenter {

    public static SerachPresenter newInstance(){
        return new SerachPresenter();
    }

    @Override
    public void loadHotKey() {
        if (mIModel == null || mIView == null)
            return;
        mRxManager.register(mIModel.loadHotKey().subscribe(new Consumer<BaseResponse<List<HotKey>>>() {
            @Override
            public void accept(BaseResponse<List<HotKey>> response) throws Exception {
                mIView.loadHotKey(response);
            }
        }));
    }

    @Override
    public void loadHistory() {
        if (mIModel == null || mIView == null)
            return;
        mRxManager.register(mIModel.loadHistory().subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> response) throws Exception {
                mIView.showHistory(response);
            }
        }));
    }

    @Override
    public SerachContract.IModel getIModel() {
        return SerachModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
