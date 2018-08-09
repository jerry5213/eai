package com.zxtech.is.service.task;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.attach.Attach;
import com.zxtech.is.model.s1.ElevatorAddress;
import com.zxtech.is.model.s1.Project;
import com.zxtech.is.model.s1.S1Elevator;
import com.zxtech.is.model.s1.S1Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
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
 * Created by syp600 on 2018/4/19.
 */

public interface S1Service {

    /**
     * 查询项目信息
     * @param projectGuid
     * @return
     */
    @FormUrlEncoded
    @POST("s1/selectProjectInfo")
    Observable<BaseResponse<Project>> selectProjectInfo(@Field("projectGuid") String projectGuid);

    /**
     * 查询项目地址信息
     * @param taskId
     * @param procDefKey
     * @return
     */
    @FormUrlEncoded
    @POST("s1/selectProjectAddressInfo")
    Observable<BaseResponse<S1Response>> selectProjectAddressInfo(@Field("taskId") String taskId, @Field("procDefKey") String procDefKey);

    /**
     * 查询s1信息
     * @param parmas
     * @return
     */
    @FormUrlEncoded
    @POST("s1/selectISPlanS1")
    Observable<BaseResponse<S1Response>> selectISPlanS1(@FieldMap Map<String, String> parmas);

    /**
     * 上传附件
     * @param requestBody 请求参数转json
     * @param file
     * @return
     */
    @Multipart
    @POST("s1/uploadProjectAttach")
    Observable<BaseResponse<List<Attach>>> uploadProjectAttach(@Part("param") RequestBody requestBody, @Part MultipartBody.Part file);

    /**
     * 保存地址
     * @return
     */
    @FormUrlEncoded
    @POST("elevator/saveElevatorAddress")
    Observable<BaseResponse<ElevatorAddress>> saveElevatorAddress(@FieldMap Map<String, String> parmas);

    /**
     * 保存联系人
     * @return
     */
    @POST("elevator/saveElevatorContacts")
    Observable<BaseResponse<Object>> saveElevatorContacts(@Body RequestBody requestBody);

    /**
     * 保存
     * @return
     */
    @Multipart
    @POST("s1/savePlanS1")
    Observable<BaseResponse<Boolean>> savePlanS1(@Part("param") RequestBody requestBody, @PartMap Map<String, RequestBody> files);

    /**
     * 查询项目附件
     * @param projectGuid
     * @param procDefKey
     * @return
     */
    @FormUrlEncoded
    @POST("s1/selectProjectAttach")
    Observable<BaseResponse<List<Attach>>> selectProjectAttach(@Field("projectGuid") String projectGuid, @Field("procDefKey") String procDefKey);

    /**
     * 查询产品列表
     * @param taskId
     * @param procDefKey
     * @return
     */
    @FormUrlEncoded
    @POST("s1/selectElevatorList")
    Observable<BaseResponse<List<S1Elevator>>> selectElevatorList(@Field("taskId") String taskId, @Field("procDefKey") String procDefKey);



    /**
     * 删除附件
     * @return
     */
    @FormUrlEncoded
    @POST("s1/deleteAttach")
    Observable<BaseResponse<Boolean>> deleteAttach(@Field("attachGuid") String attachGuid);

    /**
     * S1提交审核
     * @return
     */
    @POST("s1/submitPlanS1")
    Observable<BaseResponse<Boolean>> submitPlanS1(@Body RequestBody requestBody);
    /**
     * 查询产品列表
     * @param projGuid
     * @param eleGuid
     * @return
     */
    @FormUrlEncoded
    @POST("s1/selectISPlanS1Detail")
    Observable<BaseResponse<S1Response>> selectISPlanS1Deatail(@Field("projGuid") String projGuid, @Field("eleGuid") String eleGuid, @Field("procDefKey") String procDefKey);

}
