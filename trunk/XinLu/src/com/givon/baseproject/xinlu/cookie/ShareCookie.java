/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ShareCookie.java  2014-2-25 下午1:41:30 - Carson
 * @author YanXu
 * @email:981385016@qq.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.cookie;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.MemberEntity;

public class ShareCookie {
	private final static String IS_LOGIN_AUTH = "isLogin";
	private final static String CURRENT_DATE = "CurrentDate";
	private final static String USER_INFO = "user_infos";
	private final static String DEFAULT_ADDRESS = "default_address";

	public static boolean setToken(String token) {
		SharedPreferences cookie = BaseApplication.getAppContext().getSharedPreferences(
				Constant.COOKIE_FILE, Context.MODE_PRIVATE);
		Editor editor = cookie.edit();
		editor.putString(Constant.APP_TOKEN, token);
		return editor.commit();
	}

	public static String getToken() {
		SharedPreferences cookie = BaseApplication.getAppContext().getSharedPreferences(
				Constant.COOKIE_FILE, Context.MODE_PRIVATE);
		return cookie.getString(Constant.APP_TOKEN, "");
	}

	public static boolean isLoginAuth() {
		SharedPreferences cookie = BaseApplication.getAppContext().getSharedPreferences(
				Constant.COOKIE_FILE, Context.MODE_PRIVATE);
		return cookie.getBoolean(Constant.IS_LOGIN_AUTH, false);
	}

	public static void setLoginAuth(boolean login) {
		SharedPreferences cookie = BaseApplication.getAppContext().getSharedPreferences(
				Constant.COOKIE_FILE, Context.MODE_PRIVATE);
		Editor editor = cookie.edit();
		editor.putBoolean(Constant.IS_LOGIN_AUTH, login);
		editor.commit();
	}

	
//	/**
//	 * 客户端类型(android或者ios)
//	 */
//	public static String getAppType() {
//		return BaseApplication.getAppContext().getString(R.string.appType);
//	}

	public static void setCurrentDate(long date) {
		SharedPreferences cookie = BaseApplication.getAppContext().getSharedPreferences(
				Constant.COOKIE_FILE, Context.MODE_PRIVATE);
		Editor editor = cookie.edit();
		editor.putLong(CURRENT_DATE, date);
		editor.commit();
	}

	public static long getCurrentDate() {
		SharedPreferences cookie = BaseApplication.getAppContext().getSharedPreferences(
				Constant.COOKIE_FILE, Context.MODE_PRIVATE);
		return cookie.getLong(CURRENT_DATE, 0);
	}

	public static boolean saveUserInfo(MemberEntity entity) {
		boolean ret = false;
		if (null == entity) {
			return false;
		}
		FileOutputStream outStream = null;
		try {
			outStream = BaseApplication.getAppContext().openFileOutput(USER_INFO,
					Context.MODE_PRIVATE);
			ObjectOutputStream objectStream = new ObjectOutputStream(outStream);
			objectStream.writeObject(entity);
			ret = true;
		} catch (IOException e) {
			ret = false;
		}
		if (null != outStream) {
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
//	public static boolean saveAddressInfo(AddressListBean bean,String userId) {
//		boolean ret = false;
//		if (null == bean) {
//			return false;
//		}
//		FileOutputStream outStream = null;
//		try {
//			outStream = BaseApplication.getAppContext().openFileOutput(userId,
//					Context.MODE_PRIVATE);
//			ObjectOutputStream objectStream = new ObjectOutputStream(outStream);
//			objectStream.writeObject(bean);
//			ret = true;
//		} catch (IOException e) {
//			ret = false;
//		}
//		if (null != outStream) {
//			try {
//				outStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return ret;
//	}
//	public static AddressListBean getAddressInfo(String userId) {
//		AddressListBean entity = null;
//		FileInputStream fin = null;
//		try {
//			fin = BaseApplication.getAppContext().openFileInput(userId);
//			ObjectInputStream inStream = new ObjectInputStream(fin);
//			entity = (AddressListBean) inStream.readObject();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (null != fin) {
//			try {
//				fin.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return entity;
//	}	
	
	public static MemberEntity getUserInfo() {
		MemberEntity entity = null;
		FileInputStream fin = null;
		try {
			fin = BaseApplication.getAppContext().openFileInput(USER_INFO);
			ObjectInputStream inStream = new ObjectInputStream(fin);
			entity = (MemberEntity) inStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != fin) {
			try {
				fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (entity == null) {
			System.out.println("NNULL");
			ShareCookie.setLoginAuth(false);
		}
		return entity;
	}

	public static String getUserId() {
		MemberEntity bean = getUserInfo();
		if (bean != null) {
			return bean.getMemberId();
		}
		return "";
	}
	public static String getUserToken() {
		MemberEntity bean = getUserInfo();
		if (bean != null) {
			return bean.getToken();
		}
		return "";
	}
	
	public static void saveNickName(String account, String name) {
		SharedPreferences cookie = BaseApplication.getAppContext().getSharedPreferences(
				Constant.COOKIE_FILE, Context.MODE_PRIVATE);
		Editor editor = cookie.edit();
		editor.putString(account, name);
		editor.commit();
	}
	
	public static String getNickName(String account) {
		SharedPreferences cookie = BaseApplication.getAppContext().getSharedPreferences(
				Constant.COOKIE_FILE, Context.MODE_PRIVATE);
		return cookie.getString(account, "");
	}

}
