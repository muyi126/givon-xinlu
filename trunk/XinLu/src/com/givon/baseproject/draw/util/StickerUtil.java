package com.givon.baseproject.draw.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.view.DrawView;

public class StickerUtil {
	private DrawView drawView;
	private View view;
	private Activity activity;

	// private ViewPager viewPager;
	private View[] pageList;

	// private ImageView[] dotImageViews;
	public StickerUtil(Activity activity, View view, DrawView drawView) {
		this.drawView = drawView;
		this.view = view;
		this.activity = activity;
		pageList = new View[1];
		pageList[0] = view;
	}


	public void stickerPicSetOnClickListener() {
		ImageView[] stickers = new ImageView[148];
		stickers[0] = (ImageView) pageList[0].findViewById(R.id.stickerthumb145);
		stickers[1] = (ImageView) pageList[0].findViewById(R.id.stickerthumb146);
		stickers[2] = (ImageView) pageList[0].findViewById(R.id.stickerthumb147);
		stickers[3] = (ImageView) pageList[0].findViewById(R.id.stickerthumb148);
		StickerPage9Listener stickerPage9Listener = new StickerPage9Listener();
		for (int i = 0; i < 4; i++) {
			stickers[i].setOnTouchListener(stickerPage9Listener);
		}
	}

	class StickerPage9Listener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
				break;
			case MotionEvent.ACTION_UP:
				v.setBackgroundColor(0x00ffffff);
				String s = "sticker145.png";
				switch (v.getId()) {
				case R.id.stickerthumb145:
					break;
				case R.id.stickerthumb146:
					s = "sticker146.png";
					break;
				case R.id.stickerthumb147:
					s = "sticker147.png";
					break;
				case R.id.stickerthumb148:
					s = "sticker148.png";
					break;
				}
				Bitmap bitmap = DrawAttribute.getImageFromAssetsFile(activity, s, false);
				drawView.addStickerBitmap(bitmap);
				break;
			case MotionEvent.ACTION_CANCEL:
				v.setBackgroundColor(0x00ff0000);
			}
			return true;
		}
	}

}