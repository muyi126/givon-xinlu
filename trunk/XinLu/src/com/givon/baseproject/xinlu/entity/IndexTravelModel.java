/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @IndexTravelModel.java  2014年11月7日 下午3:55:29 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.entity;

import java.io.Serializable;

public class IndexTravelModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String travelId;//":”123456”,//足迹id
	private String headImage;//":””//发布人头像
	private String title;//":”足迹标题”//
	private String image;//":””//主图(自动选择第一张图片为主图)
	private float widthSize;//": ""//图片宽度
	private float heightSize;//": ""//图片高度
	private String addDate;//":””//添加时间
	private String skimCount;//":””//浏览量
	public String getTravelId() {
		return travelId;
	}
	public void setTravelId(String travelId) {
		this.travelId = travelId;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getSkimCount() {
		return skimCount;
	}
	public void setSkimCount(String skimCount) {
		this.skimCount = skimCount;
	}
	public float getWidthSize() {
		return widthSize;
	}
	public void setWidthSize(float widthSize) {
		this.widthSize = widthSize;
	}
	public float getHeightSize() {
		return heightSize;
	}
	public void setHeightSize(float heightSize) {
		this.heightSize = heightSize;
	}

}
