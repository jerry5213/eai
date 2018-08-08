package com.zxtech.ecs.ui.home.quote;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DropDownVo;
import com.zxtech.ecs.model.ProjectProductInfo;
import com.zxtech.ecs.util.DateUtil;
import com.zxtech.ecs.widget.DropDownWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/19.
 */

public class PerformanceBondDialogFragment extends BaseDialogFragment {
    @BindView(R.id.performance_bond_tv)
    TextView performance_bond_tv;
    @BindView(R.id.bid_cost_et)
    EditText bid_cost_et;
    @BindView(R.id.pay_type_tv)
    TextView pay_type_tv;
    @BindView(R.id.payee_et)
    EditText payee_et;
    @BindView(R.id.after_paycost_et)
    EditText after_paycost_et;
    @BindView(R.id.letter_format_tv)
    TextView letter_format_tv;
    @BindView(R.id.letter_term_tv)
    TextView letter_term_tv;
    @BindView(R.id.return_condition_et)
    EditText return_condition_et;
    @BindView(R.id.remarks_et)
    EditText remarks_et;
    @BindView(R.id.save_tv)
    TextView save_tv;

    private ProjectProductInfo.OtherInfo otherInfo;

    public PerformanceBondCallBack callBack;

    public static final String TRUE = "true";
    public static final String FALSE = "false";

    private boolean isEdit;

    public static PerformanceBondDialogFragment newInstance() {
        return new PerformanceBondDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_performance_bond;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        String defaultPerformanceBond = FALSE;
        if (otherInfo != null) {
            if (otherInfo.getIsPerformanceBond() != null) {
                defaultPerformanceBond = otherInfo.getIsPerformanceBond();
            }
            bid_cost_et.setText(otherInfo.getPerformanceBondBidCost());
            if (otherInfo.getPayType() != null) {
                pay_type_tv.setText(otherInfo.getPayTypeText());
                pay_type_tv.setTag(otherInfo.getPayType());
            }
            payee_et.setText(otherInfo.getPBPayee());
            after_paycost_et.setText(otherInfo.getAfterPayCost());
            if (otherInfo.getLetterOfIndemnityFormat() != null) {
                letter_format_tv.setText(otherInfo.getLetterOfIndemnityFormatText());
                letter_format_tv.setTag(otherInfo.getLetterOfIndemnityFormat());
            }
            letter_term_tv.setText(otherInfo.getLetterOfIndemnityTermNotMap());
            return_condition_et.setText(otherInfo.getReturnCondition());
            remarks_et.setText(otherInfo.getRemarks());
        }
        performance_bond_tv.setText(TRUE.equals(defaultPerformanceBond)?"有":"无");
        performance_bond_tv.setTag(defaultPerformanceBond);
        selectedPerformanceBond(defaultPerformanceBond);

        if (!isEdit) {
            performance_bond_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            performance_bond_tv.setEnabled(false);
        }
    }


    @Override
    public boolean isBottomShow() {
        return false;
    }

    public void setOtherInfo(ProjectProductInfo.OtherInfo otherInfo) {
        this.otherInfo = otherInfo;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    @OnClick({R.id.performance_bond_tv, R.id.pay_type_tv, R.id.letter_format_tv,R.id.letter_term_tv, R.id.save_tv})
    public void onClick(final View view) {
        DropDownWindow dropDownWindow = null;
        final List<DropDownVo> mDatas = new ArrayList<>();
        switch (view.getId()) {
            case R.id.performance_bond_tv:
                mDatas.add(new DropDownVo(FALSE, "无"));
                mDatas.add(new DropDownVo(TRUE, "有"));
                dropDownWindow(view, mDatas);
                break;
            case R.id.pay_type_tv:
                mDatas.add(new DropDownVo("0", "请选择"));
                mDatas.add(new DropDownVo("1", "保函"));
                mDatas.add(new DropDownVo("2", "现金"));
                dropDownWindow(view, mDatas);
                break;
            case R.id.letter_format_tv:
                mDatas.add(new DropDownVo("0", "请选择"));
                mDatas.add(new DropDownVo("1", "标准"));
                mDatas.add(new DropDownVo("2", "客户"));
                dropDownWindow(view, mDatas);
                break;
            case R.id.letter_term_tv:
                new DatePickerDialog(getActivity(), listener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
            case R.id.save_tv:
                if (callBack != null) {
                    callBack.performanceBondSave(performance_bond_tv.getTag().toString(), bid_cost_et.getText().toString(), pay_type_tv.getTag().toString(), payee_et.getText().toString()
                            , after_paycost_et.getText().toString(), letter_format_tv.getTag().toString(), TextUtils.isEmpty(letter_term_tv.getText())?null:letter_term_tv.getText().toString(), return_condition_et.getText().toString(), remarks_et.getText().toString());
                }
                dismiss();
                break;

        }
    }


    private Calendar cal = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            letter_term_tv.setText(DateUtil.formatChange(year,monthOfYear,dayOfMonth,DateUtil.yyyy_MM_dd));
        }
    };

    private void selectedPerformanceBond(String value) {
        if (FALSE.equals(value)) {
            bid_cost_et.setText(null);
            bid_cost_et.setBackgroundResource(R.drawable.bg_round_border_disable);
            bid_cost_et.setEnabled(false);

            pay_type_tv.setTag("0");
            pay_type_tv.setText("请选择");
            pay_type_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            pay_type_tv.setEnabled(false);

            payee_et.setText(null);
            payee_et.setBackgroundResource(R.drawable.bg_round_border_disable);
            payee_et.setEnabled(false);

            after_paycost_et.setText(null);
            after_paycost_et.setBackgroundResource(R.drawable.bg_round_border_disable);
            after_paycost_et.setEnabled(false);

            letter_format_tv.setTag("0");
            letter_format_tv.setText("请选择");
            letter_format_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            letter_format_tv.setEnabled(false);

            letter_term_tv.setText(null);
            letter_term_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            letter_term_tv.setEnabled(false);

            return_condition_et.setText(null);
            return_condition_et.setBackgroundResource(R.drawable.bg_round_border_disable);
            return_condition_et.setEnabled(false);

            remarks_et.setText(null);
            remarks_et.setBackgroundResource(R.drawable.bg_round_border_disable);
            remarks_et.setEnabled(false);
        } else {
            bid_cost_et.setBackgroundResource(R.drawable.bg_round_border);
            bid_cost_et.setEnabled(true);

            pay_type_tv.setBackgroundResource(R.drawable.bg_round_border);
            pay_type_tv.setEnabled(true);

            payee_et.setBackgroundResource(R.drawable.bg_round_border);
            payee_et.setEnabled(true);

            after_paycost_et.setBackgroundResource(R.drawable.bg_round_border);
            after_paycost_et.setEnabled(true);

            letter_format_tv.setBackgroundResource(R.drawable.bg_round_border);
            letter_format_tv.setEnabled(true);

            letter_term_tv.setBackgroundResource(R.drawable.bg_round_border);
            letter_term_tv.setEnabled(true);

            return_condition_et.setBackgroundResource(R.drawable.bg_round_border);
            return_condition_et.setEnabled(true);

            remarks_et.setBackgroundResource(R.drawable.bg_round_border);
            remarks_et.setEnabled(true);
        }
    }

    private void dropDownWindow(final View view, final List<DropDownVo> voList) {
        new DropDownWindow(getActivity(), view, (TextView) view, voList, view.getWidth(), -2) {
            @Override
            public void initEvents(int p) {
                view.setTag(voList.get(p).getValue());
                ((TextView) view).setText(voList.get(p).getText());
                if (view.getId() == R.id.performance_bond_tv) {
                    selectedPerformanceBond(voList.get(p).getValue());
                }
            }
        };
    }

    public interface PerformanceBondCallBack {
        void performanceBondSave(String performanceBond, String bidCost, String payType, String payee, String afterPaycost, String letterFormat, String letterTerm, String returnCondition, String remarks);
    }
}
