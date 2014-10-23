/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @MyScrollView.java  2014年10月16日 下午9:00:24 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
//	private GestureDetector detector;
	private TouchListenerS touchListenerS;
	private boolean isHitTopView;
	private ScrollViewListener scrollViewListener = null;

	public ScrollViewListener getScrollViewListener() {
		return scrollViewListener;
	}

	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	public boolean isHitTopView() {
		return isHitTopView;
	}

	public void setHitTopView(boolean isHitTopView) {
		System.out.println("setHitTopView:"+isHitTopView);
		this.isHitTopView = isHitTopView;
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public TouchListenerS getTouchListenerS() {
		return touchListenerS;
	}

	public void setTouchListenerS(TouchListenerS touchListenerS) {
		this.touchListenerS = touchListenerS;
	}

	public MyScrollView(Context context) {
		super(context);
	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		// this.detector.onTouchEvent(ev);
//		// boolean ret = super.dispatchTouchEvent(ev);
//		// if (ret) {
//		// requestDisallowInterceptTouchEvent(true);
//		// }
//		return super.dispatchTouchEvent(ev);
//	}

	// 滑动距离及坐标
	private float xDistance, yDistance, xLast, yLast;

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		detector = new GestureDetector(this);
	}

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
			if (xDistance > yDistance) {
				return false;
			}
		}
		System.out.println("isHitTopView:"+isHitTopView);
		if (isHitTopView) {
			return false;
		}else {
			return super.onInterceptTouchEvent(ev);
		}
		
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}
//
//	@Override
//	public boolean onDown(MotionEvent e) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void onShowPress(MotionEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean onSingleTapUp(MotionEvent e) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void onLongPress(MotionEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//		if (e1.getY() - e2.getY() > 80) {
//			if (touchListenerS != null) {
//				touchListenerS.scrollUp();
//			}
//		} else if (e1.getY() - e2.getY() < -80) {
//			if (touchListenerS != null) {
//				touchListenerS.scrollDown();
//			}
//		} else
//			return false;
//		return true;
//	}

	private boolean isCanScall(MotionEvent event) {

		return false;
	}
	
//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		if(ev.getAction() == MotionEvent.ACTION_DOWN){
//				return false;
//		}
//		return super.onTouchEvent(ev);
//	}

	public interface TouchListenerS {
		void scrollUp();

		void scrollDown();
	}

	public interface ScrollViewListener {

		void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);

	}
	
	
}
