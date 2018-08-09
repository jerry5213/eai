package com.zxtech.mt.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.common.VolleySingleton;
import com.zxtech.mt.entity.HttpResult;
import com.zxtech.mtos.R;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by syp523 on 2017/7/31.
 */

public class HttpUtil {

    private static HttpUtil mHttpUtil;
    private Gson mGson; //请求连接的前缀
   // public static final String BASE_URL = "http://172.30.5.53:8080/essos";
    //连接超时时间
    private static final int REQUEST_TIMEOUT_TIME = 30 * 1000;
    //volley请求队列
    public static RequestQueue mRequestQueue;
    private Context mContext;


    private HttpUtil(Context mContext) {
        mGson = new Gson();
        this.mContext = mContext;
        //这里使用Application创建全局的请求队列
        mRequestQueue = VolleySingleton.getVolleySingleton(mContext.getApplicationContext()).getRequestQueue();
    }

    public static HttpUtil getInstance(Context mContext) {
        if (mHttpUtil == null) {
            synchronized (HttpUtil.class) {
                if (mHttpUtil == null) {
                    mHttpUtil = new HttpUtil(mContext.getApplicationContext());
                }
            }
        }
        return mHttpUtil;
    }

    public <T> void request(String url, final Map<String, String> param, final HttpCallBack<T> httpCallBack) {
        param.put("_token",SPUtils.get(mContext,"token","").toString());
        param.put("tenant_code",SPUtils.get(mContext,"tenant_code","").toString());
        param.put("rows",Constants.SHOW_PAGE_SIZE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, (String)SPUtils.get(mContext,"IP", UIController.IP) + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (httpCallBack == null) {
                    return;
                }
                Type type = getTType(httpCallBack.getClass());
                HttpResult httpResult = mGson.fromJson(response, HttpResult.class);
                if (httpResult != null) {
                    if (Constants.ERROR.equals(httpResult.getStatus())) {//失败
                        httpCallBack.onFail(httpResult.getMsg());
                    } else {//成功 //获取data对应的json字符串

                        String json = mGson.toJson(httpResult.getData());
                        if (type == String.class) {//泛型是String，返回结果json字符串
                            httpCallBack.onSuccess((T) json);
                        } else {//泛型是实体或者
                            T t = mGson.fromJson(json, type);
                            httpCallBack.onSuccess(t);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpCallBack == null) {
                    return;
                }

                String msg = error.getMessage();
                httpCallBack.onFail(msg);
                if (error.networkResponse != null &&error.networkResponse.statusCode == 401) {
                    ToastUtil.showLong(mContext,"登录超时或在其它设备登录,请重新登录");
                }else{
                    ToastUtil.showLong(mContext, mContext.getString(R.string.msg_3));
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError { //请求参数
                return param;
            }
        }; //设置请求超时和重试
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f)); //加入到请求队列
        if (mRequestQueue != null) {
            mRequestQueue.add(stringRequest);
            stringRequest.setTag(url);
        }

    }


    public <T> void request(String url, final String errMsg, final Map<String, String> param, final HttpCallBack<T> httpCallBack) {
        param.put("_token",SPUtils.get(mContext,"token","").toString());
        param.put("tenant_code",SPUtils.get(mContext,"tenant_code","").toString());
        param.put("rows",Constants.SHOW_PAGE_SIZE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, (String)SPUtils.get(mContext,"IP","") + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (httpCallBack == null) {
                    return;
                }
                Type type = getTType(httpCallBack.getClass());
                HttpResult httpResult = mGson.fromJson(response, HttpResult.class);
                if (httpResult != null) {
                    if (Constants.ERROR.equals(httpResult.getStatus())) {//失败
                        ToastUtil.showLong(mContext,errMsg);
                        httpCallBack.onFail(httpResult.getMsg());
                    } else {//成功 //获取data对应的json字符串

                        String json = mGson.toJson(httpResult.getData());
                        if (type == String.class) {//泛型是String，返回结果json字符串
                            httpCallBack.onSuccess((T) json);
                        } else {//泛型是实体或者
                            T t = mGson.fromJson(json, type);
                            httpCallBack.onSuccess(t);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpCallBack == null) {
                    return;
                }

                String msg = error.getMessage();
                httpCallBack.onFail(msg);
                if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                    ToastUtil.showLong(mContext,"登录超时或在其它设备登录,请重新登录");
                }else{
                    ToastUtil.showLong(mContext, errMsg);
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError { //请求参数
                return param;
            }
        }; //设置请求超时和重试
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f)); //加入到请求队列
        if (mRequestQueue != null) {
            mRequestQueue.add(stringRequest);
            stringRequest.setTag(url);
        }

    }

    private Type getTType(Class<?> clazz) {
        Type mySuperClassType = clazz.getGenericSuperclass();
        Type[] types = ((ParameterizedType) mySuperClassType).getActualTypeArguments();
        if (types != null && types.length > 0) {
            return types[0];
        }
        return null;
    }


}
