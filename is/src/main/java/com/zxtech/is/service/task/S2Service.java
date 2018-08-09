package com.zxtech.is.service.task;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.s2.S2Elevator;
import com.zxtech.is.model.s2.S2Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by syp600 on 2018/5/5.
 */

public interface S2Service {

    /**
     * 查询s2信息
     * @param parmas
     * @return
     */
    @FormUrlEncoded
    @POST("s2/selectISPlanS2")
    Observable<BaseResponse<S2Response>> selectISPlanS2(@FieldMap Map<String, String> parmas);

    /**
     * 保存
     * @return
     */
    @Multipart
    @POST("s2/savePlanS2")
    Observable<BaseResponse<Boolean>> savePlanS2(@Part("param") RequestBody requestBody, @PartMap Map<String, RequestBody> files);

    /**
     * 查询产品列表
     * @param taskId
     * @param procDefKey
     * @return
     */
    @FormUrlEncoded
    @POST("s2/selectElevatorList")
    Observable<BaseResponse<List<S2Elevator>>> selectElevatorList(@Field("taskId") String taskId, @Field("procDefKey") String procDefKey);

    /**
     * S2提交审核
     * @return
     */
    @POST("s2/submitPlanS2")
    Observable<BaseResponse<Boolean>> submitPlanS2(@Body RequestBody requestBody);

}
