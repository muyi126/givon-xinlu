package com.govin.draw.sticker;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.givon.draw.geometry.BasicGeometry;

public class TextViewStickerBitmap extends BasicGeometry {
	// public Bitmap bitmap;
	public String content;
	private boolean isLock;

	private StickerBitmapListByTT stickerBitmapList;
	// private float[] vertexs;
	// private float[] vertexs_dst;
	private RectF rectF;
	// private RectF preRectF;
	private float centerPointX;
	private float centerPointY;
	private float width_dst;
	private float height_dst;
	private float rotation;
	private float[] points;

	// public TextViewStickerBitmap(View containter, StickerBitmapList stickerBitmapList, Bitmap bitmap) {
	// this.bitmap = bitmap;
	// bitmapMatrix = new Matrix();
	// onDownMatrix = new Matrix();
	// onMoveMatrix = new Matrix();
	// onDownZoomMidPoint = new PointF();
	// this.containter = containter;
	// this.stickerBitmapList = stickerBitmapList;
	// this.isLock = false;
	// }
	public TextViewStickerBitmap(View containter, StickerBitmapListByTT stickerBitmapList,
			Paint paint, String content) {
		super(paint);
		// paint.setColor(Color.BLACK);
		// vertexs = new float[6];
		// vertexs_dst = new float[6];
		this.stickerBitmapList = stickerBitmapList;
		this.isLock = false;
		this.content = content;
		rectF = new RectF();
		points = new float[8];
		points[0] = 0;
		points[1] = 0;
		points[2] = 0;
		points[3] = height;
		points[4] = width;
		points[5] = height;
		points[6] = width;
		points[7] = 0;
		geometryMatrix.mapPoints(points);
		centerPointX = (points[0] + points[4]) / 2;
		centerPointY = (points[1] + points[5]) / 2;
		PointF frocPoint = new PointF();
		frocPoint.set(centerPointX, centerPointY);
		stickerBitmapList.setFocusPoint(frocPoint);
		stickerBitmapList.setIsFocusToolsDraw(true);
	}

	public boolean isLock() {
		return isLock;
	}

	public void setLock() {
		if (isLock)
			isLock = false;
		else
			isLock = true;
	}

	public void setEditView(Paint paint, String content) {
		this.paint = paint;
		this.content = content;
	}

	void drawText(Canvas canvas, String text, float x, float y, Paint paint, float angle) {
		if (angle != 0) {
			canvas.rotate(angle, x, y);
		}
		canvas.drawText(text, x, y, paint);
		if (angle != 0) {
			canvas.rotate(-angle, x, y);
		}
	}

	@Override
	public void drawGraphic(Canvas canvas) {
		canvas.save();
		points = new float[8];
		points[0] = 0;
		points[1] = 0;
		points[2] = 0;
		points[3] = height;
		points[4] = width;
		points[5] = height;
		points[6] = width;
		points[7] = 0;
		geometryMatrix.mapPoints(points);
//		Path path = new Path();
//		path.reset();
//		path.moveTo(points[0], points[1]);
//		path.lineTo(points[2], points[3]);
//		path.lineTo(points[4], points[5]);
//		path.lineTo(points[6], points[7]);
//		path.close();
//		canvas.drawPath(path, paint);

		centerPointX = (points[0] + points[4]) / 2;
		centerPointY = (points[1] + points[5]) / 2;
		rotation = rotation(points[0], points[1], points[2], points[3]);
		width_dst = (float) Math.sqrt((points[3] - points[1]) * (points[3] - points[1])
				+ (points[2] - points[0]) * (points[2] - points[0]));
		height_dst = (float) Math.sqrt((points[5] - points[3]) * (points[5] - points[3])
				+ (points[4] - points[2]) * (points[4] - points[2]));
		rectF.set(centerPointX - width_dst / 2, centerPointY - height_dst / 2, centerPointX
				+ width_dst / 2, centerPointY + height_dst / 2);
		paint.setTextSize(height_dst / 2);
		// paint.setColor(Color.BLUE);
		PointF pointF = new PointF();
		getMiderPointF(pointF);
		canvas.rotate(rotation, points[0], points[1]);
		canvas.drawText(content, pointF.x, pointF.y, paint);
		canvas.restore();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			stickerBitmapList.setIsFocusToolsDraw(false);
			if (!isLock) {
				mode = DRAG;
				lastXDrag = event.getX();
				lastYDrag = event.getY();
				onDownMatrix.set(geometryMatrix);
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			stickerBitmapList.setIsFocusToolsDraw(false);
			if (!isLock) {
				mode = ZOOM;
				onDownZoomDist = spacing(event);
				onDownZoomRotation = rotation(event.getX(0), event.getY(0), event.getX(1),
						event.getY(1));
				onDownMatrix.set(geometryMatrix);
				midPoint(onDownZoomMidPoint, event);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == ZOOM) {
				onMoveMatrix.set(onDownMatrix);
				float rotation = rotation(event.getX(0), event.getY(0), event.getX(1),
						event.getY(1))
						- onDownZoomRotation;
				float newDist = spacing(event);
				float scale = newDist / onDownZoomDist;
				onMoveMatrix.postScale(scale, scale, onDownZoomMidPoint.x, onDownZoomMidPoint.y);
				onMoveMatrix.postRotate(rotation, onDownZoomMidPoint.x, onDownZoomMidPoint.y);
				// System.out.println("ACTION_POINTER_DOWN:"+rotation);
				// if (matrixCheck(onMoveMatrix) == false) {
				geometryMatrix.set(onMoveMatrix);
				// containter.postInvalidate();
				// }
			} else if (mode == DRAG) {
				onMoveMatrix.set(onDownMatrix);
				onMoveMatrix.postTranslate(event.getX() - lastXDrag, event.getY() - lastYDrag);
				// if (matrixCheck(onMoveMatrix) == false) {
				geometryMatrix.set(onMoveMatrix);
				// containter.postInvalidate();
				// }
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			PointF leftTopPoint = new PointF();
			PointF frocPoint = new PointF();
			frocPoint.set(centerPointX, centerPointY);
			this.getLeftTopPointF(leftTopPoint);
			stickerBitmapList.setIsStickerToolsDraw(true, leftTopPoint, frocPoint);
			mode = NONE;
			break;
		}
		return true;
	}

	// 包含贴图的最小矩形的左上角
	public void getLeftTopPointF(PointF focesRect) {
		float[] points = new float[8];
		points[0] = 0;
		points[1] = 0;
		points[2] = 0;
		points[3] = height;
		points[4] = width;
		points[5] = height;
		points[6] = width;
		points[7] = 0;
		geometryMatrix.mapPoints(points);
		float leftTopX = points[0];
		float leftTopY = points[1];
		for (int i = 2; i < 8; i += 2) {
			if (points[i] < leftTopX)
				leftTopX = points[i];
		}
		for (int i = 1; i < 8; i += 2) {
			if (points[i] < leftTopY)
				leftTopY = points[i];
		}
		focesRect.set(leftTopX, leftTopY);
	}

	// 包含贴图的最小矩形的左上角
	public void getMiderPointF(PointF focesRect) {
		float[] points = new float[8];
		points[0] = 0;
		points[1] = 0;
		points[2] = 0;
		points[3] = height;
		points[4] = width;
		points[5] = height;
		points[6] = width;
		points[7] = 0;
		geometryMatrix.mapPoints(points);
		float leftTopX = points[0];
		float leftTopY = points[1];
		float rightBottomX = points[0];
		float rightBottomY = points[1];
		for (int i = 2; i < 8; i += 2) {
			if (points[i] < leftTopX)
				leftTopX = points[i];
		}
		for (int i = 1; i < 8; i += 2) {
			if (points[i] < leftTopY)
				leftTopY = points[i];
		}
		for (int i = 2; i < 8; i += 2) {
			if (points[i] > rightBottomX)
				rightBottomX = points[i];
		}
		for (int i = 1; i < 8; i += 2) {
			if (points[i] > rightBottomY)
				rightBottomY = points[i];
		}
		focesRect.set((rightBottomX+leftTopX)/2, (rightBottomY+leftTopY)/2);
	}

	// 触碰两点间距离
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	@Override
	protected float rotation(float x0, float y0, float x1, float y1) {
		double delta_x = (x0 - x1);
		double delta_y = (y0 - y1);
		double radians = Math.atan2(delta_y, delta_x);
		return (float) Math.toDegrees(radians) - 270;
	}

	// 取手势中心点
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
}