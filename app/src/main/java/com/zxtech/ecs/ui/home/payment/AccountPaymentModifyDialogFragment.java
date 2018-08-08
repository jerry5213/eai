package com.zxtech.ecs.ui.home.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/27.
 */

public class AccountPaymentModifyDialogFragment extends BaseDialogFragment {

    @BindView(R.id.type_tv)
    TextView type_tv;
    @BindView(R.id.reason_et)
    EditText reason_et;

    public AccountPaymentModifyDialogFragmentCallBack callBack;

    public static AccountPaymentModifyDialogFragment newInstance() {
        Bundle args = new Bundle();
        AccountPaymentModifyDialogFragment fragment = new AccountPaymentModifyDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_account_payment_modify;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        setwScale(0.8f);
        sethScale(0.4f);
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }

    @OnClick({R.id.type_tv, R.id.confirm_tv})
    public void onClick(View view) {
        String tag = view.getTag().toString();
        switch (view.getId()) {
            case R.id.type_tv:
                final List<String> list = new ArrayList<>();
                list.add(getString(R.string.payment_edit));
                list.add(getString(R.string.payment_back));
                new DropDownWindow(mContext, view, type_tv, list, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        type_tv.setText(list.get(p));
                    }

                };
                break;

            case R.id.confirm_tv:
                if (TextUtils.isEmpty(reason_et.getText())) {
                    ToastUtil.showLong(getString(R.string.msg51));
                    return;
                }
                if (callBack != null) {
                    callBack.applyModify(type_tv.getText().toString(),reason_et.getText().toString());
                }
                dismiss();

                break;
        }
    }


    public interface AccountPaymentModifyDialogFragmentCallBack {
        void applyModify(String type,String reason);
    }
}
