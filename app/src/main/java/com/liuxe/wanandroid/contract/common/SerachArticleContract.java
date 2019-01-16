package com.liuxe.wanandroid.contract.common;

import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.IBaseActivity;
import com.yunxiaosheng.baselib.base.IBaseModel;

import io.reactivex.Observable;

public interface SerachArticleContract {
    abstract class Presenter extends BasePresenter<IModel,IView>{
        /**
         * 加载文章列表
         * @param pageIndex
         * @param key
         */
        public abstract void loadArticle(String pageIndex,String key);

        /**
         * 文章列表条目点击
         * @param position
         * @param article
         */
        public abstract void onArticleItemClick(int position,Article article );
    }

    interface IModel extends IBaseModel {
        /**
         * 获取文章列表
         * @param pageIndex
         * @param key
         * @return
         */
        Observable<BaseResponse<Articles>> loadArticle(String pageIndex, String key);
        /**
         * 记录item已阅到数据库
         *
         * @param key key(item.id值作为key)
         */
        Observable<Boolean> recordItemIsRead(String key);
    }

    interface IView extends IBaseActivity{
        /**
         * 显示搜索文章
         * @param response
         */
        void initArticle(BaseResponse<Articles> response);
        /**
         * 点击事件后，刷新item
         *
         * @param position position
         */
        void itemNotifyChanged(int position);
    }
}
