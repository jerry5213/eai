package com.zxtech.mvp.konepluginp;

import org.xwalk.core.JavascriptInterface;

import java.util.Date;

public class InJavaScriptLocalObj {

	private String str="";
	private String session_time;
	private String max;
	private String score="";
	private long startTimestamp = new Date().getTime();

	private LMSFinishListener lmsFinishListener;
	
	public void setLmsFinishListener(LMSFinishListener lmsFinishListener) {
		this.lmsFinishListener = lmsFinishListener;
	}

	public void clearLMSFinishListener(){

		lmsFinishListener=null;
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
//			String[] sessions=session_time.split(":");
//			System.out.println("LMS=====sessions>>>>>"+session_time);
//			String mint=sessions[1];
//			String second=sessions[2];
//			double bb=Double.parseDouble(second) + Integer.parseInt(mint) * 60;
			if(diffTime>20000 && lmsFinishListener!=null){
				if(null==str || "".equals(str)){
					str="100";
					System.out.println("LMSFinish=====str=100");
				}
				setScore(str);
				lmsFinishListener.onLMSFinish();
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

			session_time=value;
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getMax(){

		return max;
	}
}
