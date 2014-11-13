/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActRegisterPage.java  2014年10月21日 下午3:32:53 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import android.content.Intent;
import cn.smssdk.gui.IdentifyNumPage;
import cn.smssdk.gui.RegisterPage;

public class ActRegisterPage extends RegisterPage{
	@Override
	protected void afterVerificationCodeRequested() {
		String phone = etPhoneNum.getText().toString().trim().replaceAll("\\s*", "");
		String code = tvCountryNum.getText().toString().trim();
		if (code.startsWith("+")) {
			code = code.substring(1);
		}
		String formatedPhone = "+" + code + " " + splitPhoneNum(phone);
		//验证码页面
		ActIdentifyNumPage page = new ActIdentifyNumPage();
		page.setPhone(phone, code, formatedPhone);
		page.showForResult(activity, null, this);
	}
	
	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
	}
}
