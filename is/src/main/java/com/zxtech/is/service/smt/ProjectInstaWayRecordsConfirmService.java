package com.zxtech.is.service.smt;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.project.PeFeDropDown;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 692 on 2018/4/17.
 */

public interface ProjectInstaWayRecordsConfirmService {

    @POST("smt/startSMT")
    Observable<BaseResponse<Integer>> startSMT(@Body RequestBody info);

    @POST("smt/installWayApproval")
    Observable<BaseResponse<Integer>> installWayApproval(@Body RequestBody info);

    @FormUrlEncoded
    @POST("smt/installWayCancel")
    Observable<BaseResponse<Integer>> installWayCancel(@Field("procInstId") String procInstId);

}
