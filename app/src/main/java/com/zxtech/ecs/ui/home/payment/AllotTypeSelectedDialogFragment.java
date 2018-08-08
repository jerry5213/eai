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

public class AllotTypeSelectedDialogFragment extends BaseDialogFragment {
    @BindView(R.id.equipment_tv)
    TextView equipment_tv;
    @BindView(R.id.other_tv)
    TextView other_tv;

    public SelectedCallBack callBack;

    private String currentType = "1";

    public static AllotTypeSelectedDialogFragment newInstance() {
        Bundle args = new Bundle();
        AllotTypeSelectedDialogFragment fragment = new AllotTypeSelectedDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_allot_type_selected;
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

    @OnClick({R.id.equipment_tv, R.id.other_tv, R.id.confirm_tv})
    public void onClick(View view) {
        String tag = view.getTag().toString();
        switch (view.getId()) {
            case R.id.equipment_tv:
                currentType = "1";
                equipment_tv.setBackgroundResource(R.drawable.button_main_radius);
                equipment_tv.setTextColor(getResources().getColor(R.color.white));
                other_tv.setBackgroundResource(R.drawable.solid_white_border);
                other_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                break;
            case R.id.other_tv:
                currentType = "2";
                other_tv.setBackgroundResource(R.drawable.button_main_radius);
                other_tv.setTextColor(getResources().getColor(R.color.white));
                equipment_tv.setBackgroundResource(R.drawable.solid_white_border);
                equipment_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                break;
            case R.id.confirm_tv:
                if (callBack != null) {
                    callBack.confirm(currentType);
                }
                dismiss();

                break;
        }
    }


    public interface SelectedCallBack {
        void confirm(String type);
    }
}
