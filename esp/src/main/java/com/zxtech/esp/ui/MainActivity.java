package com.zxtech.esp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.zxtech.common.util.T;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.R;
import com.zxtech.esp.ui.home.HomeFragment;
import com.zxtech.esp.ui.more.MoreFragment;
import com.zxtech.esp.ui.msg.MsgFragment;

/**
 * Created by syp521 on 2017/3/30.
 */

public class MainActivity extends AppCompatActivity {

    private static final int HEADER_SET = 1;

    private FragmentTabHost fragment_tab_host;
//    private int[] menu_on_ids = {R.mipmap.ic_menu_more_on, R.mipmap.ic_menu_home_on, R.mipmap.ic_menu_msg_on, R.mipmap.ic_menu_search_on};
//    private int[] menu_nol_ids = {R.mipmap.ic_menu_more_off, R.mipmap.ic_menu_home_off, R.mipmap.ic_menu_msg_off, R.mipmap.ic_menu_search_off};
    private int[] menu_on_ids = {R.mipmap.ic_menu_more_on, R.mipmap.ic_menu_home_on, R.mipmap.ic_menu_msg_on};
    private int[] menu_nol_ids = {R.mipmap.ic_menu_more_off, R.mipmap.ic_menu_home_off, R.mipmap.ic_menu_msg_off};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }

    private void setup() {
        fragment_tab_host = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragment_tab_host.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        int i = 0;
        fragment_tab_host.addTab(fragment_tab_host.newTabSpec(i + "").setIndicator(getMenuView(i)), MoreFragment.class, null);

        i++;
        fragment_tab_host.addTab(fragment_tab_host.newTabSpec(i + "").setIndicator(getMenuView(i)), HomeFragment.class, null);

        i++;
        fragment_tab_host.addTab(fragment_tab_host.newTabSpec(i + "").setIndicator(getMenuView(i)), MsgFragment.class, null);

//        i++;
//        fragment_tab_host.addTab(fragment_tab_host.newTabSpec(i + "").setIndicator(getMenuView(i)), BookFragment.class, null);
    }

    private View getMenuView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_item_main_menu, null, false);
        ImageView iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
        iv_menu.setImageDrawable(T.getDrawableSelector(getResources().getDrawable(menu_on_ids[position]), getResources().getDrawable(menu_nol_ids[position])));
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case HEADER_SET:
                MoreFragment fragment0 = (MoreFragment) getSupportFragmentManager().findFragmentByTag("0");
                HomeFragment fragment1 = (HomeFragment) getSupportFragmentManager().findFragmentByTag("1");
                if(!AppConfig.LAN.equals(AppConfig.LAN_OLD)){

                    if(fragment0 != null){
                        fragment0.initData();
                    }
                    if(fragment1 != null){
                        fragment1.initData();
                    }
                    AppConfig.LAN_OLD = AppConfig.LAN;
                }
                if(fragment0 != null){
                    fragment0.initPersonInfo();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //System.out.println("按下了back键   onBackPressed()");
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
