package com.zxtech.is.service.person;


import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.person.PersonMember;
import com.zxtech.is.model.project.Project;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by 692 on 2018/4/17.
 */

public interface PersonCheckService {
    @FormUrlEncoded
    @POST("selectPersonCheckList")
    Observable<PageResponse<List<PersonMember>>> selectPersonCheckList(@Field("unitGuid") String unitGuid, @Field("idCard") String idCard, @Field("projectGuid") String projectGuid, @Field("page") int page, @Field("pageSize") int pageSize);
    @FormUrlEncoded
    @POST("selectPersonCheckDetail")
    Observable<PageResponse<List<PersonMember>>> selectPersonCheckDetail(@Field("guid") String guid);

    @Multipart
    @POST("savePersonCheckPhoto")
    Observable<BaseResponse<Integer>> savePersonCheckPhoto(@Query("guid") String guid, @Part MultipartBody.Part file);

    @Multipart
    @POST("savePersonCheckName")
    Observable<BaseResponse<Integer>> savePersonCheckName(@Query("guid") String guid, @Part MultipartBody.Part sign);

    @FormUrlEncoded
    @POST("savePersonCheckInfo")
    Observable<BaseResponse<Integer>> savePersonCheckInfo(@Field("memberGuid") String memberGuid, @Field("projectGuid") String projectGuid);


    @Multipart
    @POST("savePersonCheckUpdate")
    Observable<BaseResponse<Integer>> savePersonCheckUpdate(@Part("param") RequestBody requestBody);








}
