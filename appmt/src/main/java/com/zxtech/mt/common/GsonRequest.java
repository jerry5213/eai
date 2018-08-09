package com.zxtech.mt.common;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxtech.mt.entity.BaseAccessory;
import com.zxtech.mt.entity.JsonData;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by syp523 on 2017/7/24.
 */

public class GsonRequest extends Request<String> {

    private Response.Listener<String> glistener;

    private Gson gson;

//    private Class<T> gClass;

    /**
     * 构造函数 ，可以有多个不同参数的，这里就不添加了！
     * @param method
     * @param url
     * @param listener
     */
    public GsonRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorlistener) {
        super(method, url, errorlistener);
        //初始化 参数
      //  this.gClass = gClass;
        gson=new Gson();
        glistener=listener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            //将字符流转成字符串，并且设置 字符编码 ，来自响应信息的报文都不信息
            String jsonString=new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            JsonData jsonData = gson.fromJson(jsonString, JsonData.class);
            if (Constants.SUCCESS.equals(jsonData.getStatus())) {
                Log.d("chw",jsonData.getData().toString());
                return Response.success(jsonData.getData()==null?"":jsonData.getData().toString(),HttpHeaderParser.parseCacheHeaders(response));
            }else{
                return Response.error(new ParseError(new Exception()));
            }
            //返回信息 使用 gson 直接转 对象，第二个参数 设置编码



        } catch (UnsupportedEncodingException e) {
            // 出错的时候，将错误信息重新调出
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        glistener.onResponse(response);
    }
}
