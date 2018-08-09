package com.zxtech.is.ui.workflow.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.DateUtil;
import com.zxtech.is.util.StringUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.schedule.ScheduleManager;
import com.zxtech.is.service.taskme.TaskMeService;
import com.zxtech.is.service.workflow.WorkflowService;
import com.zxtech.is.ui.taskme.adapter.ScheduleManageListAdapter;
import com.zxtech.is.ui.workflow.adapter.TaskHistoryListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp688 on 4/23/2018.
 */

public class TaskHistoryListActivity extends BaseActivity {
    private List<Map<String, Object>> mBeans = new ArrayList<>();
    private TaskHistoryListAdapter adapter;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.task_list_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.task_history_list;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TaskHistoryListAdapter(R.layout.item_task_history, mBeans);
        adapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        String procInstId = intent.getStringExtra("procInstId");
        requestNet(procInstId);
    }

    private void requestNet(String procInstId) {
        WorkflowService workflowService = HttpFactory.getService(WorkflowService.class);
        workflowService.getHiByProcInstId(procInstId).compose(RxHelper.<PageResponse<List<Map<String, Object>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Map<String, Object>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<Map<String, Object>>> response) {
                        if (response.getData().size() == 0) {
                            ToastUtil.showLong(mContext.getResources().getString(R.string.is_temporary_task_no_data));
                        } else {
                            mBeans.clear();
                            mBeans.addAll(response.getData());
                            for (int i = 0; i < mBeans.size(); i++) {
                                mBeans.get(0).put("flag", "0");
                                break;
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }
                });
    }
}
