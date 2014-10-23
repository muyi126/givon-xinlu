/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActSearch.java  2014年10月23日 下午4:35:00 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import android.view.View;
import android.widget.EditText;

import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ActSearch extends BaseActivity{
	@ViewInject (R.id.et_search)
	private EditText mEt_Search;
	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
	};

	
}
