package com.zxtech.ecs.model;

/**
 * @auth: hexl
 * @date: 2018/3/1
 * @desc:
 */

public class JsonFeedbackListEntity {

	private String FKR;
	private String PageNumber;
	private String PageCapacity;
	private String TaskStatus;
	private String OderBy;
	private String Language;

	public JsonFeedbackListEntity(String FKR, String pageIndex, String pageSize,String taskStatus,String oderBy,String language) {
		this.FKR = FKR;
		PageNumber = pageIndex;
		PageCapacity = pageSize;
		TaskStatus = taskStatus;
		OderBy = oderBy;
		Language = language;
	}

	public String getFKR() {
		return FKR;
	}

	public void setFKR(String FKR) {
		this.FKR = FKR;
	}

	public String getPageNumber() {
		return PageNumber;
	}

	public void setPageNumber(String pageNumber) {
		PageNumber = pageNumber;
	}

	public String getPageCapacity() {
		return PageCapacity;
	}

	public void setPageCapacity(String pageCapacity) {
		PageCapacity = pageCapacity;
	}

	public String getTaskStatus() {
		return TaskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		TaskStatus = taskStatus;
	}

	public String getOderBy() {
		return OderBy;
	}

	public void setOderBy(String oderBy) {
		OderBy = oderBy;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}
}
