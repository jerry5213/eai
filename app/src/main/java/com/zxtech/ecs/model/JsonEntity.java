package com.zxtech.ecs.model;

/**
 * @auth: hexl
 * @date: 2018/2/28
 * @desc:
 */

public class JsonEntity {
	private String CodeType;
	private String Language;
	private String Ex1;
	private String Ex2;
	private String Ex3;

	public JsonEntity(String codeType, String language, String ex1, String ex2, String ex3) {
		CodeType = codeType;
		Language = language;
		Ex1 = ex1;
		Ex2 = ex2;
		Ex3 = ex3;
	}

	public String getCodeType() {
		return CodeType;
	}

	public void setCodeType(String codeType) {
		CodeType = codeType;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getEx1() {
		return Ex1;
	}

	public void setEx1(String ex1) {
		Ex1 = ex1;
	}

	public String getEx2() {
		return Ex2;
	}

	public void setEx2(String ex2) {
		Ex2 = ex2;
	}

	public String getEx3() {
		return Ex3;
	}

	public void setEx3(String ex3) {
		Ex3 = ex3;
	}
}
