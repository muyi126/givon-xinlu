package com.givon.baseproject.draw.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

public class DrawAttribute {
	public static enum Corner{LEFTTOP,RIGHTTOP,LEFTBOTTOM,RIGHTBOTTOM,ERROR};
	public static enum DrawStatus{CASUAL_WATER,
		                          CASUAL_CRAYON,
		                          CASUAL_COLOR_SMALL,CASUAL_COLOR_BIG,
		                          ERASER,
		                          STAMP_BUBBLE,STAMP_DOTS,STAMP_HEART,STAMP_STAR,	                          
		                          CASUAL,WORDS,DRAW,READY_DRAW};
		                          
    public final static int backgroundOnClickColor = 0xfff08d1e;
	public static int screenHeight;
	public static int screenWidth;
	public static int screenHeight_out = 1008;
	public static int screenWidth_out = 640;
	public static int screenHeight_out_2 = 1008;
	public static int screenWidth_out_2 = 640;
	public static float screenWidthSclHeight = 640f/1008f;
	public static Paint paint = new Paint();
		
	public static Bitmap getImageFromAssetsFile(Context context,String fileName,boolean isBackground)
    {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try
        {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if(isBackground)image = Bitmap.createScaledBitmap(image, DrawAttribute.screenWidth,
        		DrawAttribute.screenHeight, false);
        return image;

    } 
}
