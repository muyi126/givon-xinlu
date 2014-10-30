/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @BannerModel.java  2014年10月24日 上午10:25:46 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.entity;

public class BannerModel {
	private String bannerId;//":”123456”,//id
	private String bannerTitle;//":”123456”//广告标题
	private String bannerImage;//":”/xxx.jpg”//广告图片
	private String bannerLink;//":””// 跳转地址
	private String sequence;//":1// 顺序
	public String getBannerId() {
		return bannerId;
	}
	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}
	public String getBannerTitle() {
		return bannerTitle;
	}
	public void setBannerTitle(String bannerTitle) {
		this.bannerTitle = bannerTitle;
	}
	public String getBannerImage() {
		return bannerImage;
	}
	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}
	public String getBannerLink() {
		return bannerLink;
	}
	public void setBannerLink(String bannerLink) {
		this.bannerLink = bannerLink;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
