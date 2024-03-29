package com.givon.baseproject.xinlu.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.givon.baseproject.draw.util.DrawAttribute;
import com.givon.baseproject.draw.util.StorageInSDCard;
import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.entity.DetailImages;
import com.givon.baseproject.xinlu.fragment.FraPublish;
import com.givon.draw.geometry.BasicGeometry;
import com.govin.draw.sticker.StickerBitmap;
import com.govin.draw.sticker.StickerBitmapList;
import com.govin.draw.sticker.StickerBitmapListByTT;
import com.govin.draw.sticker.TextViewStickerBitmap;

public class DrawView extends View implements Runnable {
	private final int VISIBLE_BTN_WIDTH = 60;
	private final int VISIBLE_TITLE_HEIGHT = 60;
	private final int VISIBLE_BTN_HEIGHT = 70;
	private ReloadingListener reloadingListener;

	private enum TouchLayer {
		GEOMETRY_LAYER, PAINT_LAYER, STICKER_BITMAP, STICKER_TEXT, STICKER_TOOL, STICKER_TEXT_TOOL, VISIBLE_BTN, NULL
	};

	private Bitmap backgroundBitmap = null;
	private PointF backgroundBitmapLeftTopP = null;
	private Bitmap paintBitmap = null;
	private Canvas paintCanvas = null;
	private BasicGeometry basicGeometry = null;
	private StickerBitmapList stickerBitmapList = null;
	private StickerBitmapListByTT stickerBitmapListByTT = null;
	// private Bitmap visibleBtnBitmap = null;
	private GestureDetector brushGestureDetector = null;
	private BrushGestureListener brushGestureListener = null;
	private DrawAttribute.DrawStatus drawStatus;
	private TouchLayer touchLayer;
	// private UndoAndRedo undoAndRedo;
	private Context context;
	private boolean isCanDraw = false;
	private FraPublish fraPublish;

	private int mHeight;
	private int mWidth;

	public DrawView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		this.context = context;
		if (null == reloadingListener) {
			initView();
		}
		new Thread(this).start();
	}

	public void initView() {
		System.out.println("initView");
		backgroundBitmap = DrawAttribute.getImageFromAssetsFile(context, "bigpaper00.jpg", true);
		backgroundBitmapLeftTopP = new PointF(0, 0);
		if (mHeight != 0 && mWidth != 0) {
			paintBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
		} else {
			paintBitmap = Bitmap.createBitmap(DrawAttribute.screenWidth_out,
					DrawAttribute.screenHeight_out, Bitmap.Config.ARGB_8888);
		}
		System.out.println("w:" + DrawAttribute.screenWidth_out + " h"
				+ DrawAttribute.screenHeight_out);
		paintCanvas = new Canvas(paintBitmap);
		paintCanvas.drawARGB(0, 255, 255, 255);
		stickerBitmapList = new StickerBitmapList(this);
		stickerBitmapListByTT = new StickerBitmapListByTT(this);
		this.drawStatus = DrawAttribute.DrawStatus.CASUAL_WATER;
		// undoAndRedo = new UndoAndRedo();
		// visibleBtnBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.drawvisiblebtn))
		// .getBitmap();
		brushGestureListener = new BrushGestureListener(
				casualStroke(R.drawable.marker, Color.BLACK), 2, null);
		brushGestureDetector = new GestureDetector(brushGestureListener);
		if (reloadingListener != null) {
			reloadingListener.onReLoding();
		}
	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		// if (mHeight == 0 || mWidth == 0) {
		// mHeight = getHeight();
		// mWidth = getWidth();
		// initView();
		// }
		System.out.println("h:" + getHeight());
		System.out.println("w:" + getWidth());
	}

	public FraPublish getFraPublish() {
		return fraPublish;
	}

	public void setFraPublish(FraPublish fraPublish) {
		this.fraPublish = fraPublish;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(backgroundBitmap, backgroundBitmapLeftTopP.x, backgroundBitmapLeftTopP.y,
				null);
		if (null != paintBitmap) {
			canvas.drawBitmap(paintBitmap, 0, 0, null);
		}
		if (basicGeometry != null) {
			basicGeometry.drawGraphic(canvas);
		}
		stickerBitmapList.drawStickerBitmapList(canvas);
		stickerBitmapListByTT.drawStickerBitmapList(canvas);
		// canvas.drawBitmap(visibleBtnBitmap, DrawAttribute.screenWidth - VISIBLE_BTN_WIDTH, 0, null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();
			// 判断是否点击了隐形按钮
			if (isClickOnVisibleBtn(x, y)) {
				touchLayer = TouchLayer.VISIBLE_BTN;
				if (null != fraPublish) {
					fraPublish.setUpAndButtomBarVisible(true);
				} else {
					((BaseActivity) context).setUpAndButtomBarVisible(true);
				}
				return true;
			} else {
				if (null != fraPublish) {
					fraPublish.setUpAndButtomBarVisible(false);
				} else {
					((BaseActivity) context).setUpAndButtomBarVisible(false);
				}
			}
			int touchType = stickerBitmapList.getOnTouchType(x, y);
			if (touchType == -1) {
				touchType = stickerBitmapListByTT.getOnTouchType(x, y);
			}
			switch (touchType) {
			case 1:
				touchLayer = TouchLayer.STICKER_TOOL;
				return true;// 点击了贴图的工具
			case 0:
				touchLayer = TouchLayer.STICKER_BITMAP;
				break;// 点击了贴图
			case 3:
				touchLayer = TouchLayer.STICKER_TEXT_TOOL;
				return true;// 点击了Text贴图的工具
			case 2:
				touchLayer = TouchLayer.STICKER_TEXT;
				if (stickerBitmapListByTT.isTouchEditText()) {
					System.out.println("EEEEEE");
					setEditTextVisible(true);
				}

				break;// 点击了Text贴图
			case -1:
				if (basicGeometry != null
						&& basicGeometry.isPointInsideGeometry(event.getX(), event.getY())) {
					touchLayer = TouchLayer.GEOMETRY_LAYER;// 点击的是几何图形
					System.out.println("点击的是几何图形");
				} else {
					touchLayer = TouchLayer.PAINT_LAYER;// 点击的是绘图层
					System.out.println("点击的是绘图层");
				}
			}
		}
		if (touchLayer != TouchLayer.STICKER_TEXT) {
			setEditTextVisible(false);
		}
		// 绘图层的监听
		if (touchLayer == TouchLayer.PAINT_LAYER) {
			if (isCanDraw) {
				brushGestureDetector.onTouchEvent(event);
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// undoAndRedo.addBitmap(paintBitmap);
				}
			}
		}
		// 几何图形的监听
		else if (touchLayer == TouchLayer.GEOMETRY_LAYER) {
			basicGeometry.onTouchEvent(event);
		}
		// 贴图的监听
		else if (touchLayer == TouchLayer.STICKER_BITMAP) {
			stickerBitmapList.onTouchEvent(event);
		} else if (touchLayer == TouchLayer.STICKER_TEXT) {
			stickerBitmapListByTT.onTouchEvent(event);
		}
		return true;
	}

	public void setEditTextVisibleStucker(boolean isVisible) {
		stickerBitmapListByTT.setTouchEditText(isVisible);
	}

	public void setEditTextVisible(boolean isVisible) {
		if (null != fraPublish) {
		} else {
			((BaseActivity) context).setEditViewVisible(isVisible);
		}
	}

	// private boolean isClickOnVisibleBtn(float x, float y) {
	// if (x > DrawAttribute.screenWidth - VISIBLE_BTN_WIDTH && x < DrawAttribute.screenWidth
	// && y < VISIBLE_BTN_HEIGHT) {
	// return true;
	// }
	// return false;
	// }
	private boolean isClickOnVisibleBtn(float x, float y) {
		System.out.println("x:" + x + " y:" + y + "  "
				+ (DrawAttribute.screenHeight - VISIBLE_BTN_HEIGHT));
		if (y + VISIBLE_TITLE_HEIGHT > DrawAttribute.screenHeight - VISIBLE_BTN_HEIGHT) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			postInvalidate();
		}
	}

	public Bitmap setBackgroundBitmap(Bitmap bitmap, boolean isFromSystem) {
		if (isFromSystem) {
			backgroundBitmap = bitmap;
			backgroundBitmapLeftTopP.set(0, 0);
			return bitmap;
		} else {
			float scaleWidth;
			float scaleHeight;

			if (mWidth != 0 && mHeight != 0) {
				scaleWidth = bitmap.getWidth() * 1.0f / mWidth;
				scaleHeight = bitmap.getHeight() * 1.0f / mHeight;
			} else {
				scaleWidth = bitmap.getWidth() * 1.0f / DrawAttribute.screenWidth_out;
				scaleHeight = bitmap.getHeight() * 1.0f / DrawAttribute.screenHeight_out;

			}

			float scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
			if (scale > 0)
				backgroundBitmap = Bitmap.createScaledBitmap(bitmap,
						(int) (bitmap.getWidth() / scale), (int) (bitmap.getHeight() / scale),
						false);
			else {
				backgroundBitmap = bitmap;
			}
			if (mWidth != 0 && mHeight != 0) {

				backgroundBitmapLeftTopP.x = (mWidth - backgroundBitmap.getWidth()) / 2;
				backgroundBitmapLeftTopP.y = (mHeight - backgroundBitmap.getHeight()) / 2;
			} else {

				backgroundBitmapLeftTopP.x = (DrawAttribute.screenWidth_out - backgroundBitmap
						.getWidth()) / 2;
				backgroundBitmapLeftTopP.y = (DrawAttribute.screenHeight_out - backgroundBitmap
						.getHeight()) / 2;
			}
			System.out.println("BBBB:" + bitmap);
			return bitmap;

		}
	}

	public void cleanPaintBitmap() {
		paintCanvas.drawColor(0xffffffff, Mode.DST_OUT);
	}

	public void recordPaintBitmap(Bitmap bitmap) {
		// undoAndRedo.addBitmap(bitmap);
	}

	public void undo() {
		// if (!undoAndRedo.currentIsFirst()) {
		// undoAndRedo.undo(paintBitmap);
		// }
	}

	public void redo() {
		// if (!undoAndRedo.currentIsLast()) {
		// undoAndRedo.redo(paintBitmap);
		// }
	}

	public void setBasicGeometry(BasicGeometry geometry) {
		if (basicGeometry != null) {
			basicGeometry.drawGraphic(paintCanvas);
		}
		this.basicGeometry = geometry;
	}

	public void addStickerBitmap(Bitmap bitmap) {
		// 增加贴图s
		stickerBitmapList.setIsStickerToolsDraw(false, null);
		if (!stickerBitmapList.addStickerBitmap(new StickerBitmap(this, stickerBitmapList, bitmap))) {
			Toast.makeText(context, "贴图太多了！", Toast.LENGTH_SHORT).show();
		}
	}

	public void deleteStickerBitmap(Bitmap bitmap) {
		// 删除贴图
		stickerBitmapList.setIsStickerToolsDraw(false, null);
		stickerBitmapList.deleteOnTouchStickerBitmap();
	}

	public void addStickerBitmapTT(String content, Paint paint) {
		// 增加贴图s
		stickerBitmapListByTT.setIsStickerToolsDraw(false, null);
		if (!stickerBitmapListByTT.addStickerBitmap(new TextViewStickerBitmap(this,
				stickerBitmapListByTT, paint, content))) {
			// (new StickerBitmap(this, stickerBitmapList, bitmap))) {
			Toast.makeText(context, "贴图太多了！", Toast.LENGTH_SHORT).show();
		}
	}

	public void editStickerBitmapTT_now(Paint paint, String content) {
		stickerBitmapListByTT.editonTouchTextView(paint, content);
		// 编辑当前文字
	}

	public void editStickerBitmapTT_Paint_now(Paint paint) {
		stickerBitmapListByTT.editonTouchPaint(paint);
		// 编辑当前文字
	}

	public Canvas getPaintCanvas() {
		return paintCanvas;
	}

	private Bitmap casualStroke(int drawableId, int color) {
		Bitmap mode = ((BitmapDrawable) this.getResources().getDrawable(drawableId)).getBitmap();
		Bitmap bitmap = mode.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas();
		canvas.setBitmap(bitmap);
		Paint paintUnder = new Paint();
		paintUnder.setColor(color);
		canvas.drawPaint(paintUnder);
		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		canvas.drawBitmap(mode, 0, 0, paint);
		return bitmap;
	}

	public boolean isCanDraw() {
		return isCanDraw;
	}

	public void setCanDraw(boolean isCanDraw) {
		this.isCanDraw = isCanDraw;
	}

	/**
	 * 设置画笔的颜色
	 * @param drawStatus
	 * @param extraData
	 */
	public void setBrushBitmap(DrawAttribute.DrawStatus drawStatus, int extraData) {
		this.drawStatus = drawStatus;
		Bitmap brushBitmap = null;
		int brushDistance = 0;
		Paint brushPaint = null;
		// 水彩笔颜色
		if (drawStatus == DrawAttribute.DrawStatus.CASUAL_WATER) {
			brushBitmap = casualStroke(R.drawable.marker, extraData);
			brushDistance = 1;
			brushPaint = null;
		}
		// else if (drawStatus == DrawAttribute.DrawStatus.CASUAL_CRAYON) {
		// brushBitmap = casualStroke(R.drawable.crayon, extraData);
		// brushDistance = brushBitmap.getWidth() / 2;
		// brushPaint = null;
		// } else if (drawStatus == DrawAttribute.DrawStatus.CASUAL_COLOR_SMALL) {
		// brushBitmap = casualStroke(R.drawable.paintcopy, extraData);
		// brushDistance = 3;
		// brushPaint = null;
		// } else if (drawStatus == DrawAttribute.DrawStatus.CASUAL_COLOR_BIG) {
		// brushBitmap = casualStroke(R.drawable.paint, extraData);
		// brushDistance = 2;
		// brushPaint = null;
		// }
		// else // if(drawStatus == DrawAttribute.DrawStatus.ERASER)
		// {
		// brushPaint = new Paint();
		// brushPaint.setFilterBitmap(true);
		// brushPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		// switch (extraData) {
		// case 0:
		// brushBitmap = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.eraser))
		// .getBitmap();
		// break;
		// case 1:
		// brushBitmap = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.eraser1))
		// .getBitmap();
		// break;
		// default:
		// brushBitmap = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.eraser2))
		// .getBitmap();
		// break;
		// }
		// brushDistance = brushBitmap.getWidth() / 4;
		// }
		brushGestureListener.setBrush(brushBitmap, brushDistance, brushPaint);
	}

	public void setStampBitmaps(DrawAttribute.DrawStatus drawStatus, int color) {
		Bitmap[] brushBitmaps = new Bitmap[4];
		switch (drawStatus) {
		case STAMP_HEART:
			brushBitmaps[0] = casualStroke(R.drawable.stamp0heart, color);
			brushBitmaps[1] = casualStroke(R.drawable.stamp1heart, color);
			brushBitmaps[2] = casualStroke(R.drawable.stamp2heart, color);
			brushBitmaps[3] = casualStroke(R.drawable.stamp3heart, color);
			break;
		case STAMP_STAR:
			brushBitmaps[0] = casualStroke(R.drawable.stamp0star, color);
			brushBitmaps[1] = casualStroke(R.drawable.stamp1star, color);
			brushBitmaps[2] = casualStroke(R.drawable.stamp2star, color);
			brushBitmaps[3] = casualStroke(R.drawable.stamp3star, color);
			break;
		case STAMP_BUBBLE:
			brushBitmaps[0] = casualStroke(R.drawable.stamp0bubble, color);
			brushBitmaps[1] = casualStroke(R.drawable.stamp1bubble, color);
			brushBitmaps[2] = casualStroke(R.drawable.stamp2bubble, color);
			brushBitmaps[3] = casualStroke(R.drawable.stamp3bubble, color);
			break;
		case STAMP_DOTS:
			brushBitmaps[0] = casualStroke(R.drawable.stamp0dots, color);
			brushBitmaps[1] = casualStroke(R.drawable.stamp1dots, color);
			brushBitmaps[2] = casualStroke(R.drawable.stamp2dots, color);
			brushBitmaps[3] = casualStroke(R.drawable.stamp3dots, color);
			break;
		}
		brushGestureListener.setStampBrush(brushBitmaps);
		this.drawStatus = drawStatus;
	}

	class BrushGestureListener extends GestureDetector.SimpleOnGestureListener {

		private Bitmap brushBitmap = null;
		private int brushDistance;
		private int halfBrushBitmapWidth;
		private Paint brushPaint = null;
		// 印花的bitmap
		private Bitmap[] stampBrushBitmaps = null;
		private boolean isStamp;

		public BrushGestureListener(Bitmap brushBitmap, int brushDistance, Paint brushPaint) {
			super();
			setBrush(brushBitmap, brushDistance, brushPaint);
			isStamp = false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			switch (drawStatus) {
			case CASUAL_WATER:
			case CASUAL_CRAYON:
			case CASUAL_COLOR_SMALL:
			case CASUAL_COLOR_BIG:
			case ERASER:
				isStamp = false;
				break;
			case STAMP_BUBBLE:
			case STAMP_DOTS:
			case STAMP_HEART:
			case STAMP_STAR:
				isStamp = true;
				break;
			}
			if (!isStamp) {
				paintCanvas.drawBitmap(brushBitmap, e.getX() - halfBrushBitmapWidth, e.getY()
						- halfBrushBitmapWidth, brushPaint);
			} else {
				paintSingleStamp(e.getX(), e.getY());
			}
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (!isStamp) {
				float beginX = e2.getX();
				float beginY = e2.getY();
				float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
				float x = distanceX / distance, x_ = 0;
				float y = distanceY / distance, y_ = 0;
				while (Math.abs(x_) <= Math.abs(distanceX) && Math.abs(y_) <= Math.abs(distanceY)) {
					x_ += x * brushDistance;
					y_ += y * brushDistance;
					paintCanvas.save();
					paintCanvas.rotate((float) (Math.random() * 10000), beginX + x_, beginY + y_);
					paintCanvas.drawBitmap(brushBitmap, beginX + x_ - halfBrushBitmapWidth, beginY
							+ y_ - halfBrushBitmapWidth, brushPaint);
					paintCanvas.restore();
				}
			} else {
				paintSingleStamp(e2.getX(), e2.getY());
			}
			return true;
		}

		public void setBrush(Bitmap brushBitmap, int brushDistance, Paint brushPaint) {
			this.brushBitmap = brushBitmap;
			this.brushDistance = brushDistance;
			halfBrushBitmapWidth = brushBitmap.getWidth() / 2;
			this.brushPaint = brushPaint;
		}

		public void setStampBrush(Bitmap[] brushBitmaps) {
			this.stampBrushBitmaps = brushBitmaps;
			halfBrushBitmapWidth = brushBitmaps[0].getWidth() / 2;
		}

		private void paintSingleStamp(float x, float y) {
			if (Math.random() > 0.1) {
				paintCanvas.drawBitmap(stampBrushBitmaps[0], x - halfBrushBitmapWidth, y
						- halfBrushBitmapWidth, null);
			}
			for (int i = 1; i < stampBrushBitmaps.length; i++)
				if (Math.random() > 0.3) {
					paintCanvas.drawBitmap(stampBrushBitmaps[i], x - halfBrushBitmapWidth, y
							- halfBrushBitmapWidth, null);
				}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		System.out.println("onMeasureww:" + mWidth + " onMeasurehh:" + mHeight);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		if (mWidth == 0 || mHeight == 0) {
			int ww = getWidth();
			int hh = getHeight();
			int cw = ww - DrawAttribute.screenWidth_out_2;
			int ch = hh - DrawAttribute.screenHeight_out_2;
			if (cw < ch) {
				mWidth = ww;
				mHeight = (int) (ww / DrawAttribute.screenWidthSclHeight);
			} else {
				mHeight = hh;
				mWidth = (int) (hh * DrawAttribute.screenWidthSclHeight);
			}
			System.out.println("ww:" + mWidth + " hh:" + mHeight + "  w:" + ww + " h:" + hh);
			setMeasuredDimension(mWidth, mHeight);
			LayoutParams parmas = getLayoutParams();
			parmas.width = mWidth;
			parmas.height = mHeight;
			setLayoutParams(parmas);
			initView();
		}
		System.out.println("D_w:" + getWidth());
		System.out.println("D_H:" + getHeight());
		System.out.println("W:" + DrawAttribute.screenWidth_out);
		System.out.println("H:" + DrawAttribute.screenHeight_out);
	}

	public DetailImages saveBitmap() {
		return saveBitmap(false);
	}

	public DetailImages saveBitmap(boolean isOutShare) {
		Bitmap bitmap;
		if (isOutShare) {
			bitmap = Bitmap.createBitmap(DrawAttribute.screenWidth_out,
					DrawAttribute.screenHeight_out, Bitmap.Config.ARGB_8888);
		} else {
			if (mHeight != 0 && mWidth != 0) {
				bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
			} else {
				bitmap = Bitmap.createBitmap(DrawAttribute.screenWidth_out,
						DrawAttribute.screenHeight_out, Bitmap.Config.ARGB_8888);
			}

		}
		System.out.println("mWidth:" + mWidth + " mHeight:" + mHeight);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.WHITE);
		if (!backgroundBitmap.isRecycled()) {
			canvas.drawBitmap(backgroundBitmap, backgroundBitmapLeftTopP.x,
					backgroundBitmapLeftTopP.y, null);
		}
		if (null != paintBitmap && !paintBitmap.isRecycled()) {
			canvas.drawBitmap(paintBitmap, 0, 0, null);
		}
		if (basicGeometry != null) {
			basicGeometry.drawGraphic(canvas);
		}
		stickerBitmapList.setStickerToolsDraw(false);
		stickerBitmapListByTT.setStickerToolsDraw(false);
		stickerBitmapList.drawStickerBitmapList(canvas);
		stickerBitmapListByTT.drawStickerBitmapList(canvas);
		stickerBitmapList.deleteOnTouchStickerBitmap();
		stickerBitmapListByTT.deleteOnTouchStickerBitmap();
		setBackgroundBitmap(bitmap, false);
		// Matrix matrix = new Matrix();
		// float sx = bitmap.getWidth() / (float) DrawAttribute.screenWidth_out_2;
		// float sy = bitmap.getHeight() / (float) DrawAttribute.screenHeight_out_2;
		// matrix.setScale(sx, sy);
		// Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
		// matrix, true);
		// bitmap.recycle();
		return StorageInSDCard.saveBitmapInExternalStorage(bitmap, context);
	}

	public interface ReloadingListener {
		void onReLoding();
	}

	public ReloadingListener getReloadingListener() {
		return reloadingListener;
	}

	public void setReloadingListener(ReloadingListener reloadingListener) {
		this.reloadingListener = reloadingListener;
	}

	public void freeBitmaps() {
		backgroundBitmap.recycle();
		if (null != paintBitmap) {
			paintBitmap.recycle();
		}
		paintBitmap = null;
		stickerBitmapList.freeBitmaps();
		stickerBitmapListByTT.freeBitmaps();
		// undoAndRedo.freeBitmaps();
	}

	public void freeBitmaps2() {
		paintBitmap.recycle();
		stickerBitmapList.freeBitmaps();
		stickerBitmapListByTT.freeBitmaps();
	}
}
