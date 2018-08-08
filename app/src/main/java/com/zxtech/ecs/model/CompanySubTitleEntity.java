package com.zxtech.ecs.model;

import com.google.gson.annotations.SerializedName;
import com.zxtech.ecs.net.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/2/7
 * @desc:
 */

public class CompanySubTitleEntity extends BaseResponse<CompanySubTitleEntity> {


	/**
	 * msg : null
	 * totalSize : 0
	 * data : {"Result":true,"ResultInfo":null,"ResultInfoDic":{"ComTag2A1":"客梯","ComTag2B1":"货梯","ComTag2C1":"杂物梯","ComTag2D1":"扶梯","ComTag2E1":"人行道","ComTag2F1":"配饰"},"ResultMessage":""}
	 */
	private List<ResultInfoDicBean> ResultInfoDic;

	public List<ResultInfoDicBean> getResultInfoDic() {
		return ResultInfoDic;
	}

	public void setResultInfoDic(List<ResultInfoDicBean> resultInfoDic) {
		ResultInfoDic = resultInfoDic;
	}

	public static class ResultInfoDicBean implements Serializable{

		private static final long serialVersionUID = -545211057259419119L;
		private String SeatId;

		private String SeatName;

		private String SeatName_En;

		public String getSeatId() {
			return SeatId;
		}

		public void setSeatId(String seatId) {
			SeatId = seatId;
		}

		public String getSeatName() {
			return SeatName;
		}

		public void setSeatName(String seatName) {
			SeatName = seatName;
		}

		public String getSeatName_En() {
			return SeatName_En;
		}

		public void setSeatName_En(String seatName_En) {
			SeatName_En = seatName_En;
		}

		//		/**
//		 * ComTag2A1 : 客梯
//		 * ComTag2B1 : 货梯
//		 * ComTag2C1 : 杂物梯
//		 * ComTag2D1 : 扶梯
//		 * ComTag2E1 : 人行道
//		 * ComTag2F1 : 配饰
//		 */
//
//		private String ComTag2A1;
//		private String ComTag2B1;
//		private String ComTag2C1;
//		private String ComTag2D1;
//		private String ComTag2E1;
//		private String ComTag2F1;
//
//
//
//		public String getComTag2A1() {
//			return ComTag2A1;
//		}
//
//		public void setComTag2A1(String ComTag2A1) {
//			this.ComTag2A1 = ComTag2A1;
//		}
//
//		public String getComTag2B1() {
//			return ComTag2B1;
//		}
//
//		public void setComTag2B1(String ComTag2B1) {
//			this.ComTag2B1 = ComTag2B1;
//		}
//
//		public String getComTag2C1() {
//			return ComTag2C1;
//		}
//
//		public void setComTag2C1(String ComTag2C1) {
//			this.ComTag2C1 = ComTag2C1;
//		}
//
//		public String getComTag2D1() {
//			return ComTag2D1;
//		}
//
//		public void setComTag2D1(String ComTag2D1) {
//			this.ComTag2D1 = ComTag2D1;
//		}
//
//		public String getComTag2E1() {
//			return ComTag2E1;
//		}
//
//		public void setComTag2E1(String ComTag2E1) {
//			this.ComTag2E1 = ComTag2E1;
//		}
//
//		public String getComTag2F1() {
//			return ComTag2F1;
//		}
//
//		public void setComTag2F1(String ComTag2F1) {
//			this.ComTag2F1 = ComTag2F1;
//		}
	}

}
