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

import java.io.File;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.givon.baseproject.draw.util.DrawAttribute;
import com.givon.baseproject.draw.util.StorageInSDCard;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.cookie.ShareCookie;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.util.CommUtil;

public class ActWelcome extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (StorageInSDCard.IsExternalStorageAvailableAndWriteable()) {

			File mian_path = new File(Constant.Main_PATH);
			if (!mian_path.exists()) {
				mian_path.mkdirs();
			}
			File draw_path = new File(Constant.DRAW_PATH);
			if (!draw_path.exists()) {
				draw_path.mkdirs();
			}
			File luyin_path = new File(Constant.LUYIN_PATH);
			if (!luyin_path.exists()) {
				luyin_path.mkdirs();
			}
		}
		Handler x = new Handler();
		x.postDelayed(new splashhandler(), 500);
	}

	private void initialDrawAttribute() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		DrawAttribute.screenWidth = dm.widthPixels;
		DrawAttribute.screenHeight = dm.heightPixels;

		DrawAttribute.screenWidth_out = DrawAttribute.screenWidth;
		// 减去TOP 和Buttons 的高度
		DrawAttribute.screenHeight_out = DrawAttribute.screenHeight- CommUtil.dip2px(50)
				- CommUtil.dip2px(60);

		DrawAttribute.paint.setColor(Color.LTGRAY);
		DrawAttribute.paint.setStrokeWidth(3);
	}

	class splashhandler implements Runnable {
		@Override
		public void run() {
			initialDrawAttribute();
//			if(ShareCookie.isLoginAuth()){
				startActivity(new Intent(getApplication(), MainActivity.class));
//			}else {
//				startActivity(new Intent(getApplication(), ActLogin.class));
//			}
		}
	}

}
