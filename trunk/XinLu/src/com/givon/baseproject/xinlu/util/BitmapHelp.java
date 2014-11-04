package com.givon.baseproject.xinlu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lidroid.xutils.BitmapUtils;

/**
 * Author: wyouflf
 * Date: 13-11-12
 * Time: 上午10:24
 */
public class BitmapHelp {
    private BitmapHelp() {
    }

    private static BitmapUtils bitmapUtils;

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     *
     * @param appContext application context
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context appContext) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(appContext);
        }
        return bitmapUtils;
    }
    
    public static Bitmap getBitpMap(File file) throws IOException
    {
//        ParcelFileDescriptor pfd;
//        try
//        {
//            pfd = getContentResolver().openFileDescriptor(uri, "r");
//        } catch (IOException ex)
//        {
//            return null;
//        }
    	FileInputStream fs = null;
		try {
			fs = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        java.io.FileDescriptor fd = fs.getFD();
        BitmapFactory.Options options = new BitmapFactory.Options();
        //先指定原始大小   
        options.inSampleSize = 1;
        //只进行大小判断   
        options.inJustDecodeBounds = true;
        //调用此方法得到options得到图片的大小   
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        //BitmapFactory.decodeStream(is, null, options);
        //我们的目标是在800pixel的画面上显示。   
        //所以需要调用computeSampleSize得到图片缩放的比例   
        options.inSampleSize = computeSampleSize(options, 800);
        //OK,我们得到了缩放的比例，现在开始正式读入BitMap数据   
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        //根据options参数，减少所需要的内存   
        //        Bitmap sourceBitmap = BitmapFactory.decodeFileDescriptor(fd, null, options);
//        Bitmap sourceBitmap = BitmapFactory.decodeStream(is, null, options);
        Bitmap sourceBitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null,
        		options);
        return sourceBitmap;
    }

    //这个函数会对图片的大小进行判断，并得到合适的缩放比例，比如2即1/2,3即1/3   
    static int computeSampleSize(BitmapFactory.Options options, int target)
    {
        int w = options.outWidth;
        int h = options.outHeight;
        int candidateW = w / target;
        int candidateH = h / target;
        int candidate = Math.max(candidateW, candidateH);
        if (candidate == 0)
            return 1;
        if (candidate > 1)
        {
            if ((w > target) && (w / candidate) < target)
                candidate -= 1;
        }
        if (candidate > 1)
        {
            if ((h > target) && (h / candidate) < target)
                candidate -= 1;
        }
        //if (VERBOSE)
        Log.v("BITMAP", "for w/h " + w + "/" + h + " returning " + candidate + "(" + (w / candidate) + " / " + (h / candidate));
        return candidate;
    }
}
