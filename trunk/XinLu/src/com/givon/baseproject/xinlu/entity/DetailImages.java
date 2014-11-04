/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @DetailImages.java  2014年10月31日 下午1:41:33 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.entity;

import java.io.Serializable;

public class DetailImages implements Serializable{
	private static final long serialVersionUID = 1L;
	private String imageUrl;//": "",//图片网络地址
    private int widthSize;//": "",//图片宽度
    private int heightSize;//": "",//图片高度
    private int sequences;//": ""//图片顺序
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getWidthSize() {
		return widthSize;
	}
	public void setWidthSize(int widthSize) {
		this.widthSize = widthSize;
	}
	public int getHeightSize() {
		return heightSize;
	}
	public void setHeightSize(int heightSize) {
		this.heightSize = heightSize;
	}
	public int getSequences() {
		return sequences;
	}
	public void setSequences(int sequences) {
		this.sequences = sequences;
	}
	
}
