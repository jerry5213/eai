package com.zxtech.ecs.model;

import com.zxtech.ecs.net.BaseResponse;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/3/1
 * @desc:
 */

public class QmsFeedbackInfoEntity extends BaseResponse<QmsFeedbackInfoEntity> {

	private FeedbackInfoBean FeedbackInfo;
	private List<QmsMaterialRequirement> FeedbackPartList;
	private List<FileList> FileList;

	public FeedbackInfoBean getFeedbackInfo() {
		return FeedbackInfo;
	}

	public List<QmsMaterialRequirement> getFeedbackPartList() {
		return FeedbackPartList;
	}

	public List<com.zxtech.ecs.model.FileList> getFileList() {
		return FileList;
	}

	public static class FeedbackInfoBean {

		/**
		 * AutoGuid : 9cf06c3f-77ed-4838-8fa7-6fe8ad2988f0
		 * JobNumber :
		 * FKR : 反馈人1
		 * FKRName : 反馈人名字
		 * W_WTMS :
		 * W_ZCLX : 1
		 * W_ZCLXValue : 物料支持
		 * C_ContractNo : 合同号
		 * C_XMMC : 项目名称
		 * C_DTLY :
		 * C_FWZT :
		 * C_FWZTValue :
		 * C_KXSJ : null
		 * C_CPXH :
		 * F_ZXT :
		 * F_ZXTValue :
		 * F_ZJ :
		 * F_ZJValue :
		 * F_BJ :
		 * F_BJValue :
		 * F_LJ :
		 * F_LJValue :
		 * F_FailureMode :
		 * F_FailureModeValue :
		 * NM_FKYY :
		 * NM_FKYYValue :
		 * NM_YZD :
		 * NM_YZDValue :
		 * NM_FSPL :
		 * NM_FSPLValue :
		 * P_BJDZ :
		 * P_BJLXR :
		 * P_BJLXDH :
		 */

		private String AutoGuid;
		private String JobNumber;
		private String FKR;
		private String FKRName;
		private String W_WTMS;
		private String W_ZCLX;
		private String W_ZCLXValue;
		private String C_ContractNo;
		private String C_XMMC;
		private String C_DTLY;
		private String C_FWZT;
		private String C_FWZTValue;
		private String C_KXSJ;
		private String C_CPXH;
		private String F_ZXT;
		private String F_ZXTValue;
		private String F_ZJ;
		private String F_ZJValue;
		private String F_BJ;
		private String F_BJValue;
		private String F_LJ;
		private String F_LJValue;
		private String F_FailureMode;
		private String F_FailureModeValue;
		private String NM_FKYY;
		private String NM_FKYYValue;
		private String NM_YZD;
		private String NM_YZDValue;
		private String NM_FSPL;
		private String NM_FSPLValue;
		private String P_BJDZ;
		private String P_BJLXR;
		private String P_BJLXDH;

		private String E_WLDHSJ;
		private String E_ZGWCSJ;
		private String E_JJQK;
		private String E_MYDDF;
		private String E_XXPJ;

		public String getE_WLDHSJ() {
			return E_WLDHSJ;
		}

		public void setE_WLDHSJ(String e_WLDHSJ) {
			E_WLDHSJ = e_WLDHSJ;
		}

		public String getE_ZGWCSJ() {
			return E_ZGWCSJ;
		}

		public void setE_ZGWCSJ(String e_ZGWCSJ) {
			E_ZGWCSJ = e_ZGWCSJ;
		}

		public String getE_JJQK() {
			return E_JJQK;
		}

		public void setE_JJQK(String e_JJQK) {
			E_JJQK = e_JJQK;
		}

		public String getE_MYDDF() {
			return E_MYDDF;
		}

		public void setE_MYDDF(String e_MYDDF) {
			E_MYDDF = e_MYDDF;
		}

		public String getE_XXPJ() {
			return E_XXPJ;
		}

		public void setE_XXPJ(String e_XXPJ) {
			E_XXPJ = e_XXPJ;
		}

		public String getAutoGuid() {
			return AutoGuid;
		}

		public void setAutoGuid(String AutoGuid) {
			this.AutoGuid = AutoGuid;
		}

		public String getJobNumber() {
			return JobNumber;
		}

		public void setJobNumber(String JobNumber) {
			this.JobNumber = JobNumber;
		}

		public String getFKR() {
			return FKR;
		}

		public void setFKR(String FKR) {
			this.FKR = FKR;
		}

		public String getFKRName() {
			return FKRName;
		}

		public void setFKRName(String FKRName) {
			this.FKRName = FKRName;
		}

		public String getW_WTMS() {
			return W_WTMS;
		}

		public void setW_WTMS(String W_WTMS) {
			this.W_WTMS = W_WTMS;
		}

		public String getW_ZCLX() {
			return W_ZCLX;
		}

		public void setW_ZCLX(String W_ZCLX) {
			this.W_ZCLX = W_ZCLX;
		}

		public String getW_ZCLXValue() {
			return W_ZCLXValue;
		}

		public void setW_ZCLXValue(String W_ZCLXValue) {
			this.W_ZCLXValue = W_ZCLXValue;
		}

		public String getC_ContractNo() {
			return C_ContractNo;
		}

		public void setC_ContractNo(String C_ContractNo) {
			this.C_ContractNo = C_ContractNo;
		}

		public String getC_XMMC() {
			return C_XMMC;
		}

		public void setC_XMMC(String C_XMMC) {
			this.C_XMMC = C_XMMC;
		}

		public String getC_DTLY() {
			return C_DTLY;
		}

		public void setC_DTLY(String C_DTLY) {
			this.C_DTLY = C_DTLY;
		}

		public String getC_FWZT() {
			return C_FWZT;
		}

		public void setC_FWZT(String C_FWZT) {
			this.C_FWZT = C_FWZT;
		}

		public String getC_FWZTValue() {
			return C_FWZTValue;
		}

		public void setC_FWZTValue(String C_FWZTValue) {
			this.C_FWZTValue = C_FWZTValue;
		}

		public String getC_KXSJ() {
			return C_KXSJ;
		}

		public void setC_KXSJ(String C_KXSJ) {
			this.C_KXSJ = C_KXSJ;
		}

		public String getC_CPXH() {
			return C_CPXH;
		}

		public void setC_CPXH(String C_CPXH) {
			this.C_CPXH = C_CPXH;
		}

		public String getF_ZXT() {
			return F_ZXT;
		}

		public void setF_ZXT(String F_ZXT) {
			this.F_ZXT = F_ZXT;
		}

		public String getF_ZXTValue() {
			return F_ZXTValue;
		}

		public void setF_ZXTValue(String F_ZXTValue) {
			this.F_ZXTValue = F_ZXTValue;
		}

		public String getF_ZJ() {
			return F_ZJ;
		}

		public void setF_ZJ(String F_ZJ) {
			this.F_ZJ = F_ZJ;
		}

		public String getF_ZJValue() {
			return F_ZJValue;
		}

		public void setF_ZJValue(String F_ZJValue) {
			this.F_ZJValue = F_ZJValue;
		}

		public String getF_BJ() {
			return F_BJ;
		}

		public void setF_BJ(String F_BJ) {
			this.F_BJ = F_BJ;
		}

		public String getF_BJValue() {
			return F_BJValue;
		}

		public void setF_BJValue(String F_BJValue) {
			this.F_BJValue = F_BJValue;
		}

		public String getF_LJ() {
			return F_LJ;
		}

		public void setF_LJ(String F_LJ) {
			this.F_LJ = F_LJ;
		}

		public String getF_LJValue() {
			return F_LJValue;
		}

		public void setF_LJValue(String F_LJValue) {
			this.F_LJValue = F_LJValue;
		}

		public String getF_FailureMode() {
			return F_FailureMode;
		}

		public void setF_FailureMode(String F_FailureMode) {
			this.F_FailureMode = F_FailureMode;
		}

		public String getF_FailureModeValue() {
			return F_FailureModeValue;
		}

		public void setF_FailureModeValue(String F_FailureModeValue) {
			this.F_FailureModeValue = F_FailureModeValue;
		}

		public String getNM_FKYY() {
			return NM_FKYY;
		}

		public void setNM_FKYY(String NM_FKYY) {
			this.NM_FKYY = NM_FKYY;
		}

		public String getNM_FKYYValue() {
			return NM_FKYYValue;
		}

		public void setNM_FKYYValue(String NM_FKYYValue) {
			this.NM_FKYYValue = NM_FKYYValue;
		}

		public String getNM_YZD() {
			return NM_YZD;
		}

		public void setNM_YZD(String NM_YZD) {
			this.NM_YZD = NM_YZD;
		}

		public String getNM_YZDValue() {
			return NM_YZDValue;
		}

		public void setNM_YZDValue(String NM_YZDValue) {
			this.NM_YZDValue = NM_YZDValue;
		}

		public String getNM_FSPL() {
			return NM_FSPL;
		}

		public void setNM_FSPL(String NM_FSPL) {
			this.NM_FSPL = NM_FSPL;
		}

		public String getNM_FSPLValue() {
			return NM_FSPLValue;
		}

		public void setNM_FSPLValue(String NM_FSPLValue) {
			this.NM_FSPLValue = NM_FSPLValue;
		}

		public String getP_BJDZ() {
			return P_BJDZ;
		}

		public void setP_BJDZ(String P_BJDZ) {
			this.P_BJDZ = P_BJDZ;
		}

		public String getP_BJLXR() {
			return P_BJLXR;
		}

		public void setP_BJLXR(String P_BJLXR) {
			this.P_BJLXR = P_BJLXR;
		}

		public String getP_BJLXDH() {
			return P_BJLXDH;
		}

		public void setP_BJLXDH(String P_BJLXDH) {
			this.P_BJLXDH = P_BJLXDH;
		}
	}

}
