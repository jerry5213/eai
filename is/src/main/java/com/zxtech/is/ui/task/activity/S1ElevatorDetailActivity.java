package com.zxtech.is.ui.task.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.DropDownWindow;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.common.IsMstOptions;
import com.zxtech.is.model.s1.ElevatorContact;
import com.zxtech.is.model.s1.S1Elevator;
import com.zxtech.is.model.s1.S1Request;
import com.zxtech.is.service.task.S1Service;
import com.zxtech.is.ui.workflow.activity.TaskHistoryListActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp600 on 2018/4/30.
 */

public class S1ElevatorDetailActivity extends ElevatorDetailCommonActivity {
    //预计3C日期
    @BindView(R2.id.tv_is_plan3CDate)
    TextView tv_is_plan3CDate;

    //排产量预计收款日期
    @BindView(R2.id.tv_is_moneyS2Date)
    TextView tv_is_moneyS2Date;

    //到货款预计收款日期
    @BindView(R2.id.tv_is_moeny3CDate)
    TextView tv_is_moeny3CDate;

    //井道
    @BindView(R2.id.wellholeItem_selector)
    TextView wellholeItem_selector;
    //告知工地必备条件
    @BindView(R2.id.notifyItem_selector)
    TextView notifyItem_selector;

    //上传的附件
    Map<String, RequestBody> files = new HashMap<>();
    //删除的附件
    List<String> deleteFileList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_s1_elevator_detail;
    }

    public void loadData() {
        S1Elevator elevator = (S1Elevator) getIntent().getSerializableExtra("elevator");
        taskId = elevator.getTaskId();
        procDefKey = getIntent().getStringExtra("procDefKey");
        projectGuid = getIntent().getStringExtra("projectGuid");
        elevatorGuid = elevator.getElevatorguid();
        procInstId = elevator.getProcinstid();

        //联系人
        if (elevator.getContactsList().size() == 0) {
            //初始化时添加一个联系人
            ElevatorContact contact = new ElevatorContact();
            contactList.add(contact);
        } else {
            contactList.addAll(elevator.getContactsList());
        }
        contactAdpter.notifyDataSetChanged();

        //产品附件
        if (elevator.getElevatorAttachList().size() > 0) {
            elevatorAttachList.addAll(elevator.getElevatorAttachList());
            elevatorAttachAdpter.notifyDataSetChanged();
        }

        //预计3C日期
        tv_is_plan3CDate.setText(elevator.getPlan3cdate());
        //排产量预计收款日期
        tv_is_moneyS2Date.setText(elevator.getMoneys2date());
        //到货款预计收款日期
        tv_is_moeny3CDate.setText(elevator.getMoeny3cdate());

        //井道
        if ("1".equals(elevator.getWellholeitem())) {
            wellholeItem_selector.setText(getResources().getString(R.string.is_qualified));
            wellholeItem_selector.setTag(elevator.getWellholeitem());
        } else if ("2".equals(elevator.getWellholeitem())) {
            wellholeItem_selector.setText(getResources().getString(R.string.is_unqualified));
            wellholeItem_selector.setTag(elevator.getWellholeitem());
        }

        //告知工地必备条件
        if ("1".equals(elevator.getNotifyitem())) {
            notifyItem_selector.setText(getResources().getString(R.string.is_notice));
            notifyItem_selector.setTag(elevator.getNotifyitem());
        } else if ("2".equals(elevator.getNotifyitem())) {
            notifyItem_selector.setText(getResources().getString(R.string.is_unnotice));
            notifyItem_selector.setTag(elevator.getNotifyitem());
        }

        //交货地址
        if (elevator.getProjectAddProvince() != null) {
            InitAddress address = new InitAddress();
            address.setProjectAddProvince(elevator.getProjectAddProvince());
            address.setProjectAddProvinceName(elevator.getProjectAddProvinceName());
            address.setProjectAddCity(elevator.getProjectAddCity());
            address.setProjectAddCityName(elevator.getProjectAddCityName());
            address.setProjectAddArea(elevator.getProjectAddArea());
            address.setProjectAddAreaName(elevator.getProjectAddAreaName());
            et_is_other_address.setText(elevator.getProjectAddOther());
            getProvince("1", address);
        } else {
            getProvince("1", null);
        }

        //安装地址
        if (elevator.getInstlProjectAddProvince() != null) {
            InitAddress address = new InitAddress();
            address.setInstlProjectAddProvince(elevator.getInstlProjectAddProvince());
            address.setInstlProjectAddProvinceName(elevator.getInstlProjectAddProvinceName());
            address.setInstlProjectAddCity(elevator.getInstlProjectAddCity());
            address.setInstlProjectAddCityName(elevator.getInstlProjectAddCityName());
            address.setInstlProjectAddArea(elevator.getInstlProjectAddArea());
            address.setInstlProjectAddAreaName(elevator.getInstlProjectAddAreaName());
            getProvince("2", address);
            install_et_is_other_address.setText(elevator.getInstlProjectAddOther());
        } else {
            getProvince("2", null);
        }
    }

    @OnClick({R2.id.tv_is_plan3CDate,
            R2.id.tv_is_moneyS2Date,
            R2.id.tv_is_moeny3CDate,
            R2.id.province_selector,
            R2.id.city_selector,
            R2.id.area_selector,
            R2.id.install_province_selector,
            R2.id.install_city_selector,
            R2.id.install_area_selector,
            R2.id.wellholeItem_selector,
            R2.id.notifyItem_selector,
            R2.id.iv_is_add,
            R2.id.iv_is_minus,
            R2.id.is_abolished,
            R2.id.is_confirm,
            R2.id.iv_is_add_img,
            R2.id.tv_proc_info})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_is_plan3CDate) {
            openTime(tv_is_plan3CDate);

        } else if (i == R.id.tv_is_moneyS2Date) {
            openTime(tv_is_moneyS2Date);

        } else if (i == R.id.tv_is_moeny3CDate) {
            openTime(tv_is_moeny3CDate);

        } else if (i == R.id.wellholeItem_selector) {
            dropDown_wellholeItem(view);

        } else if (i == R.id.notifyItem_selector) {
            dropDown_notifyItem(view);

        } else if (i == R.id.province_selector) {
            dropDown_province("1", view);

        } else if (i == R.id.city_selector) {
            dropDown_city("1", view);

        } else if (i == R.id.area_selector) {
            dropDown_area("1", view);

        } else if (i == R.id.install_province_selector) {
            dropDown_province("2", view);

        } else if (i == R.id.install_city_selector) {
            dropDown_city("2", view);

        } else if (i == R.id.install_area_selector) {
            dropDown_area("2", view);

        } else if (i == R.id.iv_is_add) {
            add();

        } else if (i == R.id.iv_is_minus) {
            minus();

        } else if (i == R.id.is_abolished) {
            finish();

        } else if (i == R.id.is_confirm) {
            save();

        } else if (i == R.id.iv_is_add_img) {
            addAttach();

        } else if (i == R.id.tv_proc_info) {
            Intent intent = new Intent(mContext, TaskHistoryListActivity.class);
            intent.putExtra("procInstId", procInstId);
            startActivity(intent);
        }
    }

    protected void save() {
        //将页面中已经填写的数据添加到contactList中
        packDataToContactList();
        if (checkData()) {
            String filename = "";
            String filepath = "";
            String attachGuid = "";
            for (int i = 0; i < elevatorAttachList.size(); i++) {
                attachGuid = elevatorAttachList.get(i).getAttachguid();
                filename = elevatorAttachList.get(i).getFilename();
                filepath = elevatorAttachList.get(i).getFilepath();
                if (attachGuid == null) {
                    //转换
                    RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), new File(filepath));
                    files.put("file\";filename=\"" + filename, file);
                }
            }
            S1Request s1Request = packData();
            String param = GsonUtils.toJson(s1Request, false);
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
            S1Service s1Service = HttpFactory.getService(S1Service.class);
            s1Service.savePlanS1(requestBody, files).compose(RxHelper.<BaseResponse<Boolean>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Boolean>>(getActivity(), true) {
                @Override
                public void onSuccess(BaseResponse<Boolean> response) {
                    boolean flag = response.getData();
                    if (flag) {
                        Intent intent = getIntent();
                        intent.putExtra("finish_taskId", taskId);
                        ToastUtil.showLong(getResources().getString(R.string.is_successfully_save));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }

    }

    //校验填写的数据
    protected boolean checkData() {
        if ("".equals(tv_is_plan3CDate.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_plan3CDate_null_msg));
            return false;
        }
        if ("".equals(tv_is_moneyS2Date.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_moneyS2Date_null_msg));
            return false;
        }
        if ("".equals(tv_is_moeny3CDate.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_moeny3CDate_null_msg));
            return false;
        }
        if (wellholeItem_selector.getTag() == null || !"1".equals(wellholeItem_selector.getTag().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_wellholeItem_not_qualified_msg));
            return false;
        }
        if (notifyItem_selector.getTag() == null || !"1".equals(notifyItem_selector.getTag().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_notifyItem_not_notice_msg));
            return false;
        }

        if (elevatorAttachList.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_elevator_attach_null_msg));
            return false;
        }

        if (province_selector.getTag() == null) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_delivery_province_msg));
            return false;
        }

        if (city_selector.getTag() == null) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_delivery_city_msg));
            return false;
        }

        if (area_selector.getTag() == null) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_delivery_area_msg));
            return false;
        }

        String otherAddress = et_is_other_address.getText().toString();
        if (!"".equals(otherAddress) && otherAddress.length() > 100) {
            ToastUtil.showLong(getResources().getString(R.string.is_address_max_length_msg));
            return false;
        }

        if (install_province_selector.getTag() == null) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_install_province_msg));
            return false;
        }

        if (install_city_selector.getTag() == null) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_install_city_msg));
            return false;
        }

        if (install_area_selector.getTag() == null) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_install_area_msg));
            return false;
        }

        String installOtherAddress = install_et_is_other_address.getText().toString();
        if (!"".equals(installOtherAddress) && installOtherAddress.length() > 100) {
            ToastUtil.showLong(getResources().getString(R.string.is_install_address_max_length_msg));
            return false;
        }


        for (int i = 0; i < contactList.size(); i++) {
            String name = contactList.get(i).getName();
            if (name == null || "".equals(name.trim())) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_contact_name_msg));
                return false;
            }
            if (name.length() > 10) {
                ToastUtil.showLong(getResources().getString(R.string.is_name_max_length_msg));
                return false;
            }
            String telephone = contactList.get(i).getTelephone();
            if (telephone == null || "".equals(telephone.trim())) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_contact_telephone_msg));
                return false;
            }
            if (telephone.length() > 12) {
                ToastUtil.showLong(getResources().getString(R.string.is_telephone_max_length_msg));
                return false;
            }
            String post = contactList.get(i).getPost();
            if (post == null || "".equals(post.trim())) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_contact_post_msg));
                return false;
            }
            if (telephone.length() > 50) {
                ToastUtil.showLong(getResources().getString(R.string.is_post_max_length_msg));
                return false;
            }
        }
        if (contactList.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_contact_null_msg));
            return false;
        }
        return true;
    }

    protected S1Request packData() {
        String plan3CDate = tv_is_plan3CDate.getText().toString();
        String moneyS2Date = tv_is_moneyS2Date.getText().toString();
        String moeny3CDate = tv_is_moeny3CDate.getText().toString();
        String wellholeItem = wellholeItem_selector.getTag().toString();
        String notifyItem = notifyItem_selector.getTag().toString();
        String province = province_selector.getTag().toString();
        String city = city_selector.getTag().toString();
        String area = area_selector.getTag().toString();
        String otherAddress = et_is_other_address.getText().toString();
        String installProvince = install_province_selector.getTag().toString();
        String installCity = install_city_selector.getTag().toString();
        String installArea = install_area_selector.getTag().toString();
        String installOtherAddress = install_et_is_other_address.getText().toString();

        S1Elevator s1Elevator = new S1Elevator();
        s1Elevator.setElevatorguid(elevatorGuid);
        s1Elevator.setProcinstid(procInstId);
        s1Elevator.setPlan3cdate(plan3CDate);
        s1Elevator.setMoneys2date(moneyS2Date);
        s1Elevator.setMoeny3cdate(moeny3CDate);
        s1Elevator.setWellholeitem(wellholeItem);
        s1Elevator.setNotifyitem(notifyItem);
        s1Elevator.setProjectAddProvince(province);
        s1Elevator.setProjectAddCity(city);
        s1Elevator.setProjectAddArea(area);
        s1Elevator.setProjectAddOther(otherAddress);
        s1Elevator.setInstlProjectAddProvince(installProvince);
        s1Elevator.setInstlProjectAddCity(installCity);
        s1Elevator.setInstlProjectAddArea(installArea);
        s1Elevator.setInstlProjectAddOther(installOtherAddress);
        s1Elevator.setContactsList(contactList);

        //删除的附件
        s1Elevator.setDeleteFileList(deleteFileList);

        S1Request s1Request = new S1Request();
        s1Request.setS1Elevator(s1Elevator);
        s1Request.setProjectGuid(projectGuid);
        s1Request.setTaskId(taskId);
        s1Request.setProcDefKey(procDefKey);
        return s1Request;
    }

    protected void dropDown_wellholeItem(View view) {
        final List<IsMstOptions> list = new ArrayList<>();
        IsMstOptions options = new IsMstOptions();
        options.setCode("1");
        options.setText(getResources().getString(R.string.is_qualified));
        list.add(options);
        options = new IsMstOptions();
        options.setCode("2");
        options.setText(getResources().getString(R.string.is_unqualified));
        list.add(options);

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        wellholeItem_selector.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(getActivity(), view, list, view.getWidth(), -2) {
            @Override
            public void initEvents(int p) {
                wellholeItem_selector.setText(list.get(p).getText());
                wellholeItem_selector.setTag(list.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                wellholeItem_selector.setCompoundDrawables(null, null, image, null);
            }

        };
    }

    protected void dropDown_notifyItem(View view) {
        final List<IsMstOptions> list = new ArrayList<>();
        IsMstOptions options = new IsMstOptions();
        options.setCode("1");
        options.setText(getResources().getString(R.string.is_notice));
        list.add(options);
        options = new IsMstOptions();
        options.setCode("2");
        options.setText(getResources().getString(R.string.is_unnotice));
        list.add(options);

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        notifyItem_selector.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(getActivity(), view, list, view.getWidth(), -2) {
            @Override
            public void initEvents(int p) {
                notifyItem_selector.setText(list.get(p).getText());
                notifyItem_selector.setTag(list.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                notifyItem_selector.setCompoundDrawables(null, null, image, null);
            }

        };
    }

}
