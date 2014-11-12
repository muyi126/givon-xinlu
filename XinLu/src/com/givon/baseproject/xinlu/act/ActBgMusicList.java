/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActBgMusicList.java  2014年11月6日 下午5:18:38 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.support.httpclient.HttpCallBack;
import com.android.support.httpclient.HttpClientAsync;
import com.givon.baseproject.xinlu.BaseListActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.cookie.ShareCookie;
import com.givon.baseproject.xinlu.entity.BackgroundMusicEntity;
import com.givon.baseproject.xinlu.entity.BackgroundMusicModel;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.givon.baseproject.xinlu.view.PullListView.PullListViewListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ActBgMusicList extends BaseListActivity<BackgroundMusicModel> implements
		PullListViewListener, com.givon.baseproject.xinlu.BaseListActivity.AddFootViewCallBack {
	private int mSelectPostion = 0;
	private BackgroundMusicModel mSelectModel;
	private View mFootView;
	private TextView mDaoruButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setAddFootViewCallBack(this);
		super.onCreate(savedInstanceState);
		mTitleBar.setMoreText("完成");
		mTitleBar.setMoreOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != mSelectModel) {
					Intent intent = new Intent();
					intent.putExtra(Constant.DATA, mSelectModel);
					setResult(Activity.RESULT_OK, intent);
				}
				finish();
			}
		});
		mTitleBar.setTitle("背景音乐");
		mListView.setPullListener(this);
		mListView.startOnRefresh();
	}

	@Override
	protected View getItemView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (null == convertView) {
			convertView = getLayoutInflater().inflate(R.layout.item_act_bgmusic, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		BackgroundMusicModel model = mAdapter.getItem(position);
		if (null != model) {
			viewHolder.name.setText(model.getMusicName());
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
				mSelectModel = mAdapter.getItem(position);
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

	private void actionGetBGMusicList() {
		HttpClientAsync clientAsync = HttpClientAsync.getInstance();
		clientAsync.setmToken(ShareCookie.getToken());
		clientAsync.get(XLHttpUrl.getUrl(XLHttpUrl.GetBackgroundMusic), new HttpCallBack() {

			@Override
			public void onHttpSuccess(Object obj) {
				BackgroundMusicEntity entity = (BackgroundMusicEntity) obj;
				if (null != entity && null != entity.getData() && entity.getData().size() > 0) {
					mAdapter.putData(entity.getData());
				}
				mListView.onRefreshFinish();
				mListView.onLoadFinish();

			}

			@Override
			public void onHttpStarted() {

			}

			@Override
			public void onHttpFailure(Exception e, String message) {
				e.printStackTrace();
				if(!StringUtil.isEmpty(message)){
					ToastUtils.showMessage(message);
				}
			}
		}, BackgroundMusicEntity.class);

	}

	@Override
	public void onPullRefresh() {
		actionGetBGMusicList();
	}

	@Override
	public void onPullLoadMore() {

	}

	@Override
	public void addFootView() {
		mFootView = (View) getLayoutInflater().inflate(R.layout.layout_act_bgmusic_list_foot, null);
		mDaoruButton = (TextView) mFootView.findViewById(R.id.tv_daoru);
		mDaoruButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		mListView.addFooterView(mFootView);
	}

}
