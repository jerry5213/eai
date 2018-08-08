package com.zxtech.ecs.model;

import android.graphics.Bitmap;

/**
 * @auth: hexl
 * @date: 2018/3/1
 * @desc:
 */

public class PhotoEntity {
	private String imgPath;
	private Bitmap mBitmap;

	public PhotoEntity() {
	}

	public PhotoEntity(String imgPath, Bitmap bitmap) {
		this.imgPath = imgPath;
		mBitmap = bitmap;
	}

	public String getImgPath() {

		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}
}
