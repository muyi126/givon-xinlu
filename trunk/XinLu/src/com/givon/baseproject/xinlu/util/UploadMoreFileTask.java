/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @UploadMoreImageTask.java  2014年9月23日 下午4:03:22 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.givon.baseproject.xinlu.cookie.ShareCookie;
import com.givon.baseproject.xinlu.entity.DetailImages;
import com.upyun.api.Uploader;
import com.upyun.api.utils.UpYunException;
import com.upyun.api.utils.UpYunUtils;

public class UploadMoreFileTask extends AsyncTask<Object, Void, ArrayList<String>> {
	int total;
	private int type;
	ArrayList<String> error = new ArrayList<String>();
	private UpLoadListener upLoadListener;
	private static final String TEST_API_IMAGE_KEY = "lVBfg35QWT+r/LMFsoJjiQ5deno="; // 测试使用的表单api验证密钥
	private static final String TEST_API_FILE_KEY = "l5i6un8Z3eacrLB+6MNUX4EG67Y="; // 测试使用的表单api验证密钥
	private static final String FILE_BUCKET_NAME = "zuji-app-file"; // 存储空间
	private static final String PIC_BUCKET_NAME = "tiangou-app-img"; // 存储空间
	private static final long EXPIRATION = System.currentTimeMillis() / 1000 + 600; // 过期时间，必须大于当前时间
	public static final int UPLOAD_FILE = 1;
	public static final int UPLOAD_IMAGE = 2;

	public UpLoadListener getUpLoadListener() {
		return upLoadListener;
	}

	public void setUpLoadListener(UpLoadListener upLoadListener) {
		this.upLoadListener = upLoadListener;
	}

	@Override
	protected ArrayList<String> doInBackground(Object... params) {
		ArrayList<String> data = (ArrayList<String>) params[0];
		ArrayList<String> data2 = new ArrayList<String>();
		total = data.size();
		try {
			for (int i = 0; i < total; i++) {
				File file = new File(data.get(i));
				if (file.exists()) {
					String string = upLoad(data.get(i),type);
					if (!StringUtil.isEmpty(string)) {
						data2.add(string);
					} else {
						error.add(data.get(i));
					}
				} else {
					error.add(data.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data2;
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		super.onPostExecute(result);
		if (result.size() > 0) {
			if(result.size()==total){
				if (null != upLoadListener) {
					upLoadListener.UpLoadResult(result);
				}
			}else {
				if (error.size() > 0) {
					if (null != upLoadListener) {
						upLoadListener.UpLoadError(error);
					}
				}
				if (result.size() > 0) {
					if (null != upLoadListener) {
						upLoadListener.UpLoadResult(result);
					}
				} else {
					if (null != upLoadListener) {
						upLoadListener.UpLoadFail();
					}
				}
			}
			
		} else {
			if (null != upLoadListener) {
				upLoadListener.UpLoadFail();
			}
		}
	}

	private String upLoad(String filePath,int type) {
		String BUCKET= "";
//		switch (type) {
//		case  UPLOAD_FILE:
			BUCKET = FILE_BUCKET_NAME;
//			break;
//		case  UPLOAD_IMAGE:
//			BUCKET = PIC_BUCKET_NAME;
//			break;
//		default:
//			BUCKET = PIC_BUCKET_NAME;
//			break;
//		}
		String string = null;
		// String filePath = params[0];
		try {
			// test/upload
			// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
//			String SAVE_KEY = File.separator + "test" + File.separator + "upload" + File.separator
//					+ System.currentTimeMillis() + ".jpg";
			String nameString = FileCache.getFileMD5(new File(filePath));
			String SAVE_KEY = File.separator + "footprint" + File.separator + "upload" + File.separator
					+ nameString;
//			System.out.println("SAVE_KEY:" + SAVE_KEY);
//			System.out.println("SAVE_KEY:" + filePath);
			// 取得base64编码后的policy
			String policy = UpYunUtils.makePolicy(SAVE_KEY, EXPIRATION, BUCKET);

			// 根据表单api签名密钥对policy进行签名
			// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
			String signature = UpYunUtils.signature(policy + "&" + TEST_API_FILE_KEY);

			// 上传文件到对应的bucket中去。
			string = Uploader.upload(policy, signature, BUCKET, filePath);
		} catch (UpYunException e) {
			e.printStackTrace();
		}

		return string;
	}

	public interface UpLoadListener {
		void UpLoadError(ArrayList<String> notUpPath);

		void UpLoadResult(ArrayList<String> path);

		void UpLoadFail();
//		void UpLoadFinish();
	}

}
