package com.liuxe.wanandroid.contract.home;

import com.liuxe.banner.Banner;
import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BannerData;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Collection;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.IBaseFragment;
import com.yunxiaosheng.baselib.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;

public interface HomeContract {
    abstract class Presenter extends BasePresenter<IModel,IView>{
        public abstract void loadBanner();
        public abstract void loadArticle(String pageIndex);
        public abstract void loadMoreArticle(String pageIndex);
        public abstract void onBannerItemClick(int position,BannerData banner );
        public abstract void onArticleItemClick(int position,Article article );
    }

    interface IModel extends IBaseModel{
        Observable<BaseResponse<List<BannerData>>> getBanner();
        Observable<BaseResponse<Articles>> getArticle(String pageIndex);
        /**
         * 记录item已阅到数据库
         *
         * @param key key(item.id值作为key)
         */
        Observable<Boolean> recordItemIsRead(String key);
    }

    interface IView extends IBaseFragment {
        void showBanner(BaseResponse<List<BannerData>> baseResponse);
        void showArticle(BaseResponse<Articles> baseResponse);
        void loadMoreArticle(BaseResponse<Articles> baseResponse);
        /**
         * 点击事件后，刷新item
         *
         * @param position position
         */
        void itemNotifyChanged(int position);
    }
}
