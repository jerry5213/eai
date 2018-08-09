package com.zxtech.is.service.engineer;

import com.zxtech.is.common.net.PageResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by syp688 on 5/2/2018.
 */

public interface EngineerService {
    @POST("s1/getCommittedTaskList")
    Observable<PageResponse<List<Map<String, Object>>>> getEngineerList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    @POST("s2/getCommittedTaskList")
    Observable<PageResponse<List<Map<String, Object>>>> getEngineersS2List(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    @POST("s3/getCommittedTaskList")
    Observable<PageResponse<List<Map<String, Object>>>> getEngineerS3List(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);
}
