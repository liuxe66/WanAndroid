package com.liuxe.wanandroid.model.common;

import com.liuxe.wanandroid.api.WanAndroidApi;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Collection;
import com.liuxe.wanandroid.bean.Collections;
import com.liuxe.wanandroid.contract.common.SerachArticleContract;
import com.yunxiaosheng.baselib.config.DBConfig;
import com.yunxiaosheng.baselib.config.ItemState;
import com.yunxiaosheng.baselib.helper.RetrofitCreateHelper;
import com.yunxiaosheng.baselib.helper.RxHelper;
import com.yunxiaosheng.baselib.utils.AppUtils;
import com.yunxiaosheng.baselib.utils.DBUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class SerachArticleModel implements SerachArticleContract.IModel {

    public static SerachArticleModel newInstance(){
        return new SerachArticleModel();
    }

    @Override
    public Observable<BaseResponse<Articles>> loadArticle(String pageIndex, String key) {
        return RetrofitCreateHelper
                .createApi(WanAndroidApi.class,WanAndroidApi.HOST)
                .getSerachArticle(pageIndex,key)
                .compose(RxHelper.<BaseResponse<Articles>>rxSchedulerHelper());
    }

    @Override
    public Observable<Boolean> recordItemIsRead(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(DBUtils.getDB(AppUtils.getContext()).insertRead(DBConfig.TABLE_ARTICLE,key,ItemState.STATE_IS_READ));
                emitter.onComplete();
            }
        }).compose(RxHelper.<Boolean>rxSchedulerHelper());
    }

}
