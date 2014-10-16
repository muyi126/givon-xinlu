package com.givon.baseproject.xinlu.util;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.givon.baseproject.xinlu.entity.Constant;

/**
 * �ַ��ж�
 * @author YW_pc
 * 
 */
public class StringUtils {

	public static boolean isEmpty(CharSequence text) {
		if (text == null || "null".equals(text)) {
			return true;
		}
		return text.length() == 0;
	}

	public static String getDefaultString(String msg) {
		return (null == msg || msg.length() < 1) ? "" : msg;
	}
	public static boolean getDefaultBoolean(int msg) {
		return msg==1?true:false;
	}

	public static String double2String(double d) {
		// DecimalFormat df0 = new DecimalFormat("###");
		// DecimalFormat df1 = new DecimalFormat("###.0");

		DecimalFormat df2 = new DecimalFormat("#.00");
		String fot = df2.format(d);
		if (d < 1 && d >= 0) {
			fot = "0" + fot;
		}

		return fot;

	}

	/**
	 * 退出程序
	 * @param ctx
	 */
	public static void onExit(Context context) {
		try {
			Intent intent = new Intent();
			intent.setAction(context.getApplicationContext().getPackageName()
					+ Constant.ACTION_EXIT_SYSTEM);
			context.sendBroadcast(intent);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					System.exit(0);
				}
			}, 200);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
