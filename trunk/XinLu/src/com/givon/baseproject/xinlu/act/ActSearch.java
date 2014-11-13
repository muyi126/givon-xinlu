/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActSearch.java  2014年10月23日 下午4:35:00 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ActSearch extends BaseActivity implements OnClickListener{
	@ViewInject (R.id.et_search)
	private EditText mEt_Search;
	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_dialog_search);
		ViewUtils.inject(this);
		
	}
	@Override
	public void onClick(View v) {
		
	};
	
}
