package com.zxtech.ecs.ui.login;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxtech.ecs.App;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BuildConfig;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.UserManager;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.service.LocationService;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.gks.common.SPUtils;
import com.zxtech.gks.model.vo.login.LoginVO;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 * Created by syp523 on 2018/1/31.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_name_edittext)
    AutoCompleteTextView login_name_edittext;
    @BindView(R.id.login_pwd_edittext)
    EditText login_pwd_edittext;
    @BindView(R.id.version_tv)
    TextView version_tv;
    @BindView(R.id.remember_cb)
    CheckBox remember_cb;
    @BindView(R.id.bg_iv)
    ImageView bg_iv;
    @BindView(R.id.logo_iv)
    ImageView logo_iv;
    @BindView(R.id.username_iv)
    ImageView username_iv;
    @BindView(R.id.psd_iv)
    ImageView psd_iv;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.reg_tv)
    TextView reg_tv;
    @BindView(R.id.findback_tv)
    TextView findback_tv;
    @BindView(R.id.wx_login_tv)
    TextView wx_login_tv;
    @BindView(R.id.login_name_line)
    View login_name_line;
    @BindView(R.id.login_pwd_line)
    View login_pwd_line;
    @BindView(R.id.vertical_line1)
    View vertical_line1;
    @BindView(R.id.vertical_line2)
    View vertical_line2;
    @BindView(R.id.vertical_line3)
    View vertical_line3;
    @BindView(R.id.country_code_et)
    EditText country_code_et;
    @BindView(R.id.country_code_line)
    View country_code_line;

    private IWXAPI api;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        login_pwd_edittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        version_tv.setText("V " + Util.getVersion(mContext));

        String userName = (String) SPUtils.get(mContext, "username", "");
        if (!TextUtils.isEmpty(userName)) {
            login_name_edittext.setText(userName);
        }

        String countryCode = (String) SPUtils.get(mContext, "country_code", "86");
        if (!TextUtils.isEmpty(countryCode)) {
            country_code_et.setText(countryCode);
        }

        if (getString(R.string.agent).equals(Constants.AGENT_MNK)) {
            bg_iv.setImageResource(R.drawable.mnk_login_bg);
            logo_iv.setImageResource(R.drawable.mnk_login_logo);
            username_iv.setImageResource(R.drawable.mnk_phone);
            psd_iv.setImageResource(R.drawable.mnk_password);
            login_btn.setBackgroundResource(R.drawable.button_main_radius);
            remember_cb.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            remember_cb.setButtonDrawable(R.drawable.mnk_remember_checkbox_style);
            reg_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            findback_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            wx_login_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            version_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            login_name_edittext.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            login_pwd_edittext.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            country_code_et.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            login_name_line.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            login_pwd_line.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            vertical_line1.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            vertical_line2.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            vertical_line3.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            country_code_line.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
        }

        Set<String> usernameSet = (Set<String>) SPUtils.get(LoginActivity.this, "username_array", new HashSet<String>());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, usernameSet.toArray(new String[usernameSet.size()]));
        login_name_edittext.setAdapter(adapter);// 设置数据适配器

        api = WXAPIFactory.createWXAPI(getActivity(), Constants.WECHAT_APP_ID);
        api.registerApp(Constants.WECHAT_APP_ID);
    }






    @OnClick({R.id.login_btn, R.id.reg_tv,R.id.wx_login_tv, R.id.findback_tv})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_btn:
                String username = login_name_edittext.getText().toString().trim();
                String pwd = login_pwd_edittext.getText().toString().trim();
                String country = country_code_et.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    ToastUtil.showLong(getString(R.string.msg20));
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showLong(getString(R.string.msg21));
                    return;
                }
                if (TextUtils.isEmpty(country)) {
                    ToastUtil.showLong(getString(R.string.msg44));
                    return;
                }
                doLogin(username, pwd, country);
                break;
            case R.id.reg_tv:
                startActivity(RegisterActivity.class);
                break;
            case R.id.wx_login_tv:
                if (BuildConfig.APPLICATION_ID.equals("com.zxtech.ecs.beta")) {
                    ToastUtil.showLong("beta版暂不提供微信登录功能");
                    return;
                }
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";//
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
                req.state = Constants.WECHAT_SDK_STATE;
                api.sendReq(req);
                finish();
                break;
            case R.id.findback_tv:
                startActivity(FindBackPasswordActivity.class);
                break;
        }

    }

    private void doLogin(final String username, final String pwd, final String country) {

        Map params = new HashMap();
        params.put("userNo", username);
        params.put("password", pwd);
        params.put("platformType", "android");
        params.put("countryCode", country);
        params.put("language", Util.convertEcsLanguage(language));

        baseResponseObservable = HttpFactory.getApiService().
                getLoginInfo(params);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<LoginVO>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<LoginVO>>(this, true) {

                    @Override
                    public void onSuccess(BaseResponse<LoginVO> response) {

                        LoginVO loginData = response.getData();
                        UserManager.saveUserInfo(mContext, loginData, remember_cb.isChecked(), username, pwd, country);
                        //存储登录成功用户名
                        Set<String> usernameSet = (Set<String>) SPUtils.get(mContext, "username_array", new HashSet<String>());
                        usernameSet.add(username);
                        SPUtils.put(mContext, "username_array", usernameSet);
                        SPUtils.put(mContext,"wx_login",false);

                        EventBus.getDefault().post(new EventAction(EventAction.LOGIN_REFRESH_MENU)); //刷新菜单
                        finish();
                        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
                    }
                });

    }




}
