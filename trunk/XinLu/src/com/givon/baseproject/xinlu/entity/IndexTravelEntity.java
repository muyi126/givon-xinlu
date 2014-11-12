/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @IndexTravelEntity.java  2014年11月7日 下午4:00:20 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class IndexTravelEntity implements Serializable{
 private static final long serialVersionUID = 1L;
private ArrayList<IndexTravelModel> data;

public ArrayList<IndexTravelModel> getData() {
	return data;
}

public void setData(ArrayList<IndexTravelModel> data) {
	this.data = data;
}
 
}
