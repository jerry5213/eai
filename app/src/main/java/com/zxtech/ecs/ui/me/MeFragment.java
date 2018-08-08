package com.zxtech.ecs.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.SettingAdapter;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.Setting;
import com.zxtech.ecs.ui.login.LoginActivity;
import com.zxtech.ecs.ui.me.about.AboutActivity;
import com.zxtech.ecs.ui.me.feedback.FeedBackActivity;
import com.zxtech.ecs.ui.me.setting.SettingActivity;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.StatusBarUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.GlideCircleTransform;
import com.zxtech.ecs.widget.MyItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 * Created by syp523 on 2018/1/12.
 */

public class MeFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener {
    @BindView(R.id.setting_rv)
    RecyclerView setting_rv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.post_tv)
    TextView post_tv;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.company_tv)
    TextView company_tv;
    @BindView(R.id.head_iv)
    ImageView head_iv;
    @BindView(R.id.exit_btn)
    Button exit_btn;
    @BindView(R.id.modify_tv)
    TextView modify_tv;

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
        EventBus.getDefault().register(this);
        toolbar_title.setText(getString(R.string.nav_me));
        toolbar.setNavigationIcon(null);
        //toolbar.setBackgroundColor(getResources().getColor(R.color.main));
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setting_rv.setLayoutManager(linearLayoutManager);

//        settings.add(new Setting("消息", R.drawable.menu_message, null));
//        settings.add(new Setting("通讯录", R.drawable.menu_mail_list, null));
        settings.add(new Setting(getString(R.string.setting), R.drawable.menu_setting, SettingActivity.class));
//        settings.add(new Setting("公司地址", R.drawable.menu_company_address, null));
//        settings.add(new Setting("企业咨询", R.drawable.menu_company_news, null));
//        settings.add(new Setting("发票信息", R.drawable.menu_invoice, null));
        settings.add(new Setting(getString(R.string.about), R.drawable.menu_about, AboutActivity.class));
        settings.add(new Setting(getString(R.string.feedback), R.drawable.menu_suggestion_feedback, FeedBackActivity.class));
        if (!getString(R.string.agent).equals(Constants.AGENT_MNK)) {
            settings.add(new Setting(getString(R.string.share), R.drawable.menu_share, null));
            settings.add(new Setting());
            settings.add(new Setting());
        }
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

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventRefreshPermission(EventAction event) {
        if (event.getCode() == EventAction.LOGIN_REFRESH_MENU) {
            refreshPermission();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPermission();
    }

    private void refreshPermission() {
        name_tv.setText((String) SPUtils.get(mContext, "user_name", "Administrator"));
        company_tv.setText((String) SPUtils.get(mContext, "user_dept_name", "沈阳中新科技"));
        String headimgUrl = (String) SPUtils.get(mContext, "headimg_url", "");
        if (!TextUtils.isEmpty(headimgUrl)); {
            Glide.with(mContext).load(headimgUrl).error(R.drawable.default_head).centerCrop().transform(new GlideCircleTransform(getActivity())).into(head_iv);
        }

        String user_id = getUserId();
        if (TextUtils.isEmpty(user_id)) {
            exit_btn.setText(R.string.login);
        } else {
            exit_btn.setText(getString(R.string.logout));
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        if (position == 3) {
            ShareDialogFragment dialogFragment = ShareDialogFragment.newInstance();
            dialogFragment.show(((Activity) mContext).getFragmentManager(), "");

        } else {
            Class intentClass = settings.get(position).getIntentClass();
            if (intentClass != null) {
                startActivity(new Intent(mContext, intentClass));
            } else {
                ToastUtil.showLong(getString(R.string.msg7));
            }
        }

    }


    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @OnClick({R.id.exit_btn, R.id.modify_tv})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.exit_btn:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.modify_tv:
                String user_id = getUserId();
                if (TextUtils.isEmpty(user_id)) {
                    ToastUtil.showLong(getString(R.string.msg15));
                    return;
                }
                startActivity(new Intent(mContext, UserInfoActivity.class));
                break;

        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
