/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @MemberEntity.java  2014年10月14日 下午3:24:51 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.entity;

import java.io.Serializable;

public class MemberEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private String memberId;//":"e9f8347c-a544-430a-ac6d-23bfcb20943e",
	private String nickName;//":"Hello",
	private String sex;//":"?",
	private String phone;//":"18780118236",
	private String headImage;//":"/test/upload/1413943835514.jpg",
	private String email;//":null,
	private int currentPoint;//":0,
	private int totalPoint;//":0,
	private int memberStatus;//":0,
	private String signature;//":null,
	private String token;//":"GXyF0c4/PIvwfICGLuZekZgIFpZY1Y2hB7mVUVtuP/aNdH7edVJrptemHpvmKNM7",
	private float longitude;//":null,
	private float latitude;//":null,
	private String registDate;//":"2014-10-22 10:38:28",
	private String birthday;//":null
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCurrentPoint() {
		return currentPoint;
	}
	public void setCurrentPoint(int currentPoint) {
		this.currentPoint = currentPoint;
	}
	public int getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}
	public int getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(int memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}
