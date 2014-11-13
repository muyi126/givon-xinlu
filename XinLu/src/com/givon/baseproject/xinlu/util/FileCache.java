/* 
 * Copyright 2014 JiaJun.Ltd  All rights reserved.
 * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @FileCache.java  2014-4-21 上午9:14:57 - Carson
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

public class FileCache {
	public static boolean saveObject(Context context,String key, Object obj) {
		boolean ret = false;
		if (null == obj) {
			return false;
		}
		FileOutputStream outStream = null;
		try {
			outStream = context.openFileOutput(key,
					Context.MODE_PRIVATE);
			ObjectOutputStream objectStream = new ObjectOutputStream(outStream);
			objectStream.writeObject(obj);
			ret = true;
		} catch (IOException e) {
			ret = false;
		}
		if (null != outStream) {
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public static Object getObject(Context context,String key) {
		Object obj = null;
		FileInputStream fin = null;
		try {
			fin = context.openFileInput(key);
			ObjectInputStream inStream = new ObjectInputStream(fin);
			obj = inStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != fin) {
			try {
				fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	public static String getSDPath(){
		  File sdDir = null;
		  boolean sdCardExist = Environment.getExternalStorageState()
		  .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
		  if (sdCardExist)
		  {
		  sdDir = Environment.getExternalStorageDirectory();//获取跟目录
		  }
		  return sdDir.toString();
		   
		 }
	
	public static String getFileMD5(File file) {
		  if (!file.isFile()) {
		   return null;
		  }
		  MessageDigest digest = null;
		  FileInputStream in = null;
		  byte buffer[] = new byte[1024];
		  int len;
		  try {
		   digest = MessageDigest.getInstance("MD5");
		   in = new FileInputStream(file);
		   while ((len = in.read(buffer, 0, 1024)) != -1) {
		    digest.update(buffer, 0, len);
		   }
		   in.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		   return null;
		  }
		  BigInteger bigInt = new BigInteger(1, digest.digest());
		  return bigInt.toString(16);
		 }
	
	public static String getFileMD5(InputStream in) {
		MessageDigest digest = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

		 /**
		  * 获取文件夹中文件的MD5值
		  * 
		  * @param file
		  * @param listChild
		  *            ;true递归子目录中的文件
		  * @return
		  */
		 public static Map<String, String> getDirMD5(File file, boolean listChild) {
		  if (!file.isDirectory()) {
		   return null;
		  }
		  Map<String, String> map = new HashMap<String, String>();
		  String md5;
		  File files[] = file.listFiles();
		  for (int i = 0; i < files.length; i++) {
		   File f = files[i];
		   if (f.isDirectory() && listChild) {
		    map.putAll(getDirMD5(f, listChild));
		   } else {
		    md5 = getFileMD5(f);
		    if (md5 != null) {
		     map.put(f.getPath(), md5);
		    }
		   }
		  }
		  return map;
		 }
		 
		 
		 private static FileCache tools = new FileCache();  
		  
		    public static FileCache getInstance() {  
		        if (tools == null) {  
		            tools = new FileCache();  
		            return tools;  
		        }  
		        return tools;  
		    }  
		  
		    // 将byte[]转换成InputStream  
		    public InputStream Byte2InputStream(byte[] b) {  
		        ByteArrayInputStream bais = new ByteArrayInputStream(b);  
		        return bais;  
		    }  
		  
		    // 将InputStream转换成byte[]  
		    public byte[] InputStream2Bytes(InputStream is) {  
		        String str = "";  
		        byte[] readByte = new byte[1024];  
		        int readCount = -1;  
		        try {  
		            while ((readCount = is.read(readByte, 0, 1024)) != -1) {  
		                str += new String(readByte).trim();  
		            }  
		            return str.getBytes();  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }  
		        return null;  
		    }  
		  
		    // 将Bitmap转换成InputStream  
		    public InputStream Bitmap2InputStream(Bitmap bm) {  
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
		        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
		        return is;  
		    }  
		  
		    // 将Bitmap转换成InputStream  
		    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {  
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);  
		        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
		        return is;  
		    }  
		  
		    // 将InputStream转换成Bitmap  
		    public Bitmap InputStream2Bitmap(InputStream is) {  
		        return BitmapFactory.decodeStream(is);  
		    }  
		  
		    // Drawable转换成InputStream  
		    public InputStream Drawable2InputStream(Drawable d) {  
		        Bitmap bitmap = this.drawable2Bitmap(d);  
		        return this.Bitmap2InputStream(bitmap);  
		    }  
		  
		    // InputStream转换成Drawable  
		    public Drawable InputStream2Drawable(InputStream is) {  
		        Bitmap bitmap = this.InputStream2Bitmap(is);  
		        return this.bitmap2Drawable(bitmap);  
		    }  
		  
		    // Drawable转换成byte[]  
		    public byte[] Drawable2Bytes(Drawable d) {  
		        Bitmap bitmap = this.drawable2Bitmap(d);  
		        return this.Bitmap2Bytes(bitmap);  
		    }  
		  
		    // byte[]转换成Drawable  
		    public Drawable Bytes2Drawable(byte[] b) {  
		        Bitmap bitmap = this.Bytes2Bitmap(b);  
		        return this.bitmap2Drawable(bitmap);  
		    }  
		  
		    // Bitmap转换成byte[]  
		    public byte[] Bitmap2Bytes(Bitmap bm) {  
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);  
		        return baos.toByteArray();  
		    }  
		  
		    // byte[]转换成Bitmap  
		    public Bitmap Bytes2Bitmap(byte[] b) {  
		        if (b.length != 0) {  
		            return BitmapFactory.decodeByteArray(b, 0, b.length);  
		        }  
		        return null;  
		    }  
		  
		    // Drawable转换成Bitmap  
		    public Bitmap drawable2Bitmap(Drawable drawable) {  
		        Bitmap bitmap = Bitmap  
		                .createBitmap(  
		                        drawable.getIntrinsicWidth(),  
		                        drawable.getIntrinsicHeight(),  
		                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
		                                : Bitmap.Config.RGB_565);  
		        Canvas canvas = new Canvas(bitmap);  
		        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
		                drawable.getIntrinsicHeight());  
		        drawable.draw(canvas);  
		        return bitmap;  
		    }  
		  
		    // Bitmap转换成Drawable  
		    public Drawable bitmap2Drawable(Bitmap bitmap) {  
		        BitmapDrawable bd = new BitmapDrawable(bitmap);  
		        Drawable d = (Drawable) bd;  
		        return d;  
		    }  
		    // Bitmap转换成Drawable  
		    public String bitmap2Md5(Bitmap bitmap) {  
		    	return getFileMD5(Bitmap2InputStream(bitmap));
		    }  
}
