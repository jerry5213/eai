package com.zxtech.esp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zxtech.common.util.SPCache;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.data.bean.LoginVO;
import com.zxtech.esp.ui.home.HomeFragment;
import com.zxtech.esp.ui.more.MoreFragment;
import com.zxtech.esp.ui.msg.MsgFragment;
import com.zxtech.esp.util.GsonCallBack;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

@Route(path = "/esp/box")
public class BoxActivity extends FragmentActivity {

    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etp_box);
        position = getIntent().getIntExtra("position",0);
        login();
    }

    private void login() {

        GsonCallBack<LoginVO> callBack = new GsonCallBack<LoginVO>(this) {
            @Override
            public void onStart() {
                showLoading(BoxActivity.this);
            }

            @Override
            protected void onDoSuccess(LoginVO bean) {
                SPCache.getInstance(context).setString(C.USER_NAME, bean.getData().getUserName());
                SPCache.getInstance(context).setString(C.PASSWORD, "123");
                SPCache.getInstance(context).setString(C.TOKEN, bean.getData().getToken());
                SPCache.getInstance(context).setString(C.DepartId, bean.getData().getDepartId());
                SPCache.getInstance(context).setString(C.USER_ID, bean.getData().getUserId());
                //getFaceInfo(bean.getData().getUserId());
                dismissLoading();
                MyApp.setUserId(bean.getData().getUserId());
                MyApp.setToken(bean.getData().getToken());
                MyApp.setPhoto_url(bean.getData().getPhoto_url());
                MyApp.setNick_name(bean.getData().getNick_name());
                //checkVersion(bean.getData());

                if(position == 0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.include_lay,
                            new MoreFragment()).commitAllowingStateLoss();
                }else if(position == 1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.include_lay,
                            new HomeFragment()).commitAllowingStateLoss();
                }else if(position == 2){
                    getSupportFragmentManager().beginTransaction().replace(R.id.include_lay,
                            new MsgFragment()).commitAllowingStateLoss();
                }
            }
        };
        GRequestParams params = new GRequestParams();
        //params.addHeader("token", SPCache.getInstance(this).getString(TOKEN));
        GRequestData requestData = new GRequestData(URL.getInstance().getLoginUrl("666666", "123"), null);
        MyApp.getInstance().getHttp().send(requestData, callBack);

    }

    protected void getFaceInfo(String uid){

        SPCache.getInstance(BoxActivity.this).setString(C.FACE_INFO_STATUS,"0");
        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(this) {

            @Override
            protected void onDoSuccess(JsonElementVO bean) {

                if(bean.getStatus() == 1){
                    SPCache.getInstance(BoxActivity.this).setString(C.FACE_INFO_STATUS,"1");
                }
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
            }
        };
        String url = AppConfig.getHOST()+"face/getUserInfo.do?uid="+uid;
        GRequestData requestData = new GRequestData(url,null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }
}