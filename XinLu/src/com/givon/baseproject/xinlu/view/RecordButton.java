package com.givon.baseproject.xinlu.view;
 
import java.io.File;
import java.io.IOException;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.act.MainActivity;
import com.givon.baseproject.xinlu.entity.Constant;

public class RecordButton extends Button  {
	
	

	public RecordButton(Context context) {
		super(context);
		init();
	}

	public RecordButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RecordButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void setSavePath(String path) {
		mFileName = path;
	}

	public void setOnFinishedRecordListener(OnFinishedRecordListener listener) {
		finishedListener = listener;
	}

	private String mFileName = null;

	private OnFinishedRecordListener finishedListener;

	private static final int MIN_INTERVAL_TIME = 2000;// 2s
	private long startTime;


	/**
	 * 取消语音发送
	 */
	private Dialog recordIndicator;

	private static int[] res = {R.drawable.mic_1, R.drawable.mic_2, R.drawable.mic_3,
			R.drawable.mic_4, R.drawable.mic_5,R.drawable.mic_6 };

	private static ImageView view;

	private MediaRecorder recorder;

	private ObtainDecibelThread thread;

	private Handler volumeHandler;
	
	public final static int   MAX_TIME =60;//一分钟
	
	private void init() {
		volumeHandler = new ShowVolumeHandler();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

//		if (mFileName == null)
//			return false;

		try {
			
		
		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			setText("松开发送");
			initDialogAndStartRecord();
			
			break;
		case MotionEvent.ACTION_UP:
			this.setText("按住说话");
			finishRecord();
			break;
		case MotionEvent.ACTION_CANCEL:// 当手指移动到view外面，会cancel
			cancelRecord();
			Toast.makeText(getContext(), "cancel", 1).show();
			break;
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	private void initDialogAndStartRecord() {

		startTime = System.currentTimeMillis();
		recordIndicator = new Dialog(getContext(),
				R.style.like_toast_dialog_style);
		view = new ImageView(getContext());
		view.setImageResource(R.drawable.mic_2);
		recordIndicator.setContentView(view, new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		recordIndicator.setOnDismissListener(onDismiss);
		LayoutParams lp = recordIndicator.getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		
		startRecording();
		recordIndicator.show();
	}

	private void finishRecord() {
		stopRecording();
		recordIndicator.dismiss();

		long intervalTime = System.currentTimeMillis() - startTime;
		if (intervalTime < MIN_INTERVAL_TIME) {
			Toast.makeText(getContext(), "时间太短！", Toast.LENGTH_SHORT).show();
			File file = new File(mFileName);
			file.delete();
			return;
		}

		if (finishedListener != null)
			finishedListener.onFinishedRecord(mFileName,(int) (intervalTime/1000));
	}

	private void cancelRecord() {
		stopRecording();
		recordIndicator.dismiss();

		Toast.makeText(getContext(), "取消说话！", Toast.LENGTH_SHORT).show();
		File file = new File(mFileName);
		file.delete();
	}

	private void startRecording() {
		
		
		String path = Constant.LUYIN_PATH;
		File file = new File(path);
		file.mkdirs();
		path += "/" + System.currentTimeMillis() + ".amr";
		setSavePath(path);
		
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setAudioChannels(1);
		recorder.setAudioEncodingBitRate(4000);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		//recorder.setVideoFrameRate(4000);
		
		recorder.setOutputFile(mFileName);

		try {
			recorder.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}

		recorder.start();
		thread = new ObtainDecibelThread();
		thread.start();

	}

	private void stopRecording() {
		if (thread != null) {
			thread.exit();
			thread = null;
		}
		if (recorder != null) {
			recorder.stop();
			recorder.release();
			recorder = null;
		}
	}

	private class ObtainDecibelThread extends Thread {

		private volatile boolean running = true;

		public void exit() {
			running = false;
		}

		@Override
		public void run() {
			while (running) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (recorder == null || !running) {
					break;
				}
				int x = recorder.getMaxAmplitude();
				if (x != 0) {
					int f = (int) (10 * Math.log(x) / Math.log(10));
					if (f < 26)
						volumeHandler.sendEmptyMessage(0);
					else if (f < 32)
						volumeHandler.sendEmptyMessage(1);
					else if (f < 38)
						volumeHandler.sendEmptyMessage(2);
					else
						volumeHandler.sendEmptyMessage(3);

				}

			}
		}

	}

	private OnDismissListener onDismiss = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface dialog) {
			stopRecording();
		}
	};

	static class ShowVolumeHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			view.setImageResource(res[msg.what]);
		}
	}

	public interface OnFinishedRecordListener {
		public void onFinishedRecord(String audioPath,int time);
	}

	
	 
	
	//private  boolean 
}
