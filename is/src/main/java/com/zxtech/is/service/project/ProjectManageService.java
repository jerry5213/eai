package com.zxtech.is.service.project;


import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.project.Project;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 692 on 2018/4/17.
 */

public interface ProjectManageService {
    @FormUrlEncoded
    @POST("getPrjList")
    Observable<PageResponse<List<Project>>> ProjectManageList(@Field("projectSearch") String projectSearch, @Field("page") int page, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("selectCheckPersonProjectBymemberGuid")
    Observable<PageResponse<List<Project>>> selectCheckPersonProjectBymemberGuid(@Field("memberGuid") String projectSearch, @Field("pageNum") int pageNum, @Field("pageSize") int pageSize);

}
