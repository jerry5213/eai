package com.zxtech.ecs.ui.home.scheme.bdspeech;

import android.app.Activity;
import android.content.SharedPreferences;

import com.baidu.speech.asr.SpeechConstant;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import static com.zxtech.ecs.common.Constants.SHARED_LANGUAGE;

/**
 * Created by fujiayi on 2017/6/13.
 */

public class OnlineRecogParams extends CommonRecogParams {


    private static final String TAG = "OnlineRecogParams";

    public OnlineRecogParams(Activity context) {
        super(context);

        stringParams.addAll(Arrays.asList(
                "_language", // 用于生成PID参数
                "_model" // 用于生成PID参数
        ));

        intParams.addAll(Arrays.asList(SpeechConstant.PROP));

        boolParams.addAll(Arrays.asList(SpeechConstant.DISABLE_PUNCTUATION));

    }


    public Map<String, Object> fetch(SharedPreferences sp,String language) {
        Map<String, Object> map = super.fetch(sp,language);
        PidBuilder builder = new PidBuilder();
        map = builder.addPidInfo(map);
        if (Locale.CHINESE.toString().equals(language)){
            map.put(SpeechConstant.PID,1537);// 生成PID， PID 在线有效
        }else{
            map.put(SpeechConstant.PID,1737);
        }
        return map;

    }

}
