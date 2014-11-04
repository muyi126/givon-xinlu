package com.govin.draw.sticker;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.givon.baseproject.xinlu.view.DrawView;

public class StickerBitmapList {
	private StickerBitmap[] stickerBitmaps;
	private int capacity;
	private int size;
	
	private int onTouchStickerBitmapIndex;
	
	private StickerTools stickerTools;
	private boolean isStickerToolsDraw;
	private final int TOOLSTOTALDRAWTIME = 75;
	private int stickerToolsDrawTime;
	
	private DrawView container;
	
	public StickerBitmapList(DrawView container) {
		capacity = 15;
		size = 0;
		onTouchStickerBitmapIndex = -1;
		stickerBitmaps = new StickerBitmap[capacity];
		stickerTools = new StickerTools(container,this);
		this.container = container;
		isStickerToolsDraw = false;
		stickerToolsDrawTime = 0;
	}
	
	//往list里添加贴图
	public boolean addStickerBitmap(StickerBitmap stickerBitmap) {
		if(capacity > size) {
			stickerBitmaps[size++] = stickerBitmap;
			onTouchStickerBitmapIndex = size-1;
			return true;
		}
		return false;
	}
	
	//上锁或解锁
	public void setOnTouchStickerBitmapLock() {
		stickerBitmaps[onTouchStickerBitmapIndex].setLock();
	}
	
	//将贴图提到最前
	public void bringOnTouchStickerBitmapToFront() {
		StickerBitmap onTouchStickerBitmap = stickerBitmaps[onTouchStickerBitmapIndex];
		for(int i = onTouchStickerBitmapIndex; i < size - 1 ; i++) {
			stickerBitmaps[i] = stickerBitmaps[i+1];
		}
		stickerBitmaps[size - 1] = onTouchStickerBitmap;
	}
	
	//水平翻转
	public void mirrorStickerBitmap() {
		stickerBitmaps[onTouchStickerBitmapIndex].mirrorTheBitmap();
	}
	
	//将贴图绘制在画布上
	public void drawOnTouchStickerBitmapInCanvas() {
		stickerBitmaps[onTouchStickerBitmapIndex].drawBitmap(container.getPaintCanvas());
		deleteOnTouchStickerBitmap();
	}
	
	//删除贴图
	public void deleteOnTouchStickerBitmap() {
		for(int i = onTouchStickerBitmapIndex; i < size - 1 ; i++) {
			stickerBitmaps[i] = stickerBitmaps[i+1];
		}
		size--;
		onTouchStickerBitmapIndex = size+1;
		isStickerToolsDraw = false;
	}
	
	//绘制所有贴图和工具
	public void drawStickerBitmapList(Canvas canvas) {
		for(int i = 0; i < size; i++) {
			stickerBitmaps[i].drawBitmap(canvas);
		}
		if(isStickerToolsDraw) {
			stickerTools.drawTools(canvas, stickerBitmaps[onTouchStickerBitmapIndex].isLock());
			stickerToolsDrawTime++;
			if(stickerToolsDrawTime >= TOOLSTOTALDRAWTIME) {
				stickerToolsDrawTime = 0;
				isStickerToolsDraw = false;
			}
		}
	}
	
	//设置是否绘制工具
	public void setIsStickerToolsDraw(boolean isStickerToolsDraw, PointF leftTopPoint) {
		this.isStickerToolsDraw = isStickerToolsDraw;
		if(isStickerToolsDraw) {
			stickerTools.setStartLeftTop(leftTopPoint);
			stickerToolsDrawTime = 0;
		}
	}

	//监听onTouch事件
	public void onTouchEvent(MotionEvent event) {
		stickerBitmaps[onTouchStickerBitmapIndex].onTouchEvent(event);
	}
	
    //返回点击贴图的下标,1表示贴图工具栏,0表示贴图,-1表示画布
	public int getOnTouchType(float x, float y) {
		if(isStickerToolsDraw && stickerTools.setOnTouchEvent(x, y)) {
			return 1;
		}
		for(int i = size - 1; i >=0; i--) {
			if(stickerBitmaps[i].isPointInsideBitmap(x, y)){
				onTouchStickerBitmapIndex = i;
				return 0;		
			}
		}
		return -1;
	}
	
	
	
	public boolean isStickerToolsDraw() {
		return isStickerToolsDraw;
	}

	public void setStickerToolsDraw(boolean isStickerToolsDraw) {
		this.isStickerToolsDraw = isStickerToolsDraw;
	}

	public void freeBitmaps() {
		for(int i = 0; i < size; i++) {
			stickerBitmaps[i].bitmap.recycle();
		}
	}
}