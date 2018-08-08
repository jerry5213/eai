package com.zxtech.ecs.standard.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by syp523 on 2018/4/25.
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APP_ID);
        try {
            boolean result = api.handleIntent(getIntent(), this);
            if (!result) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("errCode", baseResp.errCode);
//        intent.putExtra("errStr", baseResp.transaction);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);

        //形参resp 有下面两个个属性比较重要
        //1.resp.errCode
        //2.resp.transaction则是在分享数据的时候手动指定的字符创,用来分辨是那次分享(参照4.中req.transaction)
        Log.d("chw", "onResp: " + new Gson().toJson(baseResp));
        switch (baseResp.errCode) { //根据需要的情况进行处理
            case BaseResp.ErrCode.ERR_OK:

                switch (baseResp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        String code = ((SendAuth.Resp) baseResp).code;
                        EventAction<String> eventAction = new EventAction<>(EventAction.WX_LOGIN, code);
                        EventBus.getDefault().post(eventAction);
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtil.showLong("分享成功");

                        break;
                }

                //正确返回
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //认证被否决
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                //发送失败
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                //不支持错误
                break;
            case BaseResp.ErrCode.ERR_COMM:
                //一般错误
                break;
            default:
                //其他不可名状的情况
                break;

        }
        finish();
    }



}
