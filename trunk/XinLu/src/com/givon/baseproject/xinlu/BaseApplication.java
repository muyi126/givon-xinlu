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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.support.httpclient.HttpClientAsync;
import com.givon.baseproject.xinlu.util.CrashHandler;

public class BaseApplication extends Application {

	private static BaseApplication mInstance;
	public boolean m_bKeyRight = true;
	// 填写从短信SDK应用后台注册得到的APPKEY
		public static String ShareAPPKEY = "3e6fd6e4d719";

		// 填写从短信SDK应用后台注册得到的APPSECRET
		public static String ShareAPPSECRET = "8a26b829f6cdd9c51181ec9752a34e2b";
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
		HttpClientAsync.getInstance().setPrintLog(true);
		CrashHandler crashHandler = CrashHandler.getInstance();
		// 注册crashHandler
		crashHandler.init(getApplicationContext());
		
	}


	public static BaseApplication getInstance() {
		return mInstance;
	}
	
	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {   
        } else {
//如果仅仅是用来判断网络连接 ,则可以使用 cm.getActiveNetworkInfo().isAvailable();  
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    } 

}
