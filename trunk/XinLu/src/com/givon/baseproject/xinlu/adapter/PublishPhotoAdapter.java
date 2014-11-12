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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.givon.baseproject.xinlu.BaseAdapter;
import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.act.ActDraw;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.DetailImages;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PublishPhotoAdapter extends BaseAdapter<DetailImages> {
	private final static int NOMEL = 0;
	private final static int END = 1;
	public final static int RESULT_CODE = 1112;
	private int height;
	public PublishPhotoAdapter(Context ctx) {
		super(ctx);
	}

	@Override
	public int getItemViewType(int position) {
		if (null == mList || mList.size() == 0) {
			return END;
		} else {
			if (position >= mList.size()) {
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
	public View getItemView(final int position, View convertView, ViewGroup parent) {
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
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			Bitmap bmp = BitmapFactory.decodeFile(getItem(position).getImageUrl(), options);
			float ox = BaseApplication.mWidth/3;
			height = options.outHeight * ((BaseApplication.mWidth/3)/ options.outWidth);
			options.outWidth = (int) ox;
			options.outHeight = height;
			options.inJustDecodeBounds = false;
			options.inSampleSize = (int) (options.outWidth / ox);
			options.inPreferredConfig = Bitmap.Config.ARGB_4444;
			bmp = BitmapFactory.decodeFile(getItem(position).getImageUrl(), options);
			viewHolder.imageView.setImageBitmap(bmp);
			viewHolder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,ActDraw.class);
					System.out.println("position:"+position);
					intent.putExtra(Constant.ID, position);
					intent.putExtra(Constant.DATA, getItem(position).getImageUrl());
					((Activity)mContext).startActivityForResult(intent, RESULT_CODE);
				}
			});
			break;
		case END:
			viewHolder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,ActDraw.class);
					intent.putExtra(Constant.ID, position);
					((Activity)mContext).startActivityForResult(intent, RESULT_CODE);
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
