package com.zxtech.is.service.check;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.check.QualityCheck;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by hsz on 2018/4/21.
 */

public interface QualityCheckService {

    @POST("qualityCheck/getIsMstCheckQRequired")
    Observable<BaseResponse<List<QualityCheck>>> getIsMstCheckQRequired(@Body RequestBody requestBody);

    @POST("qualityCheck/getIsMstCheckQ")
    Observable<BaseResponse<List<QualityCheck>>> getIsMstCheckQ(@Body RequestBody requestBody);

    @POST("qualityCheck/saveQualityCheck")
    Observable<BaseResponse<Object>> saveQualityCheck(@Body RequestBody requestBody);
}
