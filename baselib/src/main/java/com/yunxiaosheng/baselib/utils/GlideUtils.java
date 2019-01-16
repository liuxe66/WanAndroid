package com.yunxiaosheng.baselib.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunxiaosheng.baselib.R;


/**
 * Created by Liuxe on 2018/12/21 0021
 */
public class GlideUtils {
    /**
     * 加载图片
     * @param context
     * @param url
     * @param iv
     */
    public static void loadImage(Context context, String url, ImageView iv) {
        if (context != null  && iv != null){
            //原生 API
            Glide.with(context).load(url).crossFade().dontAnimate().placeholder(R.drawable.img_default_book)
                    .error(R.drawable.img_default_book).into(iv);
        }
    }

}
