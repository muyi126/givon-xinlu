/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActIdentifyNumPage.java  2014年10月21日 下午3:25:11 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import static cn.smssdk.framework.utils.R.getStringRes;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import cn.smssdk.SMSSDK;

public class ActIdentifyNumPage extends cn.smssdk.gui.IdentifyNumPage{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	@Override
	protected void afterSubmit(final int result, final Object data) {
		runOnUIThread(new Runnable() {
			public void run() {
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}

				if (result == SMSSDK.RESULT_COMPLETE) {
//					HashMap<String, Object> res = new HashMap<String, Object>();
//					res.put("res", true);
//					res.put("page", 2);
//					res.put("phone", data);
//					setResult(res);
//					finish();
//					ActRightRegist act = new ActRightRegist();
//					show(act, null);
					Intent intent = new Intent(ActIdentifyNumPage.this.getContext(),ActRightRegist.class);
					startActivityForResult(intent, 1123);
				} else {
					((Throwable) data).printStackTrace();
					// 验证码不正确
					int resId = getStringRes(activity, "smssdk_virificaition_code_wrong");
					if (resId > 0) {
						Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		if(arg0==1123&&arg1==Activity.RESULT_OK){
			HashMap<String, Object> res = new HashMap<String, Object>();
			res.put("res", true);
			res.put("page", 2);
			res.put("phone", phone);
			setResult(res);
			finish();
		}
		
	}
}
