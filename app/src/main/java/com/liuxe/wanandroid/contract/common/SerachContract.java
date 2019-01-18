package com.liuxe.wanandroid.contract.common;

import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.HotKey;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.IBaseActivity;
import com.yunxiaosheng.baselib.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;

public interface SerachContract {
    abstract class Presenter extends BasePresenter<IModel,IView>{
        public abstract void loadHotKey();
        public abstract void loadHistory();
    }

    interface IModel extends IBaseModel{
        Observable<BaseResponse<List<HotKey>>> loadHotKey();
        Observable<List<String>> loadHistory();
    }

    interface IView extends IBaseActivity{
        void loadHotKey(BaseResponse<List<HotKey>> response);
        void showHistory(List<String> historyList);
    }
}
