package com.govin.draw.sticker;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.givon.baseproject.xinlu.view.DrawView;

public class StickerBitmapListByTT {
	private TextViewStickerBitmap[] stickerBitmaps;
	private int capacity;
	private int size;

	private int onTouchStickerBitmapIndex;

	private StickerToolsByTT stickerTools;
	private boolean isStickerToolsDraw;
	private boolean isFocusToolsDraw;
	private final int TOOLSTOTALDRAWTIME = 75;
	private int stickerToolsDrawTime;

	private DrawView container;

	public StickerBitmapListByTT(DrawView container) {
		capacity = 15;
		size = 0;
		onTouchStickerBitmapIndex = -1;
		stickerBitmaps = new TextViewStickerBitmap[capacity];
		stickerTools = new StickerToolsByTT(container, this);
		this.container = container;
		isStickerToolsDraw = false;
		isFocusToolsDraw = false;
		stickerToolsDrawTime = 0;
	}

	// 往list里添加贴图
	public boolean addStickerBitmap(TextViewStickerBitmap stickerBitmap) {
		if (capacity > size) {
			stickerBitmaps[size++] = stickerBitmap;
			return true;
		}
		return false;
	}

	// 上锁或解锁
	public void setOnTouchStickerBitmapLock() {
		stickerBitmaps[onTouchStickerBitmapIndex].setLock();
	}

	// 将贴图提到最前
	public void bringOnTouchStickerBitmapToFront() {
		TextViewStickerBitmap onTouchStickerBitmap = stickerBitmaps[onTouchStickerBitmapIndex];
		for (int i = onTouchStickerBitmapIndex; i < size - 1; i++) {
			stickerBitmaps[i] = stickerBitmaps[i + 1];
		}
		stickerBitmaps[size - 1] = onTouchStickerBitmap;
	}
	
	public void editonTouchTextView(Paint paint,String content){
		if(null!=stickerBitmaps&&stickerBitmaps.length>onTouchStickerBitmapIndex&&onTouchStickerBitmapIndex>=0){
			stickerBitmaps[onTouchStickerBitmapIndex].setEditView(paint, content);
		}
	}

	// 水平翻转
//	public void mirrorStickerBitmap() {
//		stickerBitmaps[onTouchStickerBitmapIndex].mirrorTheBitmap();
//	}

	// 将贴图绘制在画布上
	public void drawOnTouchStickerBitmapInCanvas() {
		stickerBitmaps[onTouchStickerBitmapIndex].drawGraphic(container.getPaintCanvas());
		deleteOnTouchStickerBitmap();
	}

	// 删除贴图
	public void deleteOnTouchStickerBitmap() {
		for (int i = onTouchStickerBitmapIndex; i < size - 1; i++) {
			stickerBitmaps[i] = stickerBitmaps[i + 1];
		}
		size--;
		isStickerToolsDraw = false;
		isFocusToolsDraw = false;
	}

	// 绘制所有贴图和工具
	public void drawStickerBitmapList(Canvas canvas) {
		for (int i = 0; i < size; i++) {
			stickerBitmaps[i].drawGraphic(canvas);
		}
		if (isStickerToolsDraw) {
			stickerTools.drawTools(canvas, stickerBitmaps[onTouchStickerBitmapIndex].isLock());
			stickerToolsDrawTime++;
			if (stickerToolsDrawTime >= TOOLSTOTALDRAWTIME) {
				stickerToolsDrawTime = 0;
				isStickerToolsDraw = false;
				isFocusToolsDraw = true;
			}
		}
		if (isFocusToolsDraw) {
			stickerTools.drawFocusTools(canvas);
		}
	}

	// 设置是否绘制工具
	public void setIsStickerToolsDraw(boolean isStickerToolsDraw, PointF leftTopPoint,
			PointF frocePoint) {
		this.isStickerToolsDraw = isStickerToolsDraw;

		if (isStickerToolsDraw) {
			stickerTools.setStartLeftTop(leftTopPoint, frocePoint);
			stickerToolsDrawTime = 0;
		}
	}
	
	public void setFocusPoint(PointF frocePoint){
		stickerTools.setFocusPoint(frocePoint);
	}

	public void setIsFocusToolsDraw(boolean isFocusToolsDraw) {
		this.isFocusToolsDraw = isFocusToolsDraw;
	}

	// 监听onTouch事件
	public void onTouchEvent(MotionEvent event) {
		stickerBitmaps[onTouchStickerBitmapIndex].onTouchEvent(event);
	}

	// 返回点击贴图的下标,3表示贴图工具栏,2表示贴图,-1表示画布
	public int getOnTouchType(float x, float y) {
		if (isStickerToolsDraw && stickerTools.setOnTouchEvent(x, y)) {
			return 3;
		}
		for (int i = size - 1; i >= 0; i--) {
			if (stickerBitmaps[i].isPointInsideGeometry(x, y)) {
				onTouchStickerBitmapIndex = i;
				return 2;
			}
		}
		return -1;
	}

	public void freeBitmaps() {
		// for(int i = 0; i < size; i++) {
		// stickerBitmaps[i].bitmap.recycle();
		// }
	}
}