package com.zxtech.is.service.common;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.model.common.IsMstOptions;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by syp600 on 2018/4/26.
 */

public interface IsMstOptionService {

    /**
     * 查询一级分类
     *
     * @param kind       分类
     * @param parentCode 父级编码
     * @return
     */
    @FormUrlEncoded
    @POST("common/selectParents")
    Observable<BaseResponse<List<IsMstOptions>>> selectParents(@Field("kind") String kind, @Field("parentCode") String parentCode);

    /**
     * 根据父级编码查询子分类
     *
     * @param kind       分类
     * @param parentCode 父级编码
     * @return
     */
    @FormUrlEncoded
    @POST("common/selectChildrens")
    Observable<BaseResponse<List<IsMstOptions>>> selectChildrens(@Field("kind") String kind, @Field("parentCode") String parentCode);
}
