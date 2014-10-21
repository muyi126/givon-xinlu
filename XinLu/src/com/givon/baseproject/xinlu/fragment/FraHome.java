/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @FraHome.java  2014年10月16日 上午9:12:57 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.BaseListFragment;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.adapter.MyFragmentPagerAdapter;
import com.givon.baseproject.xinlu.view.MyListView;
import com.givon.baseproject.xinlu.view.MyScrollView;
import com.givon.baseproject.xinlu.view.MyScrollView.TouchListenerS;
import com.givon.baseproject.xinlu.view.MyViewPager;

/**
 * 首页
 * @author givon
 * 
 */
public class FraHome extends BaseListFragment<String> implements
		com.givon.baseproject.xinlu.BaseListFragment.AddHeaderViewCallBack {
	Resources resources;
	private MyViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private ImageView ivBottomLine;
	private TextView tvTabNew, tvTabHot, tvTree;
	// private MyListView mListView;
	private MyScrollView mMyScrollView;
	private boolean isHit = false;// 是否隐藏
	private int oldViewPageHeight;
	private int oldScrollViewHeight;

	private int currIndex = 0;
	private int bottomLineWidth;
	private int offset = 0;
	private int position_one;
	public final static int num = 3;
	private View mHeaderView;
	Fragment home1;
	Fragment home2;
	Fragment home3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHeaderView(this);
//		 View view = super.onCreateView(inflater, container, savedInstanceState);
//		 mTitleBar.setVisibility(View.GONE);
//		 mListView.addHeaderView(mHeaderView);
		// View view = getActivity().getLayoutInflater().inflate(R.layout.layout_frahome, null);
		mHeaderView = getActivity().getLayoutInflater().inflate(R.layout.layout_frahome, null);
		// mListView = (MyListView) view.findViewById(R.id.id_pull_listview);
		resources = getResources();
		InitWidth(mHeaderView);
		InitTextView(mHeaderView);
		InitViewPager(mHeaderView);
		TranslateAnimation animation = new TranslateAnimation(position_one, offset, 0, 0);
		tvTabHot.setTextColor(resources.getColor(R.color.font_white));
		animation.setFillAfter(true);
		animation.setDuration(300);
		ivBottomLine.startAnimation(animation);
		// mListView.addHeaderView(mHeaderView);
		return mHeaderView;
	}

	private void InitTextView(View parentView) {
		tvTabNew = (TextView) parentView.findViewById(R.id.tv_tab_1);
		tvTabHot = (TextView) parentView.findViewById(R.id.tv_tab_2);
		tvTree = (TextView) parentView.findViewById(R.id.tv_tab_3);
		tvTabNew.setOnClickListener(new MyOnClickListener(0));
		tvTabHot.setOnClickListener(new MyOnClickListener(1));
		tvTree.setOnClickListener(new MyOnClickListener(2));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// ArrayList<String> list = new ArrayList<String>();
		// list.add("1");
		// mAdapter = new ListAdapter(getActivity());
		// mListView.setAdapter(mAdapter);
	}

	private void InitViewPager(View parentView) {
		mPager = (MyViewPager) parentView.findViewById(R.id.vPager);
		mMyScrollView = (MyScrollView) parentView.findViewById(R.id.sv_ScrollView);
		android.view.ViewGroup.LayoutParams params = mPager.getLayoutParams();
		// params.height = BaseApplication.mHeight+300;
		mPager.setLayoutParams(params);
//		System.out.println("mListView:H " + mListView.getHeight());
		mMyScrollView.setTouchListenerS(new TouchListenerS() {

			@SuppressLint("NewApi")
			@Override
			public void scrollUp() {
				System.out.println("UP");
				if (!isHit) {
					mMyScrollView.setTranslationY(-300f);
					System.out.println("mMyScrollView:" + mMyScrollView.getHeight() + " mPager:"
							+ mPager.getHeight() + " HH:" + BaseApplication.mHeight);
					android.view.ViewGroup.LayoutParams params = mPager.getLayoutParams();
					params.height = BaseApplication.mHeight - 300;
					mPager.setLayoutParams(params);
					android.view.ViewGroup.LayoutParams params2 = mMyScrollView.getLayoutParams();
					params2.height = BaseApplication.mHeight + 300;
					mMyScrollView.setLayoutParams(params2);
					isHit = true;
				}
			}

			@SuppressLint("NewApi")
			@Override
			public void scrollDown() {
				System.out.println("Down");
				if (isHit) {
					mMyScrollView.setTranslationY(0);
					android.view.ViewGroup.LayoutParams params = mPager.getLayoutParams();
					System.out.println("mMyScrollView:" + mMyScrollView.getHeight() + " mPager:"
							+ mPager.getHeight());
					params.height = oldViewPageHeight;
					mPager.setLayoutParams(params);
					android.view.ViewGroup.LayoutParams params2 = mMyScrollView.getLayoutParams();
					params2.height = oldScrollViewHeight;
					mMyScrollView.setLayoutParams(params2);
					isHit = false;
				}
			}
		});

		oldViewPageHeight = mPager.getLayoutParams().height;
		oldScrollViewHeight = mMyScrollView.getLayoutParams().height;
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
				params.height = view.getMeasuredHeight();
				mPager.setLayoutParams(params);
			}
		});
		fragmentsList = new ArrayList<Fragment>();

		home1 = new FraTuiJian();
		home2 = new FraFriend();
		home3 = new FraActive();

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
			mPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			System.out.println("arg0:" + arg0 + "   P:" + position_one + " offset:" + offset);
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(position_one, offset, 0, 0);
				}
				if (currIndex == 2) {
					animation = new TranslateAnimation(2 * position_one, offset, 0, 0);
				}
				tvTabNew.setTextColor(resources.getColor(R.color.font_white));
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, position_one, 0, 0);
				}
				if (currIndex == 2) {
					animation = new TranslateAnimation(2 * position_one, position_one, 0, 0);
				}
				tvTabHot.setTextColor(resources.getColor(R.color.font_white));
				break;
			case 2:
				if (currIndex == 1) {
					animation = new TranslateAnimation(position_one, 2 * position_one, 0, 0);
					// tvTabNew.setTextColor(resources.getColor(R.color.lightwhite));
				}
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, 2 * position_one, 0, 0);
					// tvTabNew.setTextColor(resources.getColor(R.color.lightwhite));
				}
				tvTree.setTextColor(resources.getColor(R.color.font_white));
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
	protected View getItemView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = getActivity().getLayoutInflater().inflate(R.layout.layout_testitem, null);
		}
		((TextView) convertView.findViewById(R.id.tex)).setText("11111");
		return convertView;
	}

	@Override
	public void addHeaderView() {
		mHeaderView = getActivity().getLayoutInflater().inflate(R.layout.layout_frahome, null);
		resources = getResources();
		InitWidth(mHeaderView);
		InitTextView(mHeaderView);
		InitViewPager(mHeaderView);
		TranslateAnimation animation = new TranslateAnimation(position_one, offset, 0, 0);
		tvTabHot.setTextColor(resources.getColor(R.color.font_white));
		animation.setFillAfter(true);
		animation.setDuration(300);
		ivBottomLine.startAnimation(animation);
	}
}