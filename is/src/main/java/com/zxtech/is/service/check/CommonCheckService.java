package com.zxtech.is.service.check;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.check.QualityCheck;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by hsz on 2018/5/11.
 */

public interface CommonCheckService {

    @FormUrlEncoded
    @POST("commonCheck/judgeInstallTypeFun")
    Observable<BaseResponse> judgeInstallTypeFun(@Field("elevatorGuid") String elevatorGuid);
}
