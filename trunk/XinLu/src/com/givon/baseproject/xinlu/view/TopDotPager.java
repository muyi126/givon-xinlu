package com.givon.baseproject.xinlu.view;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.entity.BannerModel;
import com.givon.baseproject.xinlu.entity.ImageModel;
import com.lidroid.xutils.BitmapUtils;

public class TopDotPager extends RelativeLayout implements OnPageChangeListener {

	private ViewPager mPager;
	private LinearLayout mBottom;
	private MyPagerAdapter mAdapter;
	private Timer mTimer;
	private boolean mCanChange = true;
	private OnItemClickListener mClickListener;
	private BitmapUtils mBitmapUtils;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (mCanChange) {
				mPager.setCurrentItem(msg.what, true);
			}
		}
	};

	public BitmapUtils getmBitmapUtils() {
		return mBitmapUtils;
	}

	public void setmBitmapUtils(BitmapUtils mBitmapUtils) {
		this.mBitmapUtils = mBitmapUtils;
	}

	public TopDotPager(Context context) {
		super(context);
		initViews(context);
	}

	public TopDotPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
	}

	public TopDotPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initViews(context);
	}

	private void initViews(Context context) {
		LayoutInflater.from(context).inflate(R.layout.view_top_gallery_home, this, true);
		mPager = (ViewPager) findViewById(R.id.id_viewpager);
		mBottom = (LinearLayout) findViewById(R.id.layout_bottom);
		DisplayMetrics m = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(m);
		LayoutParams lp = new LayoutParams(m.widthPixels, (int) (m.widthPixels / 1.8));
		mPager.setLayoutParams(lp);
		mPager.setOnPageChangeListener(this);
		mPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mCanChange = false;
					break;
				case MotionEvent.ACTION_UP:
					mCanChange = true;
					break;
				case MotionEvent.ACTION_CANCEL:
					mCanChange = true;
					break;
				}
				return false;
			}
		});
	}

	public void updateViews(List<BannerModel> beans) {
		if (null == beans || beans.size() < 1) {
			return;
		}
		if (mAdapter == null) {
			mAdapter = new MyPagerAdapter(getContext(), beans);
			mPager.setAdapter(mAdapter);
			int size = (beans == null ? 0 : beans.size());
			for (int i = 0; i < size; i++) {
				ImageView imageview = new ImageView(getContext());
				imageview.setImageResource(R.drawable.selecter_panic_buy_radius);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
				MarginLayoutParams mlp = lp;
				mlp.setMargins(4, 0, 4, 0);
				imageview.setLayoutParams(mlp);
				imageview.setScaleType(ScaleType.CENTER);
				if (i == 0) {
					imageview.setSelected(true);
				}
				mBottom.addView(imageview);
			}
			if (mTimer == null && beans != null && beans.size() > 0) {
				mBottom.setVisibility(View.VISIBLE);
				mTimer = new Timer();
				mTimer.schedule(new TimerTask() {

					@Override
					public void run() {
						int position = mPager.getCurrentItem();
						if (position + 1 >= mAdapter.getCount()) {
							position = 0;
						} else {
							position++;
						}
						handler.sendEmptyMessage(position);
					}
				}, 5000, 5000);
			}
		}

	}

	class MyPagerAdapter extends PagerAdapter {
		private Context mContext;
		private List<BannerModel> mBeans;

		public MyPagerAdapter(Context context, List<BannerModel> beans) {
			this.mContext = context;
			this.mBeans = beans;
		}

		@Override
		public int getCount() {
			return mBeans.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BannerModel model = mBeans.get(position);
			ImageView image = new ImageView(mContext);
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
					BaseApplication.mWidth / 8);
			image.setLayoutParams(lp);
			image.setScaleType(ScaleType.CENTER_CROP);
			image.setBackgroundResource(R.drawable.ic_launcher);
			image.setTag(position);
			image.setOnClickListener(onClickListener);
			image.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					switch (arg1.getAction()) {
					case MotionEvent.ACTION_DOWN:
						mCanChange = false;
						break;
					case MotionEvent.ACTION_UP:
						mCanChange = true;
						break;
					case MotionEvent.ACTION_CANCEL:
						mCanChange = true;
						break;
					}
					return false;
				}
			});
			if (null != mBitmapUtils) {
				mBitmapUtils.display(image, model.getBannerImage());
			}

			container.addView(image);
			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public List<BannerModel> getList() {
			return mBeans;
		}

		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				int position = (Integer) v.getTag();
				if (mClickListener != null) {
					mClickListener.onItemClickListener(position);
				}

			}
		};

	}

	public interface OnItemClickListener {
		void onItemClickListener(int position);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mClickListener = listener;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		refreshRadius(position);
	}

	public void refreshRadius(int position) {
		int size = mBottom.getChildCount();
		for (int i = 0; i < size; i++) {
			if (i == position) {
				mBottom.getChildAt(i).setSelected(true);
			} else {
				mBottom.getChildAt(i).setSelected(false);
			}
		}
	}
}
