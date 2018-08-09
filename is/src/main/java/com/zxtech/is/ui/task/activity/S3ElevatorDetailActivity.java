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
import com.zxtech.is.model.s3.S3Elevator;
import com.zxtech.is.model.s3.S3Request;
import com.zxtech.is.service.task.S3Service;
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
 * Created by syp600 on 2018/5/7.
 */

public class S3ElevatorDetailActivity extends ElevatorDetailCommonActivity {
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
    //入口通道
    @BindView(R2.id.enterItem_selector)
    TextView enterItem_selector;
    //堆场
    @BindView(R2.id.yardItem_selector)
    TextView yardItem_selector;
    //安装队伍
    @BindView(R2.id.installTemItem_selector)
    TextView installTemItem_selector;
    //库房
    @BindView(R2.id.storehouseItem_selector)
    TextView storehouseItem_selector;
    //施工条件
    @BindView(R2.id.constructionItem_selector)
    TextView constructionItem_selector;

    //上传的附件
    Map<String, RequestBody> files = new HashMap<>();
    //删除的附件
    List<String> deleteFileList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_s3_elevator_detail;
    }

    public void loadData() {
        S3Elevator elevator = (S3Elevator) getIntent().getSerializableExtra("elevator");
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
        String plan3CDate = elevator.getPlan3CDateS3() == null ? elevator.getPlan3cdate() : elevator.getPlan3CDateS3();
        String moneyS2Date = elevator.getMoneyS2DateS3() == null ? elevator.getMoneys2date() : elevator.getMoneyS2DateS3();
        String moeny3CDate = elevator.getMoeny3CDateS3() == null ? elevator.getMoeny3cdate() : elevator.getMoeny3CDateS3();
        tv_is_plan3CDate.setText(plan3CDate);
        //排产量预计收款日期
        tv_is_moneyS2Date.setText(moneyS2Date);
        //到货款预计收款日期
        tv_is_moeny3CDate.setText(moeny3CDate);

        //井道
        if ("1".equals(elevator.getWellholeItemS3())) {
            wellholeItem_selector.setText(getResources().getString(R.string.is_qualified));
            wellholeItem_selector.setTag(elevator.getWellholeItemS3());
        } else if ("2".equals(elevator.getWellholeItemS3())) {
            wellholeItem_selector.setText(getResources().getString(R.string.is_unqualified));
            wellholeItem_selector.setTag(elevator.getWellholeItemS3());
        }

        //入口通道
        if ("1".equals(elevator.getEnterItemS3())) {
            enterItem_selector.setText(getResources().getString(R.string.is_impl));
            enterItem_selector.setTag(elevator.getEnterItemS3());
        } else if ("0".equals(elevator.getEnterItemS3())) {
            enterItem_selector.setText(getResources().getString(R.string.is_unimpl));
            enterItem_selector.setTag(elevator.getEnterItemS3());
        }

        //堆场
        if ("1".equals(elevator.getYardItemS3())) {
            yardItem_selector.setText(getResources().getString(R.string.is_impl));
            yardItem_selector.setTag(elevator.getYardItemS3());
        } else if ("0".equals(elevator.getYardItemS3())) {
            yardItem_selector.setText(getResources().getString(R.string.is_unimpl));
            yardItem_selector.setTag(elevator.getYardItemS3());
        }

        //安装队伍
        if ("1".equals(elevator.getInstallTemItemS3())) {
            installTemItem_selector.setText(getResources().getString(R.string.is_impl));
            installTemItem_selector.setTag(elevator.getInstallTemItemS3());
        } else if ("0".equals(elevator.getInstallTemItemS3())) {
            installTemItem_selector.setText(getResources().getString(R.string.is_unimpl));
            installTemItem_selector.setTag(elevator.getInstallTemItemS3());
        }

        //库房
        if ("1".equals(elevator.getStorehouseItemS3())) {
            storehouseItem_selector.setText(getResources().getString(R.string.is_impl));
            storehouseItem_selector.setTag(elevator.getStorehouseItemS3());
        } else if ("0".equals(elevator.getStorehouseItemS3())) {
            storehouseItem_selector.setText(getResources().getString(R.string.is_unimpl));
            storehouseItem_selector.setTag(elevator.getStorehouseItemS3());
        }

        //施工条件
        if ("1".equals(elevator.getConstructionItemS3())) {
            constructionItem_selector.setText(getResources().getString(R.string.is_impl));
            constructionItem_selector.setTag(elevator.getConstructionItemS3());
        } else if ("0".equals(elevator.getConstructionItemS3())) {
            constructionItem_selector.setText(getResources().getString(R.string.is_unimpl));
            constructionItem_selector.setTag(elevator.getConstructionItemS3());
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
            R2.id.enterItem_selector,
            R2.id.yardItem_selector,
            R2.id.installTemItem_selector,
            R2.id.province_selector,
            R2.id.city_selector,
            R2.id.area_selector,
            R2.id.install_province_selector,
            R2.id.install_city_selector,
            R2.id.install_area_selector,
            R2.id.wellholeItem_selector,
            R2.id.storehouseItem_selector,
            R2.id.constructionItem_selector,
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

        } else if (i == R.id.enterItem_selector) {
            dropDown(view, enterItem_selector);

        } else if (i == R.id.yardItem_selector) {
            dropDown(view, yardItem_selector);

        } else if (i == R.id.installTemItem_selector) {
            dropDown(view, installTemItem_selector);

        } else if (i == R.id.storehouseItem_selector) {
            dropDown(view, storehouseItem_selector);

        } else if (i == R.id.constructionItem_selector) {
            dropDown(view, constructionItem_selector);

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
            S3Request s3Request = packData();
            String param = GsonUtils.toJson(s3Request, false);
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
            S3Service s3Service = HttpFactory.getService(S3Service.class);
            s3Service.savePlanS3(requestBody, files).compose(RxHelper.<BaseResponse<Boolean>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Boolean>>(getActivity(), true) {
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
        if (enterItem_selector.getTag() == null || !"1".equals(enterItem_selector.getTag().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_enterItem_msg));
            return false;
        }
        if (yardItem_selector.getTag() == null || !"1".equals(yardItem_selector.getTag().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_yardItem_msg));
            return false;
        }
        if (installTemItem_selector.getTag() == null || !"1".equals(installTemItem_selector.getTag().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_installTemItem_msg));
            return false;
        }
        if (storehouseItem_selector.getTag() == null || !"1".equals(storehouseItem_selector.getTag().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_storehouseItem_msg));
            return false;
        }
        if (constructionItem_selector.getTag() == null || !"1".equals(constructionItem_selector.getTag().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_constructionItem_msg));
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

    protected S3Request packData() {
        String plan3CDate = tv_is_plan3CDate.getText().toString();
        String moneyS2Date = tv_is_moneyS2Date.getText().toString();
        String moeny3CDate = tv_is_moeny3CDate.getText().toString();
        String wellholeItem = wellholeItem_selector.getTag().toString();
        String enterItem = enterItem_selector.getTag().toString();
        String yardItem = yardItem_selector.getTag().toString();
        String installTemItem = installTemItem_selector.getTag().toString();
        String storehouseItem = storehouseItem_selector.getTag().toString();
        String constructionItem = constructionItem_selector.getTag().toString();
        String province = province_selector.getTag().toString();
        String city = city_selector.getTag().toString();
        String area = area_selector.getTag().toString();
        String otherAddress = et_is_other_address.getText().toString();
        String installProvince = install_province_selector.getTag().toString();
        String installCity = install_city_selector.getTag().toString();
        String installArea = install_area_selector.getTag().toString();
        String installOtherAddress = install_et_is_other_address.getText().toString();

        S3Elevator s3Elevator = new S3Elevator();
        s3Elevator.setElevatorguid(elevatorGuid);
        s3Elevator.setProcinstid(procInstId);
        s3Elevator.setPlan3cdate(plan3CDate);
        s3Elevator.setMoneys2date(moneyS2Date);
        s3Elevator.setMoeny3cdate(moeny3CDate);
        s3Elevator.setWellholeitem(wellholeItem);
        s3Elevator.setEnteritem(enterItem);
        s3Elevator.setYarditem(yardItem);
        s3Elevator.setInstalltemitem(installTemItem);
        s3Elevator.setStorehouseitem(storehouseItem);
        s3Elevator.setConstructionitem(constructionItem);
        s3Elevator.setProjectAddProvince(province);
        s3Elevator.setProjectAddCity(city);
        s3Elevator.setProjectAddArea(area);
        s3Elevator.setProjectAddOther(otherAddress);
        s3Elevator.setInstlProjectAddProvince(installProvince);
        s3Elevator.setInstlProjectAddCity(installCity);
        s3Elevator.setInstlProjectAddArea(installArea);
        s3Elevator.setInstlProjectAddOther(installOtherAddress);
        s3Elevator.setContactsList(contactList);

        //删除的附件
        s3Elevator.setDeleteFileList(deleteFileList);

        S3Request s3Request = new S3Request();
        s3Request.setS3Elevator(s3Elevator);
        s3Request.setProjectGuid(projectGuid);
        s3Request.setTaskId(taskId);
        s3Request.setProcDefKey(procDefKey);
        return s3Request;
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

    protected void dropDown(View view, final TextView textView) {
        final List<IsMstOptions> list = new ArrayList<>();
        IsMstOptions options = new IsMstOptions();
        options.setCode("1");
        options.setText(getResources().getString(R.string.is_impl));
        list.add(options);
        options = new IsMstOptions();
        options.setCode("0");
        options.setText(getResources().getString(R.string.is_unimpl));
        list.add(options);

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        textView.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(getActivity(), view, list, view.getWidth(), -2) {
            @Override
            public void initEvents(int p) {
                textView.setText(list.get(p).getText());
                textView.setTag(list.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                textView.setCompoundDrawables(null, null, image, null);
            }

        };
    }


}
