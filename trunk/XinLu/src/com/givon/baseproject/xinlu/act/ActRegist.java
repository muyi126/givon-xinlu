/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActRegist.java  2014-2-25 上午10:12:35 - jiangyue
 * @author jiangyue
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import static cn.smssdk.framework.utils.R.getIdRes;
import static cn.smssdk.framework.utils.R.getLayoutRes;
import static cn.smssdk.framework.utils.R.getStringRes;
import static cn.smssdk.framework.utils.R.getStyleRes;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.params.HttpParams;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.CommonDialog;
import cn.smssdk.gui.CountryPage;
import cn.smssdk.gui.RegisterPage;

import com.android.support.httpclient.HttpClientAsync;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.util.CommUtil;
import com.givon.baseproject.xinlu.util.StringUtil;
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
	// private ViewFlipper mFlipper;
	private EditText etPhone;
	private EditText etCode;
	private Button btnRegist;
	private Button btnGetCode;
	private TextView tvSendState;
	private boolean isEmailRegist;
	private String phone;
	private String pwd;
	private boolean stopCountDown;
	private EventHandler handler;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_act_regist);
		initViewLayout();
	}

	private void initViewLayout() {
		mTitleBar = (AppTitleBar) findViewById(R.id.id_titlebar);
		mTitleBar.setBackTitle("取消");
		mTitleBar.setTitle(R.string.regist);
		// mFlipper = (ViewFlipper) findViewById(R.id.vf_regist);
		etPhone = (EditText) findViewById(R.id.et_phone);
		etCode = (EditText) findViewById(R.id.et_code);
		btnRegist = (Button) findViewById(R.id.bt_quest_rigest);
		btnRegist.setOnClickListener(this);
		btnGetCode = (Button) findViewById(R.id.bt_huoqu);
		btnGetCode.setOnClickListener(this);
		tvSendState = (TextView) findViewById(R.id.tv_tip_send_state);
		handler = new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
					// 提交验证码
					dismissWaitingDialog();
					if (result == SMSSDK.RESULT_COMPLETE) {
						runOnUiThread(new Runnable() {
							public void run() {
								Intent intent = new Intent(ActRegist.this,ActRightRegist.class);
								intent.putExtra(Constant.PHONE, phone);
								startActivity(intent);
//								showActivity(ActRightRegist.class, true);
							}
						});
					} else {
						((Throwable) data).printStackTrace();
						// 验证码不正确
						runOnUiThread(new Runnable() {
							public void run() {
								ToastUtils.showMessage("验证错误");
							}
						});
					}
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
					dismissWaitingDialog();
					// 获取验证码成功后的执行动作
					// afterGet(result, data);
				}
			}
		};
		SMSSDK.registerEventHandler(handler);
	}

	@Override
	public void onClick(View v) {

		if (v == btnRegist) {
			if (!BaseApplication.isNetworkAvailable(this)) {
				ToastUtils.showMessage(R.string.toast_no_intent);
				return;
			}
			String codeString = etCode.getText().toString().trim();
			if (!StringUtil.isEmpty(codeString)) {
				doRegistByPhone("86", codeString);
			} else {
				ToastUtils.showMessage("验证码不能为空");
			}
		} else if (v == btnGetCode) {
			final String phoneEmail = etPhone.getText().toString();

			if (!CommUtil.isMobileNO(phoneEmail)) {
				ToastUtils.showMessage(R.string.regist_input_phone_error);
				return;
			}
			this.phone = phoneEmail;
			doGetCodeAction(phoneEmail);
		}
	}

	private void doRegistByPhone(String code, String verificationCode) {
		final String phoneEmail = etPhone.getText().toString();
		if (!CommUtil.isMobileNO(phoneEmail)) {
			ToastUtils.showMessage(R.string.regist_input_phone_error);
			return;
		}
		showWaitingDialog();
		this.phone = phoneEmail;
		SMSSDK.submitVerificationCode(code, phoneEmail, verificationCode);
	}

	private void doGetCodeAction(String phone) {
		// 请求发送短信验证码
		String code = "86";
		checkPhoneNum(phone, code);

	}

	// 检查电话号码
	private void checkPhoneNum(String phone, String code) {
		if (code.startsWith("+")) {
			code = code.substring(1);
		}

		// if(TextUtils.isEmpty(phone)) {
		// int resId = getStringRes(activity, "smssdk_write_mobile_phone");
		// if (resId > 0) {
		// Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
		// }
		// return;
		// }
		// HashMap<String, String> countryRules
		// CountryPage countryPage = new CountryPage();
		// countryPage.setCountryId(currentId);
		// String rule = "\^1(3\|5|7\|8\|4)\\d{9}";
		Pattern p = Pattern.compile("^1(3|5|7|8|4)\\d{9}");
		Matcher m = p.matcher(phone);
		int resId = 0;
		if (!m.matches()) {
			if (resId > 0) {
				ToastUtils.showMessage("手机号码不正确");
			}
			return;
		}
		// 弹出对话框，发送验证码
		showDialog(phone, code);
	}

	// 是否请求发送验证码，对话框
	public void showDialog(final String phone, final String code) {
		int resId = getStyleRes(this, "CommonDialog");
		if (resId > 0) {
			final String phoneNum = "+" + code + " " + splitPhoneNum(phone);
			final Dialog dialog = new Dialog(this, resId);
			resId = getLayoutRes(this, "smssdk_send_msg_dialog");
			if (resId > 0) {
				dialog.setContentView(resId);
				resId = getIdRes(this, "tv_phone");
				((TextView) dialog.findViewById(resId)).setText(phoneNum);
				resId = getIdRes(this, "tv_dialog_hint");
				TextView tv = (TextView) dialog.findViewById(resId);
				resId = getStringRes(this, "smssdk_make_sure_mobile_detail");
				if (resId > 0) {
					String text = this.getString(resId);
					tv.setText(Html.fromHtml(text));
				}
				resId = getIdRes(this, "btn_dialog_ok");
				if (resId > 0) {
					((Button) dialog.findViewById(resId)).setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// 跳转到验证码页面
							dialog.dismiss();
							Log.e("verification phone ==>>", phone);
							showWaitingDialog();
							SMSSDK.getVerificationCode(code, phone.trim());
						}
					});
				}
				resId = getIdRes(this, "btn_dialog_cancel");
				if (resId > 0) {
					((Button) dialog.findViewById(resId)).setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
				}
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();
			}
		}
	}

	// 分割电话号码
	protected String splitPhoneNum(String phone) {
		StringBuilder builder = new StringBuilder(phone);
		builder.reverse();
		for (int i = 4, len = builder.length(); i < len; i += 5) {
			builder.insert(i, ' ');
		}
		builder.reverse();
		return builder.toString();
	}

	// 倒数计时
	private void countDown() {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterEventHandler(handler);
	}
}
