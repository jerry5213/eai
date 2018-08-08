package com.zxtech.ecs.ui.home.quote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DropDownVo;
import com.zxtech.ecs.model.ProjectProductInfo;
import com.zxtech.ecs.widget.DropDownWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/19.
 */

public class BidBondDialogFragment extends BaseDialogFragment {

    @BindView(R.id.bid_bond_tv)
    TextView bid_bond_tv;
    @BindView(R.id.bid_cost_et)
    EditText bid_cost_et;
    @BindView(R.id.change_performbond_tv)
    TextView change_performbond_tv;
    @BindView(R.id.bid_bond_type_tv)
    TextView bid_bond_type_tv;
    @BindView(R.id.payee_et)
    EditText payee_et;
    @BindView(R.id.save_tv)
    TextView save_tv;

    private ProjectProductInfo.OtherInfo otherInfo;
    private HashMap<String, String> valueTextMap = new HashMap<>();

    public BidBondCallBack callBack;

    public static final String TRUE = "true";
    public static final String FALSE = "false";
    private static final String NULL = "0"; //请选择
    private static final String LETTER = "1"; //保函
    private static final String MONEY = "2";//现金

    private boolean isEdit;

    public static BidBondDialogFragment newInstance() {
        return new BidBondDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_bid_bond;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        initMap();
        initView();
    }

    private void initView() {
        String defaultBidBond = FALSE;
        if (otherInfo != null) {
            if (otherInfo.getIsBid() != null) {
                defaultBidBond = otherInfo.getIsBid();
            }
            bid_cost_et.setText(otherInfo.getBidCost());
            if (otherInfo.getIsChangePerformanceBond() != null) {
                change_performbond_tv.setText(valueTextMap.get(otherInfo.getIsChangePerformanceBond()));
                change_performbond_tv.setTag(otherInfo.getIsChangePerformanceBond());
            }
            if (otherInfo.getDepositType() != null) {
                bid_bond_type_tv.setText(valueTextMap.get(otherInfo.getDepositType()));
                bid_bond_type_tv.setTag(otherInfo.getDepositType());
            }
            payee_et.setText(otherInfo.getPayee());
        }
        bid_bond_tv.setText(valueTextMap.get(defaultBidBond));
        bid_bond_tv.setTag(defaultBidBond);
        selectedBidBond(defaultBidBond);

        if (!isEdit) {
            bid_bond_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            bid_bond_tv.setEnabled(false);
        }
    }

    private void initMap() {
        valueTextMap.put(FALSE, "无");
        valueTextMap.put(TRUE, "有");
        valueTextMap.put(NULL, "请选择");
        valueTextMap.put(LETTER, "保函");
        valueTextMap.put(MONEY, "现金");
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

    @OnClick({R.id.bid_bond_tv, R.id.change_performbond_tv, R.id.bid_bond_type_tv, R.id.save_tv})
    public void onClick(final View view) {
        DropDownWindow dropDownWindow = null;
        final List<DropDownVo> mDatas = new ArrayList<>();
        switch (view.getId()) {
            case R.id.bid_bond_tv:
                mDatas.add(new DropDownVo(FALSE, "无"));
                mDatas.add(new DropDownVo(TRUE, "有"));
                dropDownWindow(view, mDatas);
                break;
            case R.id.change_performbond_tv:
                mDatas.add(new DropDownVo(FALSE, "无"));
                mDatas.add(new DropDownVo(TRUE, "有"));
                dropDownWindow(view, mDatas);
                break;
            case R.id.bid_bond_type_tv:
                mDatas.add(new DropDownVo(NULL, "请选择"));
                mDatas.add(new DropDownVo(LETTER, "保函"));
                mDatas.add(new DropDownVo(MONEY, "现金"));
                dropDownWindow(view, mDatas);
                break;
            case R.id.save_tv:
                if (callBack != null) {
                    callBack.bidBondSave(bid_bond_tv.getTag().toString(), bid_cost_et.getText().toString(), change_performbond_tv.getTag().toString(), bid_bond_type_tv.getTag().toString(), payee_et.getText().toString());
                }
                dismiss();
                break;

        }
    }

    private void selectedBidBond(String value) {
        if (FALSE.equals(value)) {
            bid_cost_et.setText(null);
            bid_cost_et.setBackgroundResource(R.drawable.bg_round_border_disable);
            bid_cost_et.setEnabled(false);

            change_performbond_tv.setTag(FALSE);
            change_performbond_tv.setText("无");
            change_performbond_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            change_performbond_tv.setEnabled(false);

            bid_bond_type_tv.setTag("0");
            bid_bond_type_tv.setText("请选择");
            bid_bond_type_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            bid_bond_type_tv.setEnabled(false);

            payee_et.setText(null);
            payee_et.setBackgroundResource(R.drawable.bg_round_border_disable);
            payee_et.setEnabled(false);
        } else {
            bid_cost_et.setBackgroundResource(R.drawable.bg_round_border);
            bid_cost_et.setEnabled(true);

            change_performbond_tv.setBackgroundResource(R.drawable.bg_round_border);
            change_performbond_tv.setEnabled(true);

            bid_bond_type_tv.setBackgroundResource(R.drawable.bg_round_border);
            bid_bond_type_tv.setEnabled(true);

            payee_et.setBackgroundResource(R.drawable.bg_round_border);
            payee_et.setEnabled(true);
        }

    }

    private void dropDownWindow(final View view, final List<DropDownVo> voList) {
        new DropDownWindow(getActivity(), view, (TextView) view, voList, view.getWidth(), -2) {
            @Override
            public void initEvents(int p) {
                view.setTag(voList.get(p).getValue());
                ((TextView) view).setText(voList.get(p).getText());
                if (view.getId() == R.id.bid_bond_tv) {
                    selectedBidBond(voList.get(p).getValue());
                }
            }
        };
    }

    public interface BidBondCallBack {
        void bidBondSave(String bidBond, String bidCost, String changePerformbond, String bidBondType, String payee);
    }
}
