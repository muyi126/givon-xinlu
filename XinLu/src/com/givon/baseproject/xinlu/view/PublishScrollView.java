/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @PublishScrollView.java  2014年11月4日 下午5:55:56 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.ScrollView;

public class PublishScrollView extends ScrollView{

	public PublishScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PublishScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PublishScrollView(Context context) {
		super(context);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(ev.getY()<200){
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}

}
