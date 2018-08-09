package com.zxtech.is.ui.me;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.me.Setting;
import com.zxtech.is.ui.login.LoginActivity;
import com.zxtech.is.ui.me.adpter.SettingAdapter;
import com.zxtech.is.util.AppUtil;
import com.zxtech.is.util.DensityUtil;
import com.zxtech.is.util.SPUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.GlideCircleTransform;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/1/12.
 */

public class MeFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener {
    @BindView(R2.id.setting_rv)
    RecyclerView setting_rv;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R2.id.post_tv)
    TextView post_tv;
    @BindView(R2.id.name_tv)
    TextView name_tv;
    @BindView(R2.id.head_iv)
    ImageView head_iv;
    @BindView(R2.id.exit_btn)
    Button exit_btn;

    List<Setting> settings = new ArrayList<>();

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar_title.setText("我的");
        toolbar.setNavigationIcon(null);
        if (SPUtils.get(AppUtil.getContext(), "username", "") != null) {
            name_tv.setText(String.valueOf(SPUtils.get(AppUtil.getContext(), "username", "")));
        }
        //toolbar.setBackgroundColor(getResources().getColor(R.color.main));
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setting_rv.setLayoutManager(linearLayoutManager);

        settings.add(new Setting("消息", R.drawable.menu_message, null));
        settings.add(new Setting("通讯录", R.drawable.menu_mail_list, null));
        settings.add(new Setting("设置", R.drawable.menu_setting, null));
        settings.add(new Setting("公司地址", R.drawable.menu_company_address, null));
        settings.add(new Setting("企业咨询", R.drawable.menu_company_news, null));
        settings.add(new Setting("发票信息", R.drawable.menu_invoice, null));
        settings.add(new Setting("关于", R.drawable.menu_about, null));
        settings.add(new Setting());
        settings.add(new Setting());
        SettingAdapter mAdapter = new SettingAdapter(mContext, R.layout.item_setting, settings);
        setting_rv.addItemDecoration(new MyItemDecoration(DensityUtil.dip2px(getActivity(), 10)));
        setting_rv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = mContext.getResources().getColor(R.color.yellow);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        post_tv.setBackground(gd);

        Glide.with(this).load(R.drawable.app_update_logo).centerCrop().transform(new GlideCircleTransform(getActivity())).into(head_iv);
    }


    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//        if (position == 0) { //

//            RxPermissions rxPermissions = new RxPermissions(getActivity());
//            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
//                @Override
//                public void accept(Boolean aBoolean) throws Exception {
//                    if (aBoolean) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                        builder.setTitle("模型升级").setMessage("是否升级模型资源？")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        download();
//                                    }
//                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                        builder.show();
//                    } else {
//                        ToastUtil.showLong("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
//                    }
//                }
//            });
//        } else {
//        }
        Class intentClass = settings.get(position).getIntentClass();
        if (intentClass != null) {
            startActivity(new Intent(mContext, intentClass));
        } else {
            ToastUtil.showLong("功能暂未开放");
        }
    }


    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @OnClick(R2.id.exit_btn)
    public void logout() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
    }

}
