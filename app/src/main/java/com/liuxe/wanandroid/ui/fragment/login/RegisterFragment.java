package com.liuxe.wanandroid.ui.fragment.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;

import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Login;
import com.liuxe.wanandroid.bean.RxEventRegister;
import com.liuxe.wanandroid.constant.RxCode;
import com.liuxe.wanandroid.constant.SputilsKey;
import com.liuxe.wanandroid.contract.login.RegisterContract;
import com.liuxe.wanandroid.presenter.login.RegisterPresenter;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.fragment.BaseMvpFragment;
import com.yunxiaosheng.baselib.rxbus.RxBus;
import com.yunxiaosheng.baselib.utils.SpUtils;
import com.yunxiaosheng.baselib.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseMvpFragment<RegisterContract.Presenter> implements RegisterContract.IView {

    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    private String username;
    private String password;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dialog_register;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return RegisterPresenter.newInstance();
    }

    @Override
    public void showNetworkError() {

    }

    @OnClick(R.id.tv_register)
    public void onViewClicked() {
        if (etUsername.getText() == null){
            ToastUtils.showToast("用户名不能为空！");
        } else if (etPassword.getText() == null){
            ToastUtils.showToast("密码不能为空！");
        } else {
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();

            mPresenter.register(username,password);
        }
    }

    @Override
    public void registerSuccess(BaseResponse<Login> response) {
        if (response.getErrorCode() == 0){
            SpUtils.putInt(mContext,SputilsKey.islogin,1);
            SpUtils.putString(mContext,SputilsKey.username,username);
            SpUtils.putString(mContext,SputilsKey.passwoed,password);

            RxBus.get().send(RxCode.RX_CODE_REGISTER_SUCCESS,new RxEventRegister(0,"注册成功"));
        } else {
            ToastUtils.showToast(response.getErrorMsg());
        }
    }
}
