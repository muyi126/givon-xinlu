/* 
 * HaoRan ImageFilter Classes v0.1
 * Copyright (C) 2012 Zhenjun Dai
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation.
 */

package HaoRan.ImageFilter;

import android.graphics.Color;

/**
 * ɫ����Ч
 * @author daizhj
 *
 */
public class TintFilter implements IImageFilter {

	public Image process(Image imageIn)
    {
       int tr = Color.RED;
       int tg = Color.GREEN;
       int tb = Color.BLUE;
       int r,g,b;
       for (int x = 0; x < imageIn.getWidth(); x++) {
           for (int y = 0; y < imageIn.getHeight(); y++) {
                r = (255-imageIn.getRComponent(x, y));
                g = (255-imageIn.getGComponent(x, y));
                b = (255-imageIn.getBComponent(x, y));

                // Convert to gray with constant factors 0.2126, 0.7152, 0.0722
                int gray = (r *  6966 + g * 23436 + b *  2366) >> 15;

                // Apply Tint color
                r = (byte)((gray * tr) >> 8);
                g = (byte)((gray * tg) >> 8);
                b = (byte)((gray * tb) >> 8);

                imageIn.setPixelColor(x,y,r,g,b);
            }
       } 
       return imageIn;
    }
}
