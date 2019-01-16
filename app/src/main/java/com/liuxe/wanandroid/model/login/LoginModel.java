package com.liuxe.wanandroid.model.login;

import com.liuxe.wanandroid.api.WanAndroidApi;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Login;
import com.liuxe.wanandroid.contract.login.LoginContract;
import com.yunxiaosheng.baselib.helper.RetrofitCreateHelper;
import com.yunxiaosheng.baselib.helper.RxHelper;

import io.reactivex.Observable;

public class LoginModel implements LoginContract.IModel {
    public static LoginModel newInstance(){
        return new LoginModel();
    }

    @Override
    public Observable<BaseResponse<Login>> login(String userName, String password) {
        return RetrofitCreateHelper
                .createApi(WanAndroidApi.class,WanAndroidApi.HOST)
                .postLogin(userName,password)
                .compose(RxHelper.<BaseResponse<Login>>rxSchedulerHelper());
    }
}
