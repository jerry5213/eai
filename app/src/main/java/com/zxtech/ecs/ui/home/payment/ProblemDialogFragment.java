package com.zxtech.ecs.ui.home.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/27.
 */

public class ProblemDialogFragment extends BaseDialogFragment {

    @BindView(R.id.desc_tv)
    TextView desc_tv;

    public ProblemDialogFragmentCallBack callBack;

    private String desc;

    public static ProblemDialogFragment newInstance() {
        Bundle args = new Bundle();
        ProblemDialogFragment fragment = new ProblemDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_account_payment_problem;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        setwScale(0.8f);
        sethScale(0.4f);
        desc_tv.setText(desc);
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }

    @OnClick({R.id.confirm_tv})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.confirm_tv:
                if (callBack != null) {
                    callBack.solveProblem();
                }
                dismiss();

                break;
        }
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public interface ProblemDialogFragmentCallBack {
        void solveProblem();
    }
}
