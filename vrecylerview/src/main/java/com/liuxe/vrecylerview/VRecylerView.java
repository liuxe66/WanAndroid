package com.liuxe.vrecylerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class VRecylerView extends FrameLayout {
    private RecyclerView mRecyclerView;
    private View vLoading;
    private View vEmpty;
    private View vError;
    private TextView tvError;

    private OnErrorListener mOnErrorListener;
    private RecyclerView.Adapter mAdapter;

    public VRecylerView(Context context) {
        this(context,null);
    }

    public VRecylerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VRecylerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_networkstatus_recylerview,this);
        mRecyclerView = this.findViewById(R.id.recylerview);
        vEmpty = this.findViewById(R.id.v_empty);
        vError = this.findViewById(R.id.v_network_error);
        vLoading = this.findViewById(R.id.v_loading);
        tvError = this.findViewById(R.id.tv_error);

        tvError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnErrorListener.setOnError();
            }
        });
    }

    public void setOnErrorListener(OnErrorListener listener){
        mOnErrorListener = listener;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        mRecyclerView.setAdapter(adapter);
    }

    public void showEmpty(){
        vError.setVisibility(GONE);
        vLoading.setVisibility(GONE);
        vEmpty.setVisibility(VISIBLE);
    }

    public void showError(){
        vError.setVisibility(VISIBLE);
        vLoading.setVisibility(GONE);
        vEmpty.setVisibility(GONE);
    }

    public void showLoading(){
        vError.setVisibility(GONE);
        vLoading.setVisibility(VISIBLE);
        vEmpty.setVisibility(GONE);
    }

    public void hideNetworkStatus(){
        vError.setVisibility(GONE);
        vLoading.setVisibility(GONE);
        vEmpty.setVisibility(GONE);
    }

    interface OnErrorListener{
        void setOnError();
    }
}
