package com.yunxiaosheng.baselib.base;

import android.support.annotation.NonNull;

import com.yunxiaosheng.baselib.RxManager;


/**
 * Created by Liuxe on 2018/9/18 0018
 */
public abstract class BasePresenter<M,V> {
    public M mIModel;
    public V mIView;
    protected RxManager mRxManager = new RxManager();

    /**
     * 返回presenter想持有的Model引用
     * @return
     */
    public abstract M getIModel();

    /**
     * 绑定IModel和IView的引用
     * @param v
     */
    public void attachMV(@NonNull V v){
        this.mIModel = getIModel();
        this.mIView = v;
        this.onStart();
    }

    public void detachMV(){
        mRxManager.unSubscribe();
        mIView = null;
        mIModel = null;
    }
    /**
     * IView和IModel绑定完成立即执行
     * <p>
     * 实现类实现绑定完成后的逻辑,例如数据初始化等,界面初始化, 更新等
     */
    public abstract void onStart();
}
