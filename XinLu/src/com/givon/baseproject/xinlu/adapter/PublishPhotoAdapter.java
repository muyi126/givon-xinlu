/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @PublishPhotoAdapter.java  2014年10月30日 上午11:54:01 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.givon.baseproject.xinlu.BaseAdapter;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PublishPhotoAdapter extends BaseAdapter<String> {
	private final static int NOMEL = 0;
	private final static int END = 1;

	public PublishPhotoAdapter(Context ctx) {
		super(ctx);
	}

	@Override
	public int getItemViewType(int position) {
		if (null == mList || mList.size() == 0) {
			return END;
		} else {
			if (position >= mList.size() - 1) {
				return END;
			} else {
				return NOMEL;
			}
		}
	}

	@Override
	public int getCount() {
		if (null == mList || mList.size() == 0) {
			return 1;
		} else {
			if (mList.size() < 6) {
				return mList.size() + 1;
			} else {
				return mList.size();
			}
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		int type;
		ViewHolder viewHolder = null;
		type = getItemViewType(position);
		if (null == convertView) {
			switch (type) {
			case NOMEL:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview_photo,
						null);
				viewHolder = new ViewHolder();
				ViewUtils.inject(viewHolder, convertView);
				convertView.setTag(viewHolder);
				break;
			case END:
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_gridview_photo_add, null);
				viewHolder = new ViewHolder();
				ViewUtils.inject(viewHolder, convertView);
				convertView.setTag(viewHolder);
				break;

			default:
				break;
			}

		}
		viewHolder = (ViewHolder) convertView.getTag();
		switch (type) {
		case NOMEL:
			viewHolder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
			break;
		case END:
			viewHolder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ToastUtils.showMessage("ADD");
				}
			});
			break;

		default:
			break;
		}

		return convertView;
	}

	private class ViewHolder {
		@ViewInject(R.id.iv_photo)
		ImageView imageView;

	}
}
