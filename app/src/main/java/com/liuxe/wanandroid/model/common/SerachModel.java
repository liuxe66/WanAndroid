package com.liuxe.wanandroid.model.common;

import com.liuxe.wanandroid.api.WanAndroidApi;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.HotKey;
import com.liuxe.wanandroid.contract.common.SerachContract;
import com.yunxiaosheng.baselib.config.DBConfig;
import com.yunxiaosheng.baselib.config.ItemState;
import com.yunxiaosheng.baselib.helper.RetrofitCreateHelper;
import com.yunxiaosheng.baselib.helper.RxHelper;
import com.yunxiaosheng.baselib.utils.AppUtils;
import com.yunxiaosheng.baselib.utils.DBUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class SerachModel implements SerachContract.IModel {

    public static SerachModel newInstance(){
        return new SerachModel();
    }

    @Override
    public Observable<BaseResponse<List<HotKey>>> loadHotKey() {
        return RetrofitCreateHelper
                .createApi(WanAndroidApi.class,WanAndroidApi.HOST)
                .getHotKey()
                .compose(RxHelper.<BaseResponse<List<HotKey>>>rxSchedulerHelper());
    }

    @Override
    public Observable<List<String>> loadHistory() {
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                emitter.onNext(DBUtils.getDB(AppUtils.getContext()).queryHistory(DBConfig.TABLE_HISTORY));
                emitter.onComplete();
            }
        }).compose(RxHelper.<List<String>>rxSchedulerHelper());
    }
}
