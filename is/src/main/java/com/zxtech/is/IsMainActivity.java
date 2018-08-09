package com.zxtech.is;

import android.Manifest;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.is.ui.home.HomeFragment;
import com.zxtech.is.ui.me.MeFragment;
import com.zxtech.is.util.ToastUtil;

import io.reactivex.functions.Consumer;

public class IsMainActivity extends BaseActivity {

    private BaseFragment[] mFragments = new BaseFragment[4];

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permission();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        BottomNavigationBar navigation = findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setTabSelectedListener(mOnNavigationItemSelectedListener);
        boolean switch_language = getIntent().getBooleanExtra("switch_language", false);
        int firstPosition = 0;
        if (switch_language) {
            firstPosition = FOURTH;
        }
        navigation
                .addItem(new BottomNavigationItem(R.drawable.ic_home_on, getString(R.string.nav_home)).setInactiveIconResource(R.drawable.ic_home))
                .addItem(new BottomNavigationItem(R.drawable.ic_tool_on, getString(R.string.nav_tool)).setInactiveIconResource(R.drawable.ic_tool))
                .addItem(new BottomNavigationItem(R.drawable.ic_bi_on, getString(R.string.nav_bi)).setInactiveIconResource(R.drawable.ic_bi))
                .addItem(new BottomNavigationItem(R.drawable.ic_me_on, getString(R.string.nav_me)).setInactiveIconResource(R.drawable.ic_me))
                .setFirstSelectedPosition(firstPosition)
                .setInActiveColor(R.color.text_color)
                .setActiveColor(R.color.main)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .initialise();
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = MeFragment.newInstance();
//            mFragments[THIRD] = HomeFragment.newInstance();
//            mFragments[FOURTH] = HomeFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, firstPosition,
                    mFragments[FIRST],
                    mFragments[SECOND]
//                    mFragments[THIRD],
//                    mFragments[FOURTH]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeFragment.class);
            mFragments[SECOND] = findFragment(MeFragment.class);
//            mFragments[THIRD] = findFragment(BiFragment.class);
//            mFragments[FOURTH] = findFragment(MeFragment.class);
        }
    }

    @Override
    public void onBackPressedSupport() {


        if (getFragmentManager().getBackStackEntryCount() > 1) {
            //如果当前存在fragment>1，当前fragment出栈
            pop();
        } else {
            //如果已经到root fragment了，2秒内点击2次退出
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                ToastUtil.showLong(getString(R.string.exit_hint));
            }
        }
    }


    private BottomNavigationBar.OnTabSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            showHideFragment(mFragments[position]);
        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {

        }
    };

    private void permission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                } else {
                    permission();
                    ToastUtil.showLong("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                }
            }
        });
    }
}
