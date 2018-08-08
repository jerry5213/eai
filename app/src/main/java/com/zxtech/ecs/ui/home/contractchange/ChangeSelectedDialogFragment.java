package com.zxtech.ecs.ui.home.contractchange;

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

public class ChangeSelectedDialogFragment extends BaseDialogFragment {
    @BindView(R.id.bus_tv)
    TextView bus_tv;
    @BindView(R.id.spec_tv)
    TextView spec_tv;
    @BindView(R.id.cancel_change_tv)
    TextView cancel_change_tv;

    private static final String UNSELECTED = "0";
    private static final String SELECTED = "1";
    //商务变更
    public static final String CONTRACT_CHANGE_TYPE_BUSINESS = "1";
    //规格参数变更
    public static final String CONTRACT_CHANGE_TYPE_SPEC = "2";
    //商务变更+规格参数变更
    public static final String CONTRACT_CHANGE_TYPE_COMBINATION = "3";

    //取消变更
    public static final String CONTRACT_CHANGE_CANCEL_CHANGE = "5";

    public ChangeSelectedBack callBack;

    public static ChangeSelectedDialogFragment newInstance(int position) {
        Bundle args = new Bundle();
        ChangeSelectedDialogFragment fragment = new ChangeSelectedDialogFragment();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_contract_change_selected;
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

    @OnClick({R.id.bus_tv, R.id.spec_tv, R.id.cancel_change_tv, R.id.confirm_tv})
    public void onClick(View view) {
        String tag = view.getTag().toString();
        switch (view.getId()) {
            case R.id.bus_tv:
                if (UNSELECTED.equals(tag)) {
                    view.setTag(SELECTED);
                    view.setBackgroundResource(R.drawable.button_main_radius);
                    ((TextView) view).setTextColor(getResources().getColor(R.color.white));

                    cancel_change_tv.setTag(UNSELECTED);
                    cancel_change_tv.setBackgroundResource(R.drawable.solid_white_border);
                    cancel_change_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                } else {
                    view.setTag(UNSELECTED);
                    view.setBackgroundResource(R.drawable.solid_white_border);
                    ((TextView) view).setTextColor(getResources().getColor(R.color.default_text_grey_color));
                }
                break;
            case R.id.spec_tv:
                if (UNSELECTED.equals(tag)) {
                    view.setTag(SELECTED);
                    view.setBackgroundResource(R.drawable.button_main_radius);
                    ((TextView) view).setTextColor(getResources().getColor(R.color.white));

                    cancel_change_tv.setTag(UNSELECTED);
                    cancel_change_tv.setBackgroundResource(R.drawable.solid_white_border);
                    cancel_change_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));
                } else {
                    view.setTag(UNSELECTED);
                    view.setBackgroundResource(R.drawable.solid_white_border);
                    ((TextView) view).setTextColor(getResources().getColor(R.color.default_text_grey_color));
                }
                break;
            case R.id.cancel_change_tv:
                bus_tv.setTag(UNSELECTED);
                bus_tv.setBackgroundResource(R.drawable.solid_white_border);
                bus_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));

                spec_tv.setTag(UNSELECTED);
                spec_tv.setBackgroundResource(R.drawable.solid_white_border);
                spec_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));

                cancel_change_tv.setTag(SELECTED);
                cancel_change_tv.setBackgroundResource(R.drawable.button_main_radius);
                cancel_change_tv.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.confirm_tv:
                if (UNSELECTED.equals(bus_tv.getTag().toString()) && UNSELECTED.equals(spec_tv.getTag().toString()) && UNSELECTED.equals(cancel_change_tv.getTag().toString())) {
                    ToastUtil.showLong(getString(R.string.msg49));
                    return;
                }
                if (callBack != null) {
                    String type = CONTRACT_CHANGE_TYPE_COMBINATION;
                    if (SELECTED.equals(bus_tv.getTag().toString()) && SELECTED.equals(spec_tv.getTag().toString())) {
                        type = CONTRACT_CHANGE_TYPE_COMBINATION;
                    } else if (SELECTED.equals(spec_tv.getTag().toString())) {
                        type = CONTRACT_CHANGE_TYPE_BUSINESS;
                    } else if (SELECTED.equals(spec_tv.getTag().toString())) {
                        type = CONTRACT_CHANGE_TYPE_SPEC;
                    } else if (SELECTED.equals(cancel_change_tv.getTag().toString())) {
                        type = CONTRACT_CHANGE_CANCEL_CHANGE;
                    }
                    callBack.confirm(type, getArguments().getInt("position"));
                }
                dismiss();

                break;
        }
    }


    public interface ChangeSelectedBack {
        void confirm(String type, int position);
    }
}
