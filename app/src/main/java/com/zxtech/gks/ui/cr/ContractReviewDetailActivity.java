package com.zxtech.gks.ui.cr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.common.OfficePoi;
import com.zxtech.gks.model.vo.ProjectInfo;
import com.zxtech.gks.model.vo.contract.Config;
import com.zxtech.gks.model.vo.contract.ContractDetail;
import com.zxtech.gks.model.vo.contract.FileData;
import com.zxtech.gks.model.vo.contract.WorkFlowNode;
import com.zxtech.gks.ui.pa.ProjectInfoDetailActivity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by SYP521 on 2017/12/13.
 */

public class ContractReviewDetailActivity extends BaseActivity implements IActivity,BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.rv)
    RecyclerView flowReviewRv;
    @BindView(R.id.pro)
    ProgressBar progressBar;
    @BindView(R.id.ll_report_user)
    LinearLayout ll_report_user;
    @BindView(R.id.ck_vice_president)
    AppCompatCheckBox ck_vice_president;
    @BindView(R.id.ck_president)
    AppCompatCheckBox ck_president;

    @BindViews({R.id.project_name, R.id.project_attribute, R.id.agent_name, R.id.product_total})
    TextView[] tvs;
    @BindView(R.id.et_opinion)
    EditText et_opinion;

    private ProjectInfo projectInfo;
    private AttachmentAdapter adapter;
    private ContractWorkFlowNodeListAdapter contractWorkFlowNodeListAdapter;
    private String contractId;
    private String instanceNodeId;
    private List<WorkFlowNode> mDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.cr_activity_contract_review_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.details));

        GridLayoutManager mgr = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        rlv.setLayoutManager(mgr);
        rlv.setItemAnimator(new DefaultItemAnimator());
        rlv.setHasFixedSize(true);
        adapter = new AttachmentAdapter(R.layout.cr_item_attachment);
        rlv.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        flowReviewRv.setLayoutManager(linearLayoutManager);
        flowReviewRv.addItemDecoration(new MyItemDecoration());
        contractWorkFlowNodeListAdapter = new ContractWorkFlowNodeListAdapter(this, R.layout.cr_item_work_flow_node_list, mDatas);
        flowReviewRv.setAdapter(contractWorkFlowNodeListAdapter);

        loadData();
    }

    private void loadData() {

        contractId = getIntent().getStringExtra("id");
        instanceNodeId = getIntent().getStringExtra("instanceNodeId");

        Map params = new HashMap();
        params.put("id", contractId);
        params.put("instanceNodeId", instanceNodeId);

        baseResponseObservable = HttpFactory.getApiService().getContractById(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<ContractDetail>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<ContractDetail>>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse<ContractDetail> response) {

                        ContractDetail datas = response.getData();

                        projectInfo = new ProjectInfo();
                        projectInfo.setProject_name(datas.getProjectName());
                        projectInfo.setProject_attr(datas.getContractType());
                        projectInfo.setAgent_name(datas.getAgentName());
                        projectInfo.setProduct_list(datas.getProductList());
                        projectInfo.setProject_no(datas.getProjectNo());
                        projectInfo.setProject_type(datas.getProjectType());
                        projectInfo.setGk_user(datas.getSalesmanUserName());
                        projectInfo.setSale_branch(datas.getBranchName());
                        if (datas.isIsKQ()) {
                            projectInfo.setIs_kq("是");
                        } else {
                            projectInfo.setIs_kq("否");
                        }

                        tvs[0].setText(datas.getProjectName());
                        tvs[1].setText(datas.getContractType());
                        tvs[2].setText(datas.getAgentName());
                        tvs[3].setText(datas.getProductList());

                        List<WorkFlowNode> workFlowNodes = datas.getWorkFlowNodeList();
                        mDatas.clear();
                        mDatas.addAll(workFlowNodes);
                        contractWorkFlowNodeListAdapter.notifyDataSetChanged();

                        List<FileData> fileDatas = datas.getFileList();
                        if (fileDatas != null && fileDatas.size() > 0) {
                            for (FileData ob : fileDatas) {
                                ob.setDrawableId(R.drawable.ic_attachment);
                            }
                            adapter.addData(fileDatas);
                            adapter.notifyDataSetChanged();
                        }

                        List<Config> configs = datas.getConfigs();
                        if (configs != null && configs.size() > 0) {
                            for (Config ob : configs) {
                                if ("AessinFollowUpNodeIsCreate".equals(ob.getName())) {
                                    ll_report_user.setVisibility(View.VISIBLE);
                                    String[] arr = ob.getValue().split(",");
                                    if (null != arr && arr.length > 1) {
                                        ck_president.setText(arr[1]);
                                        ck_vice_president.setText(arr[0]);
                                    }
                                }
                            }
                        }

                    }
                });
    }

    @OnClick({R.id.tv_refuse, R.id.tv_ok, R.id.iv_close})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_refuse:
                submitContractReview("2");
                break;
            case R.id.tv_ok:
                submitContractReview("0");
                break;
            case R.id.iv_close:
                Intent intent = new Intent(view.getContext(), ProjectInfoDetailActivity.class);
                intent.putExtra(Constants.DATA1, projectInfo);
                startActivity(intent);
                break;
        }
    }

    protected void submitContractReview(String submitResult) {

        String submitDescription = et_opinion.getText().toString();
        String a = ck_vice_president.getText().toString();
        String b = ck_president.getText().toString();
        try {
            submitDescription = URLEncoder.encode(submitDescription, "utf-8");
            a = URLEncoder.encode(a, "utf-8");
            b = URLEncoder.encode(b, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map params = new HashMap();
        params.put("contractGuid", contractId);
        params.put("userNo", getUserNo());
        params.put("submitResult", submitResult);
        params.put("submitDescription", submitDescription);

        if (ck_vice_president.isChecked() && ck_president.isChecked()) {
            params.put("nextNodeList", a + "," + b);
        } else if (ck_vice_president.isChecked()) {
            params.put("nextNodeList", a);
        } else if (ck_president.isChecked()) {
            params.put("nextNodeList", b);
        }

        baseResponseObservable = HttpFactory.getApiService().submitCMSContractWorkFlow(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse response) {

                        ToastUtil.showLong(getString(R.string.toast13));
                        setResult(1);
                        finish();
                    }
                });
    }

    String dir = APPConfig.DOWN_LOAD_PATH + "Attachment/";

    public void openAttachPreview(FileData menu) {

        if (!TextUtils.isEmpty(menu.getFileName())) {

            if (menu.getFileName().endsWith(".xlsx") || menu.getFileName().endsWith(".doc")
                    || menu.getFileName().endsWith(".docx") || menu.getFileName().endsWith(".pdf")
                    || menu.getFileName().endsWith(".jpg") || menu.getFileName().endsWith(".png")) {

                //File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + menu.getFileName());
                String realName = menu.getFileUrl().substring(menu.getFileUrl().lastIndexOf("/") + 1);
                File futureStudioIconFile = new File(dir + realName);
                if (futureStudioIconFile.exists()) {
                    OfficePoi.openFile(this, futureStudioIconFile);
                } else {
                    String path = getFileUrl() + menu.getFileUrl();
                    downloadFile(path);
                }
            } else if (!menu.getFileName().contains(".")) {
                if (TextUtils.isEmpty(menu.getFileUrl())) {
                    ToastUtil.showLong("连接无效");
                } else {
                    String url = getFileUrl() + menu.getFileUrl() + "&appUserGuid=" + getUserId();
                    Intent intent = new Intent(this, HtmlPreviewActivity.class);
                    intent.putExtra(Constants.DATA1, url);
                    startActivity(intent);
                }
            }
        }
    }

    public void downloadFile(final String file_url) {

        showProgress();
        FileDownloader.getImpl().create(file_url)
                .setPath(dir)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        progressDialog.setProgress(soFarBytes * 100 / totalBytes + "%");
                    }


                    @Override
                    protected void completed(BaseDownloadTask task) {
                        dismissProgress();
                        ToastUtil.showLong("下载完成");
                    }


                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        dismissProgress();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        dismissProgress();
                    }
                }).start();


    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        FileData fileData = (FileData)adapter.getData().get(position);
        openAttachPreview(fileData);
    }
}
