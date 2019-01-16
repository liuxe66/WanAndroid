package com.liuxe.wanandroid.presenter.tpublic;

import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Tab;
import com.liuxe.wanandroid.contract.tpublic.PublicContract;
import com.liuxe.wanandroid.model.tpublic.PublicModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public class PublicPresenter extends PublicContract.Presenter {

    public static PublicPresenter newInstance(){
        return new PublicPresenter();
    }

    @Override
    public void loadTabList() {
        if (mIModel == null || mIView == null){
            return;
        }

        mRxManager.register(mIModel.getTabData().subscribe(new Consumer<BaseResponse>() {
            @Override
            public void accept(BaseResponse baseResponse) throws Exception {
                mIView.showTab(baseResponse);
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
    public PublicContract.IModel getIModel() {
        return PublicModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
