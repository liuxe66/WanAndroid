package com.liuxe.wanandroid.contract.login;

import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Login;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.IBaseActivity;
import com.yunxiaosheng.baselib.base.IBaseModel;

import io.reactivex.Observable;

public interface RegisterContract {

    public abstract class Presenter extends BasePresenter<IModel,IView>{
        public abstract void register(String userName, String password);
    }

    interface IModel extends IBaseModel{
        Observable<BaseResponse<Login>> register(String userName, String password);
    }

    interface IView extends IBaseActivity{
        void registerSuccess(BaseResponse<Login> response);
    }
}
