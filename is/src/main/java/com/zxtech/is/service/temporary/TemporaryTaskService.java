package com.zxtech.is.service.temporary;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.project.PeFeDropDown;
import com.zxtech.is.model.temporary.TemporaryTask;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by syp602 on 2018/4/25.
 */

public interface TemporaryTaskService {

    /**
     * 获取临时任务列表
     *
     * @param requestBody
     * @return
     */
    @POST("temporary/selectTemporaryTask")
    Observable<BaseResponse<List<TemporaryTask>>> selectTemporaryTask(@Body RequestBody requestBody);

    /**
     * 按月份获取临时任务
     *
     * @param requestBody
     * @return
     */
    @POST("temporary/selectTemporaryTaskByMonth")
    Observable<BaseResponse<List<TemporaryTask>>> selectTemporaryTaskByMonth(@Body RequestBody requestBody);

    /**
     * 保存临时任务
     *
     * @param requestBody
     * @return
     */
    @POST("temporary/saveTemporaryTaskInfo")
    Observable<BaseResponse<Object>> saveTemporaryTaskInfo(@Body RequestBody requestBody);

    /**
     * 结束临时任务
     *
     * @param requestBody
     * @return
     */
    @POST("temporary/completeTemporaryTask")
    Observable<BaseResponse<Object>> completeTemporaryTask(@Body RequestBody requestBody);

    /**
     * 根据项目ID获取电梯信息
     *
     * @param projectGuid
     * @return
     */
    @FormUrlEncoded
    @POST("temporary/selectElevatorByProjectGuid")
    Observable<BaseResponse<List<TemporaryTask>>> selectElevatorByProjectGuid(@Field("projectGuid") String projectGuid);

    /**
     * 根据电梯ID获取团队人员
     *
     * @param elevatorGuid
     * @return
     */
    @FormUrlEncoded
    @POST("temporary/selectUserByElevatorGuid")
    Observable<BaseResponse<List<PeFeDropDown>>> selectUserByElevatorGuid(@Field("elevatorGuid") String elevatorGuid);

    /**
     * 根据人员ID获取即时问题
     *
     * @param userId
     * @param tasktype
     * @param pageNum
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("temporary/selectQuestionInfoByUserId")
    Observable<PageResponse<List<TemporaryTask>>> selectQuestionInfoByUserId(@Field("userId") String userId, @Field("tasktype") String tasktype, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);
}
