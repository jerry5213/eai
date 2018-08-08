package com.zxtech.ecs.ui.home.scheme;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ReduceConfigDetailAdapter;
import com.zxtech.ecs.model.ReduceConfig;

import butterknife.BindView;

/**
 *
 * Created by syp523 on 2018/2/27.
 */

public class ReduceConfigDetailDialogFragment extends BaseDialogFragment  {

    @BindView(R.id.content_rv)
    RecyclerView content_rv;
    @BindView(R.id.price_tv)
    TextView price_tv;
    @BindView(R.id.close_iv)
    ImageButton close_iv;
    @BindView(R.id.application_tv)
    TextView application_tv;

    private int position = 0;
    private ReduceConfigDetailAdapter mAdatper;
    private ReduceConfig reduceConfig;
    public ReduceConfigDetailDialogFragmentCallBack callBack;


    public static ReduceConfigDetailDialogFragment newInstance() {
        ReduceConfigDetailDialogFragment fragment = new ReduceConfigDetailDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    private void initView() {
        if (reduceConfig != null)
            price_tv.setText(-Integer.parseInt(reduceConfig.getReducePrice()) + "");
    }


    @Override
    public int getLayoutId() {
        return R.layout.dialog_reduce_config_detail;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        application_tv.setOnClickListener(this);
        close_iv.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        content_rv.setLayoutManager(layoutManager);
        mAdatper = new ReduceConfigDetailAdapter(getActivity(), R.layout.item_reduce_detail, reduceConfig.getReduceParams());
        content_rv.setAdapter(mAdatper);

        initView();
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setReduceConfig(ReduceConfig reduceConfig) {
        this.reduceConfig = reduceConfig;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_iv) {
            dismiss();
        } else if (v.getId() == R.id.application_tv) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.reduction_with_modified).setMessage(R.string.msg3)
                    .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            callBack.detailApplication(position);
                            dismiss();

                        }
                    }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        }
    }

    public interface ReduceConfigDetailDialogFragmentCallBack {
        void detailApplication(int position);
    }
}
