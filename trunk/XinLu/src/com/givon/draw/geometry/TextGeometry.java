package com.givon.draw.geometry;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class TextGeometry extends BasicGeometry {
	private float[] vertexs;
	private float[] vertexs_dst;
	private String content;
	private boolean isLock;

	private View containter;

	public TextGeometry(View view, Paint paint) {
		super(paint);
		vertexs = new float[4];
		vertexs_dst = new float[4];
		vertexs[0] = 0;
		vertexs[1] = 0;
		vertexs[2] = width;
		vertexs[3] = height;
		containter = view;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void drawGraphic(Canvas canvas) {
		geometryMatrix.mapPoints(vertexs_dst, vertexs);
		if (null != content) {
			canvas.drawText(content, 0, content.length(), vertexs_dst[0], vertexs_dst[1], paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean isc = super.onTouchEvent(event);
		return isc;

	}

}
