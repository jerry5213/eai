package com.zxtech.is.ui.task.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.DateUtil;
import com.zxtech.is.util.ImageUtil;
import com.zxtech.is.util.PermissionUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.DropDownWindow;
import com.zxtech.is.widget.SelectDialog;
import com.zxtech.is.BuildConfig;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.activity.BasePhotoActivity;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.attach.Attach;
import com.zxtech.is.model.common.IsMstOptions;
import com.zxtech.is.model.s1.ElevatorContact;
import com.zxtech.is.service.common.IsMstOptionService;
import com.zxtech.is.ui.task.adpter.ContactAdpter;
import com.zxtech.is.ui.task.adpter.ElevatorAttachAdpter;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by syp600 on 2018/4/30.
 */

public abstract class ElevatorDetailCommonActivity extends BasePhotoActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    //交货地址 省
    @BindView(R2.id.province_selector)
    TextView province_selector;
    //交货地址 市
    @BindView(R2.id.city_selector)
    TextView city_selector;
    //交货地址 区
    @BindView(R2.id.area_selector)
    TextView area_selector;
    //交货地址 详细地址
    @BindView(R2.id.et_is_other_address)
    EditText et_is_other_address;

    //安装地址 省
    @BindView(R2.id.install_province_selector)
    TextView install_province_selector;
    //安装地址 市
    @BindView(R2.id.install_city_selector)
    TextView install_city_selector;
    //安装地址 区
    @BindView(R2.id.install_area_selector)
    TextView install_area_selector;
    //安装地址 详细地址
    @BindView(R2.id.install_et_is_other_address)
    EditText install_et_is_other_address;

    List<IsMstOptions> provinces = new ArrayList<>();
    List<IsMstOptions> installProvinces = new ArrayList<>();
    List<IsMstOptions> cities = new ArrayList<>();
    List<IsMstOptions> installCities = new ArrayList<>();
    List<IsMstOptions> areas = new ArrayList<>();
    List<IsMstOptions> installAreas = new ArrayList<>();

    IsMstOptionService isMstOptionService = HttpFactory.getService(IsMstOptionService.class);

    //联系人列表
    @BindView(R2.id.rv_is_contact)
    RecyclerView rv_is_contact;
    List<ElevatorContact> contactList = new ArrayList<>();

    //加
    @BindView(R2.id.iv_is_add)
    ImageView iv_is_add;
    //减
    @BindView(R2.id.iv_is_minus)
    ImageView iv_is_minus;


    //产品附件
    @BindView(R2.id.rv_is_elevator_attach)
    RecyclerView rv_is_elevator_attach;

    ContactAdpter contactAdpter;
    ElevatorAttachAdpter elevatorAttachAdpter;

    //产品附件
    List<Attach> elevatorAttachList = new ArrayList<>();

    String taskId;
    String procDefKey;
    String projectGuid;
    String elevatorGuid;
    String procInstId;

    //删除的附件
    List<String> deleteFileList = new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);

        //产品附件
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_is_elevator_attach.setLayoutManager(linearLayoutManager);
        rv_is_elevator_attach.addItemDecoration(new com.zxtech.is.widget.MyItemDecoration());
        elevatorAttachAdpter = new ElevatorAttachAdpter(R.layout.item_img_common, elevatorAttachList);
        rv_is_elevator_attach.setAdapter(elevatorAttachAdpter);

        //联系人列表
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rv_is_contact.setLayoutManager(linearLayoutManager1);
        rv_is_contact.addItemDecoration(new MyItemDecoration());
        contactAdpter = new ContactAdpter(R.layout.item_contact, contactList);
        contactAdpter.bindToRecyclerView(rv_is_contact);
        rv_is_contact.setAdapter(contactAdpter);

        elevatorAttachAdpter.setOnItemClickListener(this);
        elevatorAttachAdpter.setOnItemChildClickListener(this);
        //加载数据
        loadData();
    }

    //初始化
    protected abstract void loadData();

    protected void openTime(final TextView textView) {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DATE);
        String inputDate = textView.getText().toString();
        if (!"".equals(inputDate)) {
            String[] dateArr = inputDate.split("-");
//            mYear = Integer.parseInt(inputDate.substring(0, 4));
//            mMonth = Integer.parseInt(inputDate.substring(6, 7))-1;
//            mDay = Integer.parseInt(inputDate.substring(9));
            mYear = Integer.parseInt(dateArr[0]);
            mMonth = Integer.parseInt(dateArr[1]) - 1;
            mDay = Integer.parseInt(dateArr[2]);
        }
        @SuppressWarnings("ResourceType")
        DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker dp, int year, int month, int day) {
                String date = DateUtil.formatChange(year, month, day, "yyyy-MM-dd");
                textView.setText(date);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void addAttach() {
        List<String> names = new ArrayList<>();
        names.add(getResources().getString(R.string.is_take_photo));
        names.add(getResources().getString(R.string.is_take_image));
        showSelectDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        RxPermissions rxPermissions = new RxPermissions(getActivity());
                        rxPermissions.requestEach(Manifest.permission.CAMERA)
                                .subscribe(new Consumer<Permission>() {
                                    @Override
                                    public void accept(Permission permission) throws Exception {
                                        if (permission.granted) {
                                            // 用户已经同意该权限
                                            takePhoto();
                                        } else if (permission.shouldShowRequestPermissionRationale) {
                                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                            ToastUtil.showLong(getResources().getString(R.string.is_user_denial_authority));
                                        } else {
                                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                            ToastUtil.showLong(getResources().getString(R.string.is_denial_authority_operation));
                                        }
                                    }
                                });
                        break;
                    case 1:
                        takeImage();
                        break;
                }
            }
        }, names);
    }

    @Override
    protected void picSuccess(Bitmap bitmap) {
        Attach attach = new Attach();
        attach.setBitmap(bitmap);
        //本地图片路径
        String mPhotoPath = ImageUtil.saveImageToGallery(ElevatorDetailCommonActivity.this, bitmap);
        String filetype = mPhotoPath.substring(mPhotoPath.lastIndexOf(".") + 1);
        String filename = mPhotoPath.substring(mPhotoPath.lastIndexOf("/") + 1);
        attach.setFiletype(filetype);
        attach.setFilepath(mPhotoPath);
        attach.setFilename(filename);

        elevatorAttachList.add(attach);
        elevatorAttachAdpter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PermissionUtils.checkAndRequestPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, 0);
        Intent intent = new Intent();
        switch (elevatorAttachList.get(position).getFiletype()) {
            case "pdf":
//                File file = new File(DOWN_LOAD_PATH, projectAttachList.get(0).getFilename());
//                if (file.exists()) {//如果已存在,直接打开
//                    OfficePoiUtil.openFile(mContext, file);
//                } else {//文件不存在,下载后打开
////                    downloadFile(fileInfoList, file);
//                }
                break;
            case "jpg":
            case "png":
                String attachguid = elevatorAttachList.get(position).getAttachguid();
                String url = "";
                if (attachguid != null) {
                    url = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + attachguid;
                } else {
                    url = elevatorAttachList.get(position).getFilepath();
                }
                intent.putExtra("url", url);
                intent.setClass(mContext, ShowBigImageSimpleActivity.class);
                startActivity(intent);
                break;
        }
    }

    protected void getProvince(final String addressType, final InitAddress address) {
        String kind = "GK0001";
        String parentCode = null;

        isMstOptionService.selectParents(kind, parentCode).compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), false) {
            @Override
            public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                if ("1".equals(addressType)) {
                    provinces.clear();
                    provinces.addAll(response.getData());
                    if (provinces != null && provinces.size() > 0) {
                        if (address != null && address.getProjectAddProvince() != null) {
                            province_selector.setText(address.getProjectAddProvinceName());
                            province_selector.setTag(address.getProjectAddProvince());
                            getCity(addressType, address.getProjectAddProvince(), address);
                        }
                    }
                } else if ("2".equals(addressType)) {
                    installProvinces.clear();
                    installProvinces.addAll(response.getData());
                    if (installProvinces != null && installProvinces.size() > 0) {
                        if (address != null && address.getInstlProjectAddProvince() != null) {
                            install_province_selector.setText(address.getInstlProjectAddProvinceName());
                            install_province_selector.setTag(address.getInstlProjectAddProvince());
                            getCity(addressType, address.getInstlProjectAddProvince(), address);
                        }
                    }
                }
            }
        });
    }

    protected void getCity(final String addressType, String ProvinceCode, final InitAddress address) {
        String kind = "GK0002";
        isMstOptionService.selectChildrens(kind, ProvinceCode).compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), false) {
            @Override
            public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                if ("1".equals(addressType)) {
                    cities.clear();
                    cities.addAll(response.getData());
                    if (cities != null && cities.size() > 0) {
                        if (address == null || address.getProjectAddCity() == null) {
                            city_selector.setText(cities.get(0).getText());
                            city_selector.setTag(cities.get(0).getCode());
                            getArea(addressType, cities.get(0).getCode(), address);
                        } else {
                            city_selector.setText(address.getProjectAddCityName());
                            city_selector.setTag(address.getProjectAddCity());
                            getArea(addressType, address.getProjectAddCity(), address);
                        }
                    } else {
                        city_selector.setText("");
                        city_selector.setTag("");
                    }
                } else if ("2".equals(addressType)) {
                    installCities.clear();
                    installCities.addAll(response.getData());
                    if (installCities != null && installCities.size() > 0) {
                        if (address == null || address.getInstlProjectAddCity() == null) {
                            install_city_selector.setText(installCities.get(0).getText());
                            install_city_selector.setTag(installCities.get(0).getCode());
                            getArea(addressType, installCities.get(0).getCode(), address);
                        } else {
                            install_city_selector.setText(address.getInstlProjectAddCityName());
                            install_city_selector.setTag(address.getInstlProjectAddCity());
                            getArea(addressType, address.getInstlProjectAddCity(), address);
                        }
                    } else {
                        install_city_selector.setText("");
                        install_city_selector.setTag("");
                    }
                }

            }
        });
    }

    protected void getArea(final String addressType, String cityCode, final InitAddress address) {
        String kind = "GK0003";
        isMstOptionService.selectChildrens(kind, cityCode).compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), false) {
            @Override
            public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                if ("1".equals(addressType)) {
                    areas.clear();
                    areas.addAll(response.getData());
                    if (areas != null && areas.size() > 0) {
                        if (address == null || address.getProjectAddArea() == null) {
                            area_selector.setText(areas.get(0).getText());
                            area_selector.setTag(areas.get(0).getCode());
                        } else {
                            area_selector.setText(address.getProjectAddAreaName());
                            area_selector.setTag(address.getProjectAddArea());
                        }
                    } else {
                        area_selector.setText("");
                        area_selector.setTag("");
                    }
                } else if ("2".equals(addressType)) {
                    installAreas.clear();
                    installAreas.addAll(response.getData());
                    if (installAreas != null && installAreas.size() > 0) {
                        if (address == null || address.getInstlProjectAddArea() == null) {
                            install_area_selector.setText(installAreas.get(0).getText());
                            install_area_selector.setTag(installAreas.get(0).getCode());
                        } else {
                            install_area_selector.setText(address.getInstlProjectAddAreaName());
                            install_area_selector.setTag(address.getInstlProjectAddArea());
                        }
                    } else {
                        install_area_selector.setText("");
                        install_area_selector.setTag("");
                    }
                }
            }
        });
    }

    protected void dropDown_province(final String addressType, View view) {
        if ("1".equals(addressType)) {
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
                    getCity(addressType, provinces.get(p).getCode(), null);
                    this.dismiss();
                }

                @Override
                public void dismissEvents() {
                    Drawable image = getResources().getDrawable(R.drawable.down);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                    province_selector.setCompoundDrawables(null, null, image, null);
                }

            };
        } else if ("2".equals(addressType)) {
            if (installProvinces == null || installProvinces.size() == 0) {
                ToastUtil.showLong(getResources().getString(R.string.is_no_data));
                return;
            }

            Drawable image = getResources().getDrawable(R.drawable.up);
            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
            install_province_selector.setCompoundDrawables(null, null, image, null);

            DropDownWindow mWindow = new DropDownWindow(getActivity(), view, installProvinces, view.getWidth(), -2) {
                @Override
                public void initEvents(int p) {
                    install_province_selector.setText(installProvinces.get(p).getText());
                    install_province_selector.setTag(installProvinces.get(p).getCode());
                    getCity(addressType, installProvinces.get(p).getCode(), null);
                    this.dismiss();
                }

                @Override
                public void dismissEvents() {
                    Drawable image = getResources().getDrawable(R.drawable.down);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                    install_province_selector.setCompoundDrawables(null, null, image, null);
                }

            };
        }
    }

    protected void dropDown_city(final String addressType, View view) {
        if ("1".equals(addressType)) {
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
                    getArea(addressType, cities.get(p).getCode(), null);
                    this.dismiss();
                }

                @Override
                public void dismissEvents() {
                    Drawable image = getResources().getDrawable(R.drawable.down);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                    city_selector.setCompoundDrawables(null, null, image, null);
                }
            };
        } else if ("2".equals(addressType)) {
            if (installCities == null || installCities.size() == 0) {
                ToastUtil.showLong(getResources().getString(R.string.is_no_data));
                return;
            }
            Drawable image = getResources().getDrawable(R.drawable.up);
            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
            install_city_selector.setCompoundDrawables(null, null, image, null);

            DropDownWindow mWindow = new DropDownWindow(getActivity(), view, installCities, view.getWidth(), -2) {
                @Override
                public void initEvents(int p) {
                    install_city_selector.setText(installCities.get(p).getText());
                    install_city_selector.setTag(installCities.get(p).getCode());
                    getArea(addressType, installCities.get(p).getCode(), null);
                    this.dismiss();
                }

                @Override
                public void dismissEvents() {
                    Drawable image = getResources().getDrawable(R.drawable.down);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                    install_city_selector.setCompoundDrawables(null, null, image, null);
                }
            };
        }
    }

    protected void dropDown_area(final String addressType, View view) {
        if ("1".equals(addressType)) {
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
        } else if ("2".equals(addressType)) {
            if (installAreas == null || installAreas.size() == 0) {
                ToastUtil.showLong(getResources().getString(R.string.is_no_data));
                return;
            }

            Drawable image = getResources().getDrawable(R.drawable.up);
            image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
            install_area_selector.setCompoundDrawables(null, null, image, null);

            DropDownWindow mWindow = new DropDownWindow(getActivity(), view, installAreas, view.getWidth(), -2) {
                @Override
                public void initEvents(int p) {
                    install_area_selector.setText(installAreas.get(p).getText());
                    install_area_selector.setTag(installAreas.get(p).getCode());
                    this.dismiss();
                }

                @Override
                public void dismissEvents() {
                    Drawable image = getResources().getDrawable(R.drawable.down);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                    install_area_selector.setCompoundDrawables(null, null, image, null);
                }
            };
        }

    }

    protected void add() {
        //将页面中已经填写的数据添加到contactList中
        packDataToContactList();
        ElevatorContact contact = new ElevatorContact();
        contactList.add(contact);
        contactAdpter.notifyDataSetChanged();
    }

    protected void minus() {
        if (contactList.size() == 1) {
            ToastUtil.showLong(getResources().getString(R.string.is_delete_contact_msg));
        } else {
            //将页面中已经填写的数据添加到contactList中
            packDataToContactList();
            List<ElevatorContact> newContactList = new ArrayList<>();
            for (int i = 0; i < contactList.size(); i++) {
                boolean flag = contactList.get(i).isCheck();
                if (!flag) {
                    newContactList.add(contactList.get(i));
                }
            }
            if (newContactList.size() == 0) {
                newContactList.add(new ElevatorContact());
            }
            contactList.clear();
            contactList.addAll(newContactList);
            contactAdpter.notifyDataSetChanged();
        }
    }

    protected void packDataToContactList() {
        int itemCount = contactAdpter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            CheckBox checkBox = (CheckBox) contactAdpter.getViewByPosition(i, R.id.cb_is_checkbox);
            EditText nameEditText = (EditText) contactAdpter.getViewByPosition(i, R.id.et_is_contact_name);
            EditText telephoneEditText = (EditText) contactAdpter.getViewByPosition(i, R.id.et_is_contact_telephone);
            EditText postEditText = (EditText) contactAdpter.getViewByPosition(i, R.id.et_is_contact_post);
            boolean flag = checkBox != null ? checkBox.isChecked() : false;
            String name = nameEditText != null ? nameEditText.getText().toString() : "";
            String telephone = telephoneEditText != null ? telephoneEditText.getText().toString() : "";
            String post = postEditText != null ? postEditText.getText().toString() : "";
            contactList.get(i).setCheck(flag);
            contactList.get(i).setName(name);
            contactList.get(i).setTelephone(telephone);
            contactList.get(i).setPost(post);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int i = view.getId();
        if (i == R.id.common_img_close) {
            removeAttach(position);

        } else {
        }
    }

    private void removeAttach(final int position) {
        String msg = getResources().getString(R.string.is_sure_delete_attach_msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.is_reminder));
        builder.setMessage(msg);
        builder.setNegativeButton(getResources().getString(R.string.cancel), null);
        builder.setPositiveButton(getResources().getString(R.string.is_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Attach attach = elevatorAttachList.get(position);
                String attachGuid = attach.getAttachguid();
                if (attachGuid != null) {
                    deleteFileList.add(attachGuid);
                }
                elevatorAttachList.remove(position);
                elevatorAttachAdpter.notifyItemRemoved(position);
                elevatorAttachAdpter.notifyDataSetChanged();
            }
        });
        builder.show();

    }

    class InitAddress {
        private String projectAddProvinceName;
        private String projectAddProvince;
        private String projectAddCity;
        private String projectAddCityName;
        private String projectAddArea;
        private String projectAddAreaName;
        private String instlProjectAddProvince;
        private String instlProjectAddProvinceName;
        private String instlProjectAddCity;
        private String instlProjectAddCityName;
        private String instlProjectAddArea;
        private String instlProjectAddAreaName;

        public String getProjectAddProvince() {
            return projectAddProvince;
        }

        public void setProjectAddProvince(String projectAddProvince) {
            this.projectAddProvince = projectAddProvince;
        }

        public String getProjectAddProvinceName() {
            return projectAddProvinceName;
        }

        public void setProjectAddProvinceName(String projectAddProvinceName) {
            this.projectAddProvinceName = projectAddProvinceName;
        }

        public String getProjectAddCity() {
            return projectAddCity;
        }

        public void setProjectAddCity(String projectAddCity) {
            this.projectAddCity = projectAddCity;
        }

        public String getProjectAddCityName() {
            return projectAddCityName;
        }

        public void setProjectAddCityName(String projectAddCityName) {
            this.projectAddCityName = projectAddCityName;
        }

        public String getProjectAddArea() {
            return projectAddArea;
        }

        public void setProjectAddArea(String projectAddArea) {
            this.projectAddArea = projectAddArea;
        }

        public String getProjectAddAreaName() {
            return projectAddAreaName;
        }

        public void setProjectAddAreaName(String projectAddAreaName) {
            this.projectAddAreaName = projectAddAreaName;
        }

        public String getInstlProjectAddProvince() {
            return instlProjectAddProvince;
        }

        public void setInstlProjectAddProvince(String instlProjectAddProvince) {
            this.instlProjectAddProvince = instlProjectAddProvince;
        }

        public String getInstlProjectAddProvinceName() {
            return instlProjectAddProvinceName;
        }

        public void setInstlProjectAddProvinceName(String instlProjectAddProvinceName) {
            this.instlProjectAddProvinceName = instlProjectAddProvinceName;
        }

        public String getInstlProjectAddCity() {
            return instlProjectAddCity;
        }

        public void setInstlProjectAddCity(String instlProjectAddCity) {
            this.instlProjectAddCity = instlProjectAddCity;
        }

        public String getInstlProjectAddCityName() {
            return instlProjectAddCityName;
        }

        public void setInstlProjectAddCityName(String instlProjectAddCityName) {
            this.instlProjectAddCityName = instlProjectAddCityName;
        }

        public String getInstlProjectAddArea() {
            return instlProjectAddArea;
        }

        public void setInstlProjectAddArea(String instlProjectAddArea) {
            this.instlProjectAddArea = instlProjectAddArea;
        }

        public String getInstlProjectAddAreaName() {
            return instlProjectAddAreaName;
        }

        public void setInstlProjectAddAreaName(String instlProjectAddAreaName) {
            this.instlProjectAddAreaName = instlProjectAddAreaName;
        }
    }
}
