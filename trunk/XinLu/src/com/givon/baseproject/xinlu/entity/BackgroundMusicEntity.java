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
import java.util.ArrayList;

public class BackgroundMusicEntity implements Serializable{
private static final long serialVersionUID = 1L;
private ArrayList<BackgroundMusicModel> data;

public ArrayList<BackgroundMusicModel> getData() {
	return data;
}

public void setData(ArrayList<BackgroundMusicModel> data) {
	this.data = data;
}


}
