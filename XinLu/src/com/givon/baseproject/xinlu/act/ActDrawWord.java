/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActDrawWord.java  2014年11月2日 下午3:22:53 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.givon.baseproject.draw.util.DrawAttribute;
import com.givon.baseproject.draw.util.GeometryUtil;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.entity.DetailImages;
import com.givon.baseproject.xinlu.util.BitmapHelp;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.view.DrawView;
import com.givon.baseproject.xinlu.view.EditTextDialog;
import com.givon.baseproject.xinlu.view.EditTextDialog.OnEditTextOnClickListener;

public class ActDrawWord extends BaseActivity implements OnClickListener {
	private DrawView drawView;
	private LinearLayout mTextColorLinearLayout;
	private LinearLayout mTextTypeLinearLayout;
	// private LinearLayout mTextBianJiLinearLayout;
	public int brushDrawableId;
	private FrameLayout toolFrameLayout;
	private Button finishButton;
	private Button cancelButton;
	private DetailImages images;
	private GeometryUtil graphicUtil;
	private EditTextDialog mEditTextDialog;

	// private LinearLayout mEditLayout;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_draw_word);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initView();
		drawView.addStickerBitmapTT("点击我编辑",
				graphicUtil.GetTextPaintClass(GeometryUtil.TextType.SONG));
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

	private void initView() {
		drawView = (DrawView) findViewById(R.id.drawview);
		mTextColorLinearLayout = (LinearLayout) findViewById(R.id.ly_textcolor);
		// mTextBianJiLinearLayout = (LinearLayout) findViewById(R.id.ly_textbianji);
		mTextTypeLinearLayout = (LinearLayout) findViewById(R.id.ly_texttype);
		// mEditLayout = (LinearLayout) findViewById(R.id.ly_texttype);
		toolFrameLayout = (FrameLayout) findViewById(R.id.fl_botton_tool);
		finishButton = (Button) findViewById(R.id.bt_finish);
		cancelButton = (Button) findViewById(R.id.bt_cancel);
		finishButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		mTextColorLinearLayout.setVisibility(View.INVISIBLE);
		// mTextBianJiLinearLayout.setVisibility(View.INVISIBLE);
		mTextTypeLinearLayout.setVisibility(View.VISIBLE);
		// 下面的菜单
		TextView textColor = (TextView) this.findViewById(R.id.tv_textcolor);
		TextView textBianji = (TextView) this.findViewById(R.id.tv_bianji);
		TextView textType = (TextView) this.findViewById(R.id.tv_texttype);

		textColor.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					mTextColorLinearLayout.setVisibility(View.VISIBLE);
					// mTextBianJiLinearLayout.setVisibility(View.INVISIBLE);
					mTextTypeLinearLayout.setVisibility(View.INVISIBLE);
					drawView.setCanDraw(false);
					setUpAndButtomBarVisible(true);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(0x00ff0000);
				}
				flawOperation();
				return true;
			}
		});
		textBianji.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					mTextColorLinearLayout.setVisibility(View.INVISIBLE);
					// mTextBianJiLinearLayout.setVisibility(View.VISIBLE);
					mTextTypeLinearLayout.setVisibility(View.INVISIBLE);
					drawView.setCanDraw(false);
					setUpAndButtomBarVisible(true);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(0x00ff0000);
				}
				flawOperation();
				return true;
			}
		});
		textType.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					mTextColorLinearLayout.setVisibility(View.INVISIBLE);
					// mTextBianJiLinearLayout.setVisibility(View.INVISIBLE);
					mTextTypeLinearLayout.setVisibility(View.VISIBLE);
					drawView.setCanDraw(false);
					setUpAndButtomBarVisible(true);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(0x00ff0000);
				}
				flawOperation();
				return true;
			}
		});
		mEditTextDialog = new EditTextDialog(this, R.style.selectorDialog);
		mEditTextDialog.setOnEditTextOnClickListener(new OnEditTextOnClickListener() {

			@Override
			public void onClickSure(String string) {
				drawView.editStickerBitmapTT_now(
						graphicUtil.GetTextPaintClass(GeometryUtil.TextType.SONG), string);
			}

			@Override
			public void onClickCancel() {
				drawView.setEditTextVisibleStucker(false);
			}
		});
		graphicUtil = new GeometryUtil(this, getWindow().getDecorView(), drawView);
		graphicUtil.graphicPicSetOnClickListener();
	}

	private void flawOperation() {
		drawView.setBasicGeometry(null);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_finish:

			break;
		case R.id.bt_cancel:

			break;

		default:
			break;
		}
	}

	@Override
	public void setUpAndButtomBarVisible(boolean isVisible) {
		if (isVisible) {
			toolFrameLayout.setVisibility(View.VISIBLE);
			// bottomBar.setVisibility(View.VISIBLE);
		} else {
			toolFrameLayout.setVisibility(View.INVISIBLE);
			// bottomBar.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void setEditViewVisible(boolean isVisible) {
		if (isVisible) {
			mEditTextDialog.show();
		} else {

			if (mEditTextDialog.isShowing()) {
				mEditTextDialog.dismiss();
			}
		}
	}

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
