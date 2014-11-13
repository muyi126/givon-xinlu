///* 
// * Copyright 2014 JiaJun.Ltd  All rights reserved.
// * SiChuan JiaJun.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// * 
// * @DDC.java  2014年11月10日 下午8:13:47 - Guzhu
// * @author Guzhu
// * @email:muyi126@163.com
// * @version 1.0
// */
//
//package HaoRan.ImageFilter.Main;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import HaoRan.ImageFilter.EdgeFilter;
//import HaoRan.ImageFilter.FilmFilter;
//import HaoRan.ImageFilter.FocusFilter;
//import HaoRan.ImageFilter.GammaFilter;
//import HaoRan.ImageFilter.Gradient;
//import HaoRan.ImageFilter.HslModifyFilter;
//import HaoRan.ImageFilter.IImageFilter;
//import HaoRan.ImageFilter.LightFilter;
//import HaoRan.ImageFilter.LomoFilter;
//import HaoRan.ImageFilter.OldPhotoFilter;
//import HaoRan.ImageFilter.PaintBorderFilter;
//import HaoRan.ImageFilter.R;
//import HaoRan.ImageFilter.SceneFilter;
//import HaoRan.ImageFilter.SoftGlowFilter;
//import HaoRan.ImageFilter.TileReflectionFilter;
//import HaoRan.ImageFilter.VideoFilter;
//import HaoRan.ImageFilter.VignetteFilter;
//import HaoRan.ImageFilter.VintageFilter;
//import HaoRan.ImageFilter.YCBCrLinearFilter;
//
//public class DDC {
//	private List<FilterInfo> filterArray = new ArrayList<FilterInfo>();
//	
//	private void addFilter(){
//		filterArray.add(new FilterInfo(R.drawable.video_filter1, new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_STAGGERED)));//+
//		filterArray.add(new FilterInfo(R.drawable.tilereflection_filter2, new TileReflectionFilter(20, 8, 45, (byte)2)));//+
//		filterArray.add(new FilterInfo(R.drawable.ycb_crlinear_filter2, new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.276f, 0.163f), new YCBCrLinearFilter.Range(-0.202f, 0.5f))));//+
//		filterArray.add(new FilterInfo(R.drawable.hslmodify_filter0, new HslModifyFilter(40f)));//+
//		filterArray.add(new FilterInfo(R.drawable.hslmodify_filter1, new HslModifyFilter(60f)));//+
//		filterArray.add(new FilterInfo(R.drawable.hslmodify_filter2, new HslModifyFilter(80f)));//+
//		filterArray.add(new FilterInfo(R.drawable.softglow_filter, new SoftGlowFilter(10, 0.1f, 0.1f)));//+
//		filterArray.add(new FilterInfo(R.drawable.gamma_filter, new GammaFilter(50)));//+
//		filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene())));//green//+
//		filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene1())));//purple//+
//		filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene2())));//blue//+
//		filterArray.add(new FilterInfo(R.drawable.invert_filter, new SceneFilter(5f, Gradient.Scene3())));//+
//		filterArray.add(new FilterInfo(R.drawable.invert_filter, new FilmFilter(80f)));//+
//		filterArray.add(new FilterInfo(R.drawable.invert_filter, new FocusFilter()));//+
//		filterArray.add(new FilterInfo(R.drawable.invert_filter, new PaintBorderFilter(0x00FF00)));//green//+
//		filterArray.add(new FilterInfo(R.drawable.invert_filter, new PaintBorderFilter(0x00FFFF)));//yellow//+
//		filterArray.add(new FilterInfo(R.drawable.invert_filter, new PaintBorderFilter(0xFF0000)));//blue//+
//		filterArray.add(new FilterInfo(R.drawable.invert_filter, new LomoFilter()));//+
//		filterArray.add(new FilterInfo(R.drawable.edge_filter, new EdgeFilter()));//+
//		filterArray.add(new FilterInfo(R.drawable.light_filter,	new LightFilter()));//+
//		filterArray.add(new FilterInfo(R.drawable.vignette_filter,	new VignetteFilter()));//+
//		filterArray.add(new FilterInfo(R.drawable.vintage_filter,new VintageFilter()));//+
//		filterArray.add(new FilterInfo(R.drawable.oldphoto_filter,new OldPhotoFilter()));//+
//	}
//	
//	private class FilterInfo {
//		public int filterID;
//		public IImageFilter filter;
//
//		public FilterInfo(int filterID, IImageFilter filter) {
//			this.filterID = filterID;
//			this.filter = filter;
//		}
//	}
//
//	
//}
