/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @FraHome.java  2014年10月16日 上午9:12:57 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.MediaStore.Video;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.givon.baseproject.draw.util.BackgroundUtil;
import com.givon.baseproject.draw.util.DrawAttribute;
import com.givon.baseproject.draw.util.GeometryUtil;
import com.givon.baseproject.draw.util.StickerUtil;
import com.givon.baseproject.draw.util.TuYaUtil;
import com.givon.baseproject.xinlu.BaseFragment;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.act.ActPublish;
import com.givon.baseproject.xinlu.view.DrawView;

/**
 * 发布
 * @author givon
 * 
 */
public class FraPublish extends BaseFragment {
	private DrawView drawView;
	private LinearLayout rightBar;
	private LinearLayout bottomBar;
	public int brushDrawableId;
	// private HorizontalScrollView markerList;
	// private HorizontalScrollView crayonList;
	// private HorizontalScrollView colorList;
	private HorizontalScrollView stamppenList;
	private HorizontalScrollView backgroundList;
	// private HorizontalScrollView eraserList;
	private LinearLayout stickerList;
	private LinearLayout geometryTool;
	private LinearLayout rightPhotoToo_ly;
	private LinearLayout tuyaTool;
	private Activity activity;
	private View mView;
	private Button saveButton;

	// private LinearLayout picTool;
	// private LinearLayout wordTool;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setContentView(R.layout.layout_testdrawactivity);
		mView = activity.getLayoutInflater().inflate(R.layout.layout_frapublish, null);
		rightPhotoToo_ly = (LinearLayout) mView.findViewById(R.id.ly_photos);
		saveButton = (Button) mView.findViewById(R.id.finish);
		ArrayList<SLBitmap> photos = viewPhoto();
		for (int i = 0; i < photos.size(); i++) {
			ImageView imageView = new ImageView(activity);
			imageView.setTag(photos.get(i));
			LayoutParams params = new LayoutParams(60, 60);
			imageView.setBackgroundDrawable(new BitmapDrawable(photos.get(i).bitmap));
			rightPhotoToo_ly.addView(imageView, params);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					SLBitmap obj = (SLBitmap) v.getTag();
					if (obj.isCheck) {
						obj.isCheck = false;
						drawView.addStickerBitmap(BitmapFactory.decodeFile(obj.path));
					} else {
						obj.isCheck = true;
						drawView.deleteStickerBitmap(null);
					}
					v.setTag(obj);
				}
			});
		}
		// 设置默认选择黑色水笔
		// ImageView imageView = (ImageView) findViewById(R.id.marker01);
		// imageView.scrollTo(0, 0);
		// brushDrawableId = R.id.marker01;
		rightBar = (LinearLayout) mView.findViewById(R.id.drawright);
		bottomBar = (LinearLayout) mView.findViewById(R.id.drawbottom);
		// 此处有用处，切勿删除
		rightBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		// 此处有用处，切勿删除
		bottomBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		drawView = (DrawView) mView.findViewById(R.id.drawview);
		drawView.setFraPublish(this);
		// Intent intent = this.getIntent();
		// Bundle bundle = intent.getExtras();
		// if (bundle != null) {
		// String backgroundBitmapPath = bundle
		// .getString("com.example.finaldesign.BackgroundBitmapPath");
		// Bitmap bitmap = BitmapFactory.decodeFile(backgroundBitmapPath);
		// drawView.setBackgroundBitmap(bitmap, false);
		// }
		geometryTool = (LinearLayout) mView.findViewById(R.id.geometrytool);
		tuyaTool = (LinearLayout) mView.findViewById(R.id.tuyatool);
		backgroundList = (HorizontalScrollView) mView.findViewById(R.id.backgroundlist);
		stickerList = (LinearLayout) mView.findViewById(R.id.stickerlist);
		geometryTool.setVisibility(View.VISIBLE);
		tuyaTool.setVisibility(View.INVISIBLE);
		backgroundList.setVisibility(View.INVISIBLE);
		stickerList.setVisibility(View.INVISIBLE);
		// 下面的菜单
		TextView drawTextButton = (TextView) mView.findViewById(R.id.drawText);
		TextView drawTuyaButton = (TextView) mView.findViewById(R.id.drawTuya);
		TextView drawMubanBUtton = (TextView) mView.findViewById(R.id.drawMuban);
		TextView drawLvjinButton = (TextView) mView.findViewById(R.id.drawLvjin);
		TextView drawTuanButton = (TextView) mView.findViewById(R.id.drawTuan);
		// 上面的菜单
		ImageButton visibleButton = (ImageButton) mView.findViewById(R.id.drawvisiblebtn);
		Button undoButton = (Button) mView.findViewById(R.id.drawundobtn);
		Button redoButton = (Button) mView.findViewById(R.id.drawredobtn);
		// 选择文字
		drawTextButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					geometryTool.setVisibility(View.VISIBLE);
					tuyaTool.setVisibility(View.INVISIBLE);
					backgroundList.setVisibility(View.INVISIBLE);
					stickerList.setVisibility(View.INVISIBLE);
					drawView.setCanDraw(false);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(0x00ff0000);
				}
				flawOperation();
				return true;
			}
		});
		// 选择涂鸦
		drawTuyaButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					geometryTool.setVisibility(View.INVISIBLE);
					tuyaTool.setVisibility(View.VISIBLE);
					backgroundList.setVisibility(View.INVISIBLE);
					stickerList.setVisibility(View.INVISIBLE);
					drawView.setCanDraw(true);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(0x00ff0000);
				}
				flawOperation();
				return true;
			}
		});
		// 选择模板
		drawMubanBUtton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					geometryTool.setVisibility(View.INVISIBLE);
					tuyaTool.setVisibility(View.INVISIBLE);
					backgroundList.setVisibility(View.INVISIBLE);
					stickerList.setVisibility(View.INVISIBLE);
					drawView.setCanDraw(false);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(0x00ff0000);
				}
				flawOperation();
				return true;
			}
		});
		// 选择滤镜
		drawLvjinButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					geometryTool.setVisibility(View.INVISIBLE);
					tuyaTool.setVisibility(View.INVISIBLE);
					backgroundList.setVisibility(View.INVISIBLE);
					stickerList.setVisibility(View.INVISIBLE);
					drawView.setCanDraw(false);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(0x00ff0000);
				}
				// flawOperation();
				return true;
			}
		});
		// 选择图案
		drawTuanButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					geometryTool.setVisibility(View.INVISIBLE);
					tuyaTool.setVisibility(View.INVISIBLE);
					backgroundList.setVisibility(View.INVISIBLE);
					stickerList.setVisibility(View.VISIBLE);
					drawView.setCanDraw(false);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(0x00ff0000);
				}
				flawOperation();
				return true;
			}
		});
		// // 选择壁纸按钮
		// backgroundButton.setOnTouchListener(new OnTouchListener()
		// {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event)
		// {
		// switch (event.getAction())
		// {
		// case MotionEvent.ACTION_DOWN:
		// v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
		// break;
		// case MotionEvent.ACTION_UP:
		// v.setBackgroundColor(0x00ffffff);
		// markerList.setVisibility(View.INVISIBLE);
		// crayonList.setVisibility(View.INVISIBLE);
		// colorList.setVisibility(View.INVISIBLE);
		// geometryTool.setVisibility(View.INVISIBLE);
		// stamppenList.setVisibility(View.INVISIBLE);
		// backgroundList.setVisibility(View.VISIBLE);
		// stickerList.setVisibility(View.INVISIBLE);
		// eraserList.setVisibility(View.INVISIBLE);
		// picTool.setVisibility(View.INVISIBLE);
		// wordTool.setVisibility(View.INVISIBLE);
		// break;
		// case MotionEvent.ACTION_CANCEL:
		// v.setBackgroundColor(0x00ff0000);
		// }
		// flawOperation();
		// return true;
		// }
		// });
		// // 选择素材按钮
		// stickerButton.setOnTouchListener(new OnTouchListener()
		// {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event)
		// {
		// switch (event.getAction())
		// {
		// case MotionEvent.ACTION_DOWN:
		// v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
		// break;
		// case MotionEvent.ACTION_UP:
		// v.setBackgroundColor(0x00ffffff);
		// markerList.setVisibility(View.INVISIBLE);
		// crayonList.setVisibility(View.INVISIBLE);
		// colorList.setVisibility(View.INVISIBLE);
		// geometryTool.setVisibility(View.INVISIBLE);
		// stamppenList.setVisibility(View.INVISIBLE);
		// backgroundList.setVisibility(View.INVISIBLE);
		// stickerList.setVisibility(View.VISIBLE);
		// eraserList.setVisibility(View.INVISIBLE);
		// picTool.setVisibility(View.INVISIBLE);
		// wordTool.setVisibility(View.INVISIBLE);
		// break;
		// case MotionEvent.ACTION_CANCEL:
		// v.setBackgroundColor(0x00ff0000);
		// }
		// flawOperation();
		// return true;
		// }
		// });
		// // 选择橡皮按钮
		// eraserButton.setOnTouchListener(new OnTouchListener()
		// {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event)
		// {
		// switch (event.getAction())
		// {
		// case MotionEvent.ACTION_DOWN:
		// v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
		// break;
		// case MotionEvent.ACTION_UP:
		// v.setBackgroundColor(0x00ffffff);
		// markerList.setVisibility(View.INVISIBLE);
		// crayonList.setVisibility(View.INVISIBLE);
		// colorList.setVisibility(View.INVISIBLE);
		// geometryTool.setVisibility(View.INVISIBLE);
		// stamppenList.setVisibility(View.INVISIBLE);
		// backgroundList.setVisibility(View.INVISIBLE);
		// stickerList.setVisibility(View.INVISIBLE);
		// eraserList.setVisibility(View.VISIBLE);
		// picTool.setVisibility(View.INVISIBLE);
		// wordTool.setVisibility(View.INVISIBLE);
		// break;
		// case MotionEvent.ACTION_CANCEL:
		// v.setBackgroundColor(0x00ff0000);
		// }
		// flawOperation();
		// return true;
		// }
		// });
		// // 选择图片按钮
		// picButton.setOnTouchListener(new OnTouchListener()
		// {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event)
		// {
		// switch (event.getAction())
		// {
		// case MotionEvent.ACTION_DOWN:
		// v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
		// break;
		// case MotionEvent.ACTION_UP:
		// v.setBackgroundColor(0x00ffffff);
		// markerList.setVisibility(View.INVISIBLE);
		// crayonList.setVisibility(View.INVISIBLE);
		// colorList.setVisibility(View.INVISIBLE);
		// geometryTool.setVisibility(View.INVISIBLE);
		// stamppenList.setVisibility(View.INVISIBLE);
		// backgroundList.setVisibility(View.INVISIBLE);
		// stickerList.setVisibility(View.INVISIBLE);
		// eraserList.setVisibility(View.INVISIBLE);
		// picTool.setVisibility(View.VISIBLE);
		// wordTool.setVisibility(View.INVISIBLE);
		// break;
		// case MotionEvent.ACTION_CANCEL:
		// v.setBackgroundColor(0x00ff0000);
		// }
		// flawOperation();
		// return true;
		// }
		// });
		// // 点击文字按钮
		// wordsButton.setOnTouchListener(new OnTouchListener()
		// {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event)
		// {
		// switch (event.getAction())
		// {
		// case MotionEvent.ACTION_DOWN:
		// v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
		// break;
		// case MotionEvent.ACTION_UP:
		// v.setBackgroundColor(0x00ffffff);
		// markerList.setVisibility(View.INVISIBLE);
		// crayonList.setVisibility(View.INVISIBLE);
		// colorList.setVisibility(View.INVISIBLE);
		// geometryTool.setVisibility(View.INVISIBLE);
		// stamppenList.setVisibility(View.INVISIBLE);
		// backgroundList.setVisibility(View.INVISIBLE);
		// stickerList.setVisibility(View.INVISIBLE);
		// eraserList.setVisibility(View.INVISIBLE);
		// picTool.setVisibility(View.INVISIBLE);
		// wordTool.setVisibility(View.VISIBLE);
		// break;
		// case MotionEvent.ACTION_CANCEL:
		// v.setBackgroundColor(0x00ff0000);
		// }
		// flawOperation();
		// return true;
		// }
		// });
		// // 设置水彩笔点击监听
		// CasualWaterUtil casualWaterUtil = new CasualWaterUtil(this, drawView);
		// casualWaterUtil.casualWaterPicSetOnClickListener();
		// // 设置蜡笔点击监听
		// CasualCrayonUtil casualCrayonUtil = new CasualCrayonUtil(this, drawView);
		// casualCrayonUtil.casualCrayonPicSetOnClickListener();
		// // 设置颜料笔点击监听
		// CasualColorUtil casualColorUtil = new CasualColorUtil(this, drawView);
		// casualColorUtil.casualColorPicSetOnClickListener();
		// // 文字
		GeometryUtil graphicUtil = new GeometryUtil(activity, mView, drawView);
		graphicUtil.graphicPicSetOnClickListener();
		// 涂鸦
		TuYaUtil tuYaUtil = new TuYaUtil(activity, mView, drawView);
		tuYaUtil.tuyaPicSetOnClickListener();
		// // 设置印花点击的监听
		// StamppenUtil stamppenUtil = new StamppenUtil(this, drawView);
		// stamppenUtil.stampPenPicSetOnClickListener();
		// 设置背景图片点击的监听
		BackgroundUtil backgroundUtil = new BackgroundUtil(activity, mView, drawView);
		backgroundUtil.backgroundPicSetOnClickListener();
		// // 设置橡皮点击的监听
		// EraserUtil eraserUtil = new EraserUtil(this, drawView);
		// eraserUtil.eraserPicSetOnClickListener();
		// // 设置素材点击的监听
		StickerUtil stickerUtil = new StickerUtil(activity, mView, drawView);
		stickerUtil.stickerPicSetOnClickListener();
		// // 设置图片点击的监听
		// PicUtil picUtil = new PicUtil(this);
		// picUtil.picPicSetOnClickListener();
		// // 点击返回按钮
		// drawbackButton.setOnClickListener(new Button.OnClickListener()
		// {
		// public void onClick(View v)
		// {
		// Intent intent = new Intent();
		// intent.setClass(DrawActivity.this, MainActivity.class);
		// startActivity(intent);
		// DrawActivity.this.finish();
		// }
		// });
		// 点击保存按钮
		saveButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String path = drawView.saveBitmap();
				showActivity(ActPublish.class, path);
			}
		});
		// 点击undo按钮
		undoButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				drawView.undo();
			}
		});
		// 点击undo按钮
		redoButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				drawView.redo();
			}
		});
		// 点击隐形按钮
		visibleButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				setUpAndButtomBarVisible(false);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == mView) {
			return super.onCreateView(inflater, container, savedInstanceState);
		}
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	public void setUpAndButtomBarVisible(boolean isVisible) {
		if (isVisible) {
			rightBar.setVisibility(View.VISIBLE);
			bottomBar.setVisibility(View.VISIBLE);
		} else {
			rightBar.setVisibility(View.INVISIBLE);
			bottomBar.setVisibility(View.INVISIBLE);
		}
	}

	private ArrayList<SLBitmap> viewPhoto() {
		String[] projection = new String[] { MediaStore.Images.ImageColumns._ID, Thumbnails.DATA,
				MediaStore.Images.ImageColumns.DATE_TAKEN };
		Cursor cursor = activity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				projection, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
		ContentResolver cr = activity.getContentResolver();
		if (cursor == null) {
			return null;
		}
		ArrayList<SLBitmap> bitmaps = new ArrayList<SLBitmap>();
		int count = 0;
		for (cursor.moveToFirst(); !cursor.isAfterLast() && count < 9; cursor.moveToNext()) {
			count++;
			long thumbNailsId = cursor.getLong(cursor.getColumnIndex("_ID"));
			Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, thumbNailsId,
					Video.Thumbnails.MICRO_KIND, null);
			SLBitmap sBitmap = new SLBitmap();
			sBitmap.bitmap = bitmap;
			int dataColumn = cursor.getColumnIndex(Thumbnails.DATA);
			String path = cursor.getString(dataColumn);
			sBitmap.path = path;
			bitmaps.add(sBitmap);
		}
		return bitmaps;
	}

	private class SLBitmap {
		Bitmap bitmap;
		String path;
		boolean isCheck = true;

		SLBitmap() {

		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindDrawables(mView.findViewById(R.id.drawroot));
		drawView.freeBitmaps();
		System.gc();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// PIC_STORAGE_BG=1 PIC_STORAGE_STICKER=2 PIC_CAMERA_BG=3
		// PIC_CAMERA_STICKER=4
		if ((requestCode >= 1 && requestCode <= 4) && resultCode == Activity.RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			Bitmap bitmap = null;
			AssetFileDescriptor fileDescriptor = null;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			try {
				fileDescriptor = activity.getContentResolver().openAssetFileDescriptor(
						selectedImage, "r");
			} catch (FileNotFoundException e) {
			}
			bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null,
					options);
			// 获取这个图片的宽和高
			float scaleWidth = options.outWidth * 1.0f / DrawAttribute.screenWidth;
			float scaleHeight = options.outHeight * 1.0f / DrawAttribute.screenHeight;
			float scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
			if (scale < 1.0f) {
				scale = 1.0f;
			}
			options.inJustDecodeBounds = false;
			options.inSampleSize = (int) scale;
			bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null,
					options);
			try {
				fileDescriptor.close();
			} catch (IOException e) {
			}
			if (requestCode == 1 || requestCode == 3) {
				drawView.setBackgroundBitmap(bitmap, false);
			} else {
				drawView.addStickerBitmap(bitmap);
			}
		}
	}

	private void flawOperation() {
		drawView.setBasicGeometry(null);
	}

	// @Override
	// public void onBackPressed() {
	// // Intent intent = new Intent();
	// // intent.setClass(TestDrawActivity.this, MainActivity.class);
	// // startActivity(intent);
	// TestDrawActivity.this.finish();
	// }

	private void unbindDrawables(View view) {
		if (view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
	}
}
