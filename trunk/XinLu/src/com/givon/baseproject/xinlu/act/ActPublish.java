/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
import android.widget.Button;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.support.httpclient.HttpCallBack;
import com.android.support.httpclient.HttpClientAsync;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.adapter.PublishPhotoAdapter;
import com.givon.baseproject.xinlu.cookie.ShareCookie;
import com.givon.baseproject.xinlu.entity.BaseEntity;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.DetailImages;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.util.UploadMoreImageTask;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.givon.baseproject.xinlu.util.UploadMoreImageTask.UpLoadListener;

public class ActPublish extends BaseActivity {
	private GridView mGridView;
	private PublishPhotoAdapter mAdapter;
	private ArrayList<DetailImages> pathList = new ArrayList<DetailImages>();
	private Button mFinishButton;
	private Button mCancelButton;
	private boolean isUpDataing = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_actpublish);
		mGridView = (GridView) findViewById(R.id.GridView_photo);
		mFinishButton = (Button) findViewById(R.id.bt_finish);
		mCancelButton = (Button) findViewById(R.id.bt_cancel);
		mAdapter = new PublishPhotoAdapter(this);
		mGridView.setAdapter(mAdapter);
		Intent intent = getIntent();
		if (intent.hasExtra(Constant.DATA)) {
			DetailImages detailImages = (DetailImages) intent.getSerializableExtra(Constant.DATA);
			if (null != detailImages) {
				pathList.add(detailImages);
				mAdapter.updateAdapter(pathList, pathList.size());
			}
		}
		mFinishButton.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
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
							isUpDataing = false;
							dismissWaitingDialog();
						}

						@Override
						public void UpLoadError(ArrayList<String> notUpPath) {

						}
					});
					task.execute(pathList);
				}
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
		jsonObject.put("memberId", ShareCookie.getUserId());
		jsonObject.put("title", "Test_One");
		jsonObject.put("releaseContent", "");
		jsonObject.put("voice", "");
		jsonObject.put("groupId", 1);
		jsonObject.put("location", "");
		jsonObject.put("backgroundMusic", "");
		jsonObject.put("regularTime", "");
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
						if(null!=entity){
							if(entity.getState()==1){
								ToastUtils.showMessage("上传成功");
							}else {
								ToastUtils.showMessage("上传失败");
							}
						}else {
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
			if (data.hasExtra(Constant.DATA)) {
				DetailImages detailImages = (DetailImages) data.getSerializableExtra(Constant.DATA);
				pathList.add(detailImages);
				ArrayList<DetailImages> list = new ArrayList<DetailImages>();
				list.add(detailImages);
				mAdapter.updateAdapter(list, pathList.size());
			}
		}
	}

}
