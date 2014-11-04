/*
 * Copyright (C) 2010 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.givon.util.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Displays a color picker to the user and allow them to select a color. A slider for the alpha channel is also available. Enable it by
 * setting setAlphaSliderVisible(boolean) to true.
 * @author Daniel Nilsson
 */
public class ColorPickerView extends View {

	private final static int PANEL_SAT_VAL = 0;
	private final static int PANEL_HUE = 1;

	/**
	 * The width in pixels of the border surrounding all color panels.
	 */
	private final static float BORDER_WIDTH_PX = 1;

	/**
	 * The width in dp of the hue panel.
	 */
	private float HUE_PANEL_WIDTH = 30f;
	/**
	 * The height in dp of the alpha panel
	 */
	private float ALPHA_PANEL_HEIGHT = 20f;
	/**
	 * The distance in dp between the different color panels.
	 */
	private float PANEL_SPACING = 10f;
	/**
	 * The radius in dp of the color palette tracker circle.
	 */
	private float PALETTE_CIRCLE_TRACKER_RADIUS = 5f;
	/**
	 * The dp which the tracker of the hue or alpha panel will extend outside of its bounds.
	 */
	private float RECTANGLE_TRACKER_OFFSET = 2f;

	private float mDensity = 1f;

	private OnColorChangedListener mListener;

	// private Paint mSatValPaint;
	// private Paint mSatValTrackerPaint;

	private Paint mHuePaint;
	private Paint mHueTrackerPaint;

	// private Paint mAlphaPaint;
	// private Paint mAlphaTextPaint;

	private Paint mBorderPaint;

	// private Shader mValShader;
	// private Shader mSatShader;
	private Shader mHueShader;
	// private Shader mAlphaShader;

	// private int mAlpha = 0xff;
	private float mHue = 364f;
	// private float mSat = 0f;
	// private float mVal = 0f;

	private String mAlphaSliderText = "";
	private int mSliderTrackerColor = 0xff1c1c1c;
	private int mBorderColor = 0xff6E6E6E;
	private boolean mShowAlphaPanel = false;

	/*
	 * To remember which panel that has the "focus" when processing hardware button data.
	 */
	private int mLastTouchedPanel = PANEL_SAT_VAL;

	/**
	 * Offset from the edge we must have or else the finger tracker will get clipped when it is drawn outside of the view.
	 */
	private float mDrawingOffset;

	/*
	 * Distance form the edges of the view of where we are allowed to draw.
	 */
	private RectF mDrawingRect;

	private RectF mHueRect;

	private Point mStartTouchPoint = null;

	public interface OnColorChangedListener {
		public void onColorChanged(int color);
	}

	public ColorPickerView(Context context) {
		this(context, null);
	}

	public ColorPickerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ColorPickerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mDensity = getContext().getResources().getDisplayMetrics().density;
		PALETTE_CIRCLE_TRACKER_RADIUS *= mDensity;
		RECTANGLE_TRACKER_OFFSET *= mDensity;
		HUE_PANEL_WIDTH *= mDensity;
		ALPHA_PANEL_HEIGHT *= mDensity;
		PANEL_SPACING = PANEL_SPACING * mDensity;

		mDrawingOffset = calculateRequiredOffset();

		initPaintTools();

		// Needed for receiving trackball motion events.
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	private void initPaintTools() {

		// mSatValPaint = new Paint();
		// mSatValTrackerPaint = new Paint();
		mHuePaint = new Paint();
		mHueTrackerPaint = new Paint();
		// mAlphaPaint = new Paint();
		// mAlphaTextPaint = new Paint();
		mBorderPaint = new Paint();

		// mSatValTrackerPaint.setStyle(Style.STROKE);
		// mSatValTrackerPaint.setStrokeWidth(2f * mDensity);
		// mSatValTrackerPaint.setAntiAlias(true);

		mHueTrackerPaint.setColor(mSliderTrackerColor);
		mHueTrackerPaint.setStyle(Style.STROKE);
		mHueTrackerPaint.setStrokeWidth(2f * mDensity);
		mHueTrackerPaint.setAntiAlias(true);

		// mAlphaTextPaint.setColor(0xff1c1c1c);
		// mAlphaTextPaint.setTextSize(14f * mDensity);
		// mAlphaTextPaint.setAntiAlias(true);
		// mAlphaTextPaint.setTextAlign(Align.CENTER);
		// mAlphaTextPaint.setFakeBoldText(true);

	}

	private float calculateRequiredOffset() {
		float offset = Math.max(PALETTE_CIRCLE_TRACKER_RADIUS, RECTANGLE_TRACKER_OFFSET);
		offset = Math.max(offset, BORDER_WIDTH_PX * mDensity);

		return offset * 1.5f;
	}

	private int[] buildHueColorArray() {

		int[] hue = new int[371];

		for (int i = 0; i < 360; i++) {
			hue[i] = Color.HSVToColor(new float[] { i, 1f, 1f });
		}
		for (int j = 5; j >= 0; j--) {
			hue[360 + j] = Color.HSVToColor(new float[] { 1, 1f, 0f });
		}
		for (int j = 5; j >= 0; j--) {
			hue[365 + j] = Color.HSVToColor(new float[] { 1, 0f, 1f });
		}

		return hue;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (mDrawingRect.width() <= 0 || mDrawingRect.height() <= 0)
			return;

		drawHuePanel(canvas);

	}

	private void drawHuePanel(Canvas canvas) {

		final RectF rect = mHueRect;

		if (BORDER_WIDTH_PX > 0) {
			mBorderPaint.setColor(mBorderColor);
			canvas.drawRect(rect.left - BORDER_WIDTH_PX, rect.top - BORDER_WIDTH_PX, rect.right
					+ BORDER_WIDTH_PX, rect.bottom + BORDER_WIDTH_PX, mBorderPaint);
		}
		if (mHueShader == null) {
			mHueShader = new LinearGradient(rect.left, rect.top, rect.right, rect.top,
					buildHueColorArray(), null, TileMode.CLAMP);
			mHuePaint.setShader(mHueShader);
		}

		canvas.drawRect(rect, mHuePaint);

		float rectWidth = 4 * mDensity / 2;

		Point p = hueToPoint(mHue);

		RectF r = new RectF();
		r.left = p.x - rectWidth;
		r.right = p.x + rectWidth;
		r.top = rect.top - RECTANGLE_TRACKER_OFFSET;
		r.bottom = rect.bottom + RECTANGLE_TRACKER_OFFSET;
		canvas.drawRoundRect(r, 2, 2, mHueTrackerPaint);

	}

	private Point hueToPoint(float hue) {

		final RectF rect = mHueRect;
		final float width = rect.width();

		Point p = new Point();

		p.y = (int) rect.top;
		p.x = (int) ((hue * width / 370f) + rect.top);
		return p;
	}

	private float pointToHue(float x) {

		final RectF rect = mHueRect;

		float width = rect.width();

		if (x < rect.left) {
			x = 0f;
		} else if (x > rect.right) {
			x = width;
		} else {
			x = x - rect.left;
		}

		return (x * 370f / width);
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event) {

		float x = event.getX();
		float y = event.getY();

		boolean update = false;

		if (event.getAction() == MotionEvent.ACTION_MOVE) {

			switch (mLastTouchedPanel) {

			case PANEL_HUE:

				float hue = mHue - y * 10f;

				if (hue < 0f) {
					hue = 0f;
				} else if (hue > 370f) {
					hue = 370f;
				}

				mHue = hue;

				update = true;

				break;

			}

		}

		if (update) {

			if (mListener != null) {
				mListener.onColorChanged(getColor());
			}

			invalidate();
			return true;
		}

		return super.onTrackballEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		boolean update = false;

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			mStartTouchPoint = new Point((int) event.getX(), (int) event.getY());

			update = moveTrackersIfNeeded(event);

			break;

		case MotionEvent.ACTION_MOVE:

			update = moveTrackersIfNeeded(event);

			break;

		case MotionEvent.ACTION_UP:

			mStartTouchPoint = null;

			update = moveTrackersIfNeeded(event);

			break;

		}

		if (update) {

			if (mListener != null) {
				mListener.onColorChanged(getColor());
			}

			invalidate();
			return true;
		}

		return super.onTouchEvent(event);
	}

	private boolean moveTrackersIfNeeded(MotionEvent event) {

		if (mStartTouchPoint == null)
			return false;

		boolean update = false;

		int startX = mStartTouchPoint.x;
		int startY = mStartTouchPoint.y;

		if (mHueRect.contains(startX, startY)) {
			mLastTouchedPanel = PANEL_HUE;

			mHue = pointToHue(event.getX());

			update = true;
		}

		return update;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mDrawingRect = new RectF();
		mDrawingRect.left = mDrawingOffset + getPaddingLeft();
		mDrawingRect.right = w - mDrawingOffset - getPaddingRight();
		mDrawingRect.top = mDrawingOffset + getPaddingTop();
		mDrawingRect.bottom = h - mDrawingOffset - getPaddingBottom();
		System.out.println("mDrawingRect.left:" + mDrawingRect.left + " " + mDrawingRect.right
				+ " " + mDrawingRect.top + " " + h);
		// setUpSatValRect();
		setUpHueRect();
		// setUpAlphaRect();
	}

	// private void setUpSatValRect(){
	//
	// final RectF dRect = mDrawingRect;
	// float panelSide = dRect.height() - BORDER_WIDTH_PX * 2;
	//
	// if(mShowAlphaPanel){
	// panelSide -= PANEL_SPACING + ALPHA_PANEL_HEIGHT;
	// }
	//
	// float left = dRect.left + BORDER_WIDTH_PX;
	// float top = dRect.top + BORDER_WIDTH_PX;
	// float bottom = top + panelSide;
	// float right = left + panelSide;
	//
	// mSatValRect = new RectF(left,top, right, bottom);
	// }

	private void setUpHueRect() {
		final RectF dRect = mDrawingRect;

		// float left = dRect.right - HUE_PANEL_WIDTH + BORDER_WIDTH_PX;
		// float top = dRect.top + BORDER_WIDTH_PX;
		// float bottom = dRect.bottom - BORDER_WIDTH_PX - (mShowAlphaPanel ? (PANEL_SPACING + ALPHA_PANEL_HEIGHT) : 0);
		// float right = dRect.right - BORDER_WIDTH_PX;
		float left = dRect.left;
		float top = dRect.top + BORDER_WIDTH_PX;
		// float bottom = dRect.bottom - BORDER_WIDTH_PX - (mShowAlphaPanel ? (PANEL_SPACING + ALPHA_PANEL_HEIGHT) : 0);
		float bottom = 60;
		float right = dRect.right - BORDER_WIDTH_PX;

		mHueRect = new RectF(left, top, right, bottom);
	}

	// private void setUpAlphaRect() {
	//
	// if(!mShowAlphaPanel) return;
	//
	// final RectF dRect = mDrawingRect;
	//
	// float left = dRect.left + BORDER_WIDTH_PX;
	// float top = dRect.bottom - ALPHA_PANEL_HEIGHT + BORDER_WIDTH_PX;
	// float bottom = dRect.bottom - BORDER_WIDTH_PX;
	// float right = dRect.right - BORDER_WIDTH_PX;
	//
	// mAlphaRect = new RectF(left, top, right, bottom);
	//
	// mAlphaPattern = new AlphaPatternDrawable((int) (5 * mDensity));
	// mAlphaPattern.setBounds(
	// Math.round(mAlphaRect.left),
	// Math.round(mAlphaRect.top),
	// Math.round(mAlphaRect.right),
	// Math.round(mAlphaRect.bottom)
	// );
	//
	// }

	/**
	 * Set a OnColorChangedListener to get notified when the color selected by the user has changed.
	 * @param listener
	 */
	public void setOnColorChangedListener(OnColorChangedListener listener) {
		mListener = listener;
	}

	/**
	 * Set the color of the border surrounding all panels.
	 * @param color
	 */
	public void setBorderColor(int color) {
		mBorderColor = color;
		invalidate();
	}

	/**
	 * Get the color of the border surrounding all panels.
	 */
	public int getBorderColor() {
		return mBorderColor;
	}

	/**
	 * Get the current color this view is showing.
	 * @return the current color.
	 */
	public int getColor() {
//		System.out.println("mHue:" + mHue);
		if (mHue < 360) {
			return Color.HSVToColor(new float[] { mHue, 1f, 1f });
		} else if (mHue >= 360 && mHue < 365) {
			return Color.HSVToColor(new float[] { mHue, 1f, 0f });
		} else {
			return Color.HSVToColor(new float[] { mHue, 0f, 1f });
		}
	}

	/**
	 * Set the color the view should show.
	 * @param color
	 *        The color that should be selected.
	 */
	// public void setColor(int color){
	// setColor(color, false);
	// }

	/**
	 * Set the color this view should show.
	 * @param color
	 *        The color that should be selected.
	 * @param callback
	 *        If you want to get a callback to your OnColorChangedListener.
	 */
	// public void setColor(int color, boolean callback){
	//
	// int alpha = Color.alpha(color);
	// int red = Color.red(color);
	// int blue = Color.blue(color);
	// int green = Color.green(color);
	//
	// float[] hsv = new float[3];
	//
	// Color.RGBToHSV(red, green, blue, hsv);
	//
	// mAlpha = alpha;
	// mHue = hsv[0];
	// mSat = hsv[1];
	// mVal = hsv[2];
	//
	// if(callback && mListener != null){
	// mListener.onColorChanged(Color.HSVToColor(mAlpha, new float[]{mHue, mSat, mVal}));
	// }
	//
	// invalidate();
	// }

	/**
	 * Get the drawing offset of the color picker view. The drawing offset is the distance from the side of a panel to the side of the view
	 * minus the padding. Useful if you want to have your own panel below showing the currently selected color and want to align it perfectly.
	 * @return The offset in pixels.
	 */
	public float getDrawingOffset() {
		return mDrawingOffset;
	}

	/**
	 * Set if the user is allowed to adjust the alpha panel. Default is false. If it is set to false no alpha will be set.
	 * @param visible
	 */
	public void setAlphaSliderVisible(boolean visible) {

		if (mShowAlphaPanel != visible) {
			mShowAlphaPanel = visible;

			/*
			 * Reset all shader to force a recreation. Otherwise they will not look right after the size of the view has changed.
			 */
			// mValShader = null;
			// mSatShader = null;
			mHueShader = null;
			// mAlphaShader = null;;

			requestLayout();
		}

	}

	public void setSliderTrackerColor(int color) {
		mSliderTrackerColor = color;

		mHueTrackerPaint.setColor(mSliderTrackerColor);

		invalidate();
	}

	public int getSliderTrackerColor() {
		return mSliderTrackerColor;
	}

	/**
	 * Set the text that should be shown in the alpha slider. Set to null to disable text.
	 * @param res
	 *        string resource id.
	 */
	public void setAlphaSliderText(int res) {
		String text = getContext().getString(res);
		setAlphaSliderText(text);
	}

	/**
	 * Set the text that should be shown in the alpha slider. Set to null to disable text.
	 * @param text
	 *        Text that should be shown.
	 */
	public void setAlphaSliderText(String text) {
		mAlphaSliderText = text;
		invalidate();
	}

	/**
	 * Get the current value of the text that will be shown in the alpha slider.
	 * @return
	 */
	public String getAlphaSliderText() {
		return mAlphaSliderText;
	}
}