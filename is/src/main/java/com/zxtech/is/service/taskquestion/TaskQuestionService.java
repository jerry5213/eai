package com.zxtech.is.service.taskquestion;

import com.zxtech.is.common.net.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by hsz on 2018/5/1.
 */

public interface TaskQuestionService {

    @Multipart
    @POST("taskquestion/saveTaskQuestion")
    Observable<BaseResponse<Object>> saveTaskQuestion(@Part("param") RequestBody requestBody, @PartMap Map<String, RequestBody> files, @Query("sortFiles") String sortFiles);
}
