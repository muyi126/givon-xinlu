/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActRegist.java  2014-2-25 上午10:12:35 - jiangyue
 * @author jiangyue
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import java.util.HashMap;

import org.apache.http.params.HttpParams;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import com.android.support.httpclient.HttpClientAsync;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.util.CommUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.givon.baseproject.xinlu.view.AppTitleBar;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/***
 * 注册
 * @author jiangyue
 * 
 */
public class ActRegist extends BaseActivity implements OnClickListener {
	private ViewFlipper mFlipper;
	private EditText etPhone;
	private EditText etUserName;
	private EditText etPwd;
	private EditText etCode;
	private Button btnRegist;
	private Button btnGetCode;
	private TextView tvSendState;
	private boolean isEmailRegist;
	private String phone;
	private String username;
	private String pwd;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				btnGetCode.setText(R.string.regist_tip_regetcode);
				btnGetCode.setEnabled(true);
			} else {
				btnGetCode.setText(msg.what + getString(R.string.regist_tip_regetcode_60));
			}
		};
	};
	private boolean stopCountDown;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_regist);
		initViewLayout();
	}

	private void initViewLayout() {
		mTitleBar = (AppTitleBar) findViewById(R.id.id_titlebar);
		mTitleBar.setTitle(R.string.regist);
		mTitleBar.setBackImage(R.drawable.icon_back);
//		mTitleBar.setIconOnClickListener(this);
		mFlipper = (ViewFlipper) findViewById(R.id.vf_regist);
		etPhone = (EditText) findViewById(R.id.et_phone);
		etUserName = (EditText) findViewById(R.id.et_account);
		etPwd = (EditText) findViewById(R.id.et_pwd);
		etCode = (EditText) findViewById(R.id.et_code);
		btnRegist = (Button) findViewById(R.id.btn_regist);
		btnRegist.setOnClickListener(this);
		btnGetCode = (Button) findViewById(R.id.btn_get_code);
		btnGetCode.setOnClickListener(this);
		tvSendState = (TextView) findViewById(R.id.tv_tip_send_state);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.id_button_back) {
			if (mFlipper.getDisplayedChild() == 1) {
				mFlipper.setInAnimation(this, R.anim.push_left_in);
				mFlipper.setOutAnimation(this, R.anim.push_right_out);
				mFlipper.setDisplayedChild(0);
				mTitleBar.setMoreOnClickListener(null);
				mTitleBar.setMoreText(" ");
			} else {
				onBackPressed();
			}
		} else if (v.getId() == R.id.id_button_more) {
			doRegistByPhone();
		} else if (v == btnRegist) {
			if(!BaseApplication.isNetworkAvailable(this)) {
				ToastUtils.showMessage(R.string.toast_no_intent);
				return;
			}
			doRegistAction();
		} else if (v == btnGetCode) {
			doGetCodeAction();
		}
	}

	private void doRegistAction() {
		final String phoneEmail = etPhone.getText().toString();
		final String username = etUserName.getText().toString();
		String pwd = etPwd.getText().toString();
		if (TextUtils.isEmpty(phoneEmail)) {
			ToastUtils.showMessage(R.string.regist_input_phone);
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			ToastUtils.showMessage(R.string.login_input_pwd);
			return;
		} else if (pwd.length() < 6) {
			ToastUtils.showMessage(R.string.login_input_pwd_lengtherror);
			return;
		} else if (TextUtils.isEmpty(username)) {
			ToastUtils.showMessage(R.string.regist_input_username);
			return;
		} else if (phoneEmail.contains("@")) {
			isEmailRegist = true;
			if (!CommUtil.checkEmail(phoneEmail)) {
				isEmailRegist = false;
				ToastUtils.showMessage(R.string.login_input_email_error);
				return;
			}
		} else if (!CommUtil.isMobileNO(phoneEmail)) {
			ToastUtils.showMessage(R.string.regist_input_phone_error);
			return;
		}

		if (isEmailRegist) {
//			HttpClientAsync client = HttpClientAsync.getInstance();
//			HttpParams params = new HttpParams();
//			params.put("Email", phoneEmail);
//			params.put("Password", pwd);
//			params.put("ConfirmPassword", pwd);
//			client.post(HttpUrl.getUrl(HttpUrl.REGISTER_EMAIL), params, HttpUrl.CONTENT_TYPE,
//					new HttpCallBack() {
//
//						@Override
//						public void onHttpSuccess(Object arg0) {
//							dismissWaitingDialog();
//							ToastUtil.showMessage(R.string.regist_success_email);
//							ShareCookie.saveNickName(phoneEmail, username);
//							onBackPressed();
//						}
//
//						@Override
//						public void onHttpStarted() {
//							showWaitingDialog();
//						}
//
//						@Override
//						public void onHttpFailure(Exception arg0, String arg1) {
//							dismissWaitingDialog();
//							if (!TextUtils.isEmpty(arg1)) {
//								ToastUtil.showMessage(arg1);
//							} else {
//								ToastUtil.showMessage(R.string.regist_fail);
//							}
//						}
//					}, StateBean.class);
		} else {
			this.phone = phoneEmail;
			this.username = username;
			this.pwd = pwd;
			doGetCodeAction();
		}
	}

	private void doRegistByPhone() {
		
//		String code = etCode.getText().toString();
//		if (TextUtils.isEmpty(code)) {
//			ToastUtils.showMessage(R.string.regist_input_code);
//			return;
//		}
//		
//		HttpClientAsync clientAsync = HttpClientAsync.getInstance();
//		clientAsync.setmToken("");
//		com.android.support.httpclient.HttpParams params = new com.android.support.httpclient.HttpParams();
//		params.put("memberName", "");
//		params.put("password", "");
//		params.put("nickName", "");
//		params.put("headImage", "");
//		clientAsync.post(XLHttpUrl.getUrl(XLHttpUrl.Register), listener)
		
//		HttpClientAsync client = HttpClientAsync.getInstance();
//		HttpParams params = new HttpParams();
//		params.put("CellPhone", phone);
//		params.put("Captcha", code);
//		params.put("Password", pwd);
//		params.put("ConfirmPassword", pwd);
//		client.post(HttpUrl.getUrl(HttpUrl.REGISTER_PHONE), params, HttpUrl.CONTENT_TYPE,
//				new HttpCallBack() {
//
//					@Override
//					public void onHttpSuccess(Object arg0) {
//						dismissWaitingDialog();
//						ToastUtil.showMessage(R.string.regist_success);
//						ShareCookie.saveNickName(phone, username);
//						onBackPressed();
//					}
//
//					@Override
//					public void onHttpStarted() {
//						showWaitingDialog();
//					}
//
//					@Override
//					public void onHttpFailure(Exception arg0, String arg1) {
//						dismissWaitingDialog();
//						if (!TextUtils.isEmpty(arg1)) {
//							ToastUtil.showMessage(arg1);
//						} else {
//							ToastUtil.showMessage(R.string.regist_fail);
//						}
//					}
//				}, StateBean.class);
	}

	private void doGetCodeAction() {
//		HttpClientAsync client = HttpClientAsync.getInstance();
//		client.setAuthToken(ShareCookie.getToken());
//		HttpParams params = new HttpParams();
//		params.put("value", phone);
//		client.post(HttpUrl.getUrl(HttpUrl.GET_CODE), params, HttpUrl.CONTENT_TYPE,
//				new HttpCallBack() {
//
//					@Override
//					public void onHttpSuccess(Object arg0) {
//						dismissWaitingDialog();
//						ToastUtil.showMessage(R.string.send_code_success);
//						if (mFlipper.getDisplayedChild() == 0) {
//							mFlipper.setInAnimation(ActRegist.this, R.anim.push_right_in);
//							mFlipper.setOutAnimation(ActRegist.this, R.anim.push_left_out);
//							mFlipper.setDisplayedChild(1);
//							mTitleBar.setMoreText("确定");
//							mTitleBar.setMoreOnClickListener(ActRegist.this);
//						}
//						tvSendState.setVisibility(View.VISIBLE);
//						stopCountDown = true;
//						Timer t = new Timer();
//						t.schedule(new TimerTask() {
//							
//							@Override
//							public void run() {
//								stopCountDown = false;
//								for (int i = 60; i >= 0; i--) {
//									if(stopCountDown) {
//										break;
//									}
//									mHandler.sendEmptyMessage(i);
//									try {
//										Thread.sleep(1000);
//									} catch (InterruptedException e) {
//										e.printStackTrace();
//									}
//								}
//							}
//						}, 1100);
//					}
//
//					@Override
//					public void onHttpStarted() {
//						showWaitingDialog();
//						btnGetCode.setEnabled(false);
//					}
//
//					@Override
//					public void onHttpFailure(Exception arg0, String arg1) {
//						dismissWaitingDialog();
//						if (!TextUtils.isEmpty(arg1)) {
//							ToastUtil.showMessage(arg1);
//						} else {
//							ToastUtil.showMessage(R.string.send_code_fail);
//						}
//						btnGetCode.setText(R.string.regist_tip_regetcode);
//						btnGetCode.setEnabled(true);
//					}
//				},StateBean.class);//
	}
}
