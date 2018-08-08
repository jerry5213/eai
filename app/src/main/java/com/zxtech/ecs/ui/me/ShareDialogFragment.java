package com.zxtech.ecs.ui.me;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.BuildConfig;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.util.BitmapUtil;

import butterknife.OnClick;

/**
 * 微信分享
 * Created by syp523 on 2018/4/25.
 */

public class ShareDialogFragment extends BaseDialogFragment {
    private static final int THUMB_SIZE = 150;
    private IWXAPI api;

    public static ShareDialogFragment newInstance() {
        ShareDialogFragment fragment = new ShareDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @OnClick(R.id.cancel_tv)
    public void cancelAction() {
        dismiss();
    }

    @OnClick(R.id.friend_layout)
    public void friendAction() {
       share(SendMessageToWX.Req.WXSceneSession);
//        SendAuth.Req req = new SendAuth.Req();
//        req.scope = "snsapi_userinfo";//
////                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
//        req.state = Constants.WECHAT_SDK_STATE;
//        api.sendReq(req);

    }

    @OnClick(R.id.friends_layout)
    public void friendsAction() {
        share(SendMessageToWX.Req.WXSceneTimeline);
    }

    private void share(int scene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = APPConfig.BASE_URL.split("mobileapi")[0]+"app/share.html";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = getString(R.string.wx_share_title);
        msg.description = getString(R.string.wx_share_description);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);

        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
        bmp.recycle();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }



    @Override
    public int getLayoutId() {
        return R.layout.dialog_share;
    }


    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(getActivity(), Constants.WECHAT_APP_ID);
        api.registerApp(Constants.WECHAT_APP_ID);
    }

    @Override
    public boolean isBottomShow() {
        return true;
    }
}
