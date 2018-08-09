package com.zxtech.is.service.project;

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

public interface ProjectItemAssignedService {

    @FormUrlEncoded
    @POST("project/projectItemAssigned/selectPeFe")
    Observable<BaseResponse<Map<String, List<PeFeDropDown>>>> getPeFe(@Field("projectId") String projectId);


    @POST("project/projectItemAssigned/saveIteamAssigned")
    Observable<BaseResponse<Integer>> saveIteamAssigned(@Body RequestBody info);

    @POST("project/projectItemAssigned/selectProjecctInformaiton")
    Observable<BaseResponse<Map<String, Object>>> getProjecctInformaiton(@Body String projectId);


}
