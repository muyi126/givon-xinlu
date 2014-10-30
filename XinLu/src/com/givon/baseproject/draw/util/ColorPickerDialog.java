package com.givon.baseproject.draw.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.givon.baseproject.xinlu.R;
import com.givon.util.colorpicker.ColorPicker;
import com.givon.util.colorpicker.ColorPicker.OnColorChangedListener;
import com.givon.util.colorpicker.OpacityBar;
import com.givon.util.colorpicker.SVBar;

public class ColorPickerDialog implements OnColorChangedListener{

	private ColorPicker picker;
	private SVBar svBar;
	private OpacityBar opacityBar;
	private TextView textSample;
	private Button buttonOK;
	private Button buttonCancel;
	private View dialogView;
	private ImageView selectedColor;
	private Dialog dialog;
	private int currentColor;
	private ColorChangeListenrt changeListenrt;
	
	public ColorChangeListenrt getChangeListenrt() {
		return changeListenrt;
	}

	public void setChangeListenrt(ColorChangeListenrt changeListenrt) {
		this.changeListenrt = changeListenrt;
	}

	public ColorPickerDialog(Activity drawActivity,ImageView imageView) {	
		LayoutInflater factory = LayoutInflater.from(drawActivity);
		dialogView = factory.inflate(R.layout.colorpicker, null);
		picker = (ColorPicker)dialogView.findViewById(R.id.picker);
		svBar = (SVBar)dialogView.findViewById(R.id.svbar);
		opacityBar = (OpacityBar)dialogView.findViewById(R.id.opacitybar);	
		picker.addSVBar(svBar);
		picker.addOpacityBar(opacityBar);
		picker.setOnColorChangedListener(this);
		textSample = (TextView)dialogView.findViewById(R.id.colorpickertextview);	
		buttonOK = (Button)dialogView.findViewById(R.id.colorpickerok);
		buttonCancel = (Button)dialogView.findViewById(R.id.colorpickercancel);
		this.selectedColor = imageView;
		dialog = new AlertDialog.Builder(drawActivity).setView(dialogView).create();
		currentColor = 0xff000000;
		
		buttonOK.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//textSample.setTextColor(picker.getColor());
				selectedColor.setBackgroundColor(picker.getColor());
				currentColor = picker.getColor();
				dialog.dismiss();
				picker.setOldCenterColor(picker.getColor());
				if(null!=changeListenrt){
					changeListenrt.onChange(currentColor);
				}
			}
		});
		
		buttonCancel.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
	
	public Dialog getDialog() {
		return dialog;
	}
	
	public int getColor() {
		return currentColor;
	}
	
	@Override
	public void onColorChanged(int color) {
		textSample.setTextColor(picker.getColor());
	}
	
	public interface ColorChangeListenrt{
		void onChange(int color);
	}

}
