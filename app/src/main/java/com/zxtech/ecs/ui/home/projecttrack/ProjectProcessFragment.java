package com.zxtech.ecs.ui.home.projecttrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ProjectInfo;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.is.ui.mt.activity.MtProjectProcessItemActivity;

import java.util.Map;

import butterknife.BindViews;
import butterknife.OnClick;

//import com.zxtech.is.R;

/**
 * Created by syp600 on 2018/7/7.
 */

public class ProjectProcessFragment extends BaseFragment implements View.OnClickListener {

    private static final String DONE = "1";
    private static final String DOING = "2";
    private static final String UNDO = "3";

    /**
     * projectNo 项目
     */
    private String projectNo;

    private String projectState;

    private String taskFlag;

    private String[] result = null;

    /**
     * 项目申请、项目审批、项目报价、
     * 报价审批、合同申请、合同评审
     * 合同签订、土建复核、生产跟踪
     * 发运跟踪、安装进场、设备调试
     * 厂检、   官检、    安装交付
     */
    @BindViews({R.id.project_process_iv_a, R.id.project_process_iv_b, R.id.project_process_iv_c
            , R.id.project_process_iv_d, R.id.project_process_iv_e, R.id.project_process_iv_f
            , R.id.project_process_iv_h, R.id.project_process_iv_g, R.id.project_process_iv_i
            , R.id.project_process_iv_j, R.id.project_process_iv_k, R.id.project_process_iv_l
            , R.id.project_process_iv_m, R.id.project_process_iv_n, R.id.project_process_iv_o})
    ImageView[] imageViews;

    @BindViews({R.id.project_process_tv_a, R.id.project_process_tv_b, R.id.project_process_tv_c
            , R.id.project_process_tv_d, R.id.project_process_tv_e, R.id.project_process_tv_f
            , R.id.project_process_tv_h, R.id.project_process_tv_g, R.id.project_process_tv_i
            , R.id.project_process_tv_j, R.id.project_process_tv_k, R.id.project_process_tv_l
            , R.id.project_process_tv_m, R.id.project_process_tv_n, R.id.project_process_tv_o})
    TextView[] textViews;
    @BindViews({R.id.project_process_row_g, R.id.project_process_row_i, R.id.project_process_row_j
            , R.id.project_process_row_k, R.id.project_process_row_l, R.id.project_process_row_m
            , R.id.project_process_row_n, R.id.project_process_row_o})
    View[] views;

    private String[] prjStateArray = {"报备-准备", "报备中", "报备-完成", "价审-准备", "价审中", "价审-完成", "合同评审-准备", "合同评审中", "合同评审-完成"};
    private int[] prjStateNum = {0, 1, 2, 2, 3, 4, 4, 5};


    public static ProjectProcessFragment newInstance(ProjectInfo projectInfo) {
        Bundle bundle = new Bundle();
        bundle.putString("projectNo", projectInfo.getProjectNo());
        bundle.putString("projectState", projectInfo.getProjectStateName());
        ProjectProcessFragment fragment = new ProjectProcessFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_project_process;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        projectNo = arguments.get("projectNo").toString();
        projectState = arguments.get("projectState").toString();
        for (int i = 0; i < prjStateArray.length; i++) {
            if (i > 7) {
                imageViews[6].setBackgroundResource(com.zxtech.is.R.drawable.express_point_prj_prc_green);
                requestNet();
                break;
            }
            if (projectState.equals(prjStateArray[i])) {

                for (int j = 0; j < i; j++) {
                    textViews[prjStateNum[j]].setText(getString(R.string.completed));
                    textViews[prjStateNum[j]].setBackgroundResource(com.zxtech.is.R.drawable.button_dark_red_radius);
                    imageViews[prjStateNum[j]].setBackgroundResource(com.zxtech.is.R.drawable.express_point_prj_prc_green);

                }
                imageViews[prjStateNum[i]].setBackgroundResource(com.zxtech.is.R.drawable.express_point_prj_prc_green);
                textViews[prjStateNum[i]].setText(getString(R.string.in_process));//正在办理
                textViews[prjStateNum[i]].setBackgroundResource(com.zxtech.is.R.drawable.button_yellow_radius);
                break;
            }
        }

        //灰色 圆 R.drawable.express_point_prj_prc
        //亮绿色 圆 R.drawable.express_point_prj_prc_green;
        //红暗色 R.drawable.button_dark_red_radius
        //黄色 R.drawable.button_yellow_radius
        //灰色 R.drawable.button_dark_grey_radius
    }
    private void requestNet() {
//        projectNo = "GK2017-05555";
        /*AppMtService appMtService = HttpFactory.getService(AppMtService.class);
        appMtService.getProjectProgressInfo(projectNo)
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {

                    }
                });*/

        baseResponseObservable = com.zxtech.ecs.net.HttpFactory.getApiService().
                getProjectProgressInfo(projectNo);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {

                        for (int l = 0; l < 6; l++) {
                            imageViews[l].setBackgroundResource(com.zxtech.is.R.drawable.express_point_prj_prc_green);
                            textViews[l].setText(getString(R.string.completed));
                            textViews[l].setBackgroundResource(com.zxtech.is.R.drawable.button_dark_red_radius);
                        }

                        Map<String, String> map = response.getData();
                        result = new String[]{map.get("s1"), map.get("s2"), map.get("s3"),
                                map.get("s4"), map.get("s5"), map.get("s6"),
                                map.get("s7"), map.get("s8")};
                        for (int i = 0; i < result.length; i++) {
//                           已完成
                            if (DONE.equals(result[i])) {
                                textViews[i + 7].setBackgroundResource(com.zxtech.is.R.drawable.button_dark_red_radius);
                                textViews[i + 7].setText(getString(R.string.completed));
                                imageViews[i + 7].setBackgroundResource(com.zxtech.is.R.drawable.express_point_prj_prc_green);
                            }
                            if (DOING.equals(result[i])) {
                                textViews[i + 7].setBackgroundResource(com.zxtech.is.R.drawable.button_yellow_radius);
                                textViews[i + 7].setText(getString(R.string.in_process));
                                imageViews[i + 7].setBackgroundResource(com.zxtech.is.R.drawable.express_point_prj_prc_green);
                            }
                        }


                        if (DOING.equals(result[0]) || UNDO.equals(result[0])) {
                            result[0] = "2";
                            imageViews[7].setBackgroundResource(com.zxtech.is.R.drawable.express_point_prj_prc_green);
                            textViews[7].setText(getString(R.string.in_process));
                            textViews[7].setBackgroundResource(com.zxtech.is.R.drawable.button_yellow_radius);
                            imageViews[6].setBackgroundResource(com.zxtech.is.R.drawable.express_point_prj_prc);
                        } else {
                            textViews[6].setText(getString(R.string.in_process));
                            textViews[6].setBackgroundResource(com.zxtech.is.R.drawable.button_yellow_radius);
                            for (int j = 1; j < result.length; j++) {
                                if (DONE.equals(result[j]) || DOING.equals(result[j])) {
                                    textViews[6].setText(getString(R.string.completed));
                                    textViews[6].setBackgroundResource(com.zxtech.is.R.drawable.button_dark_red_radius);
                                    imageViews[6].setBackgroundResource(com.zxtech.is.R.drawable.express_point_prj_prc);
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });

    }

    @Override
    @OnClick({R.id.project_process_row_g, R.id.project_process_row_i, R.id.project_process_row_j
            , R.id.project_process_row_k, R.id.project_process_row_l, R.id.project_process_row_m
            , R.id.project_process_row_n, R.id.project_process_row_o})
    public void onClick(View v) {
        int id = v.getId();
        if (result != null && result.length > 0) {
            for (int i = 0; i < views.length; i++) {
                if (views[i].getId() == id) {
                    if (DOING.equals(result[i])) {
                        Intent intent = new Intent(mContext, MtProjectProcessItemActivity.class);
                        taskFlag = "s" + (i + 1);
                        intent.putExtra("projectNo", projectNo);
                        intent.putExtra("taskFlag", taskFlag);
                        startActivity(intent);
                    }
                }
            }
        }

    }
}
