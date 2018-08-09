package com.zxtech.is.service.mt;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.project.ProductInformation;
import com.zxtech.is.model.schedule.ScheduleManager;

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
 * Created by syp660 on 2018/4/19.
 */

public interface AppMtService {

    @POST("project/getProjectProgressInfo")
    Observable<PageResponse<Map<String, String>>> getProjectProgressInfo(@Query("projectno") String projectno);
    @POST("project/getProjectElevatorProgressInfo")
    Observable<PageResponse<List<Map<String, String>>>> getProjectElevatorProgressInfo(@Body RequestBody requestBody);

}
