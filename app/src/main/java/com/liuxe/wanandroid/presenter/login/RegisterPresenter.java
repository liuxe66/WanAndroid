package com.liuxe.wanandroid.presenter.login;

import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Login;
import com.liuxe.wanandroid.contract.login.RegisterContract;
import com.liuxe.wanandroid.model.login.RegisterModel;

import io.reactivex.functions.Consumer;

public class RegisterPresenter extends RegisterContract.Presenter {

    public static RegisterPresenter newInstance(){
        return new RegisterPresenter();
    }
    @Override
    public void register(String userName, String password) {
        mRxManager.register(mIModel.register(userName,password).subscribe(new Consumer<BaseResponse<Login>>() {
            @Override
            public void accept(BaseResponse<Login> response) throws Exception {
                mIView.registerSuccess(response);
            }
        }));
    }

    @Override
    public RegisterContract.IModel getIModel() {
        return RegisterModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
