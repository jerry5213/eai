package com.zxtech.ecs.ui.home.scheme.detail;

import android.app.Activity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.model.ScriptReturnParam;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.engineering.EngineeringEditActivity;
import com.zxtech.ecs.ui.home.escscheme.EscalatorSchemeActivity;
import com.zxtech.ecs.ui.home.quote.ProductEditActivity;

import java.util.List;
import java.util.Map;

import static com.zxtech.ecs.model.ScriptReturnParam.GONE;
import static com.zxtech.ecs.model.ScriptReturnParam.SET;

/**
 * 处理四个tab页面参数联动公用逻辑
 * Created by syp523 on 2018/3/27.
 */

public abstract class BaseSchemeDetailFragment extends BaseFragment {
    CallBackValue callBackValue;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        callBackValue = (CallBackValue) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBackValue = null;
    }


    protected void scriptHandle(Parameters parameters) {
        if (parameters == null || parameters.getScriptInfo().size() <= 0) {
            return;
        }

        JsonArray array = new JsonArray();
        for (Parameters.ScriptInfo info : parameters.getScriptInfo()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("ScriptName", info.getScriptName());
            jsonObject.addProperty(Constants.CODE_ELEVATORPRODUCT, getParamsValue(Constants.CODE_ELEVATORPRODUCT));
            jsonObject.addProperty(Constants.CODE_ELEVATORTYPE, getParamsValue(Constants.CODE_ELEVATORTYPE));
            jsonObject.addProperty("ControlParamList", info.getCustomScripts());
            String[] sptScripts = info.getCustomScripts().split(",");
            for (int i = 0; i < sptScripts.length; i++) {
                String code = sptScripts[i];
                String value = getParamsValue(code);
                jsonObject.addProperty(code, value);
            }
            array.add(jsonObject);
        }


        baseResponseObservable = HttpFactory.getApiService().scriptParamsLink(array.toString());
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<ScriptReturnParam>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ScriptReturnParam>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<ScriptReturnParam>> response) {
                        List<ScriptReturnParam> data = response.getData();
                        if (data.size() == 0) {
                            scriptReturnParam(data);
                            return;
                        }

                        for (ScriptReturnParam returnParam : data) {
                            if (GONE == returnParam.getOperation()) continue;//不处理
                            if (SET == returnParam.getOperation()) { //处理赋值情况
                                putParams(returnParam.getParamCode(), returnParam.getFirstParamValue());
                            } else if (returnParam.getParamCode() != null) {
                                putScriptReturnParam(returnParam.getParamCode(), returnParam);
                            }
                        }
                        scriptReturnParam(data);
                    }
                });
    }

    public abstract void scriptReturnParam(List<ScriptReturnParam> returnParams);


    protected void putParams(String key, String value) {
        callBackValue.sendMessageValue(key, value);
    }

    protected String getParamsValue(String proCode) {
        if (getActivity() instanceof SchemeDetailActivity) {
            return ((SchemeDetailActivity) getActivity()).paramMap.get(proCode);
        } else if (getActivity() instanceof EscalatorSchemeActivity) {
            return ((EscalatorSchemeActivity) getActivity()).paramMap.get(proCode);
        } else if (getActivity() instanceof ProductEditActivity){
            return ((ProductEditActivity) getActivity()).paramMap.get(proCode);
        }else{
            return ((EngineeringEditActivity) getActivity()).paramMap.get(proCode);
        }
    }

    protected Map<String, String> getAllParams() {
        if (getActivity() instanceof SchemeDetailActivity) {
            return ((SchemeDetailActivity) getActivity()).paramMap;
        } else if (getActivity() instanceof ProductEditActivity){
            return ((ProductEditActivity) getActivity()).paramMap;
        }else if (getActivity() instanceof EngineeringEditActivity){
            return ((EngineeringEditActivity) getActivity()).paramMap;
        }else {
            return ((EscalatorSchemeActivity) getActivity()).paramMap;
        }
    }


    protected void putScriptReturnParam(String key, ScriptReturnParam returnParam) {
        if (getActivity() instanceof SchemeDetailActivity) {
            ((SchemeDetailActivity) getActivity()).scriptReturnParamMap.put(key, returnParam);
        }else if (getActivity() instanceof ProductEditActivity){
            ((ProductEditActivity) getActivity()).scriptReturnParamMap.put(key, returnParam);
        }else if (getActivity() instanceof EngineeringEditActivity){
            ((EngineeringEditActivity) getActivity()).scriptReturnParamMap.put(key, returnParam);
        }
    }

    protected Map<String, ScriptReturnParam> getScriptReturnParams() {
        if (getActivity() instanceof SchemeDetailActivity) {
            return ((SchemeDetailActivity) getActivity()).scriptReturnParamMap;
        }else if (getActivity() instanceof ProductEditActivity){
            return ((ProductEditActivity) getActivity()).scriptReturnParamMap;
        } else if (getActivity() instanceof EngineeringEditActivity){
            return((EngineeringEditActivity) getActivity()).scriptReturnParamMap;
        }else {
            return null;
        }
    }

    //其它界面联动关系
    protected ScriptReturnParam getScriptReturnParam(String key) {
        if (getActivity() instanceof SchemeDetailActivity) {
            return ((SchemeDetailActivity) getActivity()).scriptReturnParamMap.get(key);
        }else if (getActivity() instanceof ProductEditActivity){
            return ((ProductEditActivity) getActivity()).scriptReturnParamMap.get(key);
        }else if (getActivity() instanceof EngineeringEditActivity){
            return ((EngineeringEditActivity) getActivity()).scriptReturnParamMap.get(key);
        } else {
            return null;
        }
    }

    //电梯类型
    protected String getElevatorType() {
        if (getActivity() instanceof SchemeDetailActivity) {
            return ((SchemeDetailActivity) getActivity()).paramMap.get(Constants.CODE_ELEVATORTYPE);
        } else if (getActivity() instanceof EscalatorSchemeActivity) {
            return ((EscalatorSchemeActivity) getActivity()).paramMap.get(Constants.CODE_ELEVATORTYPE);
        }else if (getActivity() instanceof ProductEditActivity){
            return ((ProductEditActivity) getActivity()).paramMap.get(Constants.CODE_ELEVATORTYPE);
        }else if (getActivity() instanceof EngineeringEditActivity){
            return ((EngineeringEditActivity) getActivity()).paramMap.get(Constants.CODE_ELEVATORTYPE);
        } else {
            return null;
        }
    }

    //产品
    protected String getElevatorProduct() {
        if (getActivity() instanceof SchemeDetailActivity) {
            return ((SchemeDetailActivity) getActivity()).paramMap.get(Constants.CODE_ELEVATORPRODUCT);
        } else if (getActivity() instanceof EscalatorSchemeActivity) {
            return ((EscalatorSchemeActivity) getActivity()).paramMap.get(Constants.CODE_ELEVATORPRODUCT);
        }else if (getActivity() instanceof ProductEditActivity){
            return ((ProductEditActivity) getActivity()).paramMap.get(Constants.CODE_ELEVATORPRODUCT);
        } else if (getActivity() instanceof EngineeringEditActivity){
            return ((EngineeringEditActivity) getActivity()).paramMap.get(Constants.CODE_ELEVATORPRODUCT);
        }  else {
            return null;
        }
    }
    //获取有无机房
    protected boolean getMR() {
        if (getActivity() instanceof SchemeDetailActivity) {
            return "YES".equals(((SchemeDetailActivity) getActivity()).paramMap.get(Constants.CODE_MACHINEROOM));
        } else if (getActivity() instanceof EscalatorSchemeActivity) {
            return "YES".equals(((EscalatorSchemeActivity) getActivity()).paramMap.get(Constants.CODE_MACHINEROOM));
        } else if (getActivity() instanceof ProductEditActivity){
            return "YES".equals(((ProductEditActivity) getActivity()).paramMap.get(Constants.CODE_MACHINEROOM));
        }else if (getActivity() instanceof EngineeringEditActivity){
            return "YES".equals(((EngineeringEditActivity) getActivity()).paramMap.get(Constants.CODE_MACHINEROOM));
        } else {
            return true;
        }
    }

    //是否是家用梯
    protected boolean isHouseElevator() {
        if (getActivity() instanceof SchemeDetailActivity) {
            return getString(R.string.house_elevator).equals(((SchemeDetailActivity) getActivity()).paramMap.get(Constants.CODE_ELE_TYPE));
        } else if (getActivity() instanceof EscalatorSchemeActivity) {
            return getString(R.string.house_elevator).equals(((EscalatorSchemeActivity) getActivity()).paramMap.get(Constants.CODE_ELE_TYPE));
        }else if (getActivity() instanceof ProductEditActivity){
            return getString(R.string.house_elevator).equals(((ProductEditActivity) getActivity()).paramMap.get(Constants.CODE_ELE_TYPE));
        } else if (getActivity() instanceof EngineeringEditActivity){
            return getString(R.string.house_elevator).equals(((EngineeringEditActivity) getActivity()).paramMap.get(Constants.CODE_ELE_TYPE));
        } else {
            return true;
        }
    }


    //定义一个回调接口
    public interface CallBackValue {
        void sendMessageValue(String key, String strValue);
    }
}
