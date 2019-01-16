package com.liuxe.wanandroid.ui.activity.common;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuxe.wanandroid.R;
import com.yunxiaosheng.baselib.base.activity.BaseActivity;
import com.yunxiaosheng.baselib.utils.DisplayUtils;
import com.yunxiaosheng.baselib.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebLoadDetailsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.pb_web)
    ProgressBar pbWeb;

    private String title;
    private String url;



    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            url = bundle.getString("url");
        }
    }



    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,"跳转中...");
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pbWeb.setVisibility(View.GONE);//加载完网页进度条消失
                    initTitleBar(toolbar,title);
                } else {
                    pbWeb.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pbWeb.setProgress(newProgress);//设置进度值
                }
            }
        });
        webview.loadUrl(url);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_load_details;
    }

}
