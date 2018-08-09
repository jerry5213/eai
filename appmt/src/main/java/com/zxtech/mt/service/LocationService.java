package com.zxtech.mt.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chw on 2016/6/22.
 */
public class LocationService extends Service implements AMapLocationListener {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String url = "/mtmo/uploadempposition.mo";
    private Intent alarmIntent = null;
    private PendingIntent alarmPi = null;
    private AlarmManager alarm = null;
    private long lastTime = 0;
    private Intent intent = new Intent("com.mt.receiver");
    //private long UPLOAD_INTERVAL_TIME = 60*1000*15;
    private long UPLOAD_INTERVAL_TIME = 3000;
    private String emp_id = "";
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode()== 0 ) {
            //获取经度
            //获取纬度
            intent.putExtra("address",aMapLocation.getAddress());
            intent.putExtra("longitude",String.valueOf(aMapLocation.getLongitude()));
            intent.putExtra("latitude",String.valueOf(aMapLocation.getLatitude()));
            intent.putExtra("info","地址:"+aMapLocation.getAddress()+" 经度:"+String.valueOf(aMapLocation.getLongitude())+" 纬度:"+String.valueOf(aMapLocation.getLatitude()));
            sendBroadcast(intent);
            long currentTime = System.currentTimeMillis();
            if ((lastTime!=0)&&(currentTime-lastTime <= UPLOAD_INTERVAL_TIME)) {
                return;
            }
            lastTime = currentTime;
            Map<String, String> param = new HashMap<>();
            param.put("longitude", String.valueOf(aMapLocation.getLongitude()));
            param.put("latitude", String.valueOf(aMapLocation.getLatitude()));
            param.put("emp_id", emp_id);
            if (!TextUtils.isEmpty(aMapLocation.getAddress())) {
                param.put("address",aMapLocation.getAddress());
            }

            HttpUtil.getInstance(this).request(url,param, new HttpCallBack<String>() {
                @Override
                public void onSuccess(String result) {

                }

                @Override
                public void onFail(String msg) {
                    Log.d("chw","网络错误");
                }
            });

            }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
   //     Log.d("chw","service已启动");
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        initOption();
        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();


        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            emp_id =  intent.getStringExtra("emp_id");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
       locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(false);
        // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
        locationOption.setInterval(15000);
        locationOption.setOnceLocation(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != alarmReceiver){
            unregisterReceiver(alarmReceiver);
            alarmReceiver = null;
        }
    }

    /**
     * 初始化定位
     */
    private void init() {
        // 创建Intent对象，action为LOCATION
        alarmIntent = new Intent();
        alarmIntent.setAction("LOCATION");
        IntentFilter ift = new IntentFilter();

        // 定义一个PendingIntent对象，PendingIntent.getBroadcast包含了sendBroadcast的动作。
        // 也就是发送了action 为"LOCATION"的intent
        alarmPi = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        // AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        //动态注册一个广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("LOCATION");
        registerReceiver(alarmReceiver, filter);
        if(null != alarm) {
            //设置一个闹钟，15秒之后每隔一段时间执行启动一次定位程序
            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 2 * 1000,
                    15 * 1000, alarmPi);
        }
    }

    private BroadcastReceiver alarmReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("LOCATION")){
                if(null != locationClient){
                    locationClient.startLocation();
                }
            }
        }
    };
}
