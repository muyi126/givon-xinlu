/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @EditTextDialog.java  2014年11月4日 上午11:31:56 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.view;

import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.util.StringUtil;
import com.givon.baseproject.xinlu.util.ToastUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTextDialog extends Dialog {
	private Context context;
	private Button bt_Sure;
	private Button bt_Cancel;
	private EditText et_Content;
	private OnEditTextOnClickListener onEditTextOnClickListener;

	public OnEditTextOnClickListener getOnEditTextOnClickListener() {
		return onEditTextOnClickListener;
	}

	public void setOnEditTextOnClickListener(OnEditTextOnClickListener onEditTextOnClickListener) {
		this.onEditTextOnClickListener = onEditTextOnClickListener;
	}

	public EditTextDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.context = context;
	}

	public EditTextDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public EditTextDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_edit_text);
		getWindow().setGravity(Gravity.BOTTOM);
		et_Content = (EditText) findViewById(R.id.et_content);
		et_Content.setFocusable(true);
		et_Content.setFocusableInTouchMode(true);
		bt_Sure = (Button) findViewById(R.id.bt_sure);
		bt_Cancel = (Button) findViewById(R.id.bt_cancel);
		bt_Sure.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(null!=onEditTextOnClickListener){
					String content = et_Content.getText().toString().trim();
					if(!StringUtil.isEmpty(content)){
						onEditTextOnClickListener.onClickSure(content);
						dismiss();
					}else {
						ToastUtils.showMessage("内容不能为空");
					}
				}
			}
		});
		bt_Cancel.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null!=onEditTextOnClickListener){
					onEditTextOnClickListener.onClickCancel();
					dismiss();
				}
			}
		});
		setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				if(null!=onEditTextOnClickListener){
					System.out.println("setOnCancelListener");
					onEditTextOnClickListener.onClickCancel();
					dismiss();
				}
			}
		});
		
	}

	public interface OnEditTextOnClickListener {
		void onClickCancel();

		void onClickSure(String string);
	}

}
