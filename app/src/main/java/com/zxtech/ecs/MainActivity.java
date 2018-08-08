package com.zxtech.ecs;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.UserManager;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.service.LocationService;
import com.zxtech.ecs.test.wxapi.WXEntryActivity;
import com.zxtech.ecs.ui.bi.BiFragment;
import com.zxtech.ecs.ui.home.HomeFragment;
import com.zxtech.ecs.ui.login.LoginActivity;
import com.zxtech.ecs.ui.me.MeFragment;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.gks.model.vo.login.LoginVO;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    private BaseFragment[] mFragments = new BaseFragment[4];

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private LocationService locationService;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        BottomNavigationBar navigation = findViewById(R.id.navigation);
        navigation.setTabSelectedListener(mOnNavigationItemSelectedListener);
        boolean switch_language = getIntent().getBooleanExtra("switch_language", false);
        int firstPosition = 0;
        if (switch_language) {
            firstPosition = THIRD;
        }
        navigation
                .addItem(new BottomNavigationItem(R.drawable.ic_home_on, getString(R.string.nav_home)).setInactiveIconResource(R.drawable.ic_home))
                //.addItem(new BottomNavigationItem(R.drawable.ic_tool_on, getString(R.string.nav_tool)).setInactiveIconResource(R.drawable.ic_tool))
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
            //    mFragments[SECOND] = ToolFragment.newInstance();
            mFragments[SECOND] = BiFragment.newInstance();
            mFragments[THIRD] = MeFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, firstPosition,
                    mFragments[FIRST],
                    // mFragments[SECOND],
                    mFragments[SECOND],
                    mFragments[THIRD]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeFragment.class);
            // mFragments[SECOND] = findFragment(ToolFragment.class);
            mFragments[SECOND] = findFragment(BiFragment.class);
            mFragments[THIRD] = findFragment(MeFragment.class);
        }

        Log.d("chw", "initView: ==="+Util.getSHA1(mContext));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permission();
    }


    private void permission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                } else {
                    permission();
                    ToastUtil.showLong(getString(R.string.msg13));
                }
            }
        });
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventWXLogin(EventAction event) {
        if (event.getCode() == EventAction.WX_LOGIN) {
            wxLogin(event.getData().toString());
        }
    }

    private void wxLogin(String code) {
        baseResponseObservable = HttpFactory.getApiService().wechatLogin(code, Constants.WECHAT_APP_ID,Constants.WECHAT_APP_SECRET);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<LoginVO>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<LoginVO>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<LoginVO> response) {
                        LoginVO loginData = response.getData();
                        SPUtils.put(mContext, "wx_login", true);
                        UserManager.saveUserInfo(mContext, loginData, false, null, null, null);
                        EventBus.getDefault().post(new EventAction(EventAction.LOGIN_REFRESH_MENU)); //刷新菜单
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(getUserId())) {
            Log.d("chw", "onStop: "+"开启定位服务");
            locationService = ((App) getApplication()).locationService;
            //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
            locationService.registerListener(mListener);
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
            locationService.start();// 定位SDK
        }
    }


    @Override
    protected void onStop() {
        if (locationService != null) {
            Log.d("chw", "onStop: "+"停止定位服务");
            locationService.unregisterListener(mListener); //注销掉监听
            locationService.stop(); //停止定位服务
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                }  else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                Log.d("chw", "onReceiveLocation: "+sb.toString());

                updateLoginLog(getUserId(),String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()),location.getAddrStr());
            }
        }

    };

    private void updateLoginLog(String userId, String latitude, String lontitude, String address) {
        baseResponseObservable = HttpFactory.getApiService().
                updateLoginLog(userId,latitude,lontitude,address);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(this, false) {

                    @Override
                    public void onSuccess(BaseResponse response) {
                    }
                });
    }
}
