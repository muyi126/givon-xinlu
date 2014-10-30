package com.givon.baseproject.draw.util;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.givon.baseproject.draw.util.ColorPickerDialog.ColorChangeListenrt;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.view.DrawView;
import com.givon.draw.geometry.BasicGeometry;
import com.givon.draw.geometry.DiamondGeometry;
import com.givon.draw.geometry.HexagonGeometry;
import com.givon.draw.geometry.LineGeometry;
import com.givon.draw.geometry.OvalGeometry;
import com.givon.draw.geometry.PentagonGeometry;
import com.givon.draw.geometry.RectangleGeometry;
import com.givon.draw.geometry.RightTriangleGeometry;
import com.givon.draw.geometry.RoundedRectangleGeometry;
import com.givon.draw.geometry.StarGeometry;
import com.givon.draw.geometry.TriangleGeometry;

public class TuYaUtil {
	private enum GraphicType{LINE, OVAL, RECTANGLE, ROUNDEDRECTANGLE, TRIANGLE, RIGHTTRIANGLE,
		DIAMOND, PENTAGON, HEXAGON, STAR};
	private View view;
	private Activity activity;
	private DrawView drawView;
	
	private ImageView selectedGraphic;
	private GraphicType graphicType;
	private ImageView selectedColor;
	private SeekBar sizeSeekBar;
	private CheckBox strokeCheckBox;
	private ColorPickerDialog colorPickerDialog;
	
	public TuYaUtil(Activity drawActivity,View view,DrawView drawView){
		this.view = view;
		this.activity =drawActivity;
		this.drawView = drawView;	
		selectedGraphic = (ImageView)view.findViewById(R.id.tuya_line);
		graphicType = GraphicType.LINE;
		selectedGraphic.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
		selectedColor = (ImageView)view.findViewById(R.id.tuyacolorview);
		sizeSeekBar = (SeekBar)view.findViewById(R.id.tuyasizeskb);
		strokeCheckBox = (CheckBox)view.findViewById(R.id.tuyastrokckb);
	    colorPickerDialog = new ColorPickerDialog(activity,selectedColor);
	}
	
	public void tuyaPicSetOnClickListener() {
		ImageView[] graphics = new ImageView[10];
		graphics[0] = (ImageView)view.findViewById(R.id.tuya_line);
		graphics[1] = (ImageView)view.findViewById(R.id.tuya_oval);
		graphics[2] = (ImageView)view.findViewById(R.id.tuya_rectangle);
		graphics[3] = (ImageView)view.findViewById(R.id.tuya_roundedrectangle);
		graphics[4] = (ImageView)view.findViewById(R.id.tuya_triangle);
		graphics[5] = (ImageView)view.findViewById(R.id.tuya_righttriangle);
		graphics[6] = (ImageView)view.findViewById(R.id.tuya_diamond);
		graphics[7] = (ImageView)view.findViewById(R.id.tuya_pentagon);
		graphics[8] = (ImageView)view.findViewById(R.id.tuya_hexagon);
		graphics[9] = (ImageView)view.findViewById(R.id.tuya_star);
		TuYaOnClickListener graphicOnTouchListener = new TuYaOnClickListener(graphics);
		for(int i = 0;i < graphics.length;i++) {
			graphics[i].setOnClickListener(graphicOnTouchListener);
		}	
		Button chooseColorButton = (Button)view.findViewById(R.id.tuyacolorbtn);
		chooseColorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {			
        		colorPickerDialog.getDialog().show();
			}
			
		});	
		Button startDrawingbutton = (Button)view.findViewById(R.id.tuyadrawbtn);
		startDrawingbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {			
				drawView.setBasicGeometry(GetGraphicClass(graphicType));
//				drawView.setBrushBitmap(DrawAttribute.DrawStatus.CASUAL_WATER,colorPickerDialog.getColor());
//				colorPickerDialog.getColor();
//				drawView.addStickerBitmapTT("这个是个测试");
			}
			
		});
		colorPickerDialog.setChangeListenrt(new ColorChangeListenrt() {
			
			@Override
			public void onChange(int color) {
				drawView.setBrushBitmap(DrawAttribute.DrawStatus.CASUAL_WATER,color);
			}
		});
	}
	
	class TuYaOnClickListener implements View.OnClickListener {
		private ImageView[] graphics;
		
		public TuYaOnClickListener(ImageView[] graphics) {
			this.graphics = graphics;
		}

		@Override
		public void onClick(View v) {
			selectedGraphic.setBackgroundColor(0x00000000);
			switch(v.getId()) {
			case R.id.tuya_line:
				selectedGraphic = graphics[0];
				graphicType = GraphicType.LINE;break;
			case R.id.tuya_oval:
				selectedGraphic = graphics[1];
				graphicType = GraphicType.OVAL;break;
			case R.id.tuya_rectangle:
				selectedGraphic = graphics[2];
				graphicType = GraphicType.RECTANGLE;break;
			case R.id.tuya_roundedrectangle:
				selectedGraphic = graphics[3];
				graphicType = GraphicType.ROUNDEDRECTANGLE;break;
			case R.id.tuya_triangle:
				selectedGraphic = graphics[4];
				graphicType = GraphicType.TRIANGLE;break;
			case R.id.tuya_righttriangle:
				selectedGraphic = graphics[5];
				graphicType = GraphicType.RIGHTTRIANGLE;break;
			case R.id.tuya_diamond:
				selectedGraphic = graphics[6];
				graphicType = GraphicType.DIAMOND;break;
			case R.id.tuya_pentagon:
				selectedGraphic = graphics[7];
				graphicType = GraphicType.PENTAGON;break;
			case R.id.tuya_hexagon:
				selectedGraphic = graphics[8];
				graphicType = GraphicType.HEXAGON;break;
			case R.id.tuya_star:
				selectedGraphic = graphics[9];
				graphicType = GraphicType.STAR;
			}
			selectedGraphic.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
		}		
	}
	
	private BasicGeometry GetGraphicClass(GraphicType graphicType) {
		Paint paint = new Paint();
		paint.setColor(colorPickerDialog.getColor());
		paint.setStrokeWidth(sizeSeekBar.getProgress());
		if(strokeCheckBox.isChecked())paint.setStyle(Style.STROKE);
		if(graphicType == GraphicType.LINE)return new LineGeometry(paint);
		if(graphicType == GraphicType.OVAL)return new OvalGeometry(paint);
		if(graphicType == GraphicType.RECTANGLE)return new RectangleGeometry(paint);
		if(graphicType == GraphicType.ROUNDEDRECTANGLE)return new RoundedRectangleGeometry(paint);
		if(graphicType == GraphicType.TRIANGLE)return new TriangleGeometry(paint);
		if(graphicType == GraphicType.RIGHTTRIANGLE)return new RightTriangleGeometry(paint);
		if(graphicType == GraphicType.DIAMOND)return new DiamondGeometry(paint);
		if(graphicType == GraphicType.PENTAGON)return new PentagonGeometry(paint);
		if(graphicType == GraphicType.HEXAGON)return new HexagonGeometry(paint);
		if(graphicType == GraphicType.STAR)return new StarGeometry(paint);
		return null;
	 }
}
