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

import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.support.httpclient.HttpCallBack;
import com.android.support.httpclient.HttpClientAsync;
import com.android.support.httpclient.HttpParams;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.cookie.ShareCookie;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.MemberEntity;
import com.givon.baseproject.xinlu.util.StatisticManager;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ActLogin extends BaseActivity implements Callback {
	@ViewInject(R.id.bt_register)
	private Button mRegistButton;
	@ViewInject(R.id.bt_login)
	private Button mLoginButton;
	@ViewInject(R.id.et_phone)
	private EditText mUserName;
	@ViewInject(R.id.et_password)
	private EditText mPassWord;

	private boolean ready;
	private Dialog pd;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_actlogin);
		ViewUtils.inject(this);
		initSDK();
	}

	public void doRegist(View v) {
		// 打开注册页面
		ActRegisterPage registerPage = new ActRegisterPage();
		registerPage.setRegisterCallback(new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				// 解析注册结果
				if (result == SMSSDK.RESULT_COMPLETE) {
					@SuppressWarnings("unchecked")
					HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
					String country = (String) phoneMap.get("country");
					String phone = (String) phoneMap.get("phone");
					// 提交用户信息
					// registerUser(country, phone);
//					Intent intent = new Intent(ActLogin.this, ActRightRegist.class);
//					intent.putExtra(Constant.PHONE, phone);

				}
			}
		});
		registerPage.show(this);
	}

	public void doLogin(View v) {
		JSONObject object = new JSONObject();
		object.put("memberName", "18780118236");
		object.put("password", "123456");
		String dataString = JSON.toJSONString(object);
		HttpClientAsync httpClientAsync = HttpClientAsync.getInstance();
		httpClientAsync.post(XLHttpUrl.getUrl(XLHttpUrl.Login), dataString, XLHttpUrl.CONTENT_TYPE, new HttpCallBack() {
			
			@Override
			public void onHttpSuccess(Object obj) {
				MemberEntity entity = (MemberEntity) obj;
				if(null!=entity){
					ShareCookie.saveUserInfo(entity);
					ShareCookie.setToken(entity.getToken());
					ShareCookie.setLoginAuth(true);
					setResult(Activity.RESULT_OK, null);
					showActivity(MainActivity.class, true);
				}
			}
			
			@Override
			public void onHttpStarted() {
				
			}
			
			@Override
			public void onHttpFailure(Exception e, String message) {
				if(!StringUtil.isEmpty(message)){
					ToastUtils.showMessage(message);
				}
			}
		}, MemberEntity.class);
//		HttpUtils httpUtils = new HttpUtils();
//		httpUtils.send(HttpMethod.GET, XLHttpUrl.getUrl(XLHttpUrl.Login),
//				new RequestCallBack<String>() {
//
//					@Override
//					public void onSuccess(ResponseInfo<String> responseInfo) {
//						// MemberEntity entity = responseInfo.result;
//						// entity.getData();
//						System.out.println(responseInfo);
//					}
//
//					@Override
//					public void onFailure(HttpException error, String msg) {
//						// TODO Auto-generated method stub
//
//					}
//				});

	}

	protected void onDestroy() {
		if (ready) {
			// 销毁回调监听接口
			SMSSDK.unregisterAllEventHandler();
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ready) {
			// StatisticManager.onResume(ActLogin.this);
			// // 获取新好友个数
			// showDialog();
			// SMSSDK.getNewFriendsCount();
		}
	}

	// 提交用户信息
	private void registerUser(String country, String phone) {
		Random rnd = new Random();
		int id = Math.abs(rnd.nextInt());
		String uid = String.valueOf(id);
		String nickName = "SmsSDK_User_" + uid;
		String avatar = "";
		SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
	}

	private void initSDK() {
		// 初始化短信SDK
		SMSSDK.initSDK(this, BaseApplication.ShareAPPKEY, BaseApplication.ShareAPPSECRET);
		final Handler handler = new Handler(this);
		EventHandler eventHandler = new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
		ready = true;

		// 获取新好友个数
		// showDialog();
		// SMSSDK.getNewFriendsCount();

		StatisticManager.initAnalysisSDK(ActLogin.this);
		StatisticManager.registerAnalysisHandler(ActLogin.this);
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}

		int event = msg.arg1;
		int result = msg.arg2;
		Object data = msg.obj;
		if (event == SMSSDK.EVENT_SUBMIT_USER_INFO) {
			// 短信注册成功后，返回MainActivity,然后提示新好友
			if (result == SMSSDK.RESULT_COMPLETE) {
				Toast.makeText(this, R.string.smssdk_user_info_submited, Toast.LENGTH_SHORT).show();
			} else {
				((Throwable) data).printStackTrace();
			}
		} else if (event == SMSSDK.EVENT_GET_NEW_FRIENDS_COUNT) {
			if (result == SMSSDK.RESULT_COMPLETE) {
				// refreshViewCount(data);
			} else {
				((Throwable) data).printStackTrace();
			}
		}
		return false;
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("requestCode:"+requestCode+" resultCode:"+resultCode);
	}

}
