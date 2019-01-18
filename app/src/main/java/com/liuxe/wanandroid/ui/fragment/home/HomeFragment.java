package com.liuxe.wanandroid.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liuxe.banner.Banner;
import com.liuxe.banner.ItemClickListener;
import com.liuxe.banner.PageChangeListener;
import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.adapter.ArticleAdapter;
import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BannerData;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.contract.home.HomeContract;
import com.liuxe.wanandroid.presenter.home.HomePresenter;

import com.liuxe.wanandroid.ui.activity.common.SerachActivity;
import com.orhanobut.logger.Logger;
import com.yunxiaosheng.baselib.anim.ToolbarAnimManager;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.fragment.BaseMvpFragment;

import com.yunxiaosheng.baselib.rxbus.RxBus;
import com.yunxiaosheng.baselib.rxbus.Subscribe;
import com.yunxiaosheng.baselib.utils.ToastUtils;
import com.yunxiaosheng.baselib.widgets.VRecylerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeFragment extends BaseMvpFragment<HomeContract.Presenter> implements HomeContract.IView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.v_recylerview)
    VRecylerView vRecylerview;


    private int statusBarHeight;
    private int pageIndex = 0;
    private View header;
    private ArticleAdapter mArticleAdapter;
    private List<Article> mList;
    private List<Article> mNewList;
    private Banner banner;
    private OnOpenDrawerLayoutListener mOpenDrawerLayoutListener;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }



    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return HomePresenter.newInstance();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadArticle(pageIndex+"");
        mPresenter.loadBanner();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mOpenDrawerLayoutListener != null) {
                    mOpenDrawerLayoutListener.onOpen();
                }
            }
        });
        mToolbar.inflateMenu(R.menu.home_toolbar_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                       startNewActivity(SerachActivity.class);
                        break;
                }
                return false;
            }
        });
        ToolbarAnimManager.animIn(mContext, mToolbar);

        mList = new ArrayList<>();
        mNewList = new ArrayList<>();

        mArticleAdapter = new ArticleAdapter(R.layout.item_article_home,mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        vRecylerview.setLayoutManager(layoutManager);
        vRecylerview.setAdapter(mArticleAdapter);
        vRecylerview.setOnErrorListener(new VRecylerView.OnErrorListener() {
            @Override
            public void setOnError() {
                mPresenter.loadBanner();
                mPresenter.loadMoreArticle(pageIndex+"");
            }
        });
        mArticleAdapter.openLoadAnimation();
        mArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onArticleItemClick(position,mList.get(position));
            }
        });
        mArticleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMoreArticle(pageIndex+"");
            }
        },vRecylerview.getRecyclerView());

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vRecylerview.getRecyclerView().smoothScrollToPosition(0);
            }
        });

        RxBus.get().register(this);

    }
    @Override
    public void showNetworkError() {
        vRecylerview.showError();
    }

    @Override
    public void showBanner(final BaseResponse<List<BannerData>> baseResponse) {
        if (baseResponse.getErrorCode() == 0){
            List<String> images = new ArrayList<>();
            for (int i = 0 ; i<baseResponse.getData().size() ; i++){
                images.add(baseResponse.getData().get(i).getImagePath());
            }
            header = LayoutInflater.from(mContext).inflate(R.layout.item_header_home,null);
            banner = header.findViewById(R.id.home_banner);
            banner.setImgList(images)
                    .setOnItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            mPresenter.onBannerItemClick(position,baseResponse.getData().get(position));
                        }
                    })
                    .start();

            mArticleAdapter.addHeaderView(header);
        } else {
            ToastUtils.showToast(baseResponse.getErrorMsg());
        }
    }

    @Override
    public void showArticle(BaseResponse<Articles> baseResponse) {
        if (baseResponse.getErrorCode() == 0){
            mNewList = baseResponse.getData().getDatas();
            if (mNewList.size() == 0){
                vRecylerview.showEmpty();
                return;
            }
            vRecylerview.hideNetworkStatus();
            pageIndex++;
            mList.addAll(mNewList);
            mArticleAdapter.setNewData(mList);
        } else {
            ToastUtils.showToast(baseResponse.getErrorMsg());
        }
    }

    @Override
    public void loadMoreArticle(BaseResponse<Articles> baseResponse) {
        if (baseResponse.getErrorCode() == 0){
            mNewList = baseResponse.getData().getDatas();
            if (mNewList.size() == 0){
                mArticleAdapter.setEnableLoadMore(false);
                ToastUtils.showToast("已经到底部了！");
                return;
            }
            pageIndex++;
            mList.addAll(mNewList);
            mArticleAdapter.setNewData(mList);
        } else {
            ToastUtils.showToast(baseResponse.getErrorMsg());
        }

    }
    /**
     * 重新加载数据
     */
    @Subscribe(code = 1001)
    public void rxBusEvent() {
        pageIndex = 0;
        mPresenter.loadArticle(pageIndex+"");
    }

    @Override
    public void itemNotifyChanged(int position) {
        mArticleAdapter.notifyItemChanged(position+1);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOpenDrawerLayoutListener) {
            mOpenDrawerLayoutListener = (OnOpenDrawerLayoutListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDrawerLayoutListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (banner != null){
            banner.stopPlay();
        }

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (banner != null){
            banner.startPlay();
        }
    }


    /**
     * fragment打开DrawerLayout监听
     */
    public interface OnOpenDrawerLayoutListener {
        void onOpen();
    }
}
