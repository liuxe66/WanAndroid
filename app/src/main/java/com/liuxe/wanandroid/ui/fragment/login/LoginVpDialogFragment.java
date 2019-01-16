package com.liuxe.wanandroid.ui.fragment.login;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.bean.RxEventRegister;
import com.liuxe.wanandroid.constant.RxCode;
import com.liuxe.wanandroid.ui.widget.NoScrollViewPager;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.yunxiaosheng.baselib.rxbus.RxBus;
import com.yunxiaosheng.baselib.rxbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class LoginVpDialogFragment extends DialogFragment {

    private NoScrollViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private DialogPagerAdapter mDialogPagerAdapter;
    private LoginFragment mLoginFragment;
    private int index;

    public static LoginVpDialogFragment newInstance(int index) {
        LoginVpDialogFragment fragment = new LoginVpDialogFragment();
        Bundle args = new Bundle();
        args.putInt("index",index);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.verticalMargin = 0.06f;
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px( 48f);
        params.height = getResources().getDisplayMetrics().heightPixels/2;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.BottomDialog_Animation);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_viewpager,null);
        index = getArguments().getInt("index");
        mFragmentList = new ArrayList<>();
        mFragmentList.add(RegisterFragment.newInstance());
        mLoginFragment = LoginFragment.newInstance();
        mFragmentList.add(mLoginFragment);

        mViewPager = view.findViewById(R.id.noscroll_viewpager);
        mDialogPagerAdapter = new DialogPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mDialogPagerAdapter);
        mViewPager.setCurrentItem(index);

        RxBus.get().register(this);
        return view;
    }

    /**
     * RxBus接收图片Uri
     *
     * @param bean RxEventHeadBean
     */
    @Subscribe(code = RxCode.RX_CODE_REGISTER_SUCCESS)
    public void rxBusEvent(RxEventRegister bean) {
        if (bean.getCode() == 0){
            mLoginFragment.putParam();
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    class DialogPagerAdapter extends FragmentPagerAdapter{

        public DialogPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}
