package com.zxtech.is.service.workflow;

import com.zxtech.is.common.net.PageResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by syp660 on 2018/4/19.
 */

public interface WorkflowService {

    @POST("workflow/getHiByProcInstId")
    Observable<PageResponse<List<Map<String, Object>>>> getHiByProcInstId(@Query("procInstId") String procInstId);

}
