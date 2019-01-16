package com.liuxe.wanandroid.model.tpublic;

import com.liuxe.wanandroid.api.WanAndroidApi;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Tab;
import com.liuxe.wanandroid.contract.tpublic.PublicContract;
import com.yunxiaosheng.baselib.helper.RetrofitCreateHelper;
import com.yunxiaosheng.baselib.helper.RxHelper;

import java.util.List;

import io.reactivex.Observable;

public class PublicModel implements PublicContract.IModel {

    public static PublicModel newInstance(){
        return new PublicModel();
    }

    @Override
    public Observable<BaseResponse<List<Tab>>> getTabData() {
        return RetrofitCreateHelper
                .createApi(WanAndroidApi.class,WanAndroidApi.HOST)
                .getWxChapters()
                .compose(RxHelper.<BaseResponse<List<Tab>>>rxSchedulerHelper());
    }
}
