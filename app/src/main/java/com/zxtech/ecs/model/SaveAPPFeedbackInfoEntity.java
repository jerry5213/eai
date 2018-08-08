package com.zxtech.ecs.model;

import com.zxtech.ecs.net.BaseResponse;

/**
 * @auth: hexl
 * @date: 2018/3/1
 * @desc:
 */

public class SaveAPPFeedbackInfoEntity extends BaseResponse<SaveAPPFeedbackInfoEntity>{
	private String Result;
	private String ErrorMsg;

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
}
