package com.zxtech.esp.ui.more;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;
import com.zxtech.common.util.T;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.R;
import com.zxtech.esp.R2;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.bean.ExamVO;
import com.zxtech.esp.ui.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by syp521 on 2018/4/23.
 */

public class ExamFaceContrastActivity extends BaseActivity {

    @BindView(R2.id.captureButton)
    Button captureButton;
    @BindView(R2.id.tv_timer)
    TextView tv_timer;
    @BindView(R2.id.camera)
    CameraView cameraView;

    private boolean pendingVideoCapture;
    private boolean capturingVideo;
    private int cameraMethod = CameraKit.Constants.METHOD_STANDARD;
    private boolean cropOutput = false;

    private MyCountDownTimer mc;
    private ExamVO.ExamInfo examInfo;
    private int reviewStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exam_face_contrast;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        cameraView.setMethod(cameraMethod);
        cameraView.setCropOutput(cropOutput);

        examInfo = (ExamVO.ExamInfo) getIntent().getSerializableExtra(C.DATA);
        reviewStatus = getIntent().getIntExtra("reviewStatus",0);

        mc = new MyCountDownTimer(10000, 1000);
        mc.start();
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

    @OnClick(R2.id.captureButton)
    public void click(){
        startExam();
    }

    public void upFile(Bitmap photodata) {

        String actionUrl = AppConfig.getHOST()+"face/contrastFace.do";

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
            params.put("name", getUserId());//传输的字符数据

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(actionUrl, params, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String result = new String(responseBody);
                    Gson gson = new Gson();
                    Map map = gson.fromJson(result, Map.class);
                    if (map.containsKey("status")) {

                        T.showToast(ExamFaceContrastActivity.this, map.get("msg").toString());
                        double status = (double) map.get("status");
                        if (status == 1) {
                            //finish();
                            runOnUiThread(new Runnable(){
                                public void run(){
                                    //执行更新ui的操作
                                    captureButton.setEnabled(true);
                                    captureButton.setBackgroundResource(R.drawable.circle_corner_button_fill_blue);
                                }
                            });
                        }else{
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    String result = new String(responseBody);
                    Gson gson = new Gson();
                    Map map = gson.fromJson(result, Map.class);
                    if (map.containsKey("status")) {
                        T.showToast(ExamFaceContrastActivity.this, map.get("msg").toString());
                    }
                    finish();
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startExam() {

        Intent intent = new Intent(this, ExamAnswerActivity.class);
        intent.putExtra(C.DATA, examInfo);
        intent.putExtra("visitor", "exam");
        intent.putExtra("reviewStatus", reviewStatus);
        startActivity(intent);
        finish();
    }

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            tv_timer.setText("距离拍摄还有" + l / 1000 + "s,请对准摄像头");
        }

        @Override
        public void onFinish() {

            tv_timer.setVisibility(View.GONE);
            cameraView.captureImage(new CameraKitEventCallback<CameraKitImage>() {
                @Override
                public void callback(CameraKitImage event) {
                    imageCaptured(event);
                }
            });
        }
    }
}
