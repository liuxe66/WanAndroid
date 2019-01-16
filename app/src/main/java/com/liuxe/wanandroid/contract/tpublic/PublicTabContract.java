package com.liuxe.wanandroid.contract.tpublic;

import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Collection;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.IBaseFragment;
import com.yunxiaosheng.baselib.base.IBaseModel;

import io.reactivex.Observable;

public interface PublicTabContract {
    abstract class Preaenter extends BasePresenter<IModel,IView> {
        public abstract void loadList(String wxId,String pageIndex);
        public abstract void loadMoreList(String wxId, String pageIndex);
        public abstract void onArticleItemClick(int position,Article article );
    }

    interface IModel extends IBaseModel{
        Observable<BaseResponse<Articles>> getWxlistData(String wxId, String pageIndex);
        /**
         * 记录item已阅到数据库
         *
         * @param key key(item.id值作为key)
         */
        Observable<Boolean> recordItemIsRead(String key);
    }

    interface IView extends IBaseFragment{
        void showWxlist(BaseResponse<Articles> response);
        void showMoreList(BaseResponse<Articles> response);
        /**
         * 点击事件后，刷新item
         *
         * @param position position
         */
        void itemNotifyChanged(int position);
    }
}
