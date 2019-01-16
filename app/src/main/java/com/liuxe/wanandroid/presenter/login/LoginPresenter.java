package com.liuxe.wanandroid.presenter.login;

import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Login;
import com.liuxe.wanandroid.contract.login.LoginContract;
import com.liuxe.wanandroid.model.login.LoginModel;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends LoginContract.Presenter {

    public static LoginPresenter newInstance(){
        return new LoginPresenter();
    }
    @Override
    public void loadLogin(String userName, String password) {
        mRxManager.register(mIModel.login(userName,password).subscribe(new Consumer<BaseResponse<Login>>() {
            @Override
            public void accept(BaseResponse<Login> response) throws Exception {
                mIView.loginSuccess(response);
            }
        }));
    }

    @Override
    public LoginContract.IModel getIModel() {
        return LoginModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
