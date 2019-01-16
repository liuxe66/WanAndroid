package com.liuxe.wanandroid.ui.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.adapter.FlowLayoutAdapter;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.HotKey;
import com.liuxe.wanandroid.contract.common.SerachHotKeyContract;
import com.liuxe.wanandroid.presenter.common.SerachHotKeyPresenter;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.activity.BaseMvpActivity;
import com.yunxiaosheng.baselib.utils.AnimationUtils;
import com.yunxiaosheng.baselib.utils.ToastUtils;
import com.yunxiaosheng.baselib.widgets.VRecylerView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SerachActivity extends BaseMvpActivity<SerachHotKeyPresenter> implements SerachHotKeyContract.IView {

    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.flowlayout)
    TagFlowLayout mFlowLayout;

    private FlowLayoutAdapter mFlowAdapter;
    private List<HotKey> mHotKeyList;
    private String keyWord = "";

    @Override
    protected void initView(Bundle savedInstanceState) {
        mHotKeyList = new ArrayList<>();
        mPresenter.loadHotKey();

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                keyWord = mHotKeyList.get(position).getName();
                serach();
                return true;
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
        return SerachHotKeyPresenter.newInstance();
    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void loadHotKey(BaseResponse<List<HotKey>> response) {
        mHotKeyList = response.getData();
        mFlowAdapter = new FlowLayoutAdapter(mHotKeyList);
        mFlowLayout.setAdapter(mFlowAdapter);
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

        Intent intent = new Intent(this,SerachArticleActivity.class);
        intent.putExtra("keyword",keyWord);
        startActivity(SerachArticleActivity.class,intent);

    }
}
