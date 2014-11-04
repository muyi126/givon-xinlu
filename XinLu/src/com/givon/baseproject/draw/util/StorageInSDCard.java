package com.givon.baseproject.draw.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import com.givon.baseproject.xinlu.entity.DetailImages;
import com.givon.baseproject.xinlu.util.FileCache;

public class StorageInSDCard {
	
	public static boolean IsExternalStorageAvailableAndWriteable() {
		boolean externalStorageAvailable = false;
		boolean externalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
		
		if(Environment.MEDIA_MOUNTED.equals(state)) {
			//you can read and write the media
			externalStorageAvailable = externalStorageWriteable = true;
		}
		else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			//you can only read the media
			externalStorageAvailable = true;
			externalStorageWriteable = false;
		}
		else {
			externalStorageAvailable = externalStorageWriteable = false;
		}
		return externalStorageAvailable && externalStorageWriteable;
	}
	
	public static DetailImages saveBitmapInExternalStorage(Bitmap bitmap,Context context) {
		try {
			if(IsExternalStorageAvailableAndWriteable()) {
				File extStorage = new File(Environment.getExternalStorageDirectory().getPath() +"/drawpics");
				if (!extStorage.exists()) {
					extStorage.mkdirs();
				}
				String nameString = FileCache.getInstance().bitmap2Md5(bitmap);
				File file = new File(extStorage,nameString+".jpg");
				if(file.exists()){
					DetailImages detailImages = new DetailImages();
					detailImages.setHeightSize(DrawAttribute.screenHeight);
					detailImages.setWidthSize(DrawAttribute.screenWidth);
					detailImages.setImageUrl(file.getPath());
					return detailImages;
				}
				FileOutputStream fOut = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
				fOut.flush();
				fOut.close();
				Toast.makeText(context,  "保存成功", Toast.LENGTH_SHORT).show();
				DetailImages detailImages = new DetailImages();
				detailImages.setHeightSize(DrawAttribute.screenHeight_out);
				detailImages.setWidthSize(DrawAttribute.screenWidth_out);
				detailImages.setImageUrl(file.getPath());
				return detailImages;
			}
			else {
				Toast.makeText(context,  "保存失败", Toast.LENGTH_SHORT).show();
			}
		}
		catch (IOException ioe){
			ioe.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<String> getBitmapsPathFromExternalStorage() {
		ArrayList<String> fileList = new ArrayList<String>();
		if(IsExternalStorageAvailableAndWriteable()) {
			File extStorage = new File(Environment.getExternalStorageDirectory().getPath() +"/drawpics");
			if (!extStorage.exists()) {
				extStorage.mkdirs();				
			}				
			File[] files = extStorage.listFiles();
			//对文件进行排序
			if(files != null) {				
				for(int i = 1 ;i < files.length ;i++) {
					for(int j = i ;j > 0 ;j--) {
						if(files[j].lastModified() > files[j - 1].lastModified()) {
							File tempfile = files[j];
							files[j] = files[j - 1];
							files[j - 1] = tempfile;
						}
						else {
							break;
						}
					}
				}
				fileList = null;
				fileList = new ArrayList<String>(files.length);
			}
			//获取路径
			for(int i=0 ;i<files.length ;i++)
			{
				if(files[i].isFile())
				{					
					String filename = files[i].getName();
					//获取bmp,jpg,png格式的图片
					if(filename.endsWith(".jpg")||filename.endsWith(".png")||filename.endsWith(".bmp"))						
					{ 
						fileList.add(files[i].getAbsolutePath());
					}						                     
				}               
			}
		}
		else {
		}
		return fileList;
	}	
}
