/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ActPublish.java  2014年10月30日 上午11:12:41 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.act;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.givon.baseproject.xinlu.BaseActivity;
import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.adapter.PublishPhotoAdapter;
import com.givon.baseproject.xinlu.entity.Constant;
import com.givon.baseproject.xinlu.util.StringUtil;

public class ActPublish extends BaseActivity{
	private GridView mGridView;
	private PublishPhotoAdapter mAdapter;
	private ArrayList<String> pathList=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_actpublish);
		mGridView = (GridView) findViewById(R.id.GridView_photo);
		mAdapter = new PublishPhotoAdapter(this);
		mGridView.setAdapter(mAdapter);
		Intent intent = getIntent();
		if(intent.hasExtra(Constant.DATA)){
			String path = intent.getStringExtra(Constant.DATA);
			if(!StringUtil.isEmpty(path)){
				pathList.add(path);
				mAdapter.updateAdapter(pathList, pathList.size());
			}
		}
		
	}

}
