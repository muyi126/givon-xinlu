/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActPublish.java  2014年10月30日 上午11:12:41 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.support.httpclient.HttpCallBack;
import com.android.support.httpclient.HttpClientAsync;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.adapter.PublishPhotoAdapter;
import com.givon.baseproject.xinlu.cookie.ShareCookie;
import com.givon.baseproject.xinlu.entity.BaseEntity;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.DetailImages;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.util.UploadMoreFileTask;
import com.givon.baseproject.xinlu.util.UploadMoreImageTask;
import com.givon.baseproject.xinlu.util.UploadMoreImageTask.UpLoadListener;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.givon.baseproject.xinlu.view.PublishGridView;
import com.givon.baseproject.xinlu.view.PublishScrollView;
import com.givon.baseproject.xinlu.view.RecordButton;
import com.givon.baseproject.xinlu.view.RecordButton.OnFinishedRecordListener;
import com.givon.baseproject.xinlu.view.SelectTimeDialog;
import com.givon.baseproject.xinlu.view.SelectTimeDialog.OnSelectTimeListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ActPublish extends BaseActivity {
	private PublishGridView mGridView;
	private PublishPhotoAdapter mAdapter;
	private ArrayList<DetailImages> pathList = new ArrayList<DetailImages>();
	private Button mFinishButton;
	private Button mCancelButton;
	private boolean isUpDataing = false;
	private boolean isUpLuYining = false;
	private PublishScrollView mScroll;
	private RecordButton mLuYinButton;
	private SelectTimeDialog mSelectTimeDialog;
	private String mAddress;
	private String dateTre;
	private String luYinPath;

	@ViewInject(R.id.et_title)
	private EditText et_Title;
	@ViewInject(R.id.et_content)
	private EditText et_Content;

	@ViewInject(R.id.rl_time)
	private RelativeLayout m_Rl_SelectTime;
	@ViewInject(R.id.rl_publish_zone)
	private RelativeLayout m_Rl_PublishZone;
	@ViewInject(R.id.rl_bgMusic)
	private RelativeLayout m_Rl_BgMusic;
	@ViewInject(R.id.rl_auto_address)
	private RelativeLayout m_Rl_AutoAddress;

	@ViewInject(R.id.tv_time_select)
	private TextView m_Tv_Time_select;
	@ViewInject(R.id.tv_publish_zone_select)
	private TextView m_Tv_Publish_zone;
	@ViewInject(R.id.tv_bgMusic_select)
	private TextView m_Tv_BgMusic_select;
	@ViewInject(R.id.tv_auto_address_select)
	private TextView m_Tv_Auto_address_select;

	private static final int SHOW_AUTOADDRESS_RESULT = 112312;
	private static final int SHOW_BGMUSIC_RESULT = 112313;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_actpublish);
		ViewUtils.inject(this);
		mGridView = (PublishGridView) findViewById(R.id.GridView_photo);
		mFinishButton = (Button) findViewById(R.id.bt_finish);
		mCancelButton = (Button) findViewById(R.id.bt_cancel);
		mScroll = (PublishScrollView) findViewById(R.id.sc_Scroll);
		mLuYinButton = (RecordButton) findViewById(R.id.bt_Listener);
		mAdapter = new PublishPhotoAdapter(this);
		mGridView.setAdapter(mAdapter);
		LayoutParams params = mGridView.getLayoutParams();
		params.height = (BaseApplication.mWidth / 3) * 2;
		mGridView.setLayoutParams(params);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});
		Intent intent = getIntent();
		if (intent.hasExtra(Constant.DATA)) {
			DetailImages detailImages = (DetailImages) intent.getSerializableExtra(Constant.DATA);
			detailImages.setSequences(0);
			if (null != detailImages) {
				pathList.add(detailImages);
				mAdapter.updateAdapter(pathList, pathList.size());
			}
		}
		mFinishButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				String titleString = et_Title.getText().toString().trim();
				String contentString = et_Content.getText().toString().trim();
				if (StringUtil.isEmpty(titleString)) {
					ToastUtils.showMessage("标题不能为空");
					return;
				}
				// if(StringUtil.isEmpty(contentString)){
				// ToastUtils.showMessage("内容不能为空");
				// return;
				// }
				if (!isUpDataing) {
					showWaitingDialog();
					isUpDataing = true;
					UploadMoreImageTask task = new UploadMoreImageTask();
					task.setUpLoadListener(new UpLoadListener() {

						@Override
						public void UpLoadResult(ArrayList<DetailImages> path) {
							isUpDataing = false;
							dismissWaitingDialog();
							actionSaveTravel(path);
						}

						@Override
						public void UpLoadFail() {
							ToastUtils.showMessage("上传失败");
							isUpDataing = false;
							dismissWaitingDialog();
						}

						@Override
						public void UpLoadError(ArrayList<String> notUpPath) {

						}
					});
					task.execute(pathList, UploadMoreImageTask.UPLOAD_IMAGE);
				}
			}
		});

		mLuYinButton.setOnFinishedRecordListener(new OnFinishedRecordListener() {
			@Override
			public void onFinishedRecord(String audioPath, int time) {
				showWaitingDialog();
				luYinPath = "";
				if (!StringUtil.isEmpty(audioPath)) {
					upLoadLuYin(audioPath);
				} else {
					ToastUtils.showMessage("录音失败");
				}
				// System.out.println("地址" + audioPath + "时间是" + time);
			}
		});

		mSelectTimeDialog = new SelectTimeDialog(this, R.style.selectorDialog);
		mSelectTimeDialog.setOnSelectTimeListener(new OnSelectTimeListener() {

			@Override
			public void onSelectFinish(String date, long dateTre) {
				m_Tv_Time_select.setText(date);
				System.out.println("time:" + dateTre + "");
				ActPublish.this.dateTre = dateTre / 1000 + "";
			}

			@Override
			public void onSelectCancel() {

			}
		});

	}

	private void actionSaveTravel(ArrayList<DetailImages> pathList) {
		JSONObject jsonObject = new JSONObject();
		// JSONArray jsonArray = new JSONArray();
		// for (int i = 0; i < pathList.size(); i++) {
		// DetailImages detailImages = new DetailImages();
		// detailImages.setImageUrl(pathList.get(i));
		// jsonArray.add(detailImages);
		// }
		String titleString = et_Title.getText().toString().trim();
		String content = et_Content.getText().toString().trim();
		jsonObject.put("memberId", ShareCookie.getUserId());
		if (!StringUtil.isEmpty(titleString)) {
			jsonObject.put("title", titleString);
		}
		if (!StringUtil.isEmpty(content)) {
			jsonObject.put("releaseContent", content);
		}
		if (!StringUtil.isEmpty(luYinPath)) {
			jsonObject.put("voice", luYinPath);
		}
		jsonObject.put("groupId", 1);
		if (!StringUtil.isEmpty(mAddress)) {
			jsonObject.put("location", mAddress);
		}
		jsonObject.put("backgroundMusic", "");
		if (!StringUtil.isEmpty(dateTre)) {
			jsonObject.put("regularTime", dateTre);
		}
		jsonObject.put("detailImages", pathList);
		String dataString = JSON.toJSONString(jsonObject);
		System.out.println(dataString);
		HttpClientAsync httpClientAsync = HttpClientAsync.getInstance();
		httpClientAsync.setmToken(ShareCookie.getToken());
		httpClientAsync.post(XLHttpUrl.getUrl(XLHttpUrl.SaveTravel), dataString,
				XLHttpUrl.CONTENT_TYPE, new HttpCallBack() {

					@Override
					public void onHttpSuccess(Object obj) {
						dismissWaitingDialog();
						BaseEntity entity = JSON.parseObject(((String) obj), BaseEntity.class);
						if (null != entity) {
							if (entity.getState() == 1) {
								ToastUtils.showMessage("上传成功");
							} else {
								ToastUtils.showMessage("上传失败");
							}
						} else {
							ToastUtils.showMessage("上传失败");
						}
					}

					@Override
					public void onHttpStarted() {
						showWaitingDialog();
					}

					@Override
					public void onHttpFailure(Exception e, String message) {
						dismissWaitingDialog();
						e.printStackTrace();
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (PublishPhotoAdapter.RESULT_CODE == requestCode && Activity.RESULT_OK == resultCode) {
			if (data.hasExtra(Constant.DATA) && data.hasExtra(Constant.ID)) {
				int id = data.getIntExtra(Constant.ID, 0);
				DetailImages detailImages = (DetailImages) data.getSerializableExtra(Constant.DATA);
				detailImages.setSequences(id);
				System.out.println("id:" + id + "  pathList:" + pathList.size());
				if (id == pathList.size()) {
					pathList.add(detailImages);
					ArrayList<DetailImages> list = new ArrayList<DetailImages>();
					list.add(detailImages);
					mAdapter.updateAdapter(list, pathList.size());

				} else {
					if (id < pathList.size()) {
						pathList.set(id, detailImages);
						mAdapter.mList.set(id, detailImages);
						mAdapter.notifyDataSetChanged();
					}
				}
			}
		} else
		// 地址
		if (SHOW_AUTOADDRESS_RESULT == requestCode && Activity.RESULT_OK == resultCode) {
			if (data.hasExtra(Constant.DATA)) {
				mAddress = data.getStringExtra(Constant.DATA);
				m_Tv_Auto_address_select.setText(mAddress);
			}
		}
	}

	@OnClick(R.id.rl_auto_address)
	public void actionAutoAddress(View v) {
		Intent intent = new Intent(this, ActAutoAddress.class);
		startActivityForResult(intent, SHOW_AUTOADDRESS_RESULT);

	}

	@OnClick(R.id.rl_publish_zone)
	public void actionPublishZone(View v) {

	}

	@OnClick(R.id.rl_time)
	public void actionSelectTime(View v) {
		if (null != mSelectTimeDialog) {
			if (!mSelectTimeDialog.isShowing()) {
				mSelectTimeDialog.show();
			}
		}
	}

	@OnClick(R.id.rl_bgMusic)
	public void actionBgMusic(View v) {
		Intent intent = new Intent(this, ActBgMusicList.class);
		startActivityForResult(intent, SHOW_BGMUSIC_RESULT);
	}

	private void upLoadLuYin(final String path) {
		if (!isUpDataing) {
			showWaitingDialog();
			isUpLuYining = true;
			ArrayList<String> pathList = new ArrayList<String>();
			pathList.add(path);
			UploadMoreFileTask taFileTask = new UploadMoreFileTask();
			taFileTask.setUpLoadListener(new UploadMoreFileTask.UpLoadListener() {

				@Override
				public void UpLoadResult(ArrayList<String> pathd) {
					isUpLuYining = false;
					dismissWaitingDialog();
					luYinPath = pathd.get(0);
					ToastUtils.showMessage("上传录音成功");
				}

				@Override
				public void UpLoadFail() {
					ToastUtils.showMessage("上传失败");
					isUpLuYining = false;
					dismissWaitingDialog();

				}

				@Override
				public void UpLoadError(ArrayList<String> notUpPath) {

				}
			});
			taFileTask.execute(pathList);
		}
	}

}
