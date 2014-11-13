/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @FraHome.java  2014年10月16日 上午9:12:57 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.fragment;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.android.support.httpclient.HttpCallBack;
import com.android.support.httpclient.HttpClientAsync;
import com.android.support.httpclient.HttpParams;
import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.BaseFragment;
import com.givon.baseproject.xinlu.BaseListFragment;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.act.ActSearch;
import com.givon.baseproject.xinlu.adapter.MyFragmentPagerAdapter;
import com.givon.baseproject.xinlu.entity.BannerEntity;
import com.givon.baseproject.xinlu.interfaceview.ViewPageTopListener;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.util.XLHttpUrl;
import com.givon.baseproject.xinlu.view.MyScrollView;
import com.givon.baseproject.xinlu.view.MyScrollView.ScrollViewListener;
import com.givon.baseproject.xinlu.view.MyViewPager;
import com.givon.baseproject.xinlu.view.RefreshableView;
import com.givon.baseproject.xinlu.view.RefreshableView.RefreshListener;
import com.givon.baseproject.xinlu.view.TopDotPager;
import com.lidroid.xutils.BitmapUtils;

/**
 * 首页
 * @author givon
 * 
 */
public class FraHome extends BaseFragment implements ViewPageTopListener,OnClickListener, RefreshListener{
	Resources resources;
	private MyViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private ImageView ivBottomLine;
	private TextView tvTabNew, tvTabHot, tvTree;
	private TextView tvTabNew_jiadi, tvTabHot_jiadi, tvTree_jiadi;
	// private MyListView mListView;
	private MyScrollView mMyScrollView;
	public static boolean isHit = false;// 是否隐藏
	private int currIndex = 0;
	private int bottomLineWidth;
	private int offset = 0;
	private int position_one;
	public final static int num = 3;
	private RefreshableView mRefreshableView;
	private LinearLayout mTopBarLayout;
	private LinearLayout mTopBarLayout_Jiadi;
	private TopDotPager mDotPager;
	Fragment home1;
	Fragment home2;
	Fragment home3;
	private TextView mTv_SearchView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.layout_frahome, null);
		mRefreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
		mRefreshableView.setRefreshListener(this);
		mTopBarLayout = (LinearLayout) view.findViewById(R.id.ly_top_bar);
		mTv_SearchView = (TextView) view.findViewById(R.id.tv_search);
		mTv_SearchView.setOnClickListener(this);
		mTopBarLayout_Jiadi = (LinearLayout) view.findViewById(R.id.ly_top_bar_jiadi);
		mDotPager = (TopDotPager) view.findViewById(R.id.topDotPager);
		mDotPager.setmBitmapUtils(new BitmapUtils(getActivity()));
		mMyScrollView = (MyScrollView) view.findViewById(R.id.sv_ScrollView);
		InitWidth(view);
		InitTextView(view);
		InitViewPager(view);
		resources = getResources();
		TranslateAnimation animation = new TranslateAnimation(position_one, offset, 0, 0);
		tvTabHot.setTextColor(resources.getColor(R.color.font_white));
		animation.setFillAfter(true);
		animation.setDuration(300);
		ivBottomLine.startAnimation(animation);
		return view;
	}

	private void InitTextView(View parentView) {
		tvTabNew = (TextView) parentView.findViewById(R.id.tv_tab_1);
		tvTabHot = (TextView) parentView.findViewById(R.id.tv_tab_2);
		tvTree = (TextView) parentView.findViewById(R.id.tv_tab_3);
		tvTabNew.setOnClickListener(new MyOnClickListener(0));
		tvTabHot.setOnClickListener(new MyOnClickListener(1));
		tvTree.setOnClickListener(new MyOnClickListener(2));
		tvTabNew_jiadi = (TextView) parentView.findViewById(R.id.tv_tab_1_jd);
		tvTabHot_jiadi = (TextView) parentView.findViewById(R.id.tv_tab_2_jd);
		tvTree_jiadi = (TextView) parentView.findViewById(R.id.tv_tab_3_jd);
		tvTabNew_jiadi.setOnClickListener(new MyOnClickListener(0));
		tvTabHot_jiadi.setOnClickListener(new MyOnClickListener(1));
		tvTree_jiadi.setOnClickListener(new MyOnClickListener(2));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mMyScrollView.setScrollViewListener(new ScrollViewListener() {

			@Override
			public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
				int[] location = new int[4];
				mTopBarLayout.getLocationInWindow(location);
				System.out.println("HH:" + location[0] + " " + location[1] + " " + location[2]
						+ " " + location[3]);
				if (location[1]  < 0 && !isHit) {
					// 高度小于0 Scroll不监听上拉下拉事件
					System.out.println("回掉onScrollChanged");
					mMyScrollView.setHitTopView(true);
					mMyScrollView.setPressed(false);
					mMyScrollView.setClickable(false);
					isHit = true;
				}
				if (location[1]  < 0 && mTopBarLayout_Jiadi.getVisibility() == View.GONE) {
					mTopBarLayout_Jiadi.setVisibility(View.VISIBLE);
				} else if (location[1]  > 0
						&& mTopBarLayout_Jiadi.getVisibility() == View.VISIBLE) {
					mTopBarLayout_Jiadi.setVisibility(View.GONE);
				}
			}
		});
		mRefreshableView.doRefresh();
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	private void InitViewPager(View parentView) {
		mPager = (MyViewPager) parentView.findViewById(R.id.vPager);
		mPager.setOffscreenPageLimit(2);
		final int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		final int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		ViewTreeObserver vto = mPager.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			public void onGlobalLayout() {
				mPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				View view = mPager.getChildAt(mPager.getCurrentItem());
				view.measure(w, h);
				LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT);
				// params.height = view.getMeasuredHeight();
				params.height = BaseApplication.mHeight;
				mPager.setLayoutParams(params);
			}
		});
		fragmentsList = new ArrayList<Fragment>();

		home1 = new FraTuiJian(this);
		home2 = new FraFriend(this);
		home3 = new FraActive(this);

		fragmentsList.add(home1);
		fragmentsList.add(home2);
		fragmentsList.add(home3);

		mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mPager.setCurrentItem(0);

	}

	private void InitWidth(View parentView) {
		ivBottomLine = (ImageView) parentView.findViewById(R.id.iv_bottom_line);
		bottomLineWidth = ivBottomLine.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (int) ((screenW / num - bottomLineWidth) / 3);
		int avg = (int) (screenW / num);
		position_one = avg + offset;

	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			System.out.println("index:" + index);
			mPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			View view = mPager.getChildAt(arg0);
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(position_one, offset, 0, 0);
				}
				if (currIndex == 2) {
					animation = new TranslateAnimation(2 * position_one, offset, 0, 0);
				}
				tvTabNew.setTextColor(resources.getColor(R.color.font_white));
				tvTabNew_jiadi.setTextColor(resources.getColor(R.color.font_white));
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, position_one, 0, 0);
				}
				if (currIndex == 2) {
					animation = new TranslateAnimation(2 * position_one, position_one, 0, 0);
				}
				tvTabHot.setTextColor(resources.getColor(R.color.font_white));
				tvTabHot_jiadi.setTextColor(resources.getColor(R.color.font_white));
				break;
			case 2:
				if (currIndex == 1) {
					animation = new TranslateAnimation(position_one, 2 * position_one, 0, 0);
				}
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, 2 * position_one, 0, 0);
				}
				tvTree.setTextColor(resources.getColor(R.color.font_white));
				tvTree_jiadi.setTextColor(resources.getColor(R.color.font_white));
				break;
			}
			// View view = mPager.getChildAt(currIndex);
			// int height = view.getMeasuredHeight();
			// LayoutParams layoutParams = (LinearLayout.LayoutParams) mPager.getLayoutParams();
			// layoutParams.height = height;
			// mPager.setLayoutParams(layoutParams);
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public void onFirst() {
		if (isHit) {
			System.out.println("回掉onFirst");
			mMyScrollView.setHitTopView(false);
			mMyScrollView.setPressed(true);
			mMyScrollView.setClickable(true);
			isHit = false;
		}
		
	}

	@Override
	public void onClick(View v) {
		if(v==mTv_SearchView){
			showActivity(ActSearch.class);
		}
	}
	
	
	private void getBanner() {
		HttpClientAsync httpClientAsync = HttpClientAsync.getInstance();
		httpClientAsync.get(XLHttpUrl.getUrl(XLHttpUrl.GetBanners),new HttpParams(),XLHttpUrl.CONTENT_TYPE, new HttpCallBack() {
			
			@Override
			public void onHttpSuccess(Object obj) {
				mRefreshableView.finishRefresh();
				BannerEntity entity = (BannerEntity) obj;
				if(null!=entity&&null!=entity.getData()&&entity.getData().size()>0){
					mDotPager.updateViews(entity.getData());
				}
			}
			
			@Override
			public void onHttpStarted() {
				
			}
			
			@Override
			public void onHttpFailure(Exception e, String message) {
				mRefreshableView.finishRefresh();
				System.out.println("message:"+message);
			}
		},BannerEntity.class);
	}

	@Override
	public void onRefresh(RefreshableView view) {
		//伪处理
		getBanner();
		if(null!=home1){
			((FraTuiJian)home1).initDataRefash();
		}
		
		
	}
//	Handler handler = new Handler() {
//		public void handleMessage(Message message) {
//			super.handleMessage(message);
//			mRefreshableView.finishRefresh();
//			ToastUtils.showMessage(R.string.toast_text);
//		};
//	};
//如果ViewPage的View滑动会调用
	@Override
	public void onScroll() {
		int[] location = new int[4];
		mTopBarLayout.getLocationInWindow(location);
		if (location[1] > 0 ) {
			System.out.println("回掉onScroll");
			// 高度小于0 Scroll不监听上拉下拉事件
			mMyScrollView.setHitTopView(false);
			mMyScrollView.setPressed(true);
			mMyScrollView.setClickable(true);
			isHit = false;
		}
	}

}