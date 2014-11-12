/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActAutoAddress.java  2014年11月5日 下午3:46:15 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.BaseListActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.LBSAddress;
import com.givon.baseproject.xinlu.view.PullListView.PullListViewListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ActAutoAddress extends BaseListActivity<PoiInfo> implements PullListViewListener,
		OnGetGeoCoderResultListener {
	private int mSelectPostion = 0;
	private PoiInfo mSelectPoiInfo;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mTitleBar.setMoreText("完成");
		mTitleBar.setMoreOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != mSelectPoiInfo) {
					Intent intent = new Intent();
					intent.putExtra(Constant.DATA, mSelectPoiInfo.name);
					setResult(Activity.RESULT_OK, intent);
				}
				finish();
			}
		});
		PoiInfo poiInfo = new PoiInfo();
		poiInfo.name = "不显示位置";
		mListData.add(poiInfo);
		mListView.setPullListener(this);
		mListView.startOnRefresh();
	}

	@Override
	protected View getItemView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (null == convertView) {
			convertView = getLayoutInflater().inflate(R.layout.item_act_autoaddress, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		PoiInfo poiInfo = mAdapter.getItem(position);
		if (null != poiInfo) {
			viewHolder.name.setText(poiInfo.name);
			if (position == mSelectPostion) {
				viewHolder.iv_check.setVisibility(View.VISIBLE);
			} else {
				viewHolder.iv_check.setVisibility(View.GONE);
			}
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSelectPostion = position;
				mSelectPoiInfo = mAdapter.getItem(position);
				mAdapter.notifyDataSetChanged();
			}
		});
		return convertView;
	}

	private class ViewHolder {
		@ViewInject(R.id.tv_name)
		TextView name;
		@ViewInject(R.id.iv_isCheck)
		ImageView iv_check;
	}

	private void getLsbAddressList() {
		if (BaseApplication.lastLocation != null) {
			GeoCoder geoCoder = GeoCoder.newInstance();
			ReverseGeoCodeOption option = new ReverseGeoCodeOption();
			option.location(new LatLng(BaseApplication.lastLocation.getLatitude(),
					BaseApplication.lastLocation.getLongitude()));
			geoCoder.setOnGetGeoCodeResultListener(this);
			geoCoder.reverseGeoCode(option);
		}

	}

	@Override
	public void onPullRefresh() {
		getLsbAddressList();
	}

	@Override
	public void onPullLoadMore() {

	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {

	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		if (null != arg0 && null != arg0.getPoiList() && arg0.getPoiList().size() > 0) {
			mListData.clear();
			PoiInfo poiInfo = new PoiInfo();
			poiInfo.name = "不显示位置";
			mListData.add(poiInfo);
			mListData.addAll(arg0.getPoiList());
			mListView.onRefreshFinish();
			mListView.onLoadFinish();
		}
	}
}
