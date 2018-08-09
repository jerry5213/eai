package com.zxtech.is.service.install;

import com.zxtech.is.common.net.PageResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by syp688 on 4/30/2018.
 */

public interface InstallService {
    @POST("install/getInstallList")
    Observable<PageResponse<List<Map<String, Object>>>> getInstallList(@Query("projectName") String projectName, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);
}
