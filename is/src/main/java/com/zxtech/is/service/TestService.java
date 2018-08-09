package com.zxtech.is.service;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.project.Project;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by duomi on 2018/4/17.
 */

public interface TestService {
    @FormUrlEncoded
    @POST("test")
    Observable<BaseResponse<Project>> test(@Field("id") String id);

}
