package com.zxtech.ecs.ui.home.contractchange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;

/**
 * Created by syp523 on 2018/7/6.
 */

public class ChangeProtocolFragment extends BaseFragment {

    public static ChangeProtocolFragment newInstance(String projectGuid, String projectNo, String contractId, String contractChangeGuid) {
        Bundle args = new Bundle();
        ChangeProtocolFragment fragment = new ChangeProtocolFragment();
        args.putString("projectGuid", projectGuid);
        args.putString("projectNo", projectNo);
        args.putString("contractId", contractId);
        args.putString("contractChangeGuid", contractChangeGuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_protocol;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
