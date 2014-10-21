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
	
	public static String Main_Url = "http://192.168.1.108:8080/FootprintService";
	public static String getUrl(String url){
		return Main_Url+url;
	}
	public static String Login = "/LoginRegister/Login";
	public static String Register = "/LoginRegister/Register";
	

}
