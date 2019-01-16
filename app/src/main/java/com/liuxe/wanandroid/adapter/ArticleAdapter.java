package com.liuxe.wanandroid.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.bean.Article;
import com.yunxiaosheng.baselib.config.DBConfig;
import com.yunxiaosheng.baselib.config.ItemState;
import com.yunxiaosheng.baselib.utils.DBUtils;
import com.yunxiaosheng.baselib.utils.ResourcesUtils;
import com.yunxiaosheng.baselib.utils.SpUtils;

import java.util.List;

public class ArticleAdapter extends BaseQuickAdapter<Article,BaseViewHolder> {

    public ArticleAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        if (DBUtils.getDB(mContext).isRead(DBConfig.TABLE_ARTICLE, item.getId()+"", ItemState
                .STATE_IS_READ)) {
            helper.setTextColor(R.id.tv_article_tittle, ResourcesUtils.getColor(R.color.color_999));
        } else {
            helper.setTextColor(R.id.tv_article_tittle, ResourcesUtils.getColor(R.color.color_333));
        }

        helper.setText(R.id.tv_article_tittle,item.getTitle());
        helper.setText(R.id.tv_article_author,item.getAuthor());
        helper.setText(R.id.tv_article_type,"专题："+item.getChapterName());
        helper.setText(R.id.tv_article_date,item.getNiceDate());
    }
}
