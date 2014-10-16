/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActLogin.java  2014年10月14日 上午11:36:28 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.entity.MemberEntity;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ActLogin extends BaseActivity{
	@ViewInject (R.id.bt_register)
	private Button mRegistButton;
	@ViewInject (R.id.bt_login)
	private Button mLoginButton;
	@ViewInject (R.id.et_phone)
	private EditText mUserName;
	@ViewInject (R.id.et_password)
	private EditText mPassWord;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_actlogin);
		ViewUtils.inject(this);
	}
	
	public void doRegist(View v){
		
		
	}
	public void doLogin(View v){
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, XLHttpUrl.getUrl(XLHttpUrl.Login), new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
//				MemberEntity entity = responseInfo.result;
//				entity.getData();
				System.out.println(responseInfo);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
