/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @XLHttpUrl.java  2014年10月14日 下午3:02:18 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.util;

public class XLHttpUrl {
	public static final int PAGE_SIZE=15;
	public static final String CONTENT_TYPE = "application/json";
	public static String Main_Url = "http://192.168.1.108:8080/FootprintService";
//	public static String Main_Url = "http://192.168.1.116:8080/FootprintService";
	public static String getUrl(String url){
		return Main_Url+url;
	}
	public static String Login = "/LoginRegister/Login";
	public static String Register = "/LoginRegister/Register";
	public static String GetBanners = "/Index/GetBanners";
	public static String SaveTravel = "/Index/SaveTravel";
	public static String GetBackgroundMusic = "/Index/GetBackgroundMusic";
	public static String GetTravels = "/Index/GetTravels";
	

}
