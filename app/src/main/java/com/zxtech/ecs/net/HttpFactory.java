package com.zxtech.ecs.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpFactory {
    private Retrofit retrofit;

    private HttpFactory() {
       /* HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        File cacheFile = new File(Utils.getContext().getCacheDir(), "cache");
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(ApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(ApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor(BuildConfig.DEBUG))
                //  .addNetworkInterceptor(new HttpCacheInterceptor())
                //    .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(APPConfig.BASE_URL)
                .build();
    }



    //  创建单例
    private static class SingletonHolder {
        private static final HttpFactory INSTANCE = new HttpFactory();
    }

    public static ApiService getApiService() {
        return SingletonHolder.INSTANCE.retrofit.create(ApiService.class);
    }


    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public static <T> T getService(Class<T> service){
        return SingletonHolder.INSTANCE.retrofit.create(service);
    }


}
