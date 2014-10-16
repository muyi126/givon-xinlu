package com.givon.baseproject.xinlu.util;



import android.content.Context;
import android.widget.Toast;

import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.R;

public class ToastUtils {
	private static Context mContext = BaseApplication.getAppContext();

	public static void showMessage(Context cont, int msg) {
		Toast toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
		toast.getView().setBackgroundResource(R.drawable.base_tip_bg);
		toast.getView().setPadding(50, 20, 50, 20);
		toast.show();
	}

	public static void showMessage(CharSequence text) {
		Toast toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
		toast.getView().setBackgroundResource(R.drawable.base_tip_bg);
		toast.getView().setPadding(50, 20, 50, 20);
		toast.show();
	}

	public static void showMessage(int resid) {
		if (resid < 1) {
			return;
		}
		Toast toast = Toast.makeText(mContext, mContext.getResources().getString(resid),
				Toast.LENGTH_SHORT);
		toast.getView().setBackgroundResource(R.drawable.base_tip_bg);
		toast.getView().setPadding(50, 20, 50, 20);
		toast.show();
	}
}
