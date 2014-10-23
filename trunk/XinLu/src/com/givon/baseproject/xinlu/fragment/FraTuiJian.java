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
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.givon.baseproject.xinlu.BaseFragment;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.interfaceview.ViewPageTopListener;
import com.givon.baseproject.xinlu.view.MultiColumnListView;
import com.givon.baseproject.xinlu.view.MultiColumnListView.OnLoadMoreListener;

public class FraTuiJian extends BaseFragment {
	private MultiColumnListView mMultiColumnListView = null;
	private MySimpleAdapter mAdapter = null;
	private ViewPageTopListener mPageTopListener;
	
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
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 initAdapter();
//		mAdapterView.setAdapter(new ItemAdapterT());
	};

	private class MySimpleAdapter extends ArrayAdapter<String> {

		public MySimpleAdapter(Context context, int layoutRes) {
			super(context, layoutRes, android.R.id.text1);
		}
	}

	private Random mRand = new Random();

	
	private class ItemAdapterT extends BaseAdapter{

		@Override
		public int getCount() {
			return 100;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.sample_item, null);
			}
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			return convertView;
		}
		
	} 
	
	private void initAdapter() {
		
		
//		mAdapter = new SimpleAdapter<ImageWrapper>(getLayoutInflater(), this);
		mAdapter = new MySimpleAdapter(getActivity(), R.layout.sample_item);
		for (int i = 0; i < 50; ++i) {
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
		mMultiColumnListView.setAdapter(mAdapter);
//        dataSet.addAll(ImgResource.genData());
//        mAdapter.update(dataSet);
        mMultiColumnListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                dataSet.addAll(ImgResource.genData());
            	for (int i = 0; i < 5; ++i) {
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
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "到List底部自动加载更多数据", Toast.LENGTH_SHORT).show();
                //5秒后完成
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    	mMultiColumnListView.onLoadMoreComplete();
                    }
                }, 5000);
            }

			@Override
			public void onFirst() {
				if(null!=mPageTopListener){
					mPageTopListener.onFirst();
				}
			}
        });
//		mAdapter = new MySimpleAdapter(getActivity(), R.layout.sample_item);
//
//		for (int i = 0; i < 50; ++i) {
//			// generate 30 random items.
//
//			StringBuilder builder = new StringBuilder();
//			builder.append("Hello!![");
//			builder.append(i);
//			builder.append("] ");
//
//			char[] chars = new char[mRand.nextInt(500)];
//			Arrays.fill(chars, '1');
//			builder.append(chars);
//			mAdapter.add(builder.toString());
//		}
//		mMultiColumnListView.setAdapter(mAdapter);
//		mMultiColumnListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
//				
//			}
//		});

	}
}
