//package HaoRan.ImageFilter.Main;
//
//import java.io.File;
//import java.io.IOException;
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
//import HaoRan.ImageFilter.Image;
//import HaoRan.ImageFilter.LightFilter;
//import HaoRan.ImageFilter.LomoFilter;
//import HaoRan.ImageFilter.OldPhotoFilter;
//import HaoRan.ImageFilter.PaintBorderFilter;
//import HaoRan.ImageFilter.SceneFilter;
//import HaoRan.ImageFilter.SoftGlowFilter;
//import HaoRan.ImageFilter.TileReflectionFilter;
//import HaoRan.ImageFilter.VideoFilter;
//import HaoRan.ImageFilter.VignetteFilter;
//import HaoRan.ImageFilter.VintageFilter;
//import HaoRan.ImageFilter.YCBCrLinearFilter;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.Gallery;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.givon.baseproject.xinlu.R;
//import com.givon.baseproject.xinlu.entity.Constant;
//import com.givon.baseproject.xinlu.entity.DetailImages;
//import com.givon.baseproject.xinlu.util.BitmapHelp;
//
//public class ImageFilterMain extends Activity {
//
//	private ImageView imageView;
//	private TextView textView;
//	private Button finishButton;
//	private Button cancelButton;
//	private DetailImages images;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.layout_draw_filter);
//
//		imageView = (ImageView) findViewById(R.id.imgfilter);
//		textView = (TextView) findViewById(R.id.runtime);
//		Intent intent = getIntent();
//		if(intent.hasExtra(Constant.DATA)){
//			// 注：在android系统上，手机图片尺寸尽量控制在480*480范围内,否则在高斯运算时可以造成内存溢出的问题
//			if (intent.hasExtra(Constant.DATA)) {
//				images = (DetailImages) intent.getSerializableExtra(Constant.DATA);
//				Bitmap bmp;
//				try {
//					bmp = BitmapHelp.getBitpMap(new File(images.getImageUrl()));
//					if (null != bmp) {
////						drawView.setBackgroundBitmap(bmp, false);
//						imageView.setImageBitmap(bmp);
//						LoadImageFilter();
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
////			Bitmap bitmap = BitmapFactory.decodeResource(ImageFilterMain.this.getResources(),
////					R.drawable.image);
////			imageView.setImageBitmap(bitmap);
//
//		}
//	}
//
//	/**
//	 * 加载图片filter
//	 */
//	private void LoadImageFilter() {
//		Gallery gallery = (Gallery) findViewById(R.id.galleryFilter);
//		final ImageFilterAdapter filterAdapter = new ImageFilterAdapter(ImageFilterMain.this);
//		gallery.setAdapter(new ImageFilterAdapter(ImageFilterMain.this));
//		gallery.setSelection(2);
//		gallery.setAnimationDuration(3000);
//		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
//
//				IImageFilter filter = (IImageFilter) filterAdapter.getItem(position);
//				System.out.println("NAME:" + filter.getClass().getSimpleName());
//				new processImageTask(ImageFilterMain.this, filter).execute();
//			}
//		});
//	}
//
//	public class processImageTask extends AsyncTask<Bitmap, Void, Bitmap> {
//		private IImageFilter filter;
//		private Activity activity = null;
//
//		public processImageTask(Activity activity, IImageFilter imageFilter) {
//			this.filter = imageFilter;
//			this.activity = activity;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			textView.setVisibility(View.VISIBLE);
//		}
//
//		public Bitmap doInBackground(Bitmap... params) {
//			Image img = null;
//			Bitmap bitmap = params[0];
//			try {
//				// Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.image);
//				img = new Image(bitmap);
//				if (filter != null) {
//					img = filter.process(img);
//					img.copyPixelsFromBuffer();
//				}
//				return img.getImage();
//			} catch (Exception e) {
//				if (img != null && img.destImage.isRecycled()) {
//					img.destImage.recycle();
//					img.destImage = null;
//					System.gc(); // 提醒系统及时回收
//				}
//			} finally {
//				if (img != null && img.image.isRecycled()) {
//					img.image.recycle();
//					img.image = null;
//					System.gc(); // 提醒系统及时回收
//				}
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Bitmap result) {
//			if (result != null) {
//				super.onPostExecute(result);
//				imageView.setImageBitmap(result);
//			}
//			textView.setVisibility(View.GONE);
//		}
//	}
//
//	public class ImageFilterAdapter extends BaseAdapter {
//		private class FilterInfo {
//			public String filterName;
//			public int filterID;
//			public IImageFilter filter;
//
//			public FilterInfo(String filterName, int filterID, IImageFilter filter) {
//				this.filterName = filterName;
//				this.filterID = filterID;
//				this.filter = filter;
//			}
//		}
//
//		private Context mContext;
//		private List<FilterInfo> filterArray = new ArrayList<FilterInfo>();
//
//		public ImageFilterAdapter(Context c) {
//			mContext = c;
//
//			// 99种效果
//			filterArray.add(new FilterInfo("1", 1, new VideoFilter(
//					VideoFilter.VIDEO_TYPE.VIDEO_STAGGERED)));// +
//			filterArray.add(new FilterInfo("2", 1, new TileReflectionFilter(20, 8, 45, (byte) 2)));// +
//			filterArray.add(new FilterInfo("3", 1, new YCBCrLinearFilter(
//					new YCBCrLinearFilter.Range(-0.276f, 0.163f), new YCBCrLinearFilter.Range(
//							-0.202f, 0.5f))));// +
//			filterArray.add(new FilterInfo("4", 1, new HslModifyFilter(40f)));// +
//			filterArray.add(new FilterInfo("5", 1, new HslModifyFilter(60f)));// +
//			filterArray.add(new FilterInfo("6", 1, new HslModifyFilter(80f)));// +
//			filterArray.add(new FilterInfo("7", 1, new SoftGlowFilter(10, 0.1f, 0.1f)));// +
//			filterArray.add(new FilterInfo("8", 1, new GammaFilter(50)));// +
//			filterArray.add(new FilterInfo("9", 1, new SceneFilter(5f, Gradient.Scene())));// green//+
//			filterArray.add(new FilterInfo("10", 1, new SceneFilter(5f, Gradient.Scene1())));// purple//+
//			filterArray.add(new FilterInfo("11", 1, new SceneFilter(5f, Gradient.Scene2())));// blue//+
//			filterArray.add(new FilterInfo("12", 1, new SceneFilter(5f, Gradient.Scene3())));// +
//			filterArray.add(new FilterInfo("13", 1, new FilmFilter(80f)));// +
//			filterArray.add(new FilterInfo("14", 1, new FocusFilter()));// +
//			filterArray.add(new FilterInfo("15", 1, new PaintBorderFilter(0x00FF00)));// green//+
//			filterArray.add(new FilterInfo("16", 1, new PaintBorderFilter(0x00FFFF)));// yellow//+
//			filterArray.add(new FilterInfo("17", 1, new PaintBorderFilter(0xFF0000)));// blue//+
//			filterArray.add(new FilterInfo("18", 1, new LomoFilter()));// +
//			filterArray.add(new FilterInfo("19", 1, new EdgeFilter()));// +
//			filterArray.add(new FilterInfo("20", 1, new LightFilter()));// +
//			filterArray.add(new FilterInfo("21", 1, new VignetteFilter()));// +
//			filterArray.add(new FilterInfo("22", 1, new VintageFilter()));// +
//			filterArray.add(new FilterInfo("23", 1, new OldPhotoFilter()));// +
//
//		}
//
//		public int getCount() {
//			return filterArray.size();
//		}
//
//		public Object getItem(int position) {
//			return position < filterArray.size() ? filterArray.get(position).filter : null;
//		}
//
//		public long getItemId(int position) {
//			return position;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			Bitmap bmImg = BitmapFactory.decodeResource(mContext.getResources(),
//					filterArray.get(position).filterID);
//			int width = 100;// bmImg.getWidth();
//			int height = 100;// bmImg.getHeight();
//			bmImg.recycle();
////			ImageView imageview = new ImageView(mContext);
//			TextView textView = new TextView(mContext);
//			textView.setText(filterArray.get(position).filterName);
//			textView.setLayoutParams(new Gallery.LayoutParams(width, height));
////			imageview.setImageResource(filterArray.get(position).filterID);
////			imageview.setLayoutParams(new Gallery.LayoutParams(width, height));
////			imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);// 设置显示比例类型
//			return textView;
//		}
//	};
//
//}
