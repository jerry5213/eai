package com.zxtech.ecs.model;

/**
 * @auth: hexl
 * @date: 2018/2/2
 * @desc:
 */

public class ProductElevatorEntity {
	private String img;
	private String url;

	public ProductElevatorEntity(String img, String url) {
		this.img = img;
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
