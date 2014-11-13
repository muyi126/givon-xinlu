/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @TuiJianAdapter.java  2014年11月12日 下午3:39:14 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.givon.baseproject.draw.util.DrawAttribute;
import com.givon.baseproject.xinlu.BaseAdapter;
import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.entity.IndexTravelModel;
import com.givon.baseproject.xinlu.util.TimeToUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TuiJianAdapter extends BaseAdapter<IndexTravelModel> {

	private final static int TYPE_LEFT = 0;
	private final static int TYPE_RIGHT = 1;

	public TuiJianAdapter(Context ctx) {
		super(ctx);
	}

	// @Override
	// public int getItemViewType(int position) {
	// return position % 2 == 1 ? TYPE_LEFT : TYPE_RIGHT;
	// }
	//
	// @Override
	// public int getViewTypeCount() {
	// return 2;
	// }

	@Override
	public int getCount() {
		if (mList.size() == 0) {
			return 0;
		} else {
			int type = mList.size() % 2 == 0 ? TYPE_RIGHT : TYPE_LEFT;
			if (type == TYPE_RIGHT) {
				return mList.size() / 2;
			} else {
				return mList.size() / 2 + 1;
			}
		}
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (null == convertView) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_tuijian, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);
		}
		int left_position = position * 2;
		int right_position = position * 2 + 1;
		viewHolder = (ViewHolder) convertView.getTag();
		if (left_position < mList.size()) {
			IndexTravelModel model_left = mList.get(left_position);
			float ww = BaseApplication.mWidth/2f;
			LayoutParams params = viewHolder.iv_img_left.getLayoutParams();
			params.width= (int) ww;
			params.height= (int) (ww/DrawAttribute.screenWidthSclHeight);
			viewHolder.iv_img_left.setLayoutParams(params);
			BitmapDisplayConfig config = new BitmapDisplayConfig();
			config.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(mContext).scaleDown(2));
			bitmapUtils.display(viewHolder.iv_img_left, model_left.getImage(),
					new CustomBitmapLoadCallBack(viewHolder));
			viewHolder.tvNum_left.setText(left_position + "");
			try {
				viewHolder.tvTime_left.setText(TimeToUtil.getSystemTimeSetFormat(
						Long.valueOf(model_left.getAddDate()), TimeToUtil.ACCURATE_TO_ZUJI));
			} catch (Exception e) {
				e.printStackTrace();
			}
			viewHolder.rl_left.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
		}
		if (right_position < mList.size()) {
			IndexTravelModel model_right = mList.get(right_position);
			float ww = BaseApplication.mWidth/2f;
			LayoutParams params = viewHolder.iv_img_right.getLayoutParams();
			params.width= (int) ww;
			params.height= (int) (ww/DrawAttribute.screenWidthSclHeight);
			viewHolder.iv_img_right.setLayoutParams(params);
			BitmapDisplayConfig config = new BitmapDisplayConfig();
			config.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(mContext).scaleDown(2));
			bitmapUtils.display(viewHolder.iv_img_right, model_right.getImage(),
					new CustomBitmapLoadCallBack(viewHolder));
			viewHolder.tvNum_right.setText(right_position + "");
			try {
				viewHolder.tvTime_right.setText(TimeToUtil.getSystemTimeSetFormat(
						Long.valueOf(model_right.getAddDate()), TimeToUtil.ACCURATE_TO_ZUJI));
			} catch (Exception e) {
				e.printStackTrace();
			}
			viewHolder.rl_right.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
		}
		return convertView;

	}

	public class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
		private final ViewHolder holder;

		public CustomBitmapLoadCallBack(ViewHolder holder) {
			this.holder = holder;
		}

		@Override
		public void onLoading(ImageView container, String uri, BitmapDisplayConfig config,
				long total, long current) {
//			this.holder.iv_img_left.setProgress((int) (current * 100 / total));
		}

		@Override
		public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap,
				BitmapDisplayConfig config, BitmapLoadFrom from) {
			// super.onLoadCompleted(container, uri, bitmap, config, from);
			fadeInDisplay(container, bitmap);
//			this.holder.imgPb.setProgress(100);
		}
	}

	private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(
			android.R.color.transparent);

	private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
		final TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[] {
				TRANSPARENT_DRAWABLE, new BitmapDrawable(imageView.getResources(), bitmap) });
		imageView.setImageDrawable(transitionDrawable);
		transitionDrawable.startTransition(500);
	}

	private class ViewHolder {
		@ViewInject(R.id.rl_left)
		RelativeLayout rl_left;
		@ViewInject(R.id.tv_time_left)
		TextView tvTime_left;
		@ViewInject(R.id.tv_title_left)
		TextView tvTitle_left;
		@ViewInject(R.id.tv_liuLanNum_left)
		TextView tvNum_left;
		@ViewInject(R.id.iv_img_left)
		ImageView iv_img_left;
		@ViewInject(R.id.rl_right)
		RelativeLayout rl_right;
		@ViewInject(R.id.tv_time_right)
		TextView tvTime_right;
		@ViewInject(R.id.tv_title_right)
		TextView tvTitle_right;
		@ViewInject(R.id.tv_liuLanNum_right)
		TextView tvNum_right;
		@ViewInject(R.id.iv_img_right)
		ImageView iv_img_right;

	}

}
