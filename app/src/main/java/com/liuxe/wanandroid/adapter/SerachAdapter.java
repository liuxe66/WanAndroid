package com.liuxe.wanandroid.adapter;

import android.support.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuxe.wanandroid.R;

import java.util.List;

public class SerachAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public SerachAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_history,item);
        helper.addOnClickListener(R.id.tv_delete);
    }
}
