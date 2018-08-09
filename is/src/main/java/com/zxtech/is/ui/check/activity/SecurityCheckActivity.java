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
import com.zxtech.is.model.check.SecurityCheck;
import com.zxtech.is.service.check.SecurityCheckService;
import com.zxtech.is.ui.check.fragment.SecurityCheckFragment;

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
 * Created by hsz on 2018/4/26.
 */

public class SecurityCheckActivity extends BaseActivity {
    private List<SecurityCheck> isMstCheckSRequiredList = new ArrayList<SecurityCheck>();
    private Map<String, Object> isMstCheckSSaveMap = new HashMap<String, Object>();

    private BaseFragment[] mFragments = new BaseFragment[5];
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    private static final int FIFTH = 4;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @BindViews({
            R2.id.is_security_check_border_0,
            R2.id.is_security_check_border_1,
            R2.id.is_security_check_border_2,
            R2.id.is_security_check_border_3,
            R2.id.is_security_check_border_4
    })
    LinearLayout[] linearLayouts;

    @BindViews({
            R2.id.is_security_check_text_0,
            R2.id.is_security_check_text_1,
            R2.id.is_security_check_text_2,
            R2.id.is_security_check_text_3,
            R2.id.is_security_check_text_4
    })
    TextView[] textViews;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_security_check;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);

        SecurityCheck securityCheck = new SecurityCheck();
        securityCheck.setProjectGuid(getIntent().getStringExtra("projectGuid"));
        securityCheck.setProjectName(getIntent().getStringExtra("projectName"));
        securityCheck.setIsElevatorGuid(getIntent().getStringExtra("elevatorGuid"));
        securityCheck.setIsElevatorName(getIntent().getStringExtra("elevatorName"));
        securityCheck.setOptionCode(getIntent().getStringExtra("optionCode"));
        securityCheck.setOptionName(getIntent().getStringExtra("optionName"));
        securityCheck.setIsElevatorGuid(getIntent().getStringExtra("elevatorGuid"));

        int position = getIntent().getIntExtra("position", 0);
        if (savedInstanceState == null) {
            securityCheck.setIsMstCheckSCategory("01");
            mFragments[FIRST] = SecurityCheckFragment.newInstance(securityCheck);
            securityCheck.setIsMstCheckSCategory("02");
            mFragments[SECOND] = SecurityCheckFragment.newInstance(securityCheck);
            securityCheck.setIsMstCheckSCategory("03");
            mFragments[THIRD] = SecurityCheckFragment.newInstance(securityCheck);
            securityCheck.setIsMstCheckSCategory("04");
            mFragments[FOURTH] = SecurityCheckFragment.newInstance(securityCheck);
            securityCheck.setIsMstCheckSCategory("05");
            mFragments[FIFTH] = SecurityCheckFragment.newInstance(securityCheck);
            loadMultipleRootFragment(R.id.is_security_check_container, position,
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
            mFragments[FIRST] = findFragment(SecurityCheckFragment.class);
            mFragments[SECOND] = findFragment(SecurityCheckFragment.class);
            mFragments[THIRD] = findFragment(SecurityCheckFragment.class);
            mFragments[FOURTH] = findFragment(SecurityCheckFragment.class);
            mFragments[FIFTH] = findFragment(SecurityCheckFragment.class);
        }

        showTab(FIRST);

        securityCheck.setIsMstCheckSCategory("01,02,03,04,05");
        getIsMstCheckQRequiredListFun(securityCheck);
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

    private void getIsMstCheckQRequiredListFun(SecurityCheck securityCheck) {
        SecurityCheckService securityCheckService = HttpFactory.getService(SecurityCheckService.class);
        String param = GsonUtils.toJson(securityCheck, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        securityCheckService.getIsMstCheckSRequired(requestBody)
                .compose(RxHelper.<BaseResponse<List<SecurityCheck>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<SecurityCheck>>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<List<SecurityCheck>> response) {
                        if (response.getFlag().equals("1") && response.getData() != null && response.getData().size() > 0) {
                            isMstCheckSRequiredList.clear();
                            isMstCheckSRequiredList.addAll(response.getData());
                        }
                    }
                });
    }

    @OnClick({
            R2.id.is_security_check_border_0,
            R2.id.is_security_check_border_1,
            R2.id.is_security_check_border_2,
            R2.id.is_security_check_border_3,
            R2.id.is_security_check_border_4
    })
    public void onLinearLayoutsClick(View view) {
        int i = view.getId();
        if (i == R.id.is_security_check_border_0) {
            showHideFragment(mFragments[FIRST]);
            showTab(FIRST);

        } else if (i == R.id.is_security_check_border_1) {
            showHideFragment(mFragments[SECOND]);
            showTab(SECOND);

        } else if (i == R.id.is_security_check_border_2) {
            showHideFragment(mFragments[THIRD]);
            showTab(THIRD);

        } else if (i == R.id.is_security_check_border_3) {
            showHideFragment(mFragments[FOURTH]);
            showTab(FOURTH);

        } else if (i == R.id.is_security_check_border_4) {
            showHideFragment(mFragments[FIFTH]);
            showTab(FIFTH);

        }
    }

    public void setIsMstCheckQMapFun(String key, String value) {
        isMstCheckSSaveMap.put(key, value);
    }

    @OnClick(R2.id.is_security_check_save_button)
    public void isSecurityCheckSaveButtonFun() {
        System.out.println("=========================================================================================");
        System.out.println(isMstCheckSSaveMap);
        System.out.println("=========================================================================================");
        System.out.println(isMstCheckSRequiredList);
        System.out.println("=========================================================================================");
        if (isMstCheckSSaveMap != null) {
            boolean goOnFlag = true;
            if (isMstCheckSRequiredList != null
                    && isMstCheckSRequiredList.size() > 0) {
                for (SecurityCheck s : isMstCheckSRequiredList) {
                    if (isMstCheckSSaveMap.get(s.getIsMstChkSGuid()) == null
                            || isMstCheckSSaveMap.get(s.getIsMstChkSGuid()).equals("-1")) {
                        ToastUtil.showLong(s.getIsMstChkSCategoryName() + " " + getResources().getString(R.string.is_the1) + " " + s.getIsMstChkSRowNumber() + " " + getResources().getString(R.string.is_item_is_a_necessary_option));
                        goOnFlag = false;
                        break;
                    }
                }
            }
            if (goOnFlag) {
                SecurityCheck securityCheck = new SecurityCheck();
                securityCheck.setTaskId(getIntent().getStringExtra("taskId"));
                securityCheck.setCustomGuid(getIntent().getStringExtra("customGuid"));
                securityCheck.setIsElevatorGuid(getIntent().getStringExtra("elevatorGuid"));
                securityCheck.setSetIsMstCheckSSaveMap(isMstCheckSSaveMap);
                String param = GsonUtils.toJson(securityCheck, false);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
                SecurityCheckService securityCheckService = HttpFactory.getService(SecurityCheckService.class);
                securityCheckService.saveSecurityCheck(requestBody)
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
