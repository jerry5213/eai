package com.zxtech.is.ui.smt.activity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.widget.DropDownWindow;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.common.IsMstOptions;
import com.zxtech.is.model.project.PeFeDropDown;
import com.zxtech.is.model.project.ProductInformation;
import com.zxtech.is.model.smt.IsSmtInstallationElevator;
import com.zxtech.is.service.common.IsMstOptionService;
import com.zxtech.is.service.smt.ProjectInstaWayRecordsConfirmService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp661 on 2018/4/30.
 */

public class ProjectInstaWayRecordsConfirmActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private List<PeFeDropDown> isScaffoldingList = new ArrayList<>();
    private List<PeFeDropDown> isInstaWayList = new ArrayList<>();
    private List<IsSmtInstallationElevator> instaWayConfirmList = new ArrayList<>();
    private List<ProductInformation> productInformationChoose = new ArrayList<>();
    private ProductInformation productInformation;

    @BindView(R2.id.is_insta_way)
    TextView isInstaWay;
    @BindView(R2.id.is_scaffolding)
    TextView isScaffolding;
    @BindView(R2.id.is_selected_elevator)
    TextView mISE;
    @BindView(R2.id.is_team_6)
    LinearLayout isTeam6;
    @BindView(R2.id.is_cancel)
    Button isCancel;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.insta_way_records;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);
        //安装方式报备checkBox选择的电梯
        productInformationChoose = (List<ProductInformation>) getIntent().getSerializableExtra("productIC");
        if (productInformationChoose != null) {
            String str = "";
            int num = productInformationChoose.size();
            if (num > 0) {
                instaWayConfirmList.clear();
                for (int i = 0; i < num; i++) {
                    IsSmtInstallationElevator isie = new IsSmtInstallationElevator();
                    str += productInformationChoose.get(i).getArktx();
                    isie.setElevatorguid(productInformationChoose.get(i).getElevatorGuid());
                    instaWayConfirmList.add(isie);
                    if (i != num - 1) {
                        str += ",";
                    }
                }
            }
            mISE.setText(str);
            //有无脚手架
            PeFeDropDown a = new PeFeDropDown();
            a.setId("0");
            a.setText(mContext.getResources().getString(R.string.mrl));
            PeFeDropDown b = new PeFeDropDown();
            b.setId("1");
            b.setText(mContext.getResources().getString(R.string.mr));
            isScaffoldingList.add(a);
            isScaffoldingList.add(b);
            //安装方式 下拉框内容查询
            getInstaDropDown();
        }

        //安装方式报备回显 点击的电梯
        productInformation = (ProductInformation) getIntent().getSerializableExtra("productInformation");
        if (productInformation != null) {
            isTeam6.setVisibility(View.GONE);

            //判断是否已报备
            if (productInformation.getSmtType() == 2) {
                isCancel.setVisibility(View.VISIBLE);
            }
            mISE.setText(productInformation.getArktx());
            //isInstaWay.setBackground(null);
            isInstaWay.setCompoundDrawables(null, null, null, null);
            isInstaWay.setText(productInformation.getSmtinstallType());
            isInstaWay.setEnabled(false);
            //isScaffolding.setBackground(null);
            isScaffolding.setCompoundDrawables(null, null, null, null);
            isScaffolding.setEnabled(false);
            if ("0".equals(productInformation.getScaffold())) {
                isScaffolding.setText(mContext.getResources().getString(R.string.mrl));
            } else {
                isScaffolding.setText(mContext.getResources().getString(R.string.mr));
            }
        }

    }

    @OnClick({R2.id.is_insta_way, R2.id.is_scaffolding, R2.id.is_abolished, R2.id.is_confirm, R2.id.is_cancel})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.is_insta_way) {
            dropDownInstaWay(view);

        } else if (i == R.id.is_scaffolding) {
            dropDownScaffolding(view);

        } else if (i == R.id.is_abolished) {
            finish();

        } else if (i == R.id.is_confirm) {
            isInstaConfirm();

        } else if (i == R.id.is_cancel) {
            installWayCancel(productInformation);

        }
    }

    //下拉列表框  有无脚手架
    protected void dropDownScaffolding(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        isScaffolding.setCompoundDrawables(null, null, image, null);
        DropDownWindow mWindow = new DropDownWindow(mContext, view, isScaffoldingList, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                String sub = isScaffolding.getText().toString();
                String selectValue = isScaffoldingList.get(p).getText();
                if (sub.equals(selectValue)) {
                    return;
                }
                isScaffolding.setText(selectValue);
                isScaffolding.setTag(isScaffoldingList.get(p).getId());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                isScaffolding.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //安装方式 下拉列表框
    protected void dropDownInstaWay(View view) {

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        isInstaWay.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, isInstaWayList, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                String sub = isInstaWay.getText().toString();
                String selectValue = isInstaWayList.get(p).getText();
                if (sub.equals(selectValue)) {
                    return;
                }
                isInstaWay.setText(selectValue);
                isInstaWay.setTag(isInstaWayList.get(p).getId());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                isInstaWay.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //作废安装方式报备
    private void installWayCancel(ProductInformation productInformation) {
        ProjectInstaWayRecordsConfirmService piwrcService = HttpFactory.getService(ProjectInstaWayRecordsConfirmService.class);
        piwrcService.installWayCancel(productInformation.getSmtprocInstId())
                .compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper())
                .subscribe(new com.zxtech.is.common.net.DefaultObserver<BaseResponse<Integer>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<Integer> response) {
                        if (response.getData() > 0) {
                            showAlertDialog(R.string.is_success, true);
                        } else {
                            showAlertDialog(R.string.is_failed, true);
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    //获取 安装方式下拉框
    private void getInstaDropDown() {
        IsMstOptionService imoService = HttpFactory.getService(IsMstOptionService.class);
        imoService.selectParents("GK0011", "")
                .compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper())
                .subscribe(new com.zxtech.is.common.net.DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                        isInstaWayList.clear();
                        int sum = response.getData().size();
                        for (int i = 0; i < sum; i++) {
                            PeFeDropDown dropDown = new PeFeDropDown();
                            dropDown.setId(response.getData().get(i).getCode());
                            dropDown.setText(response.getData().get(i).getText());
                            isInstaWayList.add(dropDown);
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }


    //安装方式报备确认
    private void isInstaConfirm() {
        //验证是否都选择人
        if (isInstaWay.getText() == "" || isScaffolding.getText() == "") {
            showAlertDialog(R.string.is_install_msg1, false);
        } else {
            //保存安装方式报备信息
            for (int i = 0; i < instaWayConfirmList.size(); i++) {
                IsSmtInstallationElevator ise = instaWayConfirmList.get(i);
                ise.setInstalltype(String.valueOf(isInstaWay.getTag()));
                ise.setScaffold(String.valueOf(isScaffolding.getTag()));
            }
            //调用保存方法
            saveInstaWayRecords(instaWayConfirmList);
        }
    }

    //安装方式报备 保存
    private void saveInstaWayRecords(List<IsSmtInstallationElevator> instaWayConfirmList) {
        String param = GsonUtils.toJson(instaWayConfirmList, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        ProjectInstaWayRecordsConfirmService prwrcService = HttpFactory.getService(ProjectInstaWayRecordsConfirmService.class);
        prwrcService.startSMT(requestBody).compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper())
                .subscribe(new com.zxtech.is.common.net.DefaultObserver<BaseResponse<Integer>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<Integer> response) {
                        if (response.getData() > 0) {
                            showAlertDialog(R.string.is_reported_success, true);
                        } else {
                            showAlertDialog(R.string.is_reported_fail, true);
                        }
                    }

                    @Override
                    public void onFail() {
                        showAlertDialog(R.string.is_reported_fail, true);
                    }

                });
    }

    private void showAlertDialog(int msg, final boolean flag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.is_reminder);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.is_sure, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (flag) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        builder.show();
    }
}