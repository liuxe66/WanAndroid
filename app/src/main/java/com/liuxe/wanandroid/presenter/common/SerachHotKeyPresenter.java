package com.liuxe.wanandroid.presenter.common;

import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.HotKey;
import com.liuxe.wanandroid.contract.common.SerachHotKeyContract;
import com.liuxe.wanandroid.model.common.SerachHotkeyModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public class SerachHotKeyPresenter extends SerachHotKeyContract.Presenter {

    public static SerachHotKeyPresenter newInstance(){
        return new SerachHotKeyPresenter();
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
    public SerachHotKeyContract.IModel getIModel() {
        return SerachHotkeyModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
