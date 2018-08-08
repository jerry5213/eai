package com.zxtech.ecs.model;

/**
 * @auth: hexl
 * @date: 2018/2/2
 * @desc:
 */

public class PropagandistVideoEntity {
	private String url;
	private String countTime;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCountTime() {
		return countTime;
	}

	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}

	public PropagandistVideoEntity(String url, String countTime) {

		this.url = url;
		this.countTime = countTime;
	}
}
