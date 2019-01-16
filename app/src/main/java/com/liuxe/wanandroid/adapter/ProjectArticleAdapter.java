package com.liuxe.wanandroid.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.bean.Article;
import com.yunxiaosheng.baselib.config.DBConfig;
import com.yunxiaosheng.baselib.config.ItemState;
import com.yunxiaosheng.baselib.utils.AppUtils;
import com.yunxiaosheng.baselib.utils.DBUtils;
import com.yunxiaosheng.baselib.utils.DateUtils;
import com.yunxiaosheng.baselib.utils.GlideUtils;
import com.yunxiaosheng.baselib.utils.ResourcesUtils;

import java.util.List;

public class ProjectArticleAdapter extends BaseQuickAdapter<Article,BaseViewHolder> {


    public ProjectArticleAdapter(int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        if (DBUtils.getDB(AppUtils.getContext()).isRead(DBConfig.TABLE_PROJECT,item.getId()+"",ItemState.STATE_IS_READ)){
            helper.setTextColor(R.id.tv_title, ResourcesUtils.getColor(R.color.color_999));
        } else {
            helper.setTextColor(R.id.tv_title, ResourcesUtils.getColor(R.color.color_333));
        }
        helper.setText(R.id.tv_author,item.getAuthor());
        helper.setText(R.id.tv_title,item.getDesc());
        GlideUtils.loadImage(mContext,item.getEnvelopePic(), (ImageView) helper.getView(R.id.iv_img));

    }
}
