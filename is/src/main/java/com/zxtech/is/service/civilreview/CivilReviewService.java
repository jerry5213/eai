package com.zxtech.is.service.civilreview;

import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.PageResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by syp660 on 2018/4/19.
 */

public interface CivilReviewService {

    @POST("civilReview/taskIsPlanS1Committed")
    Observable<PageResponse<List<Map<String, Object>>>> taskIsPlanS1Committed(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

}
