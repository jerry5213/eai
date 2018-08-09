package com.zxtech.is.ui.project.activity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.DefaultObserver;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.DropDownWindow;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.project.PeFeDropDown;
import com.zxtech.is.model.project.ProductInformation;
import com.zxtech.is.model.project.ProjectIteamAssignedCommit;
import com.zxtech.is.service.project.ProjectItemAssignedService;
import com.zxtech.is.ui.project.adpter.ProjectManageAdpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp661 on 2018/4/24.
 */

public class ProjectItemAssignedConfirmActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private ProjectIteamAssignedCommit piac = new ProjectIteamAssignedCommit();
    private List<PeFeDropDown> peList = new ArrayList<>();
    private List<PeFeDropDown> feList = new ArrayList<>();
    private List<PeFeDropDown> teList = new ArrayList<>();
    private List<String> elevatorGuidList = new ArrayList<>();
    private List<String> procInstIdList = new ArrayList<>();
    private List<String> taskIdList = new ArrayList<>();
    private String taskCheck = "";
    private List<ProductInformation> productInformationChoose = new ArrayList<>();
    private String projectId = "";
    @BindView(R2.id.is_pe)
    TextView mPESubSystem;
    @BindView(R2.id.is_fe)
    TextView mFESubSystem;
    @BindView(R2.id.is_te)
    TextView mTESubSystem;
    @BindView(R2.id.is_selected_elevator)
    TextView mISE;
    @BindView(R2.id.is_team_6)
    LinearLayout isTeam6;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.project_item_assigned;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);


        //项目团队分配checkBox选择的电梯
        productInformationChoose = (List<ProductInformation>) getIntent()
                .getSerializableExtra("productIC");
        if (productInformationChoose != null) {
            //项目ID
            projectId = (String) getIntent()
                    .getSerializableExtra("projectId");
            int num = productInformationChoose.size();
            String str = "";
            boolean showDropDown = false;
            for (int i = 0; i < num; i++) {
                if (!showDropDown) {
                    //TODO pe fe te 初始化
                    if (productInformationChoose.get(i).getPeUserName() != null) {//都会有值
                        showDropDown = true;
                        mPESubSystem.setText(productInformationChoose.get(i).getPeUserName());
                        mPESubSystem.setTag(productInformationChoose.get(i).getPeAssignee());
                        mFESubSystem.setText(productInformationChoose.get(i).getFeUserName());
                        mFESubSystem.setTag(productInformationChoose.get(i).getFeAssignee());
                        mTESubSystem.setText(productInformationChoose.get(i).getTeUserName());
                        mTESubSystem.setTag(productInformationChoose.get(i).getTeAssignee());
                    }
                }
                //梯号显示拼接
                str += productInformationChoose.get(i).getArktx();
                elevatorGuidList.add(productInformationChoose.get(i).getElevatorGuid());
                procInstIdList.add(productInformationChoose.get(i).getProcInstId());
                taskIdList.add(productInformationChoose.get(i).getTaskId());
                if (i != num - 1) {
                    str += ",";
                }
                if (taskCheck == "") {
                    taskCheck = productInformationChoose.get(i).getTaskCheck();
                }
            }
            mISE.setText(str);

            //pe,fe 下拉框内容查询
            getPeFe();
        }


        //团队分配 人员回显
        ProductInformation productInformation = (ProductInformation) getIntent()
                .getSerializableExtra("productInformation");
        if (productInformation != null) {

            isTeam6.setVisibility(View.GONE);

            mISE.setText(productInformation.getArktx());

            //mPESubSystem.setBackground(null);
            mPESubSystem.setCompoundDrawables(null, null, null, null);
            mPESubSystem.setText(productInformation.getPeUserName());
            mPESubSystem.setEnabled(false);

            //mFESubSystem.setBackground(null);
            mFESubSystem.setCompoundDrawables(null, null, null, null);
            mFESubSystem.setText(productInformation.getFeUserName());
            mFESubSystem.setEnabled(false);

            //mTESubSystem.setBackground(null);
            mTESubSystem.setCompoundDrawables(null, null, null, null);
            mTESubSystem.setText(productInformation.getTeUserName());
            mTESubSystem.setEnabled(false);


        }

    }

    @OnClick({R2.id.is_pe, R2.id.is_fe, R2.id.is_te, R2.id.is_abolished, R2.id.is_confirm})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.is_pe) {
            dropDownPE(view);

        } else if (i == R.id.is_fe) {
            dropDownFE(view);

        } else if (i == R.id.is_te) {
            dropDownTE(view);

        } else if (i == R.id.is_abolished) {
            finish();

        } else if (i == R.id.is_confirm) {
            isTeamConfirm();

        }
    }

    //下拉列表框 PE
    protected void dropDownPE(View view) {
        if (peList == null || peList.size() == 0) {
            ToastUtil.showLong(mContext.getResources().getString(R.string.is_no_data));
            return;
        }
        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mPESubSystem.setCompoundDrawables(null, null, image, null);
        DropDownWindow mWindow = new DropDownWindow(mContext, view, peList, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {
                String sub = mPESubSystem.getText().toString();
                String selectValue = peList.get(p).getText();
                if (sub.equals(selectValue)) {
                    return;
                }
                mPESubSystem.setText(selectValue);
                mPESubSystem.setTag(peList.get(p).getId());
                //FE 下拉框逻辑
                boolean checkAdd = false;
                //移除FE中PE
                int len = feList.size();
                for (int i = 0; i < len; i++) {
                    if ("pe".equals(feList.get(i).getFlag())) {
                        feList.remove(i);
                    }
                }
                //判断所选PE 是否存在于dataFe 中
                len = feList.size();
                for (int i = 0; i < len; i++) {
                    if (peList.get(p).getId().equals(feList.get(i).getId())) {
                        checkAdd = true;
                    }
                }
                //FE 已选择了其他PE 将FE选项换成新选择的PE
                String m = String.valueOf(mFESubSystem.getText());
                if (mFESubSystem.getText() != "") {
                    boolean flag = false;
                    for (int i = 0; i < feList.size(); i++) {
                        if (mFESubSystem.getText().equals(feList.get(i).getText())) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        mFESubSystem.setText(selectValue);
                        mFESubSystem.setTag(peList.get(p).getId());
                    }
                }
                //不存在加像FE中添加
                if (!checkAdd) {
                    PeFeDropDown addFE = new PeFeDropDown();
                    addFE.setId(peList.get(p).getId());
                    addFE.setText(peList.get(p).getText());
                    addFE.setFlag(peList.get(p).getFlag());
                    feList.add(addFE);
                }

                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mPESubSystem.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //下拉列表框 FE
    protected void dropDownFE(View view) {
        if (feList == null || feList.size() == 0) {
            ToastUtil.showLong(mContext.getResources().getString(R.string.is_no_data));
            return;
        }
        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mFESubSystem.setCompoundDrawables(null, null, image, null);
        DropDownWindow mWindow = new DropDownWindow(mContext, view, feList, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {
                String sub = mFESubSystem.getText().toString();
                String selectValue = feList.get(p).getText();
                if (sub.equals(selectValue)) {
                    return;
                }
                mFESubSystem.setText(selectValue);
                mFESubSystem.setTag(feList.get(p).getId());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mFESubSystem.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //下拉列表框 FE
    protected void dropDownTE(View view) {
        if (teList == null || teList.size() == 0) {
            ToastUtil.showLong(mContext.getResources().getString(R.string.is_no_data));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mTESubSystem.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, teList, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                String sub = mTESubSystem.getText().toString();
                String selectValue = feList.get(p).getText();
                if (sub.equals(selectValue)) {
                    return;
                }
                mTESubSystem.setText(selectValue);
                mTESubSystem.setTag(teList.get(p).getId());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mTESubSystem.setCompoundDrawables(null, null, image, null);
            }
        };
    }


    //获取下拉框PE FE
    private void getPeFe() {
        ProjectItemAssignedService pIAService = HttpFactory.getService(ProjectItemAssignedService.class);
        pIAService.getPeFe(projectId)
                .compose(RxHelper.<com.zxtech.is.common.net.BaseResponse<Map<String, List<PeFeDropDown>>>>rxSchedulerHelper())
                .subscribe(new com.zxtech.is.common.net.DefaultObserver<com.zxtech.is.common.net.BaseResponse<Map<String, List<PeFeDropDown>>>>(getActivity(), true) {

                    @Override
                    public void onSuccess(com.zxtech.is.common.net.BaseResponse<Map<String, List<PeFeDropDown>>> response) {
                        peList = response.getData().get("pe");
                        feList = response.getData().get("fe");
                        teList = response.getData().get("fe");
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    //团队分配确认
    private void isTeamConfirm() {

        //验证是否都选择人
        if (mPESubSystem.getText() == "" || mFESubSystem.getText() == "" || mTESubSystem.getText() == "") {
            showAlertDialog(R.string.is_team_assigned_msg5, false);
        } else {
            //保存团队分配信息
            piac.setPeAssigneeGuid(String.valueOf(mPESubSystem.getTag()));
            piac.setFeAssigneeGuid(String.valueOf(mFESubSystem.getTag()));
            piac.setTeAssigneeGuid(String.valueOf(mTESubSystem.getTag()));
            piac.setTaskCheck(taskCheck);
            piac.setElevatorGuidList(elevatorGuidList);
            piac.setProcInstIdList(procInstIdList);
            piac.setTaskIdList(taskIdList);
            //调用保存方法
            saveIteamAssigned(piac);
        }
    }

    private void saveIteamAssigned(ProjectIteamAssignedCommit projectIteamAssignedCommit) {

        String param = GsonUtils.toJson(projectIteamAssignedCommit, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        ProjectItemAssignedService projectItemAssignedService = HttpFactory.getService(ProjectItemAssignedService.class);
        projectItemAssignedService.saveIteamAssigned(requestBody).compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper())
                .subscribe(new com.zxtech.is.common.net.DefaultObserver<BaseResponse<Integer>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<Integer> response) {
                        if (response.getData() == 1) {
                            showAlertDialog(R.string.is_team_assigned_msg6, true);
                        } else {
                            showAlertDialog(R.string.is_team_assigned_msg7, false);
                        }
                    }

                    @Override
                    public void onFail() {
                        showAlertDialog(R.string.is_team_assigned_msg7, false);
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
