package com.givon.baseproject.draw.util;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.util.ToastUtils;
import com.givon.baseproject.xinlu.view.DrawView;
import com.givon.util.colorpicker.ColorPickerView;
import com.givon.util.colorpicker.ColorPickerView.OnColorChangedListener;

public class GeometryUtil {
	public enum TextType {
		SONG, KAI
	};

	private View drawActivity;
	private Activity activity;
	private DrawView drawView;
	private ColorPickerView mColorPickerView;
	private TextView selectedGraphic;
	private TextType textType;
	private EditText mEditText;
//	private LinearLayout mEditLayout;

	public GeometryUtil(Activity activity,View view, DrawView drawView) {
		this.drawActivity = view;
		this.activity = activity;
		this.drawView = drawView;
//		this.mEditLayout = eLayout;
		selectedGraphic = (TextView) view.findViewById(R.id.text_type_1);
		textType = TextType.SONG;
		selectedGraphic.setBackgroundColor(DrawAttribute.backgroundOnClickColor);
		mEditText = (EditText) view.findViewById(R.id.et_content);
		mColorPickerView = (ColorPickerView) view.findViewById(R.id.graphiccolorview);
		mColorPickerView.setOnColorChangedListener(new OnColorChangedListener() {
			
			@Override
			public void onColorChanged(int color) {
				GeometryUtil.this.drawView.editStickerBitmapTT_Paint_now(GetTextPaintClass(textType));
			}
		});
		
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
//		Button chooseColorButton = (Button) drawActivity.findViewById(R.id.graphiccolorbtn);
//		chooseColorButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				colorPickerDialog.getDialog().show();
//			}
//
//		});
//		Button startDrawingbutton = (Button) drawActivity.findViewById(R.id.graphicdrawbtn);
//		startDrawingbutton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// drawView.setBasicGeometry(GetGraphicClass(graphicType));
//				// colorPickerDialog.getColor();
//				
//
//		});
		
//		Button cancelButton= (Button) drawActivity.findViewById(R.id.graphicdrawbtn_cancel);
//		cancelButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				mEditLayout.setFocusable(false);
//				mEditLayout.setVisibility(View.GONE);
////				if(null!=mEditText.getText()&&mEditText.getText().toString().length()==0){
////					ToastUtils.showMessage("内容不能为空");
////					return;
////				}
////				drawView.editStickerBitmapTT_now(GetTextPaintClass(textType),mEditText.getText().toString());
//			}
//			
//		});
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

	public Paint GetTextPaintClass(TextType graphicType) {
		Paint paint = new Paint();
		paint.setColor(mColorPickerView.getColor());
		return paint;
	}
}
