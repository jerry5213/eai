package com.zxtech.esp.ui.more;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;
import com.zxtech.common.util.SPCache;
import com.zxtech.common.util.T;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.R;
import com.zxtech.esp.R2;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.ui.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnTouch;
import cz.msebera.android.httpclient.Header;

/**
 * Created by syp521 on 2018/4/23.
 */

public class UserInfoFaceActivity extends BaseActivity {

    @BindView(R2.id.captureButton)
    TextView captureButton;
    @BindView(R2.id.camera)
    CameraView cameraView;

    private boolean pendingVideoCapture;
    private boolean capturingVideo;
    private int cameraMethod = CameraKit.Constants.METHOD_STANDARD;
    private boolean cropOutput = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info_face;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        cameraView.setMethod(cameraMethod);
        cameraView.setCropOutput(cropOutput);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        cameraView.destroyDrawingCache();
        cameraView = null;
        super.onDestroy();
    }

    public void imageCaptured(CameraKitImage image) {
        byte[] jpeg = image.getJpeg();
        long callbackTime = System.currentTimeMillis();
        cameraView.stop();
        upFile(image.getBitmap());
    }

    @OnTouch(R2.id.captureButton)
    boolean onTouchCapture(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                pendingVideoCapture = true;
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pendingVideoCapture) {
                            capturingVideo = true;
                            cameraView.captureVideo();
                        }
                    }
                }, 250);
                break;
            }

            case MotionEvent.ACTION_UP: {
                pendingVideoCapture = false;

                if (capturingVideo) {
                    capturingVideo = false;
                    cameraView.stopVideo();
                } else {
                    cameraView.captureImage(new CameraKitEventCallback<CameraKitImage>() {
                        @Override
                        public void callback(CameraKitImage event) {
                            imageCaptured(event);
                        }
                    });
                }
                break;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public void upFile(Bitmap photodata) {

        String actionUrl = AppConfig.getHOST()+"face/checkFace.do";

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
            photodata.compress(Bitmap.CompressFormat.PNG, 30, baos);
            //photodata.compress(Bitmap.CompressFormat.JPEG, 85, baos);
            float zoom = (float) Math.sqrt(100 * 1024 / (float) baos.toByteArray().length);
            Matrix matrix = new Matrix();
            matrix.setScale(zoom, zoom);
            Bitmap result = Bitmap.createBitmap(photodata, 0, 0, photodata.getWidth(), photodata.getHeight(), matrix, true);
            baos.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 85, baos);
            while (baos.toByteArray().length > 100 * 1024) {
                System.out.println(baos.toByteArray().length);
                matrix.setScale(0.9f, 0.9f);
                result = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
                baos.reset();
                result.compress(Bitmap.CompressFormat.JPEG, 85, baos);
            }

            baos.close();
            byte[] buffer = baos.toByteArray();
            System.out.println("图片的大小：" + buffer.length);

            //将图片的字节流数据加密成base64字符输出
            String photo = Base64.encodeToString(buffer, 0, buffer.length, Base64.NO_WRAP);

            //photo=URLEncoder.encode(photo,"UTF-8");
            RequestParams params = new RequestParams();
            params.put("photo", photo);
            params.put("uid",getUserId());//传输的字符数据
            params.put("userInfo",getUserLoginAccount());//传输的字符数据

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(actionUrl, params, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    super.onStart();
                    showProgress();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String result = new String(responseBody);
                    Gson gson = new Gson();
                    Map map =gson.fromJson(result, Map.class);
                    if(map.containsKey("status")){

                        double status = (double)map.get("status");
                        if(status == 1){
                            SPCache.getInstance(UserInfoFaceActivity.this).setString(C.FACE_INFO_STATUS,"1");
                            setResult(4);
                            finish();
                        }
                        T.showToast(UserInfoFaceActivity.this,map.get("msg").toString());
                    }
                    cameraView.start();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    String result = new String(responseBody);
                    Gson gson = new Gson();
                    Map map =gson.fromJson(result, Map.class);
                    if(map.containsKey("status")){
                        T.showToast(UserInfoFaceActivity.this, map.get("msg").toString());
                    }
                    cameraView.start();
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    dismissProgress();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
