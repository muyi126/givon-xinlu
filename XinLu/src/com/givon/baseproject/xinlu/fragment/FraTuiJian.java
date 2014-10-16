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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.givon.baseproject.xinlu.BaseFragment;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.view.MultiColumnListView;
import com.givon.baseproject.xinlu.view.PLA_AdapterView;

public class FraTuiJian extends BaseFragment {
	private MultiColumnListView mAdapterView = null;
	private MySimpleAdapter mAdapter = null;

	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sample_pull_to_refresh_act, null);
		mAdapterView = (MultiColumnListView) view.findViewById(R.id.list);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 initAdapter();
	};

	private class MySimpleAdapter extends ArrayAdapter<String> {

		public MySimpleAdapter(Context context, int layoutRes) {
			super(context, layoutRes, android.R.id.text1);
		}
	}

	private Random mRand = new Random();

	private void initAdapter() {
		mAdapter = new MySimpleAdapter(getActivity(), R.layout.sample_item);

		for (int i = 0; i < 30; ++i) {
			// generate 30 random items.

			StringBuilder builder = new StringBuilder();
			builder.append("Hello!![");
			builder.append(i);
			builder.append("] ");

			char[] chars = new char[mRand.nextInt(500)];
			Arrays.fill(chars, '1');
			builder.append(chars);
			mAdapter.add(builder.toString());
		}
		mAdapterView.setAdapter(mAdapter);

	}
}
