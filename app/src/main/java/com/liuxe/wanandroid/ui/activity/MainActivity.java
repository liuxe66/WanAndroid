package com.liuxe.wanandroid.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.liuxe.wanandroid.R;
import com.liuxe.wanandroid.ui.activity.common.AboutActivity;
import com.liuxe.wanandroid.ui.activity.common.WebLoadDetailsActivity;
import com.liuxe.wanandroid.ui.fragment.home.HomeFragment;
import com.liuxe.wanandroid.ui.fragment.home.HomeRootFragment;
import com.liuxe.wanandroid.ui.fragment.mine.MineRootFragment;
import com.liuxe.wanandroid.ui.fragment.project.ProjectRootFragment;
import com.liuxe.wanandroid.ui.fragment.tpublic.PublicRootFragment;
import com.yunxiaosheng.baselib.base.activity.BaseActivity;
import com.yunxiaosheng.baselib.helper.BottomNavigationViewHelper;
import com.yunxiaosheng.baselib.rxbus.RxBus;
import com.yunxiaosheng.baselib.utils.AppUtils;
import com.yunxiaosheng.baselib.utils.BitmapUtils;
import com.yunxiaosheng.baselib.utils.FileUtils;
import com.yunxiaosheng.baselib.utils.NavigationUtils;
import com.yunxiaosheng.baselib.utils.SpUtils;
import com.yunxiaosheng.baselib.utils.StatusBarUtils;
import com.yunxiaosheng.baselib.utils.ToastUtils;

import java.io.File;
import java.util.Random;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements HomeFragment.OnOpenDrawerLayoutListener {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bviv_bar)
    BottomNavigationView bvivBar;
    @BindView(R.id.nv_menu)
    NavigationView nvMenu;
    @BindView(R.id.dl_root)
    DrawerLayout dlRoot;

    private CircleImageView civHead;
    private RelativeLayout rlHead;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;
    private SupportFragment[] mFragments = new SupportFragment[4];
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    public static final String HEAD_IMAGE_NAME = "head_image";

    @Override
    protected void initData() {
        super.initData();
        //        Logger.e("RxBus.get().register(this)");
        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        Logger.e("RxBus.get().unRegister(this)");
        RxBus.get().unRegister(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        StatusBarUtils.setTransparent(this);
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeRootFragment.newInstance();
            mFragments[SECOND] = PublicRootFragment.newInstance();
            mFragments[THIRD] = ProjectRootFragment.newInstance();
            mFragments[FOUR] = MineRootFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeRootFragment.class);
            mFragments[SECOND] = findFragment(PublicRootFragment.class);
            mFragments[THIRD] = findFragment(ProjectRootFragment.class);
            mFragments[FOUR] = findFragment(MineRootFragment.class);
        }
        NavigationUtils.disableNavigationViewScrollbars(nvMenu);
        civHead = nvMenu.getHeaderView(0).findViewById(R.id.civ_head);
        rlHead = nvMenu.getHeaderView(0).findViewById(R.id.root_header);

        Bitmap rootBtimap = BitmapUtils.getBitmapFromDrawable(mContext, R.drawable.ic_photo);
        Bitmap rootBlurBitmap = BitmapUtils.getBlurBitmap(mContext, rootBtimap, 20);
        rlHead.setBackground(new BitmapDrawable(rootBlurBitmap));
        //此处实际应用中替换成服务器拉取图片
        Uri headUri = Uri.fromFile(new File(getCacheDir(), HEAD_IMAGE_NAME + ".jpg"));
        if (headUri != null) {
            String cropImagePath = FileUtils.getRealFilePathFromUri(AppUtils.getContext(), headUri);
            Bitmap bitmap = BitmapFactory.decodeFile(cropImagePath);
            if (bitmap != null) {
                civHead.setImageBitmap(bitmap);
            }
        }

        civHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlRoot.closeDrawer(GravityCompat.START);
                bvivBar.setSelectedItemId(R.id.menu_item_mine);
            }
        });

        nvMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.group_item_project_address:
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "玩安卓项目");
                        bundle.putString("url", "https://github.com/liuxe66/WanAndroid");
                        startActivity(WebLoadDetailsActivity.class, bundle);
                        break;
                    case R.id.group_item_about:
                        Intent about = new Intent(mContext,AboutActivity.class);
                        startActivity(about);
                        break;
                    case R.id.group_item_theme:
                        Random random = new Random();
                        int themeIndex = random.nextInt(17)%(17+1);
                        SpUtils.setThemeIndex(mContext,themeIndex);
                        reload();
                        break;
                }
                menuItem.setCheckable(false);
//                dlRoot.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        BottomNavigationViewHelper.disableShiftMode(bvivBar);
        bvivBar.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_home:
                        showHideFragment(mFragments[FIRST]);
                        break;
                    case R.id.menu_item_public:
                        showHideFragment(mFragments[SECOND]);
                        break;
                    case R.id.menu_item_project:
                        showHideFragment(mFragments[THIRD]);
                        break;
                    case R.id.menu_item_mine:
                        showHideFragment(mFragments[FOUR]);
                        break;
                }
                return true;
            }
        });


    }

    @Override
    public void onBackPressedSupport() {
        if (dlRoot.isDrawerOpen(GravityCompat.START)) {
            dlRoot.closeDrawer(GravityCompat.START);
            return;
        }

        if (getFragmentManager().getBackStackEntryCount() > 1) {
            //如果当前存在fragment>1，当前fragment出栈
            pop();
        } else {
            //如果已经到root fragment了，2秒内点击2次退出
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                setIsTransAnim(false);
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                ToastUtils.showToast(R.string.press_again);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onOpen() {
        if (!dlRoot.isDrawerOpen(GravityCompat.START)) {
            dlRoot.openDrawer(GravityCompat.START);
        }
    }
}
