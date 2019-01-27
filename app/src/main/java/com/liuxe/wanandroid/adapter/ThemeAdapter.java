package com.liuxe.wanandroid.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ThemeAdapter extends BaseQuickAdapter<Integer,BaseViewHolder> {

    public ThemeAdapter(@Nullable List<Integer> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {

    }
}
