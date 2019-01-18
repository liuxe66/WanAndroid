package com.liuxe.wanandroid.ui.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.adapter.FlowLayoutAdapter;
import com.liuxe.wanandroid.adapter.SerachAdapter;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.HotKey;
import com.liuxe.wanandroid.contract.common.SerachContract;
import com.liuxe.wanandroid.presenter.common.SerachPresenter;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.activity.BaseMvpActivity;
import com.yunxiaosheng.baselib.config.DBConfig;
import com.yunxiaosheng.baselib.utils.AnimationUtils;
import com.yunxiaosheng.baselib.utils.AppUtils;
import com.yunxiaosheng.baselib.utils.DBUtils;
import com.yunxiaosheng.baselib.utils.LogUtils;
import com.yunxiaosheng.baselib.utils.ToastUtils;
import com.yunxiaosheng.baselib.widgets.VRecylerView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SerachActivity extends BaseMvpActivity<SerachPresenter> implements SerachContract.IView {

    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.v_recylerview)
    VRecylerView mVRecylerView;

    private SerachAdapter mSerachAdapter;
    private FlowLayoutAdapter mFlowAdapter;
    private List<HotKey> mHotKeyList;
    private List<String> mHistoryList;
    private String keyWord = "";

    @Override
    protected void initView(Bundle savedInstanceState) {

        mHotKeyList = new ArrayList<>();
        mHistoryList = new ArrayList<>();
        mPresenter.loadHotKey();

        mSerachAdapter = new SerachAdapter(R.layout.item_history,mHistoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mVRecylerView.setLayoutManager(layoutManager);
        mVRecylerView.setAdapter(mSerachAdapter);

        mVRecylerView.getRecyclerView().addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DBUtils.getDB(mContext).deleteHistory(DBConfig.TABLE_HISTORY,mHistoryList.get(position));
                mPresenter.loadHistory();
            }
        });

        mSerachAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                keyWord = mHistoryList.get(position);
                serach();
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (etSearch.getText().toString().isEmpty()){
                        etSearch.startAnimation(AnimationUtils.shakeAnimation(5));
                        ToastUtils.showToast("搜索关键字不能为空");
                        return true;
                    }
                    keyWord = etSearch.getText().toString();
                    serach();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_serach;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return SerachPresenter.newInstance();
    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void loadHotKey(BaseResponse<List<HotKey>> response) {
        mHotKeyList = response.getData();
        View header = LayoutInflater.from(mContext).inflate(R.layout.item_header_serach,null);
        TextView tvClearAll;
        TagFlowLayout mFlowLayout;
        mFlowLayout = header.findViewById(R.id.flowlayout);
        tvClearAll = header.findViewById(R.id.tv_clear_all);
        tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtils.getDB(mContext).clearAll(DBConfig.TABLE_HISTORY);
                mPresenter.loadHistory();
            }
        });
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                keyWord = mHotKeyList.get(position).getName();
                serach();
                return true;
            }
        });
        mFlowAdapter = new FlowLayoutAdapter(mHotKeyList);
        mFlowLayout.setAdapter(mFlowAdapter);

        mSerachAdapter.addHeaderView(header);
        mPresenter.loadHistory();
    }

    @Override
    public void showHistory(List<String> historyList) {
        mHistoryList = historyList;
        mSerachAdapter.setNewData(mHistoryList);
    }

    @OnClick({R.id.iv_arrow_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_arrow_back:
                finish();
                break;
            case R.id.iv_search:
                if (etSearch.getText().toString().isEmpty()){
                    etSearch.startAnimation(AnimationUtils.shakeAnimation(5));
                    ToastUtils.showToast("搜索关键字不能为空");
                    return;
                }
                keyWord = etSearch.getText().toString();
                serach();
                break;
        }
    }

    /**
     * 搜索功能 根据关键字
     */
    private void serach() {

        DBUtils.getDB(mContext).insertHistory(DBConfig.TABLE_HISTORY,keyWord);
        Intent intent = new Intent(this,SerachArticleActivity.class);
        intent.putExtra("keyword",keyWord);
        startActivity(SerachArticleActivity.class,intent);
        mPresenter.loadHistory();
    }
}
