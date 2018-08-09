package com.zxtech.is.service.team;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.team.IsSlInstallationmember;
import com.zxtech.is.model.team.IsSlTeam;
import com.zxtech.is.model.team.Leader;
import com.zxtech.is.model.team.Slinstallationunit;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 692 on 2018/4/17.
 */

public interface IsSlTeamMemberService {


    @FormUrlEncoded
    @POST("selectIsSlTeamInfo")
    Observable<BaseResponse<IsSlTeam>> selectIsSlTeamInfo(@Field("elevatorGuids") String elevatorGuids);

    @FormUrlEncoded
    @POST("saveTeamMemberInfo")
    Observable<BaseResponse<String>> saveTeamMemberInfo(@Field("unitGuid") String unitGuid, @Field("teamGuid") String teamGuid, @Field("memberGuids") String memberGuids, @Field("elevatorGuids") String elevatorGuids, @Field("leaderId") String leaderId);

    @FormUrlEncoded
    @POST("deleteTeamInfoInfo")
    Observable<BaseResponse<String>> deleteTeamInfoInfo(@Field("unitGuid") String unitGuid, @Field("teamGuid") String teamGuid, @Field("memberGuids") String memberGuids, @Field("elevatorGuids") String elevatorGuids, @Field("leaderId") String leaderId);


}
