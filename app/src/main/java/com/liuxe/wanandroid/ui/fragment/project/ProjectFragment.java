package com.liuxe.wanandroid.ui.fragment.project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cocosw.bottomsheet.BottomSheet;
import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.adapter.ProjectArticleAdapter;
import com.liuxe.wanandroid.bean.Article;
import com.liuxe.wanandroid.bean.Articles;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Tab;
import com.liuxe.wanandroid.contract.project.ProjectContract;
import com.liuxe.wanandroid.presenter.project.ProjectPresenter;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.fragment.BaseMvpFragment;
import com.yunxiaosheng.baselib.utils.LogUtils;
import com.yunxiaosheng.baselib.utils.ToastUtils;
import com.yunxiaosheng.baselib.widgets.VRecylerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends BaseMvpFragment<ProjectPresenter> implements ProjectContract.IView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.project_recyler)
    VRecylerView projectRecyler;
    @BindView(R.id.tv_type_name)
    TextView tvHeaderText;
    @BindView(R.id.ll_choose)
    LinearLayout llHeaderChoose;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private int cid;
    private int pageIndex = 0;

    private ProjectArticleAdapter mAdapter;
    private List<Article> mList;
    private List<Article> mNewList;
    private List<Tab> mTypeList;


    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        projectRecyler.showLoading();
        mPresenter.loadProjectTree();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mTypeList = new ArrayList<>();
        mNewList = new ArrayList<>();
        mAdapter = new ProjectArticleAdapter(R.layout.item_article_project, mList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        projectRecyler.setLayoutManager(layoutManager);
        projectRecyler.setAdapter(mAdapter);
        mAdapter.openLoadAnimation();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onArticleItemClick(position,mList.get(position));
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e("=============onLoadMoreRequested===========");
                mPresenter.loadMoreProjectList(pageIndex + "", cid + "");
            }
        }, projectRecyler.getRecyclerView());

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectRecyler.getRecyclerView().smoothScrollToPosition(0);
            }
        });
    }

    private void showBottomSheet() {
        BottomSheet.Builder builder = new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog);
        builder.title("项目分类");
        for (int i = 0; i < mTypeList.size(); i++) {
            builder.sheet(i, mTypeList.get(i).getName());
        }
        builder.listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.e("===========BottomSheet===============");
                tvHeaderText.setText(mTypeList.get(which).getName());
                cid = mTypeList.get(which).getId();
                pageIndex = 0;
                mPresenter.loadProjectList("" + pageIndex, "" + cid);
            }
        });
        builder.show();
    }

    @Override
    public void showProjectTree(BaseResponse<List<Tab>> response) {

        mTypeList = response.getData();
        cid = response.getData().get(0).getId();
        mPresenter.loadProjectList("" + pageIndex, "" + cid);

        tvHeaderText.setText(response.getData().get(0).getName());
        llHeaderChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
    }

    @Override
    public void showProjectList(BaseResponse<Articles> response) {
        if (response.getData().getDatas().size() == 0) {
            projectRecyler.showEmpty();
        } else {
            projectRecyler.hideNetworkStatus();
        }
        projectRecyler.getRecyclerView().scrollToPosition(0);
        pageIndex++;
        mList.clear();
        mList = response.getData().getDatas();
        mAdapter.setNewData(mList);
    }

    @Override
    public void showMoreProjectList(BaseResponse<Articles> response) {
        if (response.getData().getDatas().size() == 0) {
            mAdapter.setEnableLoadMore(false);
            ToastUtils.showToast("已经到底部了！");
            return;
        }
        pageIndex++;
        mList.addAll(response.getData().getDatas());
        mAdapter.setNewData(mList);
    }

    @Override
    public void itemNotifyChanged(int position) {
        mAdapter.notifyItemChanged(position);
    }


    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return ProjectPresenter.newInstance();
    }

    @Override
    public void showNetworkError() {
        projectRecyler.showError();
    }


}
