package com.liuxe.wanandroid.model.common;

import com.liuxe.wanandroid.api.WanAndroidApi;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.HotKey;
import com.liuxe.wanandroid.contract.common.SerachHotKeyContract;
import com.yunxiaosheng.baselib.helper.RetrofitCreateHelper;
import com.yunxiaosheng.baselib.helper.RxHelper;

import java.util.List;

import io.reactivex.Observable;

public class SerachHotkeyModel implements SerachHotKeyContract.IModel {

    public static SerachHotkeyModel newInstance(){
        return new SerachHotkeyModel();
    }

    @Override
    public Observable<BaseResponse<List<HotKey>>> loadHotKey() {
        return RetrofitCreateHelper
                .createApi(WanAndroidApi.class,WanAndroidApi.HOST)
                .getHotKey()
                .compose(RxHelper.<BaseResponse<List<HotKey>>>rxSchedulerHelper());
    }
}
