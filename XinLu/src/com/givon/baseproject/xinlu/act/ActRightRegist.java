/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActRightRegist.java  2014年10月21日 下午1:55:25 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.support.httpclient.HttpCallBack;
import com.android.support.httpclient.HttpClientAsync;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.cookie.ShareCookie;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.DetailImages;
import com.givon.baseproject.xinlu.entity.MemberEntity;
import com.givon.baseproject.xinlu.util.CommUtil;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.util.UploadMoreImageTask;
import com.givon.baseproject.xinlu.util.UploadMoreImageTask.UpLoadListener;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.givon.baseproject.xinlu.view.RoundImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ActRightRegist extends BaseActivity {
	private static final int SHOW_PHOTO_RESULT = 3112; // 返回图片
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

	private boolean isCheckBoy = true;
	private boolean isUping = false;

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

	private void initCheckBox(boolean ischeckBoy) {
		mCB_Boy.setChecked(ischeckBoy);
		mCB_Girl.setChecked(!ischeckBoy);
	}

	private void initView() {
		initCheckBox(isCheckBoy);
		mCB_Boy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isCheckBoy) {
					isCheckBoy = false;
					initCheckBox(isCheckBoy);
				} else {
					isCheckBoy = true;
					initCheckBox(isCheckBoy);
				}
			}
		});
		mCB_Girl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isCheckBoy) {
					isCheckBoy = false;
					initCheckBox(isCheckBoy);
				} else {
					isCheckBoy = true;
					initCheckBox(isCheckBoy);
				}
			}
		});
	}

	@OnClick(R.id.bt_actHome)
	public void doLoginHome(View v) {

		String password = mEdPassWord.getText().toString().trim();
		String nickName = mEdNickName.getText().toString().trim();
		if (StringUtil.isEmpty(avtaString)) {
			ToastUtils.showMessage("头像不能为空");
			return;
		}
		if (StringUtil.isEmpty(mPhone)) {
			ToastUtils.showMessage("注册号码为空，需要重新验证");
			return;
		}
		if (StringUtil.isEmpty(password)) {
			ToastUtils.showMessage("密码不能为空");
			return;
		}
		if (StringUtil.isEmpty(nickName)) {
			ToastUtils.showMessage("昵称不能为空");
			return;
		}
		registAction(mPhone, password, nickName, isCheckBoy, avtaString);
	}

	@OnClick(R.id.rv_header)
	public void selectHeader(View v) {
		if(isUping){
			ToastUtils.showMessage("上传中。。。");
			return;
		}
		Intent intent = new Intent(ActRightRegist.this, ActSelectPic.class);
		startActivityForResult(intent, SHOW_PHOTO_RESULT);
	}

	private void registAction(String memberName, String password, String nickName, Boolean sex,
			String headImage) {
		HttpClientAsync httpClientAsync = HttpClientAsync.getInstance();
		// HttpParams params = new HttpParams();
		// params.put("memberName", "18780118236");
		// params.put("password", "123456");
		// params.put("nickName", "Hello");
		// params.put("sex", "男");
		// params.put("headImage", "/test/upload/1413943835514.jpg");

		JSONObject object = new JSONObject();
		object.put("memberName", mPhone);
		object.put("password", password);
		object.put("nickName", nickName);
		if(sex){
			object.put("sex", "男");
		}else {
			object.put("sex", "女");
		}
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


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SHOW_PHOTO_RESULT && data != null && resultCode == Activity.RESULT_OK) {
			if (data.hasExtra(ActSelectPic.KEY_PHOTO_PATH)) {
				String stringPath = data.getStringExtra(ActSelectPic.KEY_PHOTO_PATH);
//				BitmapFactory.Options options = new BitmapFactory.Options();
//				options.inJustDecodeBounds = true;
//				Bitmap bmp = BitmapFactory.decodeFile(stringPath, options);
//				float ox = 120;
//				int height = (int) (options.outHeight * (ox/ options.outWidth));
//				options.outWidth = (int) ox;
//				options.outHeight = height;
//				options.inJustDecodeBounds = false;
////				options.inSampleSize = (int) (options.outWidth / ox);
//				options.inInputShareable = true;
//				options.inPurgeable = true;
//				options.inPreferredConfig = Bitmap.Config.ARGB_4444;
				Bitmap bmp = CommUtil.createImageThumbnail(stringPath);
				mRoundImageView.cleanbitmap();
				mRoundImageView.setImageBitmap(bmp);
				isUping = true;
				showWaitingDialog();
				UploadMoreImageTask imageTask = new UploadMoreImageTask();
				imageTask.setUpLoadListener(new UpLoadListener() {

					@Override
					public void UpLoadResult(ArrayList<DetailImages> path) {
						dismissWaitingDialog();
						isUping = false;
						// 上传成功
						ToastUtils.showMessage("上传成功");
						avtaString = path.get(0).getImageUrl();
					}

					@Override
					public void UpLoadFail() {
						dismissWaitingDialog();
						isUping = false;
						ToastUtils.showMessage("上传失败");
					}

					@Override
					public void UpLoadError(ArrayList<String> notUpPath) {

					}
				});
				ArrayList<DetailImages> list = new ArrayList<DetailImages>();
				DetailImages detailImages = new DetailImages();
				detailImages.setImageUrl(stringPath);
				list.add(detailImages);
				imageTask.execute(list, UploadMoreImageTask.UPLOAD_IMAGE);
			}
		}
	}
	
	

}
