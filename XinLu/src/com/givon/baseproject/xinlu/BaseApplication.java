/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @BaseActivity.java  2014年3月25日 上午9:31:21 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu;

import android.app.Application;
import android.content.Context;

import com.givon.baseproject.xinlu.util.CrashHandler;

public class BaseApplication extends Application {

	private static BaseApplication mInstance;
	public boolean m_bKeyRight = true;
	public static final String strKey = "hzRfLBMEMqnv1ozmPBb3QeGW";
	public static int mWidth;
	public static int mHeight;
	public static Context getAppContext() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		mWidth = getResources().getDisplayMetrics().widthPixels;
		mHeight = getResources().getDisplayMetrics().heightPixels;
		CrashHandler crashHandler = CrashHandler.getInstance();
		// 注册crashHandler
		crashHandler.init(getApplicationContext());
		
	}


	public static BaseApplication getInstance() {
		return mInstance;
	}

}
