package com.zxtech.ecs.model;

import android.text.TextUtils;

import com.zxtech.ecs.net.BaseResponse;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/2/28
 * @desc:
 */

public class QmsMyFeedBackEntity extends BaseResponse<QmsMyFeedBackEntity> {

	private int TotalCount;
	private int PageNumber;
	private int PageCapacity;
	private List<FeedbackListBean> FeedbackList;

	public List<FeedbackListBean> getFeedbackList() {
		return FeedbackList;
	}

	public void setFeedbackList(List<FeedbackListBean> FeedbackList) {
		this.FeedbackList = FeedbackList;
	}

	public int getTotalCount() {
		return TotalCount;
	}

	public void setTotalCount(int totalCount) {
		TotalCount = totalCount;
	}

	public int getPageNumber() {
		return PageNumber;
	}

	public void setPageNumber(int pageNumber) {
		PageNumber = pageNumber;
	}

	public int getPageCapacity() {
		return PageCapacity;
	}

	public void setPageCapacity(int pageCapacity) {
		PageCapacity = pageCapacity;
	}

	public static class FeedbackListBean {

		private String AutoGuid;
		private String JobNumber;
		private String CreateDate;
		private int TaskStatus;
		private String W_WTMS;
		private String W_ZCLX;
		private String C_ContractNo;
		private String C_XMMC;
		private boolean isPJ;

		public String getAutoGuid() {
			return AutoGuid;
		}

		public void setAutoGuid(String autoGuid) {
			AutoGuid = autoGuid;
		}

		public String getJobNumber() {
			return JobNumber;
		}

		public void setJobNumber(String jobNumber) {
			JobNumber = jobNumber;
		}

		public String getCreateDate() {

			if(!TextUtils.isEmpty(CreateDate)){
				CreateDate = CreateDate.substring(0,10);
			}
			return CreateDate;
		}

		public void setCreateDate(String createDate) {
			CreateDate = createDate;
		}

		public int getTaskStatus() {

			return TaskStatus;
		}

		public void setTaskStatus(int taskStatus) {
			TaskStatus = taskStatus;
		}

		public String getW_WTMS() {
			return W_WTMS;
		}

		public void setW_WTMS(String w_WTMS) {
			W_WTMS = w_WTMS;
		}

		public String getW_ZCLX() {
			return W_ZCLX;
		}

		public void setW_ZCLX(String w_ZCLX) {
			W_ZCLX = w_ZCLX;
		}

		public String getC_ContractNo() {
			return C_ContractNo;
		}

		public void setC_ContractNo(String c_ContractNo) {
			C_ContractNo = c_ContractNo;
		}

		public String getC_XMMC() {
			return C_XMMC;
		}

		public void setC_XMMC(String c_XMMC) {
			C_XMMC = c_XMMC;
		}

		public boolean isPJ() {
			return isPJ;
		}

		public void setPJ(boolean PJ) {
			isPJ = PJ;
		}
	}
}
