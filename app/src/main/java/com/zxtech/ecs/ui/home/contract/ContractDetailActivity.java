package com.zxtech.ecs.ui.home.contract;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.OfficePoiUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.gks.model.vo.contract.ContractDetail;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/4.
 */

public class ContractDetailActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.attachment_rv)
    RecyclerView attachment_rv;
    @BindView(R.id.remark_et)
    EditText remark_et;
    @BindView(R.id.project_info_tv)
    TextView project_info_tv;
    @BindView(R.id.project_attr_tv)
    TextView project_attr_tv;
    @BindView(R.id.agent_tv)
    TextView agent_tv;
    @BindView(R.id.count_tv)
    TextView count_tv;
    @BindView(R.id.product_tv)
    TextView product_tv;

    private String contractGuid;
    private String PRProductGuid;

    private List<String> attachments = new ArrayList<>();
    private MyAdapter mAdapter;
    public static final String FILE_FLAG = "ContractStandardDocument#";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.contract_details));

        contractGuid = getIntent().getStringExtra("contractGuid");
        PRProductGuid = getIntent().getStringExtra("PRProductGuid");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        attachment_rv.setLayoutManager(linearLayoutManager);

        mAdapter = new MyAdapter(R.layout.item_contract_detail_attachment, attachments);
        attachment_rv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

        initData();
    }

    private void initData() {
        baseResponseObservable = HttpFactory.getApiService().getContactDetail(this.PRProductGuid, getUserId(), getUserNo(), getUserName(), getRoleNo(), getUserDeptNo());
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<ContractDetail>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<ContractDetail>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<ContractDetail> response) {
                        initProjectInfo(response.getData());
                    }
                });
    }

    private void initProjectInfo(ContractDetail data) {
        project_info_tv.setText(data.getProjectName());
        project_attr_tv.setText(data.getEqContractType());
        agent_tv.setText(data.getAgentName());
        count_tv.setText(data.getElevatorCount());
        product_tv.setText(data.getProductAndCount());
        remark_et.setText(data.getRemark());
        this.contractGuid = data.getContractGuid();
    }


    @OnClick({R.id.save_btn, R.id.submit_btn, R.id.generate_contract_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_btn:
                saveAndSubmit("save");
                break;
            case R.id.submit_btn:
                saveAndSubmit("saveAndSubmit");
                break;
            case R.id.generate_contract_btn:
                if (TextUtils.isEmpty(this.contractGuid)) {
                    return;
                }
                generateContract();
                break;
        }
    }

    private void generateContract() {
        baseResponseObservable = HttpFactory.getApiService().createStandardContract(this.contractGuid, getRoleNo(), getUserId());
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<String[]>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String[]>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<String[]> response) {
                        attachments.clear();
                        attachments.addAll(Arrays.asList(response.getData()));
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    private void saveAndSubmit(final String operateType) {
        baseResponseObservable = HttpFactory.getApiService().saveAndSubmitContract(this.contractGuid, remark_et.getText().toString(), getRoleNo(), getUserNo(), getUserName(), operateType);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (operateType.equals("save")) {
                            ToastUtil.showLong("保存成功");
                        } else {
                            ToastUtil.showLong("提交成功");
                        }

                        setResult(10032);
                        finish();
                    }

                });

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
        String contractType = attachments.get(position);
        final String fileId = this.contractGuid + contractType;

        if ((boolean) com.zxtech.gks.common.SPUtils.get(mContext, FILE_FLAG + fileId, false)) {
            File file = new File(mContext.getFilesDir(), fileId + ".pdf");
            OfficePoiUtil.openFile(mContext, file);
        } else {

            baseResponseObservable = HttpFactory.getApiService().
                    getContractDocument( this.contractGuid, Integer.valueOf(contractType), getUserId());
            baseResponseObservable
                    .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            downloadDoc(response.getData(), fileId, position);
                        }
                    });
        }
    }


    private void downloadDoc(String url, final String fileId, final int position) {
        showProgress();
        progressDialog.setTint(getString(R.string.downloading));
        final File file = new File(mContext.getFilesDir(), fileId + ".pdf");
        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(url)
                .setPath(file.getAbsolutePath())
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        progressDialog.setProgress(soFarBytes * 100 / totalBytes + "%");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        dismissProgress();
                        SPUtils.put(mContext, FILE_FLAG + fileId, true);
                        mAdapter.notifyItemChanged(position);
                        OfficePoiUtil.openFile(mContext, file);
                    }


                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        dismissProgress();
                    }

                });
        baseDownloadTask.start();
    }


    class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        public MyAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.contract_name_tv, getContractName(item));
        }

        public String getContractName(String contractType) {
            if ("1".equals(contractType)) {
                return "标准设备合同";
            } else if ("2".equals(contractType)) {
                return "服务费协议";
            } else if ("3".equals(contractType)) {
                return "调试及校验协议";
            }else if ("4".equals(contractType)) {
                return "安装合同";
            }
            return contractType;
        }
    }


}
