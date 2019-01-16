package com.liuxe.wanandroid.contract.project;

import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Tab;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.IBaseFragment;
import com.yunxiaosheng.baselib.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;


public interface ProjectContract {
    abstract class Presenter extends BasePresenter<IModel,IView>{
        public abstract void loadProjectTree();
        public abstract void loadProjectList(String pageIndex,String cid);
        public abstract void loadMoreProjectList(String pageIndex,String cid);
        public abstract void onArticleItemClick(int position,Article article);
    }

    interface IModel extends IBaseModel{
        Observable<BaseResponse<List<Tab>>> getProjectTree();
        Observable<BaseResponse<Articles>> getProjectList(String pageIndex,String cid);
        /**
         * 记录item已阅到数据库
         *
         * @param key key(item.id值作为key)
         */
        Observable<Boolean> recordItemIsRead(String key);
    }

    interface IView extends IBaseFragment{
        void showProjectTree(BaseResponse<List<Tab>> response);
        void showProjectList(BaseResponse<Articles> response);
        void showMoreProjectList(BaseResponse<Articles> response);
        /**
         * 点击事件后，刷新item
         *
         * @param position position
         */
        void itemNotifyChanged(int position);
    }

}
