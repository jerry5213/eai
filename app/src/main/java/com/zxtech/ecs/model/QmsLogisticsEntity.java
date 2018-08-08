package com.zxtech.ecs.model;

/**
 * @auth: hexl
 * @date: 2018/3/5
 * @desc:
 */

public class QmsLogisticsEntity {
	private String text;
	private String time;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public QmsLogisticsEntity(String text, String time) {

		this.text = text;
		this.time = time;
	}
}
