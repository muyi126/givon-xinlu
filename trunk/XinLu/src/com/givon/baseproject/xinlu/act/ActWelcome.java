/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActWelcome.java  2014年10月21日 上午9:50:32 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import android.os.Bundle;

import com.givon.baseproject.xinlu.BaseActivity;

public class ActWelcome extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		showActivity(MainActivity.class, true);
	}

}
