/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActDrawTuYa.java  2014年11月2日 下午3:23:55 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.givon.baseproject.draw.util.TuYaUtil;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.DetailImages;
import com.givon.baseproject.xinlu.util.BitmapHelp;
import com.givon.baseproject.xinlu.view.DrawView;
import com.givon.baseproject.xinlu.view.DrawView.ReloadingListener;

public class ActDrawTuYa extends BaseActivity implements OnClickListener{
	
	private DrawView drawView;
	public int brushDrawableId;
	private Button finishButton;
	private Button cancelButton;
	private DetailImages images;
	private TuYaUtil tuYaUtil;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_draw_tuya);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initView();
		drawView.setCanDraw(true);
		drawView.setReloadingListener(new ReloadingListener() {
			
			@Override
			public void onReLoding() {
				Intent intent = getIntent();
				if (intent.hasExtra(Constant.DATA)) {
					images = (DetailImages) intent.getSerializableExtra(Constant.DATA);
					Bitmap bmp;
					try {
						bmp = BitmapHelp.getBitpMap(new File(images.getImageUrl()));
						if (null != bmp) {
							drawView.setBackgroundBitmap(bmp, false);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		

	}

	private void initView() {
		drawView = (DrawView) findViewById(R.id.drawview);
		finishButton = (Button) findViewById(R.id.bt_finish);
		cancelButton = (Button) findViewById(R.id.bt_cancel);
		finishButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		// 下面的菜单
		tuYaUtil = new TuYaUtil(this, getWindow().getDecorView(), drawView);
	}

	private void flawOperation() {
		drawView.setBasicGeometry(null);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_finish:
			DetailImages image = drawView.saveBitmap();
			Intent intent = new Intent();
			intent.putExtra(Constant.DATA, image);
			setResult(Activity.RESULT_OK, intent);
			finish();
			break;
		case R.id.bt_cancel:
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;

		default:
			break;
		}
	}

	// @Override
	// public void setUpAndButtomBarVisible(boolean isVisible) {
	// if (isVisible) {
	// toolFrameLayout.setVisibility(View.VISIBLE);
	// // bottomBar.setVisibility(View.VISIBLE);
	// } else {
	// toolFrameLayout.setVisibility(View.INVISIBLE);
	// // bottomBar.setVisibility(View.INVISIBLE);
	// }
	// }

	// @Override
	// public void setEditViewVisible(boolean isVisible) {
	// if (isVisible) {
	// mEditTextDialog.show();
	// } else {
	//
	// if (mEditTextDialog.isShowing()) {
	// mEditTextDialog.dismiss();
	// }
	// }
	// }

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindDrawables(findViewById(R.id.drawroot));
		drawView.freeBitmaps();
		System.gc();
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

}
