package com.zxtech.is.service.taskme;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.project.ProductInformation;
import com.zxtech.is.model.schedule.ScheduleManager;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by syp660 on 2018/4/19.
 */

public interface TaskMeService {

    @POST("taskMe/getTaskMeList")
    Observable<PageResponse<List<Map<String, Object>>>> taskMeCommon(@Query("key") String key, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    @POST("taskMe/getTaskTypeAmount")
    Observable<BaseResponse<Map<String, Object>>> getTaskTypeAmount();

    @POST("taskMe/getScheduleManageList")
    Observable<BaseResponse<List<ScheduleManager>>> getScheduleManageList(@Query("key") String key, @Query("iden") Boolean iden, @Query("taskDefKey") String taskDefKey);

    @POST("taskMe/saveTime")
    Observable<BaseResponse<List<ScheduleManager>>> saveTime(@Query("date") String date, @Query("key") String scheduleKey, @Query("scheduleGuid") String scheduleGuid, @Query("procInstId") String procInstId, @Query("taskId") String taskId);

    /**
     * 任务口或项目口进入获取项目团队分配信息、或者安装方式报备信息
     */
    @FormUrlEncoded
    @POST("taskMe/getTaskMeItemAssignedList")
    Observable<BaseResponse<List<ProductInformation>>> getTaskMeItemAssignedList(@Field("projectId") String projectId, @Field("flag") String flag, @Field("checkItemInsta") String checkItemInsta);
}
