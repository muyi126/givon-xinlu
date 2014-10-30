package com.givon.baseproject.draw.util;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.view.DrawView;

public class GeometryUtil {
	private enum TextType {
		SONG, KAI
	};

	private View drawActivity;
	private Activity activity;
	private DrawView drawView;

	private TextView selectedGraphic;
	private TextType textType;
	private ImageView selectedColor;
	private ColorPickerDialog colorPickerDialog;
	private EditText mEditText;

	public GeometryUtil(Activity activity,View view, DrawView drawView) {
		this.drawActivity = view;
		this.activity = activity;
		this.drawView = drawView;
		selectedGraphic = (TextView) view.findViewById(R.id.text_type_1);
		textType = TextType.SONG;
		selectedGraphic.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
		selectedColor = (ImageView) view.findViewById(R.id.graphiccolorview);
		mEditText = (EditText) view.findViewById(R.id.et_content);
		// sizeSeekBar = (SeekBar)drawActivity.findViewById(R.id.graphicsizeskb);
		// strokeCheckBox = (CheckBox)drawActivity.findViewById(R.id.strokckb);
		colorPickerDialog = new ColorPickerDialog(activity, selectedColor);
	}

	public void graphicPicSetOnClickListener() {
		TextView[] graphics = new TextView[4];
		graphics[0] = (TextView) drawActivity.findViewById(R.id.text_type_1);
		graphics[1] = (TextView) drawActivity.findViewById(R.id.text_type_2);
		graphics[2] = (TextView) drawActivity.findViewById(R.id.text_type_3);
		graphics[3] = (TextView) drawActivity.findViewById(R.id.text_type_4);
		GraphicOnClickListener graphicOnTouchListener = new GraphicOnClickListener(graphics);
		for (int i = 0; i < graphics.length; i++) {
			graphics[i].setOnClickListener(graphicOnTouchListener);
		}
		Button chooseColorButton = (Button) drawActivity.findViewById(R.id.graphiccolorbtn);
		chooseColorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				colorPickerDialog.getDialog().show();
			}

		});
		Button startDrawingbutton = (Button) drawActivity.findViewById(R.id.graphicdrawbtn);
		startDrawingbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// drawView.setBasicGeometry(GetGraphicClass(graphicType));
				// colorPickerDialog.getColor();
				if(null!=mEditText.getText()&&mEditText.getText().toString().length()==0){
					ToastUtils.showMessage("内容不能为空");
					return;
				}
				drawView.addStickerBitmapTT(mEditText.getText().toString(),GetTextPaintClass(textType));
			}

		});
		
		Button startDrawingbutton_now = (Button) drawActivity.findViewById(R.id.graphicdrawbtn_now);
		startDrawingbutton_now.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null!=mEditText.getText()&&mEditText.getText().toString().length()==0){
					ToastUtils.showMessage("内容不能为空");
					return;
				}
				drawView.editStickerBitmapTT_now(GetTextPaintClass(textType),mEditText.getText().toString());
			}
			
		});
	}

	class GraphicOnClickListener implements View.OnClickListener {
		private TextView[] graphics;

		public GraphicOnClickListener(TextView[] graphics) {
			this.graphics = graphics;
		}

		@Override
		public void onClick(View v) {
			selectedGraphic.setBackgroundColor(0x00000000);
			switch (v.getId()) {
			case R.id.text_type_1:
				selectedGraphic = graphics[0];
				textType = TextType.SONG;
				break;
			case R.id.text_type_2:
				selectedGraphic = graphics[1];
				textType = TextType.KAI;
				break;
			case R.id.text_type_3:
				selectedGraphic = graphics[2];
				textType = TextType.SONG;
				break;
			case R.id.text_type_4:
				selectedGraphic = graphics[3];
				textType = TextType.SONG;
				break;
			}
			selectedGraphic.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
		}
	}

	private Paint GetTextPaintClass(TextType graphicType) {
		Paint paint = new Paint();
		paint.setColor(colorPickerDialog.getColor());
//		paint.setStrokeWidth(sizeSeekBar.getProgress());
//		if (strokeCheckBox.isChecked())
//			paint.setStyle(Style.STROKE);
//		if (graphicType == GraphicType.LINE)
//			return new LineGeometry(paint);
//		if (graphicType == GraphicType.OVAL)
//			return new OvalGeometry(paint);
//		if (graphicType == GraphicType.RECTANGLE)
//			return new RectangleGeometry(paint);
//		if (graphicType == GraphicType.ROUNDEDRECTANGLE)
//			return new RoundedRectangleGeometry(paint);
//		if (graphicType == GraphicType.TRIANGLE)
//			return new TriangleGeometry(paint);
//		if (graphicType == GraphicType.RIGHTTRIANGLE)
//			return new RightTriangleGeometry(paint);
//		if (graphicType == GraphicType.DIAMOND)
//			return new DiamondGeometry(paint);
//		if (graphicType == GraphicType.PENTAGON)
//			return new PentagonGeometry(paint);
//		if (graphicType == GraphicType.HEXAGON)
//			return new HexagonGeometry(paint);
//		if (graphicType == GraphicType.STAR)
//			return new StarGeometry(paint);
		return paint;
	}
}
