package com.liuxe.wanandroid.model.home;

import com.liuxe.banner.Banner;
import com.liuxe.wanandroid.api.WanAndroidApi;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BannerData;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Collection;
import com.liuxe.wanandroid.contract.home.HomeContract;
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

public class HomeModel implements HomeContract.IModel {

    public static HomeModel newInstance() {
        return new HomeModel();
    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBanner() {
        return RetrofitCreateHelper
                .createApi(WanAndroidApi.class, WanAndroidApi.HOST)
                .gatBannerList()
                .compose(RxHelper.<BaseResponse<List<BannerData>>>rxSchedulerHelper());
    }

    @Override
    public Observable<BaseResponse<Articles>> getArticle(String pageIndex) {
        return RetrofitCreateHelper
                .createApi(WanAndroidApi.class,WanAndroidApi.HOST)
                .getArticlaList(pageIndex)
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
