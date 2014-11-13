/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @CollectionAdapter.java  2014-1-8 下午1:52:38
 * @author GuZhu
 * @version 1.0
 */

package com.givon.baseproject.xinlu.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.SCSDBaseAdapter;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.lidroid.xutils.BitmapUtils;

public class CollectionAdapter extends SCSDBaseAdapter<String>{
	private String mType;

	public CollectionAdapter(Context ctx,String type) {
		super(ctx);
		mBitmapUtils = new BitmapUtils(ctx);
		this.mType = type;
	}

	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(mType.equals("information")){
//			if (null == convertView) {
//				holder = new ViewHolder();
//				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_infolist, null);
//				holder.iv_title = (TextView) convertView.findViewById(R.id.iv_title);
//				holder.iv_msg = (TextView) convertView.findViewById(R.id.iv_msg);
//				holder.iv_time = (TextView) convertView.findViewById(R.id.iv_time);
//				holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
//				holder.iv_line = (ImageView) convertView.findViewById(R.id.iv_line);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			if (position == 0) {
//				holder.iv_line.setImageResource(R.drawable.ic_infoline_1);
//			} else {
//				holder.iv_line.setImageResource(R.drawable.ic_infoline_2);
//			}
//			final CollectionBean bean = getItem(position);
//			if (null != bean) {
//				holder.iv_title.setText(bean.getTitle());
//				holder.iv_msg.setText(bean.getContent());
//				holder.iv_time.setText(TimeUtil.getSystemTimeFormat(bean.getTime()));
//				String url = bean.getImg();
//				mImageLoader.loadImage(HttpUrl.getImageUrl(url), holder.iv_pic);
//				convertView.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						Bundle bundle = new Bundle();
//						bundle.putSerializable(Constant.COLLECTIONDATA, bean);
//						bundle.putString(Constant.TYPE, ActNewsDetail.TYPE_COLLECTION_DETAIL);
//						Intent intent = new Intent(mContext, ActNewsDetail.class);
//						intent.putExtra(Constant.PARAMS, bundle);
//						mContext.startActivity(intent);
//					}
//				});
//			}
//			return convertView;
		}else if (mType.equals("activity")) {
//			if (convertView == null) {
//				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_activitys, null);
//				holder = new ViewHolder();
//				holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
//				holder.iv_title = (TextView) convertView.findViewById(R.id.tv_title);
//				holder.iv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
//				holder.iv_startTime = (TextView) convertView.findViewById(R.id.tv_startTime);
//				holder.iv_endTime = (TextView) convertView.findViewById(R.id.tv_endTime);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			final CollectionBean bean = getItem(position);
//			if (null != bean) {
//				mImageLoader.loadImage(HttpUrl.getImageUrl(bean.getImg()), holder.iv_pic);
//				holder.iv_title.setText(bean.getTitle());
//				holder.iv_msg.setText(bean.getContent());
//				holder.iv_startTime.setText(TimeUtil.getDateNews(bean.getBeginTime()));
//				holder.iv_endTime.setText(TimeUtil.getDateNews(bean.getEndTime()));
//				convertView.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						Intent intent = new Intent(mContext, ActActivityDetail.class);
//						intent.putExtra(Constant.ID, bean.getObjectId());
//						mContext.startActivity(intent);
//					}
//				});
//			}
//			return convertView;
//		
	}else if (mType.equals("commodity")) {
//		if (null == convertView) {
//			holder = new ViewHolder();
//			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_collection_commodity, null);
//			holder.iv_title = (TextView) convertView.findViewById(R.id.title);
//			holder.iv_time = (TextView) convertView.findViewById(R.id.time);
//			holder.iv_msg = (TextView) convertView.findViewById(R.id.content);
//			holder.iv_pic = (ImageView) convertView.findViewById(R.id.item_image);
//			convertView.setTag(holder);
//		}
//		holder = (ViewHolder) convertView.getTag();
//		final CollectionBean bean = getItem(position);
//		if (null != bean) {
//			mImageLoader.loadImage(HttpUrl.getImageUrl(bean.getImg()), holder.iv_pic);
//			holder.iv_title.setText(bean.getTitle());
//			holder.iv_msg.setText(bean.getContent());
//			if(bean.getTime()==null){
//				if(!StringUtil.isEmpty(bean.getBeginTime())){
//					holder.iv_time.setText(bean.getBeginTime());
//				}
//			}else {
//				holder.iv_time.setText(TimeUtil.getSystemTimeFormat(bean.getTime()));
//			}
//			convertView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					Intent intent = new Intent(mContext, ActCommodityDetail.class);
//					intent.putExtra(Constant.ID, bean.getObjectId());
//					mContext.startActivity(intent);
//				}
//			});
//		}
//		return convertView;
	}
		
		return convertView;
	}
	
	static class ViewHolder {
		private ImageView iv_pic;
		private TextView iv_startTime;
		private TextView iv_endTime;
		private TextView iv_title;
		private TextView iv_msg;
		private TextView iv_time;
		private ImageView iv_line;
	}

}
