package com.zxtech.esp.ui.home;

import android.webkit.JavascriptInterface;

import java.util.Date;

public class InJavaScriptLocalObj {

	private String str="";
	private String max;
	private long startTimestamp = new Date().getTime();
    private LMSFinishListener lmsFinishListener;

	public void setLmsFinishListener(LMSFinishListener lmsFinishListener) {
		this.lmsFinishListener = lmsFinishListener;
	}

	public InJavaScriptLocalObj() {
	}
	
	@JavascriptInterface
	public Object API() {
		System.out
				.println("=================xxxxxxxxxxxxxxxxxxxx========================");
		return this;
	}

	@JavascriptInterface
	public String LMSInitialize(String value) {
		System.out.println("LMSInitialize(" + value + ")");
		return "true";
	}

	@JavascriptInterface
	public String LMSFinish(String value) {
		System.out.println("LMSFinish=====>>>>>" + value);
		System.out.println("LMSStartTimeStamp=====>>>>>" + startTimestamp);
		long endTimeStamp = new Date().getTime();
		System.out.println("LMSEndTimeStamp=====>>>>>" + endTimeStamp);
		long diffTime = endTimeStamp - startTimestamp;
		try {
			if(diffTime>20000){
				if(null==str || "".equals(str)){
					str="100";
					System.out.println("LMSFinish=====str=100");
				}
				lmsFinishListener.onLMSFinish(str);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "true";
	}

	@JavascriptInterface
	public String LMSGetValue(String model) {

		System.out.println("LMSGetValue(" + model + ")");
		return "true";
	}

	@JavascriptInterface
	public String LMSSetValue(String model, String value) {

		System.out.println("LMSSetValue(" + model + ", " + value + ")");
		if(model.contains("raw")){
			str=value;
		}
		if(model.contains("session_time")){
		}
		if(model.contains("max")){
			max=value;
		}
		return "true";
	}

	@JavascriptInterface
	public String LMSCommit(String value) {
		System.out.println("LMSCommit=====>>>>>" + value);
		return "true";
	}

	@JavascriptInterface
	public String LMSGetErrorString(String value) {
		System.out.println("LMSGetErrorString()");
		return "";
	}

	@JavascriptInterface
	public String LMSGetLastError() {
		System.out.println("LMSGetLastError()");
		return "0";
	}

	@JavascriptInterface
	public String LMSGetDiagnostic(String value) {
		System.out.println("LMSGetDiagnostic()");
		return "";
	}

	public interface LMSFinishListener{

		void onLMSFinish(String score);
	}
}
