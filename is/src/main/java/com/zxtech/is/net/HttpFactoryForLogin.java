package com.zxtech.is.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zxtech.is.APPConfig;
import com.zxtech.is.util.AppUtil;
import com.zxtech.is.util.SPUtils;
import com.zxtech.is.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpFactoryForLogin {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 45000;
    private Retrofit retrofit;
    private Retrofit gk_retrofit;

    private HttpFactoryForLogin() {
       /* HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        File cacheFile = new File(Utils.getContext().getCacheDir(), "cache");
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor(BuildConfig.DEBUG))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().build();
                        return chain.proceed(request);
                    }
                })
//                .addNetworkInterceptor(new HttpCacheInterceptor())
//                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(APPConfig.BASE_URL_FOR_LOGIN)
                .build();
    }

    //  创建单例
    private static class SingletonHolder {
        private static final HttpFactoryForLogin INSTANCE = new HttpFactoryForLogin();
    }

    public static <T> T getService(Class<T> service) {
        return SingletonHolder.INSTANCE.retrofit.create(service);
    }
}
