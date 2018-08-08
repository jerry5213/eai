package com.zxtech.gks.ui.record;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.BidAttachment;
import com.zxtech.ecs.model.ProjectBid;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.bid.BidInfoFragment;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.bean.ProjectDetailBean;
import com.zxtech.gks.model.vo.ProjectRecord;
import com.zxtech.gks.model.vo.RecordApproval;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by syp523 on 2017/11/30.
 */

public class RecordApprovalDetailActivity extends BaseActivity {

    @BindViews({R.id.param1_tv,R.id.param2_tv,R.id.param3_tv,R.id.param4_tv,R.id.param5_tv,
            R.id.param6_tv,R.id.param7_tv,R.id.param8_tv,R.id.param9_tv,R.id.param10_tv,R.id.param11_tv,R.id.param12_tv,R.id.param13_tv})
    TextView[] tvs;

    @BindViews({R.id.param7_layout,R.id.param8_layout,R.id.param9_layout,R.id.param10_layout,R.id.param11_layout})
    LinearLayout[] layouts;

    @BindViews({R.id.param7_view,R.id.param8_view,R.id.param9_view,R.id.param10_view,R.id.param11_view})
    View[] lines;
    @BindView(R.id.repeat_proj_tv)
    TextView repeat_proj_tv;
    @BindView(R.id.province_tv)
    TextView province_tv;
    @BindView(R.id.city_tv)
    TextView city_tv;
    @BindView(R.id.area_tv)
    TextView area_tv;
    @BindView(R.id.reject_tv)
    TextView reject_tv;
    @BindView(R.id.pass_tv)
    TextView pass_tv;
    @BindView(R.id.result_edittext)
    EditText result_edittext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.param1_tva)
    TextView param1_tva;
    @BindView(R.id.param2_tva)
    TextView param2_tva;
    @BindView(R.id.param3_tva)
    EditText param3_tva;
    @BindView(R.id.param4_tva)
    EditText param4_tva;
    @BindView(R.id.param5_tva)
    EditText param5_tva;
    @BindView(R.id.param6_tva)
    TextView param6_tva;
    @BindView(R.id.param7_tva)
    TextView param7_tva;
    @BindView(R.id.param8_tva)
    TextView param8_tva;
    @BindView(R.id.param9_tva)
    TextView param9_tva;
    @BindView(R.id.param10_tva)
    TextView param10_tva;
    @BindView(R.id.param11_tva)
    TextView param11_tva;
    @BindView(R.id.param12_tva)
    TextView param12_tva;

    @BindView(R.id.param13_tva)
    TextView param13_tva;
    @BindView(R.id.param14_tv)
    TextView param14_tv;
    @BindView(R.id.param15_tv)
    TextView param15_tv;
    @BindView(R.id.param16_tv)
    TextView param16_tv;
    @BindView(R.id.param17_tv)
    TextView param17_tv;

    @BindView(R.id.param18_tv)
    TextView param18_tv;
    @BindView(R.id.param19_tv)
    EditText param19_tv;
    @BindView(R.id.param20_tv)
    EditText param20_tv;
    @BindView(R.id.param21_tv)
    TextView param21_tv;
    @BindView(R.id.param22_tv)
    TextView param22_tv;
    @BindView(R.id.param23_tv)
    TextView param23_tv;
    @BindView(R.id.bid_tv)
    TextView bid_tv;
    @BindView(R.id.fl_container)
    FrameLayout fl_container;

    @BindView(R.id.other_panel)
    LinearLayout other_panel;
    @BindView(R.id.base_panel)
    LinearLayout base_panel;
    @BindView(R.id.iv_close)
    ImageView iv_close;

    private ProjectRecord recordApproval = new ProjectRecord();
    private BidInfoFragment fragment;
    private String purchaseType = "招投标";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_approval_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.details_of_approval));
        fragment = BidInfoFragment.newInstance();
        loadRootFragment(R.id.fl_container, fragment);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        baseResponseObservable = HttpFactory.getApiService().getProjectDetail(id);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<ProjectRecord>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<ProjectRecord>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<ProjectRecord> response) {

                        recordApproval = response.getData();
                        showText();
                        ProjectDetailBean projectDetail = response.getData().getProjectDetail();
                        ProjectBid projectBid = response.getData().getProjectBid();
                        List<BidAttachment> bidAttachments = response.getData().getDrawingFileList();

                        if(projectDetail!=null){
                            param1_tva.setText(projectDetail.getProjectState());
                            param2_tva.setText(projectDetail.getProjectStage());
                            param3_tva.setText(projectDetail.getEleCount());
                            param4_tva.setText(projectDetail.getEscCount());
                            param5_tva.setText(projectDetail.getTotalBidCost());
                            param6_tva.setText(projectDetail.getMainEqType());
                            param7_tva.setText(projectDetail.getStartBidDate());
                            param8_tva.setText(projectDetail.getEndBidDate());
                            param9_tva.setText(projectDetail.getExSignDate());
                            param10_tva.setText(projectDetail.getExPayDate());
                            param11_tva.setText(projectDetail.getExDeliveryDate());
                            param12_tva.setText(projectDetail.getExEQSDate());
                            param13_tva.setText(projectDetail.getHLAccount());
                            param14_tv.setText(projectDetail.getInfoSource());
                            param15_tv.setText(projectDetail.isIsOnlyAgent()?"是":"否");
                            param16_tv.setText(projectDetail.getMainCompA());
                            param17_tv.setText(projectDetail.getMainCompB());
                            param18_tv.setText(projectDetail.getBelongArea());
                            param19_tv.setText(projectDetail.getDeveloper());
                            param20_tv.setText(projectDetail.getDevContact());
                            param21_tv.setText(projectDetail.getDevType());
                            param22_tv.setText(projectDetail.getDevTel());
                            param23_tv.setText(projectDetail.getDevAddress());
                            bid_tv.setText(response.getData().getProject().getPurchaseType());

                            if(purchaseType.equals(response.getData().getProject().getPurchaseType())){
                                fl_container.setVisibility(View.VISIBLE);
                            }else{
                                fl_container.setVisibility(View.GONE);
                            }
                        }

                        if(projectBid!=null){
                            //BidInfoFragment fragment = findFragment(BidInfoFragment.class);
                            fragment.setProjectBid(projectBid);
                            fragment.setBidResultFiles(bidAttachments);
                            fragment.setEdit(false);
                            fragment.setExpand(false);
                        }
                    }
                });
    }

    private void showText(){
        RecordApproval project = recordApproval.getProject();
        if (project != null) {
            tvs[0].setText(project.getProjectNo());
            tvs[1].setText(project.getSalesmanUserName());
            tvs[2].setText(project.getBranchName());
            tvs[3].setText(project.getProjectAreaNameStr());
            tvs[4].setText(project.getCustomerName());
            tvs[5].setText(project.getBuildingCharacterName());
            tvs[6].setText(project.getDxmzyUserName());
            tvs[7].setText(project.getDxmzySalesManager());
            tvs[8].setText(project.getBranchSalesmanName());
            tvs[9].setText(project.getPartnerSalespersonName());
            tvs[10].setText(project.getPartnerSalespersonPhone());

            tvs[11].setText(project.getProjectName());
            tvs[12].setText(project.getProjectAdd_Other());
            province_tv.setText(project.getProjectAdd_Province());
            city_tv.setText(project.getProjectAdd_City());
            area_tv.setText(project.getProjectAdd_Area());

            if (!TextUtils.isEmpty(project.getProjectType())) {
                //普通项目、OEM项目
             if (Constants.PROJ_TYPE_DXM.equals(project.getProjectType()) || Constants.PROJ_TYPE_XSDXM.equals(project.getProjectType())) { //大项目
                    layouts[0].setVisibility(View.VISIBLE);
                    layouts[1].setVisibility(View.VISIBLE);
                    lines[0].setVisibility(View.VISIBLE);
                    lines[1].setVisibility(View.VISIBLE);
                }else if (Constants.PROJ_TYPE_PT.equals(project.getProjectType()) && !TextUtils.isEmpty(project.getBranchSalesmanNo())) { //代理商项目
                    layouts[2].setVisibility(View.VISIBLE);
                    layouts[3].setVisibility(View.VISIBLE);
                    layouts[4].setVisibility(View.VISIBLE);
                    lines[2].setVisibility(View.VISIBLE);
                    lines[3].setVisibility(View.VISIBLE);
                    lines[4].setVisibility(View.VISIBLE);
                }
            }
        }


    }

    @OnClick({R.id.repeat_proj_tv,R.id.reject_tv,R.id.pass_tv, R.id.tv_base_info
            , R.id.tv_other_info})
    public void onClick(View view) {

        AlertDialog.Builder builder  = new AlertDialog.Builder(this);

        switch (view.getId()) {
            case R.id.repeat_proj_tv:
                Intent intent = new Intent(mContext,RepeatProjectSearchActivity.class);
                intent.putExtra("projectName",recordApproval.getProject().getProjectName());
                intent.putExtra("projectId",recordApproval.getProject().getProjectGuid());
                startActivity(intent);
                break;
            case R.id.reject_tv:

                builder.setTitle(getString(R.string.tips));
                builder.setMessage(getString(R.string.dialog_content1));
                builder.setNegativeButton(getString(R.string.cancel), null);
                builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        approval(Constants.APPROVAL_REJECT);
                    }
                });
                builder.show();
                break;
            case R.id.pass_tv:

                builder.setTitle(getString(R.string.tips));
                builder.setMessage(getString(R.string.dialog_content2));
                builder.setNegativeButton(getString(R.string.cancel), null);
                builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        approval(Constants.APPROVAL_PASS);
                    }
                });
                builder.show();
                break;
            case R.id.tv_base_info:
                break;
            case R.id.tv_other_info:
                if (view.getTag() == null || (Integer) view.getTag() == 0) {
                    other_panel.setVisibility(View.VISIBLE);
                    view.setTag(1);
                    iv_close.setImageResource(R.drawable.open);
                } else {
                    other_panel.setVisibility(View.GONE);
                    view.setTag(0);
                    iv_close.setImageResource(R.drawable.close);
                }
                break;
        }
    }

    private void approval(final String flag){

        baseResponseObservable = HttpFactory.getApiService().
                submitProject(recordApproval.getProject().getProjectGuid(),result_edittext.getText().toString(),
                        getUserNo(),flag);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {

                        setResult(1001);
                        finish();
                        ToastUtil.showLong(getString(R.string.success));
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086) {
            setResult(1001);
            finish();
        }
    }
}
