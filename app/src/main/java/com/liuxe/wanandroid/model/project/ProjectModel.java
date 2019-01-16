package com.liuxe.wanandroid.model.project;

import com.liuxe.wanandroid.api.WanAndroidApi;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Tab;
import com.liuxe.wanandroid.contract.project.ProjectContract;
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

public class ProjectModel implements ProjectContract.IModel {

    public static ProjectModel newInstance(){
        return new ProjectModel();
    }


    @Override
    public Observable<BaseResponse<List<Tab>>> getProjectTree() {
        return RetrofitCreateHelper
                .createApi(WanAndroidApi.class,WanAndroidApi.HOST)
                .getProjectTree()
                .compose(RxHelper.<BaseResponse<List<Tab>>>rxSchedulerHelper());
    }

    @Override
    public Observable<BaseResponse<Articles>> getProjectList(String pageIndex, String cid) {
        return RetrofitCreateHelper
                .createApi(WanAndroidApi.class,WanAndroidApi.HOST)
                .getProjectList(pageIndex,cid)
                .compose(RxHelper.<BaseResponse<Articles>>rxSchedulerHelper());
    }

    @Override
    public Observable<Boolean> recordItemIsRead(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(DBUtils.getDB(AppUtils.getContext()).insertRead(DBConfig.TABLE_PROJECT,key,ItemState.STATE_IS_READ));
                emitter.onComplete();
            }
        }).compose(RxHelper.<Boolean>rxSchedulerHelper());
    }
}
