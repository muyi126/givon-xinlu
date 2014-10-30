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

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.givon.baseproject.draw.util.DrawAttribute;
import com.givon.baseproject.xinlu.BaseActivity;

public class ActWelcome extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Handler x = new Handler();
		x.postDelayed(new splashhandler(), 500);
	}

	private void initialDrawAttribute() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		DrawAttribute.screenWidth = dm.widthPixels;
		DrawAttribute.screenHeight = dm.heightPixels;
		DrawAttribute.paint.setColor(Color.LTGRAY);
		DrawAttribute.paint.setStrokeWidth(3);
	}

	class splashhandler implements Runnable {
		@Override
		public void run() {
			initialDrawAttribute();
			startActivity(new Intent(getApplication(), MainActivity.class));
		}
	}

}
