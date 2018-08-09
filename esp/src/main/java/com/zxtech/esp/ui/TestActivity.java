package com.zxtech.esp.ui;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;
import com.zxtech.esp.R;
import com.zxtech.esp.R2;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import cz.msebera.android.httpclient.Header;

/**
 * Created by syp521 on 2017/7/24.
 */

public class TestActivity extends AppCompatActivity {

    @BindView(R2.id.captureButton)
    TextView captureButton;

    private CameraView cameraView;

    private long captureDownTime;
    private long captureStartTime;
    private boolean pendingVideoCapture;
    private boolean capturingVideo;

    private int cameraMethod = CameraKit.Constants.METHOD_STANDARD;
    private boolean cropOutput = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_aest);
        ButterKnife.bind(this);
        cameraView = findViewById(R2.id.camera);

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
                captureDownTime = System.currentTimeMillis();
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
                    captureStartTime = System.currentTimeMillis();
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

    public void upFile(Bitmap photodata) {

        String actionUrl = "http://172.16.4.200:8080/mobileapi/face/checkFace";

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
            photodata.compress(Bitmap.CompressFormat.PNG, 30, baos);


            photodata.compress(Bitmap.CompressFormat.JPEG, 85, baos);
            float zoom = (float)Math.sqrt(100 * 1024 / (float)baos.toByteArray().length);

            Matrix matrix = new Matrix();
            matrix.setScale(zoom, zoom);

            Bitmap result = Bitmap.createBitmap(photodata, 0, 0, photodata.getWidth(), photodata.getHeight(), matrix, true);

            baos.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 85, baos);
            while(baos.toByteArray().length > 100 * 1024){
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
            params.put("name", "woshishishi");//传输的字符数据
            //String url = "http://10.0.2.2:8080/IC_Server/servlet/RegisterServlet1";


            AsyncHttpClient client = new AsyncHttpClient();
            client.post(actionUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(TestActivity.this, "头像上传成功!", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    Toast.makeText(TestActivity.this, "头像上传失败!", Toast.LENGTH_SHORT)
                            .show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
