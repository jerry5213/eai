package com.zxtech.ecs.ui.home.bid;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.BidDetail;
import com.zxtech.ecs.model.BidReview;
import com.zxtech.ecs.model.ProjectBid;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/7/26.
 */

public class BidDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.prject_no_tv)
    TextView prject_no_tv;
    @BindView(R.id.saleman_tv)
    TextView saleman_tv;
    @BindView(R.id.sale_branch_tv)
    TextView sale_branch_tv;
    @BindView(R.id.prject_branch_tv)
    TextView prject_branch_tv;
    @BindView(R.id.prject_name_tv)
    TextView prject_name_tv;
    @BindView(R.id.customer_tv)
    TextView customer_tv;
    @BindView(R.id.fieldProvince)
    TextView fieldProvince; //省
    @BindView(R.id.fieldCity)
    TextView fieldCity;
    @BindView(R.id.fieldArea)
    TextView fieldArea;
    @BindView(R.id.fieldOther)
    TextView fieldOther;
    @BindView(R.id.payment_method_et)
    EditText payment_method_et;
    @BindView(R.id.btn_panel)
    LinearLayout btn_panel;
    @BindView(R.id.bid_info_fl)
    FrameLayout fl_container;

    private BidDetail bidDetail;
    private BidReview bidReview;
    private BidInfoFragment bidInfoFragment;
    private ProjectBid projectBid = new ProjectBid();
    private boolean isEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bid_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar, "投标详情");
        ((View)prject_branch_tv.getParent()).setVisibility(View.GONE);
        bidInfoFragment = BidInfoFragment.newInstance();
        bidInfoFragment.setExpand(false);
        loadRootFragment(R.id.bid_info_fl, bidInfoFragment);

        bidReview = (BidReview) getIntent().getSerializableExtra("data");
        isEdit = getIntent().getBooleanExtra("isEdit",false);
        if(!isEdit){
            btn_panel.setVisibility(View.GONE);//已申请隐藏操作按钮
        }
        initData();
    }

    @OnClick({R.id.tv_save, R.id.tv_submit})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_save:
                sendBid("save");
                break;
            case R.id.tv_submit:
                sendBid("submit");
                break;
        }
    }

    private void initData() {

        baseResponseObservable = HttpFactory.getApiService().getBiddingReviewDetail(bidReview.getProjectGuid());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<BidDetail>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<BidDetail>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<BidDetail> response) {
                        bidDetail = response.getData();
                        prject_no_tv.setText(bidDetail.getProjectNo());
                        saleman_tv.setText(bidDetail.getSalesmanUserName());
                        sale_branch_tv.setText(bidDetail.getBranchName());
                        prject_branch_tv.setText(bidDetail.getProjectAreaNameStr());
                        prject_name_tv.setText(bidDetail.getProjectName());
                        customer_tv.setText(bidDetail.getCustomerName());
                        payment_method_et.setText(bidDetail.getPaymentType());
                        fieldProvince.setText(bidDetail.getProjectAdd_Province());
                        fieldCity.setText(bidDetail.getProjectAdd_City());
                        fieldArea.setText(bidDetail.getProjectAdd_Area());
                        fieldOther.setText(bidDetail.getProjectAdd_Other());
                        bidInfoFragment.projectBid = response.getData().getProjectBid() == null?new ProjectBid():response.getData().getProjectBid();
                        bidInfoFragment.setEdit(isEdit);
                        if(response.getData().getDrawingFileList() != null){
                            bidInfoFragment.setBidResultFiles(response.getData().getDrawingFileList());
                        }
                    }

                    @Override
                    public void onFail() {
                        getActivity().finish();
                    }
                });
    }

    public void sendBid(final String type) {

        if (bidInfoFragment.projectBid == null) {
            return;
        }

        if(fl_container.isShown() && !bidInfoFragment.isRequiredCheck(BidInfoFragment.ALL_CHECK)){
            return;
        }

        projectBid = bidInfoFragment.projectBid;
        projectBid.setUserNo(getUserNo());
        projectBid.setUserName(getUserName());
        projectBid.setOperateType(type);
        projectBid.setProjectGuid(bidReview.getProjectGuid());
        if(bidReview.getCommitType() == 1){
            //projectBid.setGuid(bidReview.getGuid());
            //projectBid.setProjectGuid(bidReview.getProjectGuid());
            projectBid.setBiddingReviewGuid(bidReview.getGuid());
            projectBid.setInstanceNodeId(bidReview.getInstanceNodeId());
        }else{

        }

        String json = new GsonBuilder().serializeNulls().create().toJson(projectBid);

        baseResponseObservable = HttpFactory.getApiService()
                .saveBiddingReviewInfo(json);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Map>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Map> response) {

                        if(bidReview.getCommitType() == 0){
                            String biddingReviewGuid = response.getData().get("guid").toString();
                            String instanceNodeId = response.getData().get("nodeId").toString();
                            projectBid.setBiddingReviewGuid(biddingReviewGuid);
                            projectBid.setInstanceNodeId(instanceNodeId);
                            bidReview.setGuid(biddingReviewGuid);
                            bidReview.setInstanceNodeId(instanceNodeId);
                            bidReview.setCommitType(1);
                            if("submit".equals(type)){
                                setResult(1);
                                finish();
                            }
                        }else{
                            setResult(1);
                            finish();
                        }
                    }
                });
    }
}
