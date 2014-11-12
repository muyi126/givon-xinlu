/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @FraTuiJian.java  2014年10月16日 下午12:19:45 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.fragment;

import java.util.Arrays;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.support.httpclient.HttpCallBack;
import com.android.support.httpclient.HttpClientAsync;
import com.android.support.httpclient.HttpParams;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.BaseFragment;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.cookie.ShareCookie;
import com.givon.baseproject.xinlu.entity.IndexTravelEntity;
import com.givon.baseproject.xinlu.entity.IndexTravelModel;
import com.givon.baseproject.xinlu.interfaceview.ViewPageTopListener;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.TimeToUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.givon.baseproject.xinlu.view.MultiColumnListView;
import com.givon.baseproject.xinlu.view.MultiColumnListView.OnLoadMoreListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ViewInject;

public class FraTuiJian extends BaseFragment {
	private MultiColumnListView mMultiColumnListView = null;
	private ViewPageTopListener mPageTopListener;
	private MySimpleAdapter mySimpleAdapter;
	private int pageIndex = 1;

	public FraTuiJian(ViewPageTopListener l) {
		mPageTopListener = l;
	}

	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sample_pull_to_refresh_act, null);
		mMultiColumnListView = (MultiColumnListView) view.findViewById(R.id.list);
		initView();
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	};

	private void initView() {
		mySimpleAdapter = new MySimpleAdapter(getActivity());
		mMultiColumnListView.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {

			}

			@Override
			public void onFirst() {
				if (null != mPageTopListener) {
					System.out.println("onFirst");
					mPageTopListener.onFirst();
				}
			}

			@Override
			public void onScroll() {
				System.out.println("onScroll");
				if (null != mPageTopListener) {
					mPageTopListener.onScroll();
				}

			}
		});
		mMultiColumnListView.setAdapter(mySimpleAdapter);
	}

	private class MySimpleAdapter extends com.givon.baseproject.xinlu.BaseAdapter<IndexTravelModel> {

		public MySimpleAdapter(Context ctx) {
			super(ctx);
		}

		@Override
		public View getItemView(final int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (null == convertView) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_home_tuijian,
						null);
				viewHolder = new ViewHolder();
				ViewUtils.inject(viewHolder, convertView);
				convertView.setTag(viewHolder);
			}
			 viewHolder = (ViewHolder) convertView.getTag();
			final IndexTravelModel data = getItem(position);
			if (null != data) {
				bitmapUtils.display(viewHolder.iv_img, data.getImage());
				bitmapUtils.display(convertView, data.getImage(), new BitmapLoadCallBack<View>() {

					@Override
					public void onLoadCompleted(View container, String uri, Bitmap bitmap,
							BitmapDisplayConfig config, BitmapLoadFrom from) {
						ViewHolder viewHolder = (ViewHolder) container.getTag();
						viewHolder.tvNum.setText(position+"");
						System.out.println("position:"+position);
						try {
							viewHolder.tvTime.setText(TimeToUtil.getSystemTimeSetFormat(
									Long.valueOf(data.getAddDate()), TimeToUtil.ACCURATE_TO_ZUJI));
						} catch (Exception e) {
							e.printStackTrace();
						}
						viewHolder.iv_img.setImageBitmap(bitmap);
						// viewHolder.iv_img.setBackground(background)(bitmap);
						viewHolder.tvTitle.setText(data.getTitle());
						float w = data.getWidthSize();
						float h = data.getHeightSize();
						float mWidth = BaseApplication.mWidth / 2;
						float scl = w / mWidth;
						float mHeight = h / scl;
						LayoutParams params = container.getLayoutParams();
						params.height = (int) mHeight;
						params.width = (int) mWidth;
						container.setLayoutParams(params);
						container.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadFailed(View container, String uri, Drawable drawable) {
						container.setVisibility(View.GONE);
					}
				});
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
			return convertView;
		}

	}

	private class ViewHolder {
		@ViewInject(R.id.tv_time)
		TextView tvTime;
		@ViewInject(R.id.tv_title)
		TextView tvTitle;
		@ViewInject(R.id.tv_liuLanNum)
		TextView tvNum;
		@ViewInject(R.id.iv_img)
		ImageView iv_img;
	}

	public void initDataRefash() {
//		HttpClientAsync clientAsync = HttpClientAsync.getInstance();
//		HttpParams params = new HttpParams();
//		params.put("sortType", "1");
//		params.put("keyword", "");
//		params.put("pageIndex", pageIndex);
//		params.put("pageSize", XLHttpUrl.PAGE_SIZE);
//		clientAsync.setmToken(ShareCookie.getToken());
//		clientAsync.get(XLHttpUrl.getUrl(XLHttpUrl.GetTravels), params, new HttpCallBack() {
//
//			@Override
//			public void onHttpSuccess(Object obj) {
//				IndexTravelEntity entity = (IndexTravelEntity) obj;
//				if (null != entity && null != entity.getData() && entity.getData().size() > 0) {
//					mySimpleAdapter.clearAdapter();
//					mySimpleAdapter.updateAdapter(entity.getData(), entity.getData().size());
//				}
//			}
//
//			@Override
//			public void onHttpStarted() {
//
//			}
//
//			@Override
//			public void onHttpFailure(Exception e, String message) {
//				if (!StringUtil.isEmpty(message)) {
//					ToastUtils.showMessage(message);
//				}
//
//			}
//		}, IndexTravelEntity.class);

	}
}
