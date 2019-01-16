package com.liuxe.wanandroid.ui.activity.login;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzn.lib.EasyTransition;
import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.constant.SputilsKey;
import com.liuxe.wanandroid.ui.activity.MainActivity;
import com.liuxe.wanandroid.ui.fragment.login.LoginVpDialogFragment;
import com.yunxiaosheng.baselib.base.activity.BaseActivity;
import com.yunxiaosheng.baselib.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity{


    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_logo)
    TextView tvLogo;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_look)
    TextView tvLook;

    @Override
    protected void initView(Bundle savedInstanceState) {
        enterAnim();
    }

    private void enterAnim() {
        if (getIntent().getBooleanExtra("anim", true)) {
            EasyTransition.enter(this, 600,new DecelerateInterpolator(), new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    initOtherView();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            return;
        }
        initOtherView();

    }

    private void initOtherView() {
        tvLogo.setVisibility(View.VISIBLE);
        tvLogo.setAlpha(0.0f);
        llLogin.setVisibility(View.VISIBLE);
        llLogin.setAlpha(0.0f);

        tvLogo.setTranslationY(-48);
        tvLogo.animate().setDuration(300).alpha(1.0f).translationY(0.0f).start();

        llLogin.animate().setDuration(300).alpha(1.0f).start();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_look})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                LoginVpDialogFragment loginVpDialogFragment1 = LoginVpDialogFragment.newInstance(1);
                loginVpDialogFragment1.show(getSupportFragmentManager(),"login");
                break;
            case R.id.tv_register:
                LoginVpDialogFragment loginVpDialogFragment2 = LoginVpDialogFragment.newInstance(0);
                loginVpDialogFragment2.show(getSupportFragmentManager(),"login");
                break;
            case R.id.tv_look:
                SpUtils.putInt(mContext,SputilsKey.islogin,2);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
