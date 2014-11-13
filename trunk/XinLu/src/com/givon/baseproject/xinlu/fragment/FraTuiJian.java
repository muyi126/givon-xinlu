/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @FraTuiJian.java  2014年10月16日 下午12:19:45 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

import com.android.support.httpclient.HttpCallBack;
import com.android.support.httpclient.HttpClientAsync;
import com.android.support.httpclient.HttpParams;
import com.givon.baseproject.xinlu.BaseListFragment;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.adapter.TuiJianAdapter;
import com.givon.baseproject.xinlu.cookie.ShareCookie;
import com.givon.baseproject.xinlu.entity.IndexTravelEntity;
import com.givon.baseproject.xinlu.entity.IndexTravelModel;
import com.givon.baseproject.xinlu.interfaceview.ViewPageTopListener;
import com.givon.baseproject.xinlu.util.BitmapHelp;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.givon.baseproject.xinlu.view.PullListView;
import com.givon.baseproject.xinlu.view.PullListView.PullListViewListener;

public class FraTuiJian extends BaseListFragment<IndexTravelModel> implements PullListViewListener {
	private ViewPageTopListener mPageTopListener;
	private int pageIndex = 1;
	private TuiJianAdapter mAdapter;
	private boolean flag;

	public FraTuiJian(ViewPageTopListener l) {
		mPageTopListener = l;
	}

	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_frauser_tuijian, container, false);
		mListView = (PullListView) v.findViewById(R.id.id_pull_listview);
		mErrorInfo = (TextView) v.findViewById(R.id.tv_info);
		mListView.setHeaderDividersEnabled(false);
		mListView.setFooterDividersEnabled(false);
		mListView.setAdapter(mAdapter);
		mListView.setPullRefreshEnable(false);
		mListView.setPullLoadEnable(true);
		mListView.setPullListener(this);
		mListView.setSelector(android.R.color.transparent);
		if (null != addHeaderViewCallBack) {
			addHeaderViewCallBack.addHeaderView();
		}
		mAdapter = new TuiJianAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		return v;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				// 当不滚动时
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 判断滚动到底部
					if (mListView.getLastVisiblePosition() == (mListView.getCount() - 1)) {
					}
					// 判断滚动到顶部

					if (mListView.getFirstVisiblePosition() == 0) {
						mPageTopListener.onFirst();
					}

					break;
				}
			}

			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				mPageTopListener.onScroll();
				if (firstVisibleItem + visibleItemCount == totalItemCount && !flag) {
					flag = true;
				} else
					flag = false;
			}
		});
	};

	private void initView() {
		
	}

	public void initDataRefash() {
		onPullRefresh();
	}

	private void loadData(int pageindex) {
		HttpClientAsync clientAsync = HttpClientAsync.getInstance();
		HttpParams params = new HttpParams();
		params.put("sortType", "1");
		params.put("keyword", "");
		params.put("pageIndex", pageindex);
		params.put("pageSize", XLHttpUrl.PAGE_SIZE);
		clientAsync.setmToken(ShareCookie.getToken());
		clientAsync.get(XLHttpUrl.getUrl(XLHttpUrl.GetTravels), params, new HttpCallBack() {

			@Override
			public void onHttpSuccess(Object obj) {
				IndexTravelEntity entity = (IndexTravelEntity) obj;
				if (null != entity && null != entity.getData() && entity.getData().size() > 0) {
					if (pageIndex == 1) {
						mAdapter.clearAdapter();
					}
					mAdapter.updateAdapter(entity.getData(), entity.getData().size());
				}
				pageIndex++;
			}

			@Override
			public void onHttpStarted() {

			}

			@Override
			public void onHttpFailure(Exception e, String message) {
				if (!StringUtil.isEmpty(message)) {
					ToastUtils.showMessage(message);
				}
				if (pageIndex - 1 >= 1) {
					pageIndex--;
				}

			}
		}, IndexTravelEntity.class);
	}

	@Override
	protected View getItemView(int position, View convertView, ViewGroup parent) {
		return null;
	}

	@Override
	public void onPullRefresh() {
		pageIndex = 1;
		loadData(pageIndex);
	}

	@Override
	public void onPullLoadMore() {
		loadData(pageIndex);
	}
}
