/* 
 * Copyright 2013 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @SCSDBaseAdapter.java  2013-12-10 8:16:07 - Carson
 * @author YanXu
 * @email:981385016@qq.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.BitmapUtils;

/**
 * 
 * @ClassName: SCSDBaseAdapter
 * @Description: TODO(BaseAdapter基类)
 * @author Yanxu
 * @date 2013-12-26 上午10:20:06
 * 
 * @param <T>
 */
public abstract class SCSDBaseAdapter<T> extends BaseAdapter {

	public ArrayList<T> mListData;
	public Context mContext;
	private int mTotal;
	public BitmapUtils mBitmapUtils;

	public int getTotal() {
		return mTotal;
	}

	public void setBitmapUtils(BitmapUtils loader) {
		this.mBitmapUtils = loader;
	}

	public void setTotal(int mTotal) {
		this.mTotal = mTotal;
	}

	public SCSDBaseAdapter(Context ctx) {
		this.mContext = ctx;
		this.mListData = new ArrayList<T>();
	}

	public void clearAdapter() {
		if (this.mListData != null) {
			this.mListData.clear();
		}
	}

	/**
	 * 
	 * @Title: resetAdapter
	 * @Description: TODO(清除list原有数据加载新数据)
	 * @param @param list 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void resetAdapter(ArrayList<T> list) {
		mTotal = list.size();
		mListData.clear();

		mListData.addAll(list);
		this.notifyDataSetChanged();
	}

	/**
	 * 
	 * @Title: updateAdapter
	 * @Description: TODO(添加新的list，更新adapter)
	 * @param @param list
	 * @param @param total 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void updateAdapter(ArrayList<T> list, int total) {
		mTotal = total;
		if (list != null && list.size() > 0) {
			mListData.addAll(list);
			this.notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return mListData == null ? 0 : mListData.size();
	}

	@Override
	public T getItem(int position) {
		return mListData == null ? null : mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 
	 * @Title: getItemView
	 * @Description: TODO(抽象方法Adapter的getView)
	 * @param @param position
	 * @param @param convertView
	 * @param @param parent
	 * @param @return 设定文件
	 * @return View 返回类型
	 * @throws
	 */
	public abstract View getItemView(int position, View convertView, ViewGroup parent);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getItemView(position, convertView, parent);
	}

}
