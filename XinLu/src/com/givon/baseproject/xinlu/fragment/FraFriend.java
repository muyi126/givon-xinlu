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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.givon.baseproject.xinlu.BaseFragment;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.interfaceview.ViewPageTopListener;
/**
 * 个人中心
 * @author givon
 *
 */
public class FraFriend extends BaseFragment{
	private ViewPageTopListener mListener;

	public FraFriend(ViewPageTopListener l ) {
		mListener = l;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_frausercenter,null);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
}
