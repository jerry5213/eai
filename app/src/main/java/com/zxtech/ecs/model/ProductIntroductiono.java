package com.zxtech.ecs.model;

import com.zxtech.ecs.net.BaseResponse;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/2/7
 * @desc:
 */

public class ProductIntroductiono extends BaseResponse<ProductIntroductiono> {

	/**
	 * Result : true
	 * ResultInfo : [{"Title":"异步客梯","CoverPath":"http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_1.jpg","PathList":["http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_1_1.jpg"],"Position":"ComTag2#ComTag2A1#1","DescribeList":[null]},{"Title":"小机房客梯","CoverPath":"http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_2.jpg","PathList":["http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_2_1.jpg","http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_2_2.jpg"],"Position":"ComTag2#ComTag2A1#2","DescribeList":[null,null]},{"Title":"无机房客梯","CoverPath":"http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_3.jpg","PathList":["http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_3_1.jpg","http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_3_2.jpg"],"Position":"ComTag2#ComTag2A1#3","DescribeList":[null,null]},{"Title":"小机房担架梯","CoverPath":"http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_4.jpg","PathList":["http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_4_1.jpg","http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_4_2.jpg"],"Position":"ComTag2#ComTag2A1#4","DescribeList":[null,null]}]
	 * ResultMessage :
	 */

	private boolean Result;
	private String ResultMessage;
	private List<ResultInfoBean> ResultInfo;

	public boolean isResult() {
		return Result;
	}

	public void setResult(boolean Result) {
		this.Result = Result;
	}

	public String getResultMessage() {
		return ResultMessage;
	}

	public void setResultMessage(String ResultMessage) {
		this.ResultMessage = ResultMessage;
	}

	public List<ResultInfoBean> getResultInfo() {
		return ResultInfo;
	}

	public void setResultInfo(List<ResultInfoBean> ResultInfo) {
		this.ResultInfo = ResultInfo;
	}

	public static class ResultInfoBean {
		/**
		 * Title : 异步客梯
		 * CoverPath : http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_1.jpg
		 * PathList : ["http://172.16.4.155:8008/CompanyManage/CompanyFile/ComTag2A1_1_1.jpg"]
		 * Position : ComTag2#ComTag2A1#1
		 * DescribeList : [null]
		 */

		private String Title;
		private String CoverPath;
		private String Position;
		private List<String> PathList;

		public String getTitle() {
			return Title;
		}

		public void setTitle(String Title) {
			this.Title = Title;
		}

		public String getCoverPath() {
			return CoverPath;
		}

		public void setCoverPath(String CoverPath) {
			this.CoverPath = CoverPath;
		}

		public String getPosition() {
			return Position;
		}

		public void setPosition(String Position) {
			this.Position = Position;
		}

		public List<String> getPathList() {
			return PathList;
		}

		public void setPathList(List<String> PathList) {
			this.PathList = PathList;
		}

	}
}
