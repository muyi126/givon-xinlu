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

import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.support.httpclient.HttpClientAsync;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
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
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	public static BDLocation lastLocation = null;
	private static double latitude = 30;
	private static double longtitude = 104;
	public static Context getAppContext() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		initLoacation();
		// 如果使用到百度地图或者类似启动remote service的第三方库，这个if判断不能少
		if (processAppName == null || processAppName.equals("")) {
			// workaround for baidu location sdk
			// 百度定位sdk，定位服务运行在一个单独的进程，每次定位服务启动的时候，都会调用application::onCreate
			// 创建新的进程。
			// 但环信的sdk只需要在主进程中初始化一次。 这个特殊处理是，如果从pid 找不到对应的processInfo
			// processName，
			// 则此application::onCreate 是被service 调用的，直接返回
			return;
		}		
		HttpClientAsync.getInstance().setPrintLog(true);
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
	
	
	private String getAppName(int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = this.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
					.next());
			try {
				if (info.pid == pID) {
					CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName,
							PackageManager.GET_META_DATA));
					// Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
					// info.processName +"  Label: "+c.toString());
					// processName = c.toString();
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				// Log.d("Process", "Error>> :"+ e.toString());
			}
		}
		return processName;
	}
	
	

	/**
	 * 定位
	 */
	private void initLoacation() {
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setIsNeedAddress(true);
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null) {
				return;
			} else {
				LocationClientOption opt = mLocClient.getLocOption();
				opt.setScanSpan(30000);
			}
//			System.out.println("lastLocation"+location.getLatitude()+","+location.getLongitude());
			lastLocation = location;
			latitude = location.getLatitude();
			longtitude = location.getLongitude();
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

}
