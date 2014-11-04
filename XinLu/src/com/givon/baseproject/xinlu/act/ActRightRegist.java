/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActRightRegist.java  2014年10月21日 下午1:55:25 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.support.httpclient.HttpCallBack;
import com.android.support.httpclient.HttpClientAsync;
import com.android.support.httpclient.HttpParams;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.cookie.ShareCookie;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.MemberEntity;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.givon.baseproject.xinlu.view.RoundImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.upyun.api.Uploader;
import com.upyun.api.utils.UpYunException;
import com.upyun.api.utils.UpYunUtils;

public class ActRightRegist extends BaseActivity {
	private static final String TEST_API_KEY = "lVBfg35QWT+r/LMFsoJjiQ5deno="; // 测试使用的表单api验证密钥
	private static final String BUCKET = "tiangou-app-img"; // 存储空间
	private static final long EXPIRATION = System.currentTimeMillis() / 1000 + 600; // 过期时间，必须大于当前时间
	private String avtaString;
	private String mPhone;

	// private static final String SOURCE_FILE = Environment.getExternalStorageDirectory()
	// .getAbsolutePath() + File.separator + "IMAG1104.jpg"; // 来源文件

	@ViewInject(R.id.bt_actHome)
	private Button mLoginButton;

	@ViewInject(R.id.mima_Check)
	private CheckBox mCB_PassWord;

	@ViewInject(R.id.ck_boyCheck)
	private CheckBox mCB_Boy;

	@ViewInject(R.id.ck_girlCheck)
	private CheckBox mCB_Girl;

	@ViewInject(R.id.rv_header)
	private RoundImageView mRoundImageView;

	@ViewInject(R.id.et_setPassWord)
	private EditText mEdPassWord;

	@ViewInject(R.id.et_nikename)
	private EditText mEdNickName;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_actright_regist);
		ViewUtils.inject(this);
		Intent intent = getIntent();
		if (null != intent && intent.hasExtra(Constant.PHONE)) {
			mPhone = intent.getStringExtra(Constant.PHONE);
		}
		initView();
	}

	private void initView() {
		mCB_Boy.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mCB_Boy.setChecked(isChecked);
					mCB_Girl.setChecked(!isChecked);
				} else {
					mCB_Boy.setChecked(!isChecked);
					mCB_Girl.setChecked(isChecked);
				}
			}
		});
		mCB_Girl.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					mCB_Girl.setChecked(isChecked);
					mCB_Boy.setChecked(!isChecked);
				} else {
					mCB_Girl.setChecked(!isChecked);
					mCB_Boy.setChecked(isChecked);
				}
			}
		});
	}

	@OnClick(R.id.bt_actHome)
	public void doLoginHome(View v) {

		String password = mEdPassWord.getText().toString().trim();
		String nickName = mEdNickName.getText().toString().trim();
		// if(StringUtil.isEmpty(avtaString)){
		// ToastUtils.showMessage("头像不能为空");
		// return;
		// }
		// if(StringUtil.isEmpty(mPhone)){
		// ToastUtils.showMessage("注册号码为空，需要重新验证");
		// return;
		// }
		// if(StringUtil.isEmpty(password)){
		// ToastUtils.showMessage("密码不能为空");
		// return;
		// }
		// if(StringUtil.isEmpty(nickName)){
		// ToastUtils.showMessage("昵称不能为空");
		// return;
		// }
		registAction(mPhone, password, nickName, "男", avtaString);
	}

	@OnClick(R.id.rv_header)
	public void selectHeader(View v) {
		loadPhotos(10);
	}

	private void registAction(String memberName, String password, String nickName, String sex,
			String headImage) {
		HttpClientAsync httpClientAsync = HttpClientAsync.getInstance();
//		HttpParams params = new HttpParams();
//		params.put("memberName", "18780118236");
//		params.put("password", "123456");
//		params.put("nickName", "Hello");
//		params.put("sex", "男");
//		params.put("headImage", "/test/upload/1413943835514.jpg");

		JSONObject object = new JSONObject();
		object.put("memberName", mPhone);
		object.put("password", "123456");
		object.put("nickName", "Hello");
		object.put("sex", "男");
		object.put("headImage", avtaString);
		String dataString = JSON.toJSONString(object);
		System.out.println(dataString);
		httpClientAsync.post(XLHttpUrl.getUrl(XLHttpUrl.Register), dataString,
				XLHttpUrl.CONTENT_TYPE, new HttpCallBack() {

					@Override
					public void onHttpSuccess(Object obj) {
						MemberEntity entity = (MemberEntity) obj;
						if (null != entity) {
							ShareCookie.saveUserInfo(entity);
							ShareCookie.setToken(entity.getToken());
							ShareCookie.setLoginAuth(true);
							setResult(Activity.RESULT_OK, null);
							finish();
						}
					}

					@Override
					public void onHttpStarted() {

					}

					@Override
					public void onHttpFailure(Exception e, String message) {
						if (!StringUtil.isEmpty(message)) {
							ToastUtils.showMessage(message);
						}
					}
				}, MemberEntity.class);

	}

	private void loadPhotos(int requestCode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 80);
		intent.putExtra("outputY", 80);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, requestCode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null && resultCode == Activity.RESULT_OK) {
			Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
			String name = "/header.jpg";
			// String name = "/"
			// + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))
			// + ".jpg";
			FileOutputStream fout = null;
			File file = new File("/sdcard/ZuJi/");
			file.mkdirs();
			String filename = file.getPath() + name;
			try {
				fout = new FileOutputStream(filename);
				cameraBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					fout.flush();
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			cameraBitmap.recycle();
			new UploadTask().execute(filename);

		}
	}

	public class UploadTask extends AsyncTask<String, Void, String> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			String string = null;
			String filePath = params[0];
			try {
				// test/upload
				// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
				String SAVE_KEY = File.separator + "test" + File.separator + "upload"
						+ File.separator + System.currentTimeMillis() + ".jpg";
				System.out.println("SAVE_KEY:" + SAVE_KEY);
				System.out.println("SAVE_KEY:" + filePath);
				// 取得base64编码后的policy
				String policy = UpYunUtils.makePolicy(SAVE_KEY, EXPIRATION, BUCKET);

				// 根据表单api签名密钥对policy进行签名
				// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
				String signature = UpYunUtils.signature(policy + "&" + TEST_API_KEY);

				// 上传文件到对应的bucket中去。
				string = Uploader.upload(policy, signature, BUCKET, filePath);
			} catch (UpYunException e) {
				e.printStackTrace();
			}

			return string;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				avtaString = result;
				Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_LONG).show();
//				BitmapUtils bitmapUtils = new BitmapUtils(ActRightRegist.this);
//				bitmapUtils.display(avtaString, displayConfig);
			} else {
				Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_LONG).show();
			}
		}

	}

}
