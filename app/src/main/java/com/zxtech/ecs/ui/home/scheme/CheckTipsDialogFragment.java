package com.zxtech.ecs.ui.home.scheme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/2/27.
 */

public class CheckTipsDialogFragment extends BaseDialogFragment {

    @BindView(R.id.content_rv)
    RecyclerView content_rv;
    @BindView(R.id.close_tv)
    TextView close_tv;
    @BindView(R.id.non_standard_tv)
    TextView non_standard_tv;

    private List<String> mData = new ArrayList<>();
    public CheckTipsCallBack callBack;
    private boolean isNonstandard;


    public static CheckTipsDialogFragment newInstance() {
        return new CheckTipsDialogFragment();
    }


    @Override
    public int getLayoutId() {
        return R.layout.dialog_check_tips;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (isNonstandard) {
            non_standard_tv.setVisibility(View.VISIBLE);
        }
        close_tv.setOnClickListener(this);
        non_standard_tv.setOnClickListener(this);

        LinearLayoutManager colorLayoutManager = new LinearLayoutManager(getActivity());
        colorLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        content_rv.setLayoutManager(colorLayoutManager);
        CommonAdapter<String> mAdatper = new CommonAdapter<String>(getContext(), R.layout.item_check_tips, mData) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.item_content_tv, (position + 1) + "." + s);
            }
        };
        content_rv.setAdapter(mAdatper);
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }


    public void setData(String msg) {
        if (msg != null) {
            String[] split = msg.split("；");
            for (int i = 0; i < split.length; i++) {
                if (!TextUtils.isEmpty(split[i])) {
                    mData.add(split[i]);
                }
            }
        }
    }

    public void setNonstandard(boolean nonstandard) {
        isNonstandard = nonstandard;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_tv) {
            dismiss();
        } else if (v.getId() == R.id.non_standard_tv) {
            if (callBack != null) {
                callBack.nonStandardConfirm();
            }
        }
    }


    public interface CheckTipsCallBack {
        void nonStandardConfirm();
    }

}
