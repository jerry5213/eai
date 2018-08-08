package com.zxtech.ecs.model;

import com.zxtech.ecs.net.BaseResponse;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/2/28
 * @desc:
 */

public class SystemCodeListEntity extends BaseResponse<SystemCodeListEntity> {

	/**
	 * Result : 0
	 * ErrorCode :
	 * ErrorMsg :
	 * SystemCodeList : [{"Code":"1","Value":"01-人机界面系统"},{"Code":"1","Value":"01-人机界面系统"},{"Code":"2","Value":"02-控制&动力系统"},{"Code":"3","Value":"03-轿厢系统"},{"Code":"4","Value":"04-液压部件"},{"Code":"5","Value":"05-位置参考系统"},{"Code":"6","Value":"06-接线系统"},{"Code":"7","Value":"07-轿架装配"},{"Code":"8","Value":"08-井道部件"},{"Code":"9","Value":"09-主机装配"},{"Code":"10","Value":"10-厅门系统"},{"Code":"11","Value":"11-轿门系统"},{"Code":"12","Value":"12-对重装配"},{"Code":"13","Value":"13-限速器"},{"Code":"14","Value":"14-绳&绳头组合"}]
	 */
	private List<SystemCodeListBean> SystemCodeList;

	public List<SystemCodeListBean> getSystemCodeList() {
		return SystemCodeList;
	}

	public void setSystemCodeList(List<SystemCodeListBean> SystemCodeList) {
		this.SystemCodeList = SystemCodeList;
	}
}
