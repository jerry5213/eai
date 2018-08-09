package com.zxtech.is.service.team;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.team.TeamAddMemberEla;
import com.zxtech.is.model.team.UsrDeptName;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by syp660 on 2018/4/26.
 */

public interface ElaTeamAddPersonService {

    // 获取当前登录人员所属分公司
    @POST("elaTeam/getUsrDeptName")
    Observable<BaseResponse<List<UsrDeptName>>> getUsrDeptName();

    // 新增未知安装工
    @Multipart
    @POST("elaTeam/addPerson")
    Observable<BaseResponse<Integer>> addPerson(@Part("record") RequestBody info, @Part MultipartBody.Part file, @Part MultipartBody.Part sign);

    //新增未知安装工至黑名单
    @Multipart
    @POST("elaTeam/addBlackList")
    Observable<BaseResponse<Integer>> addBlackList(@Part("record") RequestBody info, @Part MultipartBody.Part file, @Part("illegal") RequestBody illegal,@PartMap Map<String, RequestBody> parts);

    // 将已有安装工加入黑名单
    @Multipart
    @POST("elaTeam/moveBlackList")
    Observable<BaseResponse<Integer>> moveToBlackList(@Query("memberguid") String memberguid, @Part("illegal") RequestBody illegal, @PartMap Map<String, RequestBody> parts);

    // 根据项目id查询项目下可分配班组的电梯（实际是项目下全部电梯都查询处理，有leader可以分配班组）
    @POST("elaTeam/selectTeamMemberEla")
    Observable<BaseResponse<List<TeamAddMemberEla>>> selectTeamMemberEla(@Query("projectGuid") String projectGuid);

    //新增未知安装工至班组成员
    @Multipart
    @POST("elaTeam/addTeamMember")
    Observable<BaseResponse<Integer>> addTeamMember(@Query("teamGuid") String teamGuid,@Part("record") RequestBody info, @Part MultipartBody.Part file, @Part MultipartBody.Part sign);

    // 新增已知安装工至班组成员
    @Multipart
    @POST("elaTeam/moveToTeamMember")
    Observable<BaseResponse<Integer>> moveToTeamMember(@Query("memberguid") String memberguid,@Query("teamGuid") String teamGuid);
}
