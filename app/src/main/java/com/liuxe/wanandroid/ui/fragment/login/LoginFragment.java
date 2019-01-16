package com.liuxe.wanandroid.ui.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;

import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.bean.BaseResponse;
import com.liuxe.wanandroid.bean.Login;
import com.liuxe.wanandroid.constant.SputilsKey;
import com.liuxe.wanandroid.contract.login.LoginContract;
import com.liuxe.wanandroid.presenter.login.LoginPresenter;
import com.liuxe.wanandroid.ui.activity.MainActivity;
import com.yunxiaosheng.baselib.base.BasePresenter;
import com.yunxiaosheng.baselib.base.fragment.BaseMvpFragment;
import com.yunxiaosheng.baselib.utils.SpUtils;
import com.yunxiaosheng.baselib.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseMvpFragment<LoginContract.Presenter> implements LoginContract.IView {

    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    private String username;
    private String password;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dialog_login;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    public void putParam(){
        etUsername.setText(SpUtils.getString(mContext,SputilsKey.username,""));
        etPassword.setText(SpUtils.getString(mContext,SputilsKey.passwoed,""));
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return LoginPresenter.newInstance();
    }

    @Override
    public void showNetworkError() {

    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {

        if (etUsername.getText() == null){
            ToastUtils.showToast("用户名不能为空！");
        } else if (etPassword.getText() == null){
            ToastUtils.showToast("密码不能为空！");
        } else {
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();

            mPresenter.loadLogin(username,password);
        }


    }

    @Override
    public void loginSuccess(BaseResponse<Login> response) {
        if (response.getErrorCode() == 0){
            SpUtils.putInt(mContext,SputilsKey.islogin,1);
            SpUtils.putString(mContext,SputilsKey.username,username);
            SpUtils.putString(mContext,SputilsKey.passwoed,password);
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            ToastUtils.showToast(response.getErrorMsg());
        }
    }
}
