package com.givon.draw.geometry;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

public abstract class BasicGeometry {
	protected static final int NONE = 0;
	protected static final int DRAG = 1;
	protected static final int ZOOM = 2;
	private static final int RESIZE = 3;
	protected int mode;
	protected float lastXDrag;
	protected float lastYDrag;
	protected PointF onDownZoomMidPoint;
	protected float onDownZoomDist;
	protected float onDownZoomRotation = 0;
	protected Matrix onDownMatrix;
	protected Matrix onMoveMatrix;

	protected float width;
	protected float height;
	protected Matrix geometryMatrix;
	protected Paint paint;

	public BasicGeometry(Paint paint) {
		width = 256;
		height = 256;
		geometryMatrix = new Matrix();
		onDownMatrix = new Matrix();
		onMoveMatrix = new Matrix();
		onDownZoomMidPoint = new PointF();
		this.paint = paint;
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = DRAG;
			lastXDrag = event.getX();
			lastYDrag = event.getY();
			onDownMatrix.set(geometryMatrix);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = ZOOM;
			onDownZoomDist = spacing(event);
			onDownZoomRotation = rotation(event.getX(0), event.getY(0), event.getX(1),
					event.getY(1));
			onDownMatrix.set(geometryMatrix);
			midPoint(onDownZoomMidPoint, event);
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
			mode = NONE;
			break;
		}
		return true;
	}

	// 触碰两点间距离
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	// 取手势中心点
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	// 取旋转角度
	protected float rotation(float x0, float y0, float x1, float y1) {
		double delta_x = (x0 - x1);
		double delta_y = (y0 - y1);
		double radians = Math.atan2(delta_y, delta_x);
		return (float) Math.toDegrees(radians);
	}

	// 判断点是不是在几何图形内
	public boolean isPointInsideGeometry(float x, float y) {
		
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
		System.out.println("points:{"+points[0]+","+points[1]+","+points[2]+","+points[3]+","+points[4]+","+points[5]+","+points[6]+","+points[7]+"}"+  "geometryMatrix"+geometryMatrix.toString());
		float A, B, C;
		for (int i = 0; i < 4; i++) {
			A = -(points[(3 + 2 * i) % 8] - points[1 + 2 * i]);
			B = points[2 * (i + 1) % 8] - points[2 * i];
			C = -(A * points[2 * i] + B * points[1 + 2 * i]);
			if (A * x + B * y + C > 0)
				return false;
		}
		return true;
	}

	public abstract void drawGraphic(Canvas canvas);
}
