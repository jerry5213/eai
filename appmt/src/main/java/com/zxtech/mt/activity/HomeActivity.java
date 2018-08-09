package com.zxtech.mt.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.mt.adapter.HomeIconAdapter;
import com.zxtech.mt.entity.Icon;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by syp523 on 2017/8/25.
 */

public class HomeActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    private LinearLayout view;

    private RecyclerView icon_recyview;
    private HomeIconAdapter adapter;
    private List<View> views = new ArrayList<>();

    private final Class[] menu = new Class[]{WorkCheckActivity.class, LocationSourceActivity.class, ElevatorInfoActivity.class, AccessoryShopActivity.class, AccessoryActivity.class, AccessScanActivity.class};

    @Override
    protected void onCreate() {
        view = (LinearLayout) mInfalter.inflate(R.layout.activity_home, null);
        main_layout.addView(view);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lps);
        setBackHide();
        title_textview.setText(getString(R.string.home));
        menu1.setTextColor(getResources().getColor(R.color.bg_blue));
        menu1_bg.setText("\ue6bb");
        menu1_bg.setTextColor(mContext.getResources().getColor(R.color.c_theme));
    }

    @Override
    protected void findView() {
        icon_recyview = findViewById(R.id.icon_recyview);
        View pager1 = mInfalter.inflate(R.layout.viewpager_homeimage, null);
        View pager2 = mInfalter.inflate(R.layout.viewpager_homeimage, null);
        View pager3 = mInfalter.inflate(R.layout.viewpager_homeimage, null);
        views.add(pager1);
        views.add(pager2);
        views.add(pager3);
        ImageView image1 = pager1.findViewById(R.id.image);
        ImageView image2 = pager2.findViewById(R.id.image);
        ImageView image3 = pager3.findViewById(R.id.image);
        image1.setImageResource(R.drawable.home_ad1);
        image2.setImageResource(R.drawable.home_ad1);
        image3.setImageResource(R.drawable.home_ad1);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        List<Icon> icon = new ArrayList<>();
        icon.add(new Icon(mContext.getString(R.string.menu_clock_attendance), "\ue65f", R.color.blue_500));
        icon.add(new Icon(getString(R.string.menu_project_orientation), "\ue7f3", R.color.teal_500));
        icon.add(new Icon(getString(R.string.menu_elevator_information), "\ue66c", R.color.orange_500));
        icon.add(new Icon(getString(R.string.menu_accessory_mall), "\ue6b9", R.color.red_500));
        icon.add(new Icon(getString(R.string.menu_accessory_inquiry), "\ue7f6", R.color.blue_500));
        icon.add(new Icon(getString(R.string.menu_project_origin), "\ue7db", R.color.teal_500));
        adapter = new HomeIconAdapter(icon);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initView() {
        GridLayoutManager mgr = new GridLayoutManager(mContext, 3);
        icon_recyview.setLayoutManager(mgr);
        icon_recyview.setAdapter(adapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(mContext, menu[position]));
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}
