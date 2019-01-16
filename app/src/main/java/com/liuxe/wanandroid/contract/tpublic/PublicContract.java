package com.liuxe.wanandroid.contract.tpublic;

import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Tab;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.IBaseFragment;
import com.yunxiaosheng.baselib.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;


public interface PublicContract {

    abstract class Presenter extends BasePresenter<IModel,IView> {
       public abstract void loadTabList();
    }

    interface IModel extends IBaseModel{
        Observable<BaseResponse<List<Tab>>> getTabData();
    }

    interface IView extends IBaseFragment {
        void showTab(BaseResponse<List<Tab>> response);
    }
}
