/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ImageModel.java  2014年10月16日 下午3:53:49 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.entity;

import java.io.Serializable;

public class ImageModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String imageUrl;//      :       "图片url地址"
	private int type;
	private String uniqueId;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
