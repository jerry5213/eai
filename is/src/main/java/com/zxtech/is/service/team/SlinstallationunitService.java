package com.zxtech.is.service.team;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.model.team.IsSlInstallationmember;
import com.zxtech.is.model.team.Leader;
import com.zxtech.is.model.team.Slinstallationunit;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by 692 on 2018/4/17.
 */

public interface SlinstallationunitService {
    @FormUrlEncoded // @Field("unitGuid") String unitGuid
    @POST("selectIsSlInstallationmember")
    Observable<PageResponse<List<IsSlInstallationmember>>> selectIsSlInstallationmember(@Field("projectSearch") String projectSearch, @Field("unitGuid") String unitGuid,@Field("teamGuid") String teamGuid,@Field("deptGuid") String deptGuid,@Field("page") int page, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("selectSlInstailationUnit")
    Observable<PageResponse<List<Slinstallationunit>>> selectSlInstailationUnit(@Field("projectSearch") String projectSearch,@Field("deptGuid") String deptGuid,@Field("page") int page, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("selectUnitLeaders")
    Observable<BaseResponse<List<Leader>>> selectUnitLeaders(@Field("unitGuid") String unitGuid);


    @FormUrlEncoded
    @POST("selectMembersByTeamGuid")
    Observable<BaseResponse<List<IsSlInstallationmember>>> selectMembersByTeamGuid(@Field("unitGuid") String unitGuid,@Field("teamGuid") String teamGuid);


    @FormUrlEncoded
    @POST("saveTeamInfo")
    Observable<BaseResponse<String>> saveTeamInfo(@Field("unitGuid") String unitGuid,@Field("teamGuid") String teamGuid,@Field("memberGuids") String memberGuids,@Field("elevatorGuids") String elevatorGuids,@Field("leaderId") String leaderId);

    @Multipart
    @POST("saveTeamTemplate")
    Observable<BaseResponse<String>> saveTeamTemplate(@Query("unitGuid") String unitGuid,@Query("deptGuid") String deptGuid ,@Part("requestBody") RequestBody requestBody);




}
