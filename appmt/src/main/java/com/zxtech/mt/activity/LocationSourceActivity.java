package com.zxtech.mt.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.zxtech.mt.adapter.ViewHolder;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.common.VolleySingleton;
import com.zxtech.mt.entity.BaseAccessory;
import com.zxtech.mt.entity.JsonData;
import com.zxtech.mt.entity.MtProject;
import com.zxtech.mt.entity.MtStation;
import com.zxtech.mt.entity.MtWorkPlanAddtion;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.utils.Util;
import com.zxtech.mt.widget.CustomDialog;
import com.zxtech.mt.widget.DropDownWindow;
import com.zxtech.mt.widget.ListViewDialog;
import com.zxtech.mt.widget.PopMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxtech.mt.widget.SingleSelectionDialog;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AMapV1地图
 * chw
 *
 */
@Route(path = "/mt/location")
public class LocationSourceActivity extends BaseActivity implements LocationSource,
		AMapLocationListener ,OnClickListener,OnMapLoadedListener{
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
    private boolean isClick;
    private String type;
    private String tableId;

    private DropDownWindow mWindows;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        View view = mInfalter.inflate(R.layout.locationsource_activity, null);
        main_layout.addView(view);
        setBottomLayoutHide();
		mapView = (MapView) findViewById(R.id.map);
        set_textview.setText(getString(R.string.location));
        title_textview.setText(getString(R.string.menu_project_orientation));
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
	}

    @Override
    protected void onCreate() {

    }



    @Override
    protected void findView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    /**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.WHITE);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.parseColor("#aa0075c2"));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(5.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.setOnMapLoadedListener(this);
        UiSettings mSetting = aMap.getUiSettings();
        mSetting.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mSetting.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        mSetting.setScaleControlsEnabled(true);
        mSetting.setZoomControlsEnabled(false);
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

        // aMap.setMyLocationType()
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                if (isClick){
                    confirm(amapLocation);
                }
                mlocationClient.stopLocation();
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
				Log.e("AmapErr",errText);
			}
		}
	}

    private void confirm(final AMapLocation amapLocation) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_text,null);
        TextView content_textview = (TextView) view.findViewById(R.id.content_textview);
        CustomDialog dialog = new CustomDialog(mContext,view){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                if (v.getId()==R.id.confirm_button){
                    uploadLocation(amapLocation);
                    this.dismiss();
                }
            }
        };
        String address = getString(R.string.upload_address$)+amapLocation.getAddress();
        content_textview.setText(address);
        dialog.show();
        dialog.setTitle(getString(R.string.confirm_upload));
        dialog.setHeightAndWidth(UIController.SCREEN_WIDTH*4/5,UIController.SCREEN_HEIGHT*1/3);



    }

    private void uploadLocation(AMapLocation amapLocation){
        Map<String, String> param = new HashMap<>();

        param.put("id",tableId);
        param.put("proj_lng",String.valueOf(amapLocation.getLongitude()));
        param.put("proj_lat",String.valueOf(amapLocation.getLatitude()));

        HttpUtil.getInstance(mContext).request("/mtmo/updateprojectaddress.mo", param, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                ToastUtil.showLong(mContext, "定位成功");
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.showLong(mContext, "定位失败");
            }
        });
    }

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			//设置定位监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			//设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			mLocationOption.setOnceLocation(true);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	@Override
	public void onClick(View view) {
        super.onClick(view);
        if (view.getId()==set_textview.getId()) {

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            final int width = (int) (displayMetrics.widthPixels*0.4);
            int height = (int) (displayMetrics.heightPixels*0.4);

            final PopMenu pm = new PopMenu(this,width,height);

            List<Map<String, Object>> resources = new ArrayList<Map<String,Object>>();
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("text", getString(R.string.project));
            resources.add(maps);
            pm.addItems(resources);
            pm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    isClick = true;

                    type = String.valueOf(i);
                    switch (i){
                        case 0:
                            Map<String, String> param = new HashMap<>();
                            param.put("grp_id", SPUtils.get(mContext,"grp_id","").toString());

                            HttpUtil.getInstance(mContext).request("/mtmo/getmtprojectlist.mo", param, new HttpCallBack<List<MtProject>>() {
                                @Override
                                public void onSuccess(List<MtProject> list) {
                                    if (list == null) {
                                        ToastUtil.showLong(mContext,"未获取到相关项目");
                                        return;
                                    }
                                    SingleSelectionDialog singleDialog = new SingleSelectionDialog(mContext,getString(R.string.project),list) {
                                        @Override
                                        public void confrim(Object object) {
                                            MtProject project = (MtProject) object;
                                            tableId = project.getId();
                                            if (mlocationClient != null) {
                                                mlocationClient.startLocation();
                                            }
                                            this.dismiss();
                                        }

                                        @Override
                                        public void layout(ViewHolder holder, Object object, int position) {
                                            MtProject project = (MtProject) object;
                                            holder.setText(R.id.name_textview,project.getProj_name());
                                        }
                                    };
                                    singleDialog.show();
                                }

                                @Override
                                public void onFail(String msg) {
                                }
                            });


                            break;


                    }
                    pm.dismiss();
                }
            });
            pm.showAsDropDown(set_textview);
        }

	}



    @Override
	public void onMapLoaded() {
		aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
	}


}
