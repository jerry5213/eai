package com.zxtech.is.service.check;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.check.SecurityCheck;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by hsz on 2018/4/21.
 */

public interface SecurityCheckService {

    @POST("securityCheck/getIsMstCheckSRequired")
    Observable<BaseResponse<List<SecurityCheck>>> getIsMstCheckSRequired(@Body RequestBody requestBody);

    @POST("securityCheck/getIsMstCheckS")
    Observable<BaseResponse<List<SecurityCheck>>> getIsMstCheckS(@Body RequestBody requestBody);

    @POST("securityCheck/saveSecurityCheck")
    Observable<BaseResponse<Object>> saveSecurityCheck(@Body RequestBody requestBody);
}
