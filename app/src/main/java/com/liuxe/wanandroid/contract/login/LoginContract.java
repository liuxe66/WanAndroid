package com.liuxe.wanandroid.contract.login;

import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Login;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.IBaseActivity;
import com.yunxiaosheng.baselib.base.IBaseModel;

import io.reactivex.Observable;

public interface LoginContract {

    public abstract class Presenter extends BasePresenter<IModel,IView>{
        public abstract void loadLogin(String userName, String password);
    }

    interface IModel extends IBaseModel{
        Observable<BaseResponse<Login>> login(String userName, String password);
    }

    interface IView extends IBaseActivity{
        void loginSuccess(BaseResponse<Login> response);
    }
}
