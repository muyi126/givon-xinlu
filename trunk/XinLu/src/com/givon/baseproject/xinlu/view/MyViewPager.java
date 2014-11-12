/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @MyViewPager.java  2014年10月16日 下午5:46:17 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.view;

import com.givon.baseproject.xinlu.fragment.FraHome;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyViewPager extends ViewPager {

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

//	/**
//	 * 设置不滚动
//	 */
//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//		super.onMeasure(widthMeasureSpec, expandSpec);
//
//	}

	
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		super.onLayout(arg0, arg1, arg2, arg3, arg4);
	}
//	@Override
//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		// TODO Auto-generated method stub
//		int viewHeight = 0;
//		View childView = getChildAt(getCurrentItem());
//		if(null==childView){
//			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//			return;
//		}
//		childView
//				.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//		viewHeight = childView.getMeasuredHeight();
//		heightMeasureSpec = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY);
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		System.out.println("onTouchEvent_ViewPAger");
		
		return super.onTouchEvent(arg0);
	}
	// 滑动距离及坐标
		private float xDistance, yDistance, xLast, yLast;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;
			System.out.println("Distanc:"+(xDistance-yDistance));
			if (xDistance - yDistance>1) {
				return true;
			}
				
		}
		if(FraHome.isHit){
			return super.onInterceptTouchEvent(ev);
		}else {
			return super.onInterceptTouchEvent(ev);
		}
	}
}
