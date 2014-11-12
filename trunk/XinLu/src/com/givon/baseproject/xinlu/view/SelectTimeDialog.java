/* 
 * Copyright 2014 ShangDao.Ltd  All rights reserved.
 * SiChuan ShangDao.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @SelectTimeDialog.java  2014年11月5日 上午10:44:47 - Guzhu
 * @author Guzhu
 * @email:muyi126@163.com
 * @version 1.0
 */

package com.givon.baseproject.xinlu.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;

import com.givon.baseproject.xinlu.R;
import com.givon.baseproject.xinlu.util.StringUtil;

public class SelectTimeDialog extends Dialog {
	private Context context;
	// Time changed flag
	private boolean timeChanged = false;

	// Time scrolled flag
	private boolean timeScrolled = false;
	private OnSelectTimeListener onSelectTimeListener;

	public OnSelectTimeListener getOnSelectTimeListener() {
		return onSelectTimeListener;
	}

	public void setOnSelectTimeListener(OnSelectTimeListener onSelectTimeListener) {
		this.onSelectTimeListener = onSelectTimeListener;
	}

	public SelectTimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.context = context;
	}

	public SelectTimeDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public SelectTimeDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_time_layout);
		getWindow().setGravity(Gravity.BOTTOM);
		final String dayString[] = new String[] { "今天","明天", "后天" };
		ArrayWheelAdapter<String> dayAdapter = new ArrayWheelAdapter<String>(context, dayString);

		final WheelView day = (WheelView) findViewById(R.id.day);
		dayAdapter.setItemResource(R.layout.wheel_text_item);
		dayAdapter.setItemTextResource(R.id.text);
		day.setViewAdapter(dayAdapter);

		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setViewAdapter(new NumericWheelAdapter(context, 0, 23, "%02d"));

		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setViewAdapter(new NumericWheelAdapter(context, 0, 59, "%02d"));
		mins.setCyclic(true);

		// final TimePicker picker = (TimePicker) findViewById(R.id.time);
		// picker.setIs24HourView(true);

		// set current time
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curMinutes = c.get(Calendar.MINUTE);
		day.setCurrentItem(0);
		hours.setCurrentItem(curHours);
		mins.setCurrentItem(curMinutes);

		// picker.setCurrentHour(curHours);
		// picker.setCurrentMinute(curMinutes);

		// add listeners
		addChangingListener(mins, "min");
		addChangingListener(hours, "hour");
		addChangingListener(day, "day");
		// OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
		// public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// if (!timeScrolled) {
		// timeChanged = true;
		// picker.setCurrentHour(hours.getCurrentItem());
		// picker.setCurrentMinute(mins.getCurrentItem());
		// timeChanged = false;
		// }
		// }
		// };
		// hours.addChangingListener(wheelListener);
		// mins.addChangingListener(wheelListener);

		OnWheelClickedListener click = new OnWheelClickedListener() {
			public void onItemClicked(WheelView wheel, int itemIndex) {
				System.out.println("click:" + itemIndex);
				wheel.setCurrentItem(itemIndex, true);
			}

			@Override
			public void onClickedCurrentItem(WheelView wheel, int itemIndex) {
				System.out.println("click2:" + (dayString[day.getCurrentItem()]) + ""
						+ hours.getCurrentItem() + ":" + mins.getCurrentItem());
				int minute = mins.getCurrentItem();
				int hour = hours.getCurrentItem();
				Calendar rightNow = Calendar.getInstance();
//				rightNow.set(Calendar.DATE, 30);
				if(day.getCurrentItem() == 0){
					
				}else 
				if (day.getCurrentItem() == 1) {
					rightNow.add(Calendar.DATE, 1);
				} else if (day.getCurrentItem() == 2) {
					rightNow.add(Calendar.DATE, 2);
				}
				rightNow.set(Calendar.MINUTE, minute);
				rightNow.set(Calendar.HOUR_OF_DAY, hour);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				java.util.Date date = rightNow.getTime();
				String dateString = format.format(date);
				if(!StringUtil.isEmpty(dateString)&&null!=onSelectTimeListener){
					onSelectTimeListener.onSelectFinish(dateString,date.getTime());
					dismiss();
				}
			}
		};
		hours.addClickingListener(click);
		mins.addClickingListener(click);
		day.addClickingListener(click);
		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				// picker.setCurrentHour(hours.getCurrentItem());
				// picker.setCurrentMinute(mins.getCurrentItem());
				timeChanged = false;
			}
		};

		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);
		day.addScrollingListener(scrollListener);
		setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				if(null!=onSelectTimeListener){
					onSelectTimeListener.onSelectCancel();
					if(isShowing()){
						dismiss();
					}
				}
			}
		});
	}

	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * @param wheel
	 *        the wheel
	 * @param label
	 *        the wheel label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}

	public interface OnSelectTimeListener {
		void onSelectCancel();

		void onSelectFinish(String date, long dateTre);
	}
}