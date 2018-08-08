package com.zxtech.ecs.model;

import com.zxtech.ecs.net.BaseResponse;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/3/7
 * @desc:
 */

public class LogisticsInfoEntity extends BaseResponse<LogisticsInfoEntity>{

	private String Result;
	private String ErrorCode;
	private String ErrorMsg;
	private String JobNumber;
	private String CreateDate;
	private String TaskStatus;
	private List<LogisticsListBean> LogisticsList;

	public String getJobNumber() {
		return JobNumber;
	}

	public void setJobNumber(String jobNumber) {
		JobNumber = jobNumber;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public String getTaskStatus() {
		return TaskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		TaskStatus = taskStatus;
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String Result) {
		this.Result = Result;
	}

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String ErrorCode) {
		this.ErrorCode = ErrorCode;
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String ErrorMsg) {
		this.ErrorMsg = ErrorMsg;
	}

	public List<LogisticsListBean> getLogisticsList() {
		return LogisticsList;
	}

	public void setLogisticsList(List<LogisticsListBean> LogisticsList) {
		this.LogisticsList = LogisticsList;
	}

	public static class LogisticsListBean {

		/**
		 * Content1 : 现场整改
		 * Content2 : 刘晓峰
		 * Content3 : 2018/3/1 10:46:57
		 */

		private String Content1;
		private String Content2;
		private String Content3;

		public String getContent1() {
			return Content1;
		}

		public void setContent1(String Content1) {
			this.Content1 = Content1;
		}

		public String getContent2() {
			return Content2;
		}

		public void setContent2(String Content2) {
			this.Content2 = Content2;
		}

		public String getContent3() {
			return Content3;
		}

		public void setContent3(String Content3) {
			this.Content3 = Content3;
		}
	}
}
