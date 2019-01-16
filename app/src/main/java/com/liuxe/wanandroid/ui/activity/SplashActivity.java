package com.liuxe.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import com.hzn.lib.EasyTransition;
import com.hzn.lib.EasyTransitionOptions;
import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.constant.SputilsKey;
import com.liuxe.wanandroid.ui.activity.login.LoginActivity;
import com.yunxiaosheng.baselib.base.activity.BaseActivity;
import com.yunxiaosheng.baselib.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (SpUtils.getInt(mContext,SputilsKey.islogin,0) == 0){
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                intent.putExtra("anim", true);
//                EasyTransitionOptions options = EasyTransitionOptions
//                        .makeTransitionOptions(SplashActivity.this, ivLogo);
//
//                EasyTransition.startActivity(intent, options);
//            } else {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
//            }

            finish();

        }
    };
    @Override
    protected void initView(Bundle savedInstanceState) {

        mHandler.sendEmptyMessageDelayed(100,1000);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

}
