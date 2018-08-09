package com.zxtech.is.service.login;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.BaseResponseForLogin;
import com.zxtech.is.model.login.Login;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("oauth/token")
    Observable<BaseResponseForLogin> login(@FieldMap Map<String, Object> map);

    @POST("rbac/judgeFuncControlType")
    Observable<BaseResponse<Map<String, Object>>> judgeFuncControlType(@Body RequestBody requestBody);

}
