package com.zxtech.is.service.task;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.s3.S3Elevator;
import com.zxtech.is.model.s3.S3Response;

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

public interface S3Service {

    /**
     * 查询s3信息
     * @param parmas
     * @return
     */
    @FormUrlEncoded
    @POST("s3/selectISPlanS3")
    Observable<BaseResponse<S3Response>> selectISPlanS3(@FieldMap Map<String, String> parmas);

    /**
     * 保存
     * @return
     */
    @Multipart
    @POST("s3/savePlanS3")
    Observable<BaseResponse<Boolean>> savePlanS3(@Part("param") RequestBody requestBody, @PartMap Map<String, RequestBody> files);

    /**
     * 查询产品列表
     * @param taskId
     * @param procDefKey
     * @return
     */
    @FormUrlEncoded
    @POST("s3/selectElevatorList")
    Observable<BaseResponse<List<S3Elevator>>> selectElevatorList(@Field("taskId") String taskId, @Field("procDefKey") String procDefKey);

    /**
     * S3提交审核
     * @return
     */
    @POST("s3/submitPlanS3")
    Observable<BaseResponse<Boolean>> submitPlanS3(@Body RequestBody requestBody);

}
