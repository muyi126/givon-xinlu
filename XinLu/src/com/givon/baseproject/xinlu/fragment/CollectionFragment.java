/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @CollectionFragment.java  2014-1-8 下午4:16:09
 * @author GuZhu
 * @version 1.0
 */

package com.givon.baseproject.xinlu.fragment;

import java.util.ArrayList;

import org.apache.http.params.HttpParams;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.adapter.CollectionAdapter;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.view.PullListView;
import com.givon.baseproject.xinlu.view.PullListView.PullListViewListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class CollectionFragment extends Fragment implements
		PullListViewListener {
	private String typeId;
	private PullListView mListView;
	private CollectionAdapter mAdapter;
	private int mPageIndex;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.layout_listview_fragment,
				container, false);
		mListView = (PullListView) convertView
				.findViewById(R.id.id_pull_listview);
		mListView.setHeaderDividersEnabled(false);
		mListView.setFooterDividersEnabled(false);
		return convertView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		mListView.setBackgroundColor(Color.parseColor("#F2F2F2"));
//		mListView.setHeaderDividersEnabled(false);
//		mListView.setSelector(android.R.color.transparent);
//		mAdapter = new CollectionAdapter(getActivity(), typeId);
//		mListView.setAdapter(mAdapter);
//		mListView.setPullRefreshEnable(true);
//		mListView.setPullListener(this);
//		mListView.startOnRefresh();
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Override
	public void onPullRefresh() {
		mPageIndex = 1;
		getInfo();
	}

	@Override
	public void onPullLoadMore() {
		mPageIndex += 1;
		getInfo();
	}


	private void getInfo() {
		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		httpUtils.send(HttpMethod.GET, "", params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
//				StringUtil.string2Class(responseInfo.result, classz)
				ArrayList<String> list = new ArrayList<String>();
				list.add("1212");
				list.add("1212");
				list.add("1212");
				if (null != list) {
					if (mPageIndex == 1) {
						mAdapter.clearAdapter();
					}
					mAdapter.updateAdapter(list, list.size());
					if (mAdapter.getCount() < list.size()) {
						mListView.setPullLoadEnable(true);
					} else {
						mListView.setPullLoadEnable(false);
					}
				} else {

				}
				mListView.onRefreshFinish();
				mListView.onLoadFinish();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ToastUtils.showMessage("加载失败");
				mListView.onRefreshFinish();
				mListView.onLoadFinish();
			}
		});
//		HttpClientAsync clientAsync = HttpClientAsync.getInstance();
//		HttpParams params = new HttpParams();
//		params.put("memberId", ShareCookie.getUserId());
//		params.put("type", typeId);
//		params.put("currentPage", mPageIndex);
//		params.put("pageSize", HttpUrl.PAGE_SIZE);
//		clientAsync.post(HttpUrl.getUrl(HttpUrl.GETCOLLECTS), params, this,
//				CollectionEntity.class);
	}
}
