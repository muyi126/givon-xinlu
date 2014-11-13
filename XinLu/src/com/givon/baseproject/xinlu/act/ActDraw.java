/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @FraHome.java  2014年10月16日 上午9:12:57 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.MediaStore.Video;
import android.text.format.DateFormat;
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

import com.givon.baseproject.draw.util.DrawAttribute;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.BaseApplication;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.DetailImages;
import com.givon.baseproject.xinlu.fragment.FraPublish;
import com.givon.baseproject.xinlu.util.BitmapHelp;
import com.givon.baseproject.xinlu.util.CommUtil;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.view.DrawView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;

/**
 * 发布
 * @author givon
 * 
 */
public class ActDraw extends BaseActivity {
	private DrawView drawView;
	// private LinearLayout rightBar;
	private LinearLayout bottomBar;
	public int brushDrawableId;
	private HorizontalScrollView stamppenList;
	private LinearLayout rightPhotoContentLayout_ly;
	private LinearLayout rightPhotoToo_ly;
	private Button saveButton;
	private ArrayList<DetailImages> filesList = new ArrayList<DetailImages>();
	private ArrayList<SLBitmap> photos = new ArrayList<ActDraw.SLBitmap>();
	private boolean isCheckBitmap = false;
	private int fromOption;
	private final static int PUBLISH_SHOW_RESULT = 12311;
	// 文字
	private final static int PUBLISH_SHOW_DRAWWORD_RESULT = 12312;
	// 涂鸦
	private final static int PUBLISH_SHOW_DRAWTUYA_RESULT = 12313;
	// 图案
	private final static int PUBLISH_SHOW_DRAWTUAN_RESULT = 12314;
	// 滤镜
	private final static int PUBLISH_SHOW_DRAWLVJING_RESULT = 12315;
	// 图片选择
	private final static int SHELECT_PHOTO_RESULT = 12316;
	// 拍照
	private final static int SHELECT_TAKE_PHOTO_RESULT = 12317;
	private ImageButton visible_Right_bt;
	private ImageButton to_Photo_bt;
	private ImageButton to_TakePhoto_bt;
	private boolean isRightVisible = true;
	private static int ANIM_TIME = 500;
	private String mBitmapPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.layout_frapublish);
		drawView = (DrawView) findViewById(R.id.drawview);
		Intent intent = getIntent();
		if(null!=intent&&intent.hasExtra(Constant.ID)){
			fromOption = intent.getIntExtra(Constant.ID, 0);
			if(intent.hasExtra(Constant.DATA)){
				mBitmapPath = intent.getStringExtra(Constant.DATA);
				if(!StringUtil.isEmpty(mBitmapPath)){
					Bitmap bitmap;
					try {
						bitmap = BitmapHelp.getBitpMap(new File(mBitmapPath));
						Bitmap bitmap2 = drawView.setBackgroundBitmap(bitmap, false);
						if (bitmap!=bitmap2) {
							bitmap.recycle();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		rightPhotoContentLayout_ly = (LinearLayout) findViewById(R.id.ly_photos);
		to_Photo_bt = (ImageButton) findViewById(R.id.to_photo_bt);
		to_Photo_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadPhotos(SHELECT_PHOTO_RESULT);
			}
		});
		to_TakePhoto_bt = (ImageButton) findViewById(R.id.to_takephoto_bt);
		to_TakePhoto_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				takePhoto();
			}
		});
		visible_Right_bt = (ImageButton) findViewById(R.id.visible_Right);
		rightPhotoToo_ly = (LinearLayout) findViewById(R.id.drawright);
		animate(rightPhotoToo_ly).setDuration(0);
		animate(rightPhotoToo_ly).x(BaseApplication.mWidth);
		System.out.println("An:" + (BaseApplication.mWidth - visible_Right_bt.getWidth()));
		animate(visible_Right_bt).x(BaseApplication.mWidth - CommUtil.dip2px(45)).setListener(
				new AnimatorListener() {

					@Override
					public void onAnimationStart(Animator animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animator animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animator animation) {
						animate(visible_Right_bt).alpha(100);

					}

					@Override
					public void onAnimationCancel(Animator animation) {
						// TODO Auto-generated method stub

					}
				});
		ViewRightBarTask task = new ViewRightBarTask();
		task.execute();
		saveButton = (Button) findViewById(R.id.finish);
		bottomBar = (LinearLayout) findViewById(R.id.drawbottom);
		bottomBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

		visible_Right_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isRightVisible) {
					isRightVisible = false;
					// visible_Right_bt.setVisibility(View.GONE);
					int xValue = BaseApplication.mWidth - rightPhotoContentLayout_ly.getWidth();
					animate(rightPhotoToo_ly).setDuration(ANIM_TIME);
					animate(rightPhotoToo_ly).x(xValue);
					animate(visible_Right_bt).x(
							BaseApplication.mWidth - visible_Right_bt.getWidth()
									- rightPhotoContentLayout_ly.getWidth());
				} else {
					isRightVisible = true;
					visible_Right_bt.setVisibility(View.VISIBLE);
					animate(rightPhotoToo_ly).setDuration(ANIM_TIME);
					animate(rightPhotoToo_ly).x(BaseApplication.mWidth);
					animate(visible_Right_bt).x(
							BaseApplication.mWidth - visible_Right_bt.getWidth());
				}

			}
		});
				// 下面的菜单
		TextView drawTextButton = (TextView) findViewById(R.id.drawText);
		TextView drawTuyaButton = (TextView) findViewById(R.id.drawTuya);
		TextView drawMubanBUtton = (TextView) findViewById(R.id.drawMuban);
		TextView drawLvjinButton = (TextView) findViewById(R.id.drawLvjin);
		TextView drawTuanButton = (TextView) findViewById(R.id.drawTuan);
		// 上面的菜单
		// ImageButton visibleButton = (ImageButton) findViewById(R.id.drawvisiblebtn);
		Button undoButton = (Button) findViewById(R.id.drawundobtn);
		Button redoButton = (Button) findViewById(R.id.drawredobtn);
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

					DetailImages image = drawView.saveBitmap();
					filesList.add(image);
					Intent intent = new Intent(ActDraw.this, ActDrawWord.class);
					intent.putExtra(Constant.DATA, image);
					startActivityForResult(intent, PUBLISH_SHOW_DRAWWORD_RESULT);
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
					DetailImages image = drawView.saveBitmap();
					filesList.add(image);
					Intent intent = new Intent(ActDraw.this, ActDrawTuYa.class);
					intent.putExtra(Constant.DATA, image);
					ActDraw.this.startActivityForResult(intent, PUBLISH_SHOW_DRAWTUYA_RESULT);
					drawView.setCanDraw(false);
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
					// geometryTool.setVisibility(View.INVISIBLE);
					// tuyaTool.setVisibility(View.INVISIBLE);
					// backgroundList.setVisibility(View.INVISIBLE);
					// stickerList.setVisibility(View.INVISIBLE);
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
					DetailImages image = drawView.saveBitmap();
					filesList.add(image);
					Intent intent = new Intent(ActDraw.this, ActDrawLvJing.class);
					intent.putExtra(Constant.DATA, image);
					ActDraw.this.startActivityForResult(intent, PUBLISH_SHOW_DRAWLVJING_RESULT);
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
					DetailImages image = drawView.saveBitmap();
					filesList.add(image);
					Intent intent = new Intent(ActDraw.this, ActDrawTuAn.class);
					intent.putExtra(Constant.DATA, image);
					ActDraw.this.startActivityForResult(intent, PUBLISH_SHOW_DRAWTUAN_RESULT);
					drawView.setCanDraw(false);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(0x00ff0000);
				}
				flawOperation();
				return true;
			}
		});
		// 点击保存按钮

		saveButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {

				DetailImages detailImages = drawView.saveBitmap();

				Intent intent = new Intent();

				intent.putExtra(Constant.DATA, detailImages);
				intent.putExtra(Constant.ID, fromOption);

				setResult(Activity.RESULT_OK, intent);

				finish();

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
		// visibleButton.setOnClickListener(new Button.OnClickListener() {
		// public void onClick(View v) {
		// setUpAndButtomBarVisible(false);
		// }
		// });
	}

	public void setUpAndButtomBarVisible(boolean isVisible) {
		if (isVisible) {
			// rightBar.setVisibility(View.VISIBLE);
			// bottomBar.setVisibility(View.VISIBLE);
		} else {
			// rightBar.setVisibility(View.INVISIBLE);
			// bottomBar.setVisibility(View.INVISIBLE);
		}
	}

	private ArrayList<SLBitmap> viewPhoto() {
		String[] projection = new String[] { MediaStore.Images.ImageColumns._ID, Thumbnails.DATA,
				MediaStore.Images.ImageColumns.DATE_TAKEN };
		Cursor cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
				null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
		ContentResolver cr = getContentResolver();
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
		boolean isCheckEnable = true;

		SLBitmap() {

		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindDrawables(findViewById(R.id.drawroot));
		drawView.freeBitmaps();
		System.gc();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PUBLISH_SHOW_RESULT) {
			ViewRightBarTask task = new ViewRightBarTask();
			task.execute();
			drawView.initView();
		}
		// 滤镜
		if (requestCode == PUBLISH_SHOW_DRAWLVJING_RESULT) {
			resultInitDraw(resultCode, data);
			return;
		} else
		// 图案
		if (requestCode == PUBLISH_SHOW_DRAWTUAN_RESULT) {
			resultInitDraw(resultCode, data);
			return;
		} else
		// 涂鸦
		if (requestCode == PUBLISH_SHOW_DRAWTUYA_RESULT) {
			resultInitDraw(resultCode, data);
			return;
		} else
		// 文字
		if (requestCode == PUBLISH_SHOW_DRAWWORD_RESULT) {
			resultInitDraw(resultCode, data);
			return;
		}
		// 选择照片
		if (SHELECT_PHOTO_RESULT == requestCode && data != null && resultCode == Activity.RESULT_OK) {

			Uri originalUri = data.getData(); // 获得图片的uri
			SLBitmap bitmap = viewPhotoForUri(originalUri);
			if (bitmap != null) {
				photos.add(0, bitmap);
				initPhotoBar();
			}
		}
		// 拍照
		if (SHELECT_TAKE_PHOTO_RESULT == requestCode && resultCode == Activity.RESULT_OK) {

			if (null == data) {
				if (null != picPhotoUri) {

				} else {
					ToastUtils.showMessage("选择图片文件出错");
					return;
				}
			}
			if (!StringUtil.isEmpty(picPath)) {
				SLBitmap bitmap = doPhoto(picPath);

				if (bitmap != null) {
					photos.add(0, bitmap);
					initPhotoBar();
				}
			} else {
				ToastUtils.showMessage("选择图片文件出错");
			}
		}

	}

	
	private void resultInitDraw(int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (null != data && data.hasExtra(Constant.DATA)) {
				DetailImages images = (DetailImages) data.getSerializableExtra(Constant.DATA);
				filesList.add(images);
				Bitmap bmp;
				try {
					bmp = BitmapHelp.getBitpMap(new File(images.getImageUrl()));
					if (null != bmp) {
						drawView.setBackgroundBitmap(bmp, false);
						System.out.println("drawView:"+drawView);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private void initPhotoBar() {
		isCheckBitmap = false;
		if (null == rightPhotoContentLayout_ly) {
			return;
		}
		rightPhotoContentLayout_ly.removeAllViews();
		for (int i = 0; i < photos.size(); i++) {
			ImageView imageView = new ImageView(this);
			imageView.setTag(photos.get(i));
			LayoutParams params = new LayoutParams(60, 60);
			imageView.setBackgroundDrawable(new BitmapDrawable(photos.get(i).bitmap));
			rightPhotoContentLayout_ly.addView(imageView, params);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					SLBitmap obj = (SLBitmap) v.getTag();
					if (obj.isCheckEnable && !isCheckBitmap) {
						isRightVisible = true;
						visible_Right_bt.setVisibility(View.VISIBLE);
						animate(rightPhotoToo_ly).setDuration(ANIM_TIME);
						animate(rightPhotoToo_ly).x(BaseApplication.mWidth);
						animate(visible_Right_bt).x(
								BaseApplication.mWidth - visible_Right_bt.getWidth());
						isCheckBitmap = true;
						obj.isCheckEnable = false;
						Bitmap bmp;
						try {
							bmp = BitmapHelp.getBitpMap(new File(obj.path));
							if (null != bmp) {
								drawView.addStickerBitmap(bmp);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (!obj.isCheckEnable && isCheckBitmap) {
						isCheckBitmap = false;
						obj.isCheckEnable = true;
						drawView.deleteStickerBitmap(null);
					}
					v.setTag(obj);
				}
			});
		}
	}

	private void flawOperation() {
		drawView.setBasicGeometry(null);
	}

	private void loadPhotos(int requestCode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(intent, requestCode);
	}

	private static String photoPath = Constant.DRAW_PATH;
	private String picPath;
	private Uri picPhotoUri;

	/**
	 * 拍照获取图片
	 */
	private void takePhoto() {
		// 执行拍照前，应该先判断SD卡是否存在
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
			/***
			 * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这种方式有一个好处就是获取的图片是拍照后的原图 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
			 */

			/*
			 * ContentValues values = new ContentValues(); photoUri =
			 * this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			 * intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri); intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			 */
			// 初始化
			picPath = null;
			// CramerProActivity.imageView.setImageBitmap(null);
			String name = new DateFormat().format("yyyyMMdd_hhmmss",
					Calendar.getInstance(Locale.CHINA))
					+ ".jpg";
			File file = new File(photoPath);
			if (!file.exists()) {
				// 检查图片存放的文件夹是否存在
				file.mkdir();
				// 不存在的话 创建文件夹
			}
			picPath = photoPath + "/" + name;
			File photo = new File(picPath);
			picPhotoUri = Uri.fromFile(photo);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, picPhotoUri); // 这样就将文件的存储方式和uri指定到了Camera应用中
			startActivityForResult(intent, SHELECT_TAKE_PHOTO_RESULT);
		} else {
			ToastUtils.showMessage("内存卡不存在");
		}
	}

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

	private SLBitmap viewPhotoForUri(Uri uri) {
		String[] projection = new String[] { MediaStore.Images.ImageColumns._ID, Thumbnails.DATA,
				MediaStore.Images.ImageColumns.DATE_TAKEN };
		Cursor cursor = managedQuery(uri, projection, null, null,
				MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
		ContentResolver cr = getContentResolver();
		if (cursor == null) {
			return null;
		}
		cursor.moveToFirst();
		SLBitmap sBitmap = new SLBitmap();
		long thumbNailsId = cursor.getLong(cursor.getColumnIndex("_ID"));
		Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, thumbNailsId,
				Video.Thumbnails.MICRO_KIND, null);
		sBitmap.bitmap = bitmap;
		int dataColumn = cursor.getColumnIndex(Thumbnails.DATA);
		String path = cursor.getString(dataColumn);
		sBitmap.path = path;
		return sBitmap;
	}

	/**
	 * 选择图片后，获取图片的路径
	 * @param requestCode
	 * @param data
	 */
	private SLBitmap doPhoto(String picPath) {
		if (picPath != null) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			Bitmap bmp = BitmapFactory.decodeFile(picPath, options);
			float ox = BaseApplication.mWidth / 3;
			options.outWidth = 60;
			options.outHeight = 60;
			options.inJustDecodeBounds = false;
			options.inSampleSize = (int) (options.outWidth / ox);
			options.inPreferredConfig = Bitmap.Config.ARGB_4444;
			bmp = BitmapFactory.decodeFile(picPath, options);
			SLBitmap sBitmap = new SLBitmap();
			sBitmap.bitmap = bmp;
			sBitmap.path = picPath;
			return sBitmap;
		} else {
			ToastUtils.showMessage("选择文件不正确!");
			return null;
		}
	}
	
	private class ViewRightBarTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			System.out.println("doInBackground");
			photos = viewPhoto();
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			initPhotoBar();
		}
	}

}
