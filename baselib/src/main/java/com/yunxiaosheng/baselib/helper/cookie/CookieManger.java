package com.yunxiaosheng.baselib.helper.cookie;

import android.content.Context;

import com.yunxiaosheng.baselib.utils.LogUtils;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 管理cookies
 * Created by 陈健宇 at 2018/12/12
 */
public class CookieManger implements CookieJar {

    public static String APP_PLATFORM = "app-platform";
    private static Context mContext;
    private static PersistentCookieStore cookieStore;

    public CookieManger(Context context) {
        mContext = context;
        if (cookieStore == null ) {
            cookieStore = new PersistentCookieStore(mContext);
        }

    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            LogUtils.e(" saveFromResponse cookies:"+cookies.toString());
            for (Cookie item : cookies) {
//                LogUtils.e("item:"+item);
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        LogUtils.e(" loadForRequest cookies:"+cookies.toString());
        return cookies;
    }
}
