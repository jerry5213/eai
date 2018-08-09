package com.zxtech.is.net;

import android.text.TextUtils;
import android.util.Log;


import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggerInterceptor implements Interceptor {

    public static final String TAG = "HttpClient";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(boolean showResponse) {
        this.showResponse = showResponse;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();
            Log.d(TAG, "method : " + request.method() + "  ║  url : " + url);
            if (headers != null && headers.size() > 0) {
                //Logger.d("headers : " + headers.toString());
            }

            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    //Logger.d("requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.d(TAG, "requestBody's content : " + bodyToString(request));
                    } else {
                        //Logger.e("requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
//            e.printStackTrace();
        }
    }

    private Response logForResponse(Response response) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.d(TAG, "url : " + clone.request().url() + "  ║  code : " + clone.code() + "  ║  protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                //Logger.d("message : " + clone.message());

                if (showResponse) {
                    ResponseBody body = clone.body();
                    if (body != null) {
                        MediaType mediaType = body.contentType();
                        if (mediaType != null) {
                            //Logger.d("responseBody's contentType : " + mediaType.toString());
                            if (isText(mediaType)) {
                                String resp = body.string();
                                //打印json格式或者xml格式日志
                                switch (mediaType.subtype()) {
                                    case "xml":
                                        Log.d(TAG, resp);
                                        break;
                                    case "json":
                                        Log.d(TAG, resp);
                                        break;
                                    default:
                                        Log.d(TAG, resp);
                                        break;
                                }
                                body = ResponseBody.create(mediaType, resp);
                                return response.newBuilder().body(body).build();
                            } else {
                                Log.d(TAG, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                            }
                        }
                    }
                }
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return response;
    }


    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}