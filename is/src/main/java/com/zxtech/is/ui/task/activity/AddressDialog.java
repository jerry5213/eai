package com.zxtech.is.ui.task.activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.DropDownWindow;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.common.IsMstOptions;
import com.zxtech.is.model.s1.ElevatorAddress;
import com.zxtech.is.service.common.IsMstOptionService;
import com.zxtech.is.service.task.S1Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp600 on 2018/4/25.
 */

public class AddressDialog extends DialogFragment implements View.OnClickListener {

    //页面标题
    TextView tv_is_address_title;
    //确定
    TextView tv_is_sure;
    //取消
    TextView tv_is_cancel;
    //已选梯号
    TextView tv_is_elevator_no;
    //参数
    private Map<String, String> params = new HashMap<>();

    //省
    TextView province_selector;
    //市
    TextView city_selector;
    //区
    TextView area_selector;
    //详细地址
    EditText et_is_other_address;

    List<IsMstOptions> provinces = new ArrayList<>();
    List<IsMstOptions> cities = new ArrayList<>();
    List<IsMstOptions> areas = new ArrayList<>();

    IsMstOptionService isMstOptionService = HttpFactory.getService(IsMstOptionService.class);

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_is_cancel) {
            closeDialog();

        } else if (id == R.id.province_selector) {
            dropDown_province(view);

        } else if (id == R.id.city_selector) {
            dropDown_city(view);

        } else if (id == R.id.area_selector) {
            dropDown_area(view);

        } else if (id == R.id.tv_is_sure) {
            save();

        } else {
        }
    }

    public static AddressDialog newInstance() {
        AddressDialog fragment = new AddressDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加这句话去掉自带的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        View view = inflater.inflate(R.layout.dialog_address, null);
        //页面标题
        tv_is_address_title = view.findViewById(R.id.tv_is_address_title);
        //确定
        tv_is_sure = view.findViewById(R.id.tv_is_sure);
        //取消
        tv_is_cancel = view.findViewById(R.id.tv_is_cancel);
        //已选梯号
        tv_is_elevator_no = view.findViewById(R.id.tv_is_elevator_no);
        //省
        province_selector = view.findViewById(R.id.province_selector);
        //市
        city_selector = view.findViewById(R.id.city_selector);
        //区
        area_selector = view.findViewById(R.id.area_selector);
        //详细地址
        et_is_other_address = view.findViewById(R.id.et_is_other_address);

        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager m = getActivity().getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //params.gravity = Gravity.BOTTOM;
        params.width = (int) (d.getWidth() * 0.9);
//        params.height = (int) (d.getHeight() * 0.7);
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    private void initView() {
        /*********************设置监听开始*********************/
        //确定
        tv_is_sure.setOnClickListener(this);
        //取消
        tv_is_cancel.setOnClickListener(this);
        //省
        province_selector.setOnClickListener(this);
        //市
        city_selector.setOnClickListener(this);
        //区
        area_selector.setOnClickListener(this);
        /*********************设置监听结束*********************/

        if ("2".equals(params.get("checkedId"))) {
            tv_is_address_title.setText(R.string.is_install_address);
        } else if ("1".equals(params.get("checkedId"))) {
            tv_is_address_title.setText(R.string.is_delivery_address);
        }
        tv_is_elevator_no.setText(params.get("elevatorNo"));

        //加载省数据
        getProvince();
    }

    protected void closeDialog() {
        this.dismiss();
    }

    public interface BackResult {
        void changeElevatorByAddress(String type, ElevatorAddress elevatorAddress);
    }

    private BackResult backResult;

    public BackResult getBackResult() {
        return backResult;
    }

    public void setBackResult(BackResult backResult) {
        this.backResult = backResult;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    protected void save() {
        if (checkData()) {
            String province = province_selector.getTag().toString();
            String city = city_selector.getTag().toString();
            String area = area_selector.getTag().toString();
            String otherAddress = et_is_other_address.getText().toString();
            Map<String, String> addressParams = new HashMap<>();
            addressParams.put("projectAddProvince", province);
            addressParams.put("projectAddCity", city);
            addressParams.put("projectAddArea", area);
            addressParams.put("projectAddOther", otherAddress);
            addressParams.put("elevatorGuid", params.get("elevatorGuid"));
            addressParams.put("procDefKey", params.get("procDefKey"));
            addressParams.put("procInstId", params.get("procInstId"));
            addressParams.put("checkedId", params.get("checkedId"));

            S1Service s1Service = HttpFactory.getService(S1Service.class);
            s1Service.saveElevatorAddress(addressParams).compose(RxHelper.<BaseResponse<ElevatorAddress>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<ElevatorAddress>>(getActivity(), true) {
                @Override
                public void onSuccess(BaseResponse<ElevatorAddress> response) {
                    ElevatorAddress elevatorAddress = response.getData();
                    elevatorAddress.setProvinceName(province_selector.getText().toString());
                    elevatorAddress.setCityName(city_selector.getText().toString());
                    elevatorAddress.setAreaName(area_selector.getText().toString());
                    backResult.changeElevatorByAddress(params.get("checkedId"), response.getData());
                    ToastUtil.showLong(getResources().getString(R.string.is_successfully_save));
                    closeDialog();
                }
            });
        }
    }

    private boolean checkData() {
        if (province_selector.getTag() == null) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_province_msg));
            return false;
        }
        if (city_selector.getTag() == null) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_city_msg));
            return false;
        }
        if (area_selector.getTag() == null) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_area_msg));
            return false;
        }
        String otherAddress = et_is_other_address.getText().toString();
        if (otherAddress.length() > 100) {
            ToastUtil.showLong(getResources().getString(R.string.is_other_address_max_length_msg));
            return false;
        }
        return true;
    }

    protected void getProvince() {
        String kind = "GK0001";
        String parentCode = null;

        isMstOptionService.selectParents(kind, parentCode).compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), false) {
            @Override
            public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                provinces.clear();
                provinces.addAll(response.getData());
//                if(provinces!=null && provinces.size()>0){
//                    if(province_selector.getTag() != null){
//                        province_selector.setText(provinces.get(0).getText());
//                        province_selector.setTag(provinces.get(0).getCode());
//                    }
//                    getCity(provinces.get(0).getCode());
//                }
            }
        });
    }

    protected void getCity(String ProvinceCode) {
        String kind = "GK0002";
        isMstOptionService.selectChildrens(kind, ProvinceCode).compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), false) {
            @Override
            public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                cities.clear();
                cities.addAll(response.getData());
                if (cities != null && cities.size() > 0) {
                    city_selector.setText(cities.get(0).getText());
                    city_selector.setTag(cities.get(0).getCode());
                    getArea(cities.get(0).getCode());
                } else {
                    city_selector.setText("");
                    city_selector.setTag("");
                }
            }
        });
    }

    protected void getArea(String cityCode) {
        String kind = "GK0003";
        isMstOptionService.selectChildrens(kind, cityCode).compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), false) {
            @Override
            public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                areas.clear();
                areas.addAll(response.getData());
                if (areas != null && areas.size() > 0) {
                    area_selector.setText(areas.get(0).getText());
                    area_selector.setTag(areas.get(0).getCode());
                } else {
                    area_selector.setText("");
                    area_selector.setTag("");
                }
            }
        });
    }

    protected void dropDown_province(View view) {
        if (provinces == null || provinces.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_no_data));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        province_selector.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(getActivity(), view, provinces, view.getWidth(), -2) {
            @Override
            public void initEvents(int p) {
                province_selector.setText(provinces.get(p).getText());
                province_selector.setTag(provinces.get(p).getCode());
                getCity(provinces.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                province_selector.setCompoundDrawables(null, null, image, null);
            }

        };
    }

    protected void dropDown_city(View view) {
        if (cities == null || cities.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_no_data));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        city_selector.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(getActivity(), view, cities, view.getWidth(), -2) {
            @Override
            public void initEvents(int p) {
                city_selector.setText(cities.get(p).getText());
                city_selector.setTag(cities.get(p).getCode());
                getArea(cities.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                city_selector.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    protected void dropDown_area(View view) {
        if (areas == null || areas.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_no_data));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        area_selector.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(getActivity(), view, areas, view.getWidth(), -2) {
            @Override
            public void initEvents(int p) {
                area_selector.setText(areas.get(p).getText());
                area_selector.setTag(areas.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                area_selector.setCompoundDrawables(null, null, image, null);
            }
        };
    }
}
