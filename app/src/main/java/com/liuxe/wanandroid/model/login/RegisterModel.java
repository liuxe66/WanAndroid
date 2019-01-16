package com.liuxe.wanandroid.model.login;

import com.liuxe.wanandroid.api.WanAndroidApi;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Login;
import com.liuxe.wanandroid.contract.login.RegisterContract;
import com.yunxiaosheng.baselib.helper.RetrofitCreateHelper;
import com.yunxiaosheng.baselib.helper.RxHelper;

import io.reactivex.Observable;

public class RegisterModel implements RegisterContract.IModel {
    public static RegisterModel newInstance(){
        return new RegisterModel();
    }


    @Override
    public Observable<BaseResponse<Login>> register(String userName, String password) {
        return RetrofitCreateHelper
                .createApi(WanAndroidApi.class,WanAndroidApi.HOST)
                .postRegister(userName,password,password)
                .compose(RxHelper.<BaseResponse<Login>>rxSchedulerHelper());
    }
}
