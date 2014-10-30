/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @CommUtil.java  2014-2-25 下午5:46:24 - jiangyue
 * @author jiangyue
 * @version 1.0
 */

package com.givon.baseproject.xinlu.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.givon.baseproject.xinlu.act.MainActivity;

public class CommUtil {

	/**
	 * 验证邮箱地址是否正确
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
//			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			String check = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
//	public static Bitmap loadImageFromUrl(String url, int sc)
//    {
//
//        URL m;
//        InputStream i = null;
//        BufferedInputStream bis = null;
//        ByteArrayOutputStream out = null;
//
//        if (url == null)
//            return null;
//        try
//        {
//            m = new URL(url);
//            i = (InputStream) m.getContent();
//            bis = new BufferedInputStream(i, 1024 * 4);
//            out = new ByteArrayOutputStream();
//            int len = 0;
//
//            while ((len = bis.read(isBuffer)) != -1)
//            {
//                out.write(isBuffer, 0, len);
//            }
//            out.close();
//            bis.close();
//        } catch (MalformedURLException e1)
//        {
//            e1.printStackTrace();
//            return null;
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        if (out == null)
//            return null;
//        byte[] data = out.toByteArray();
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeByteArray(data, 0, data.length, options);
//        options.inJustDecodeBounds = false;
//        int be = (int) (options.outHeight / (float) sc);
//        if (be <= 0)
//        {
//            be = 1;
//        } else if (be > 3)
//        {
//            be = 3;
//        }
//        options.inSampleSize = be;
//        Bitmap bmp =null;
//        try
//        {
//            bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options); //返回缩略图
//        } catch (OutOfMemoryError e)
//        {
//            // TODO: handle exception
//            ToastUtils.showMessage("Tile Loader (241) Out Of Memory Error " + e.getLocalizedMessage())
//        
//            System.gc();
//            bmp =null;
//        }
//        return bmp;
//    }
}
