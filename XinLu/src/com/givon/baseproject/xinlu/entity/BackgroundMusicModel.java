/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @BackgroundMusicModel.java  2014年11月6日 下午5:36:42 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.entity;

import java.io.Serializable;

public class BackgroundMusicModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String musicId;//":””,//背景音乐id
	private String urlPath;//":””,//背景音乐地址
	private String musicName;//":””,//背景音乐名称
	private String musicLocaPath;//":””,//本地路径
	public String getMusicId() {
		return musicId;
	}
	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getMusicLocaPath() {
		return musicLocaPath;
	}
	public void setMusicLocaPath(String musicLocaPath) {
		this.musicLocaPath = musicLocaPath;
	}
	
	
	

}
