package com.zxtech.is.ui.check.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.check.QualityCheck;
import com.zxtech.is.service.check.QualityCheckService;
import com.zxtech.is.ui.check.fragment.QualityCheckFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by hsz on 2018/4/20.
 */

public class QualityCheckActivity extends BaseActivity {
    private List<QualityCheck> isMstCheckQRequiredList = new ArrayList<QualityCheck>();
    private Map<String, Object> isMstCheckQSaveMap = new HashMap<String, Object>();

    private BaseFragment[] mFragments = new BaseFragment[5];
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    private static final int FIFTH = 4;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @BindViews({
            R2.id.is_quality_check_border_0,
            R2.id.is_quality_check_border_1,
            R2.id.is_quality_check_border_2,
            R2.id.is_quality_check_border_3,
            R2.id.is_quality_check_border_4
    })
    LinearLayout[] linearLayouts;

    @BindViews({
            R2.id.is_quality_check_text_0,
            R2.id.is_quality_check_text_1,
            R2.id.is_quality_check_text_2,
            R2.id.is_quality_check_text_3,
            R2.id.is_quality_check_text_4
    })
    TextView[] textViews;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quality_check;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);

        QualityCheck qualityCheck = new QualityCheck();
        qualityCheck.setProjectGuid(getIntent().getStringExtra("projectGuid"));
        qualityCheck.setProjectName(getIntent().getStringExtra("projectName"));
        qualityCheck.setIsElevatorGuid(getIntent().getStringExtra("elevatorGuid"));
        qualityCheck.setIsElevatorName(getIntent().getStringExtra("elevatorName"));
        qualityCheck.setOptionCode(getIntent().getStringExtra("optionCode"));
        qualityCheck.setOptionName(getIntent().getStringExtra("optionName"));

        int position = getIntent().getIntExtra("position", 0);
        if (savedInstanceState == null) {
            qualityCheck.setIsMstCheckQCategory("01");
            mFragments[FIRST] = QualityCheckFragment.newInstance(qualityCheck);
            qualityCheck.setIsMstCheckQCategory("02");
            mFragments[SECOND] = QualityCheckFragment.newInstance(qualityCheck);
            qualityCheck.setIsMstCheckQCategory("03");
            mFragments[THIRD] = QualityCheckFragment.newInstance(qualityCheck);
            qualityCheck.setIsMstCheckQCategory("04");
            mFragments[FOURTH] = QualityCheckFragment.newInstance(qualityCheck);
            qualityCheck.setIsMstCheckQCategory("05");
            mFragments[FIFTH] = QualityCheckFragment.newInstance(qualityCheck);
            loadMultipleRootFragment(R.id.is_quality_check_container, position,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH],
                    mFragments[FIFTH]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(QualityCheckFragment.class);
            mFragments[SECOND] = findFragment(QualityCheckFragment.class);
            mFragments[THIRD] = findFragment(QualityCheckFragment.class);
            mFragments[FOURTH] = findFragment(QualityCheckFragment.class);
            mFragments[FIFTH] = findFragment(QualityCheckFragment.class);
        }

        showTab(FIRST);

        qualityCheck.setIsMstCheckQCategory("01,02,03,04,05");
        getIsMstCheckQRequiredListFun(qualityCheck);

        getIsMstCheckQHistoricalResultFun(qualityCheck);
    }

    private void showTab(int position) {
        for (int i = 0; i < linearLayouts.length; i++) {
            if (i == position) {
                linearLayouts[i].setSelected(true);
                textViews[i].setSelected(true);
            } else {
                linearLayouts[i].setSelected(false);
                textViews[i].setSelected(false);
            }
        }
    }

    private void getIsMstCheckQRequiredListFun(QualityCheck qualityCheck) {
        QualityCheckService qualityCheckService = HttpFactory.getService(QualityCheckService.class);
        String param = GsonUtils.toJson(qualityCheck, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        qualityCheckService.getIsMstCheckQRequired(requestBody)
                .compose(RxHelper.<BaseResponse<List<QualityCheck>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<QualityCheck>>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<List<QualityCheck>> response) {
                        if (response.getFlag().equals("1") && response.getData() != null && response.getData().size() > 0) {
                            isMstCheckQRequiredList.clear();
                            isMstCheckQRequiredList.addAll(response.getData());
                        }
                    }
                });
    }

    private void getIsMstCheckQHistoricalResultFun(QualityCheck qualityCheck) {
        QualityCheckService qualityCheckService = HttpFactory.getService(QualityCheckService.class);
        String param = GsonUtils.toJson(qualityCheck, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        qualityCheckService.getIsMstCheckQ(requestBody)
                .compose(RxHelper.<BaseResponse<List<QualityCheck>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<QualityCheck>>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<List<QualityCheck>> response) {
                        if (response.getFlag().equals("1") && response.getData() != null && response.getData().size() > 0) {

                            List<QualityCheck> list = response.getData();
                            for (QualityCheck q : list) {
                                if (q.getIsMstChkQGuid() != null
                                        && q.getIsCkQualityItemResult() != null) {
                                    setIsMstCheckQMapFun(q.getIsMstChkQGuid(), q.getIsCkQualityItemResult());
                                }
                            }
                        }
                    }
                });
    }

    @OnClick({
            R2.id.is_quality_check_border_0,
            R2.id.is_quality_check_border_1,
            R2.id.is_quality_check_border_2,
            R2.id.is_quality_check_border_3,
            R2.id.is_quality_check_border_4
    })
    public void onLinearLayoutsClick(View view) {
        int i = view.getId();
        if (i == R.id.is_quality_check_border_0) {
            showHideFragment(mFragments[FIRST]);
            showTab(FIRST);

        } else if (i == R.id.is_quality_check_border_1) {
            showHideFragment(mFragments[SECOND]);
            showTab(SECOND);

        } else if (i == R.id.is_quality_check_border_2) {
            showHideFragment(mFragments[THIRD]);
            showTab(THIRD);

        } else if (i == R.id.is_quality_check_border_3) {
            showHideFragment(mFragments[FOURTH]);
            showTab(FOURTH);

        } else if (i == R.id.is_quality_check_border_4) {
            showHideFragment(mFragments[FIFTH]);
            showTab(FIFTH);

        }
    }

    public void setIsMstCheckQMapFun(String key, String value) {
        isMstCheckQSaveMap.put(key, value);
    }

    @OnClick(R2.id.is_quality_check_save_button)
    public void isQualityCheckSaveButtonFun() {
        System.out.println("=========================================================================================");
        System.out.println(isMstCheckQSaveMap);
        System.out.println("=========================================================================================");
        System.out.println(isMstCheckQRequiredList);
        System.out.println("=========================================================================================");
        if (isMstCheckQSaveMap != null) {
            boolean goOnFlag = true;
            if (isMstCheckQRequiredList != null
                    && isMstCheckQRequiredList.size() > 0) {
                for (QualityCheck q : isMstCheckQRequiredList) {
                    if (isMstCheckQSaveMap.get(q.getIsMstChkQGuid()) == null
                            || isMstCheckQSaveMap.get(q.getIsMstChkQGuid()).equals("-1")) {
                        ToastUtil.showLong(q.getIsMstChkQCategoryName() + " " + getResources().getString(R.string.is_the1) + " " + q.getIsMstChkQRowNumber() + " " + getResources().getString(R.string.is_item_is_a_necessary_option));
                        goOnFlag = false;
                        break;
                    }
                }
            }
            if (goOnFlag) {
                QualityCheck qualityCheck = new QualityCheck();
                qualityCheck.setTaskId(getIntent().getStringExtra("taskId"));
                qualityCheck.setCustomGuid(getIntent().getStringExtra("customGuid"));
                qualityCheck.setIsElevatorGuid(getIntent().getStringExtra("elevatorGuid"));
                qualityCheck.setSetIsMstCheckQSaveMap(isMstCheckQSaveMap);
                String param = GsonUtils.toJson(qualityCheck, false);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
                QualityCheckService qualityCheckService = HttpFactory.getService(QualityCheckService.class);
                qualityCheckService.saveQualityCheck(requestBody)
                        .compose(RxHelper.<BaseResponse<Object>>rxSchedulerHelper())
                        .subscribe(new DefaultObserver<BaseResponse<Object>>(getActivity(), true) {

                            @Override
                            public void onSuccess(BaseResponse<Object> response) {
                                ToastUtil.showLong(response.getMessage());
                                setResult(RESULT_OK, getIntent());
                                finish();
                            }
                        });
            }
        }
    }
}
