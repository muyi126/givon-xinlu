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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

import com.givon.baseproject.xinlu.BaseListFragment;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.interfaceview.ViewPageTopListener;
import com.givon.baseproject.xinlu.util.BitmapHelp;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.view.PullListView;
import com.givon.baseproject.xinlu.view.PullListView.PullListViewListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
/**
 * 个人中心
 * @author givon
 *
 */
public class FraFriend extends BaseListFragment<String> implements PullListViewListener{
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
		View view = inflater.inflate(R.layout.layout_frauser_friend,null);
		bitmapUtils = BitmapHelp.getBitmapUtils(getActivity());
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		mAdapter = new ListAdapter(getActivity());
		mListView = (PullListView) view.findViewById(R.id.id_pull_listview);
		mListView.setHeaderDividersEnabled(false);
		mListView.setFooterDividersEnabled(false);
		mListView.setSelector(android.R.color.transparent);
		mListView.setAdapter(mAdapter);
		mListView.setPullRefreshEnable(false);
		mListView.setPullLoadEnable(true);
		mListView.setPullListener(this);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<String> list = new ArrayList<String>();
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		list.add("dd");
		mAdapter.putData(list);
		mListView.setOnScrollListener(new OnScrollListener() {
			
			public void onScrollStateChanged(AbsListView view, int scrollState) {  
			    switch (scrollState) {  
			    // 当不滚动时  
			    case OnScrollListener.SCROLL_STATE_IDLE:  
			    // 判断滚动到底部  
			    if (mListView.getLastVisiblePosition() == (mListView.getCount() - 1)) {  
			                 }  
			    // 判断滚动到顶部  
			  
			    if(mListView.getFirstVisiblePosition() == 0){  
			    	mListener.onFirst();
			    }  
			  
			     break;  
			        }   
			   }   
			     
			        @Override    
			        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {    
			        	mListener.onScroll();
			            if (firstVisibleItem + visibleItemCount == totalItemCount && !flag) {    
			                flag = true;    
			            } else    
			                flag = false;    
			        }    
		});
	}
	private boolean flag;
	@Override
	protected View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(null==convertView){
			convertView = getActivity().getLayoutInflater().inflate(R.layout.item_fra_friend, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		return convertView;
	}
	
	private class ViewHolder{
		@ViewInject (R.id.tv_address)
		TextView address;
		@ViewInject (R.id.tv_time)
		TextView time;
		@ViewInject (R.id.tv_name)
		TextView name;
		@ViewInject (R.id.tv_content)
		TextView content;
		@ViewInject (R.id.tv_read)
		TextView read;
		@ViewInject (R.id.tv_reple)
		TextView reple;
		
	}

	@Override
	public void onPullRefresh() {
		
	}
	@Override
	public void onPullLoadMore() {
		ToastUtils.showMessage("刷新");
	}
	
	
}
