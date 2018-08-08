package com.zxtech.ecs.model;

import com.zxtech.ecs.net.BaseResponse;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/3/6
 * @desc:
 */

public class VoiceResultMoreTypeEntity extends BaseResponse<VoiceResultMoreTypeEntity> {


	/**
	 * id : ID_100
	 * options : [{"id":"ID_101","type":"1","text":"地坎上有异物遮挡","url":"http://"},{"id":"ID_102","type":"1","text":"光幕收极指示灯常亮","url":"http://"},{"id":"ID_999","type":"1","text":"以上都不是。","url":""}]
	 */

	private String id;
	private List<OptionsBean> options;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<OptionsBean> getOptions() {
		return options;
	}

	public void setOptions(List<OptionsBean> options) {
		this.options = options;
	}

	public static class OptionsBean {
		public OptionsBean() {
		}

		public OptionsBean(String id, String type, String text, String url) {

			this.id = id;
			this.type = type;
			this.text = text;
			this.url = url;
		}

		/**
		 * id : ID_101
		 * type : 1
		 * text : 地坎上有异物遮挡
		 * url : http://
		 */

		private String id;
		private String type;
		private String text;
		private String url;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
}
