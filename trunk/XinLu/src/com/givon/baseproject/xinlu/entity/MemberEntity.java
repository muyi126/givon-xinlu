/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @MemberEntity.java  2014年10月14日 下午3:24:51 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.entity;

public class MemberEntity {
	private int State;
	private String Message;
	private MemberModel Data;

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public MemberModel getData() {
		return Data;
	}

	public void setData(MemberModel data) {
		Data = data;
	}

}
