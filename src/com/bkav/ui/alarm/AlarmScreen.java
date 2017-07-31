package com.bkav.ui.alarm;

import java.util.ArrayList;
import java.util.Calendar;

import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
    
public class AlarmScreen extends Fragment {
  
	public static final int MONDAY_INDEX = 0;
	public static final int TUESDAY_INDEX = 1;
	public static final int WEDNESDAY_INDEX = 2;
	public static final int THURSDAY_INDEX = 3;
	public static final int FRIDAY_INDEX = 4;
	public static final int SATURDAY_INDEX = 5;
	public static final int SUNDAY_INDEX = 6;
	public static final int REQUEST_ADD_ALARM = 1;
	public static final int REQUEST_EDIT_ALARM = 2;
	public static final int MIN_HOUR = 0;
	public static final int MAX_HOUR = 24;
	public static final int MIN_MINUTE = 0;
	public static final int MAX_MINUTE = 60;
	public static final int ALARM_CODE_MONDAY = 1000;
	public static final int ALARM_CODE_TUEDAY = 2000;
	public static final int ALARM_CODE_WEDNESDAY = 3000;
	public static final int ALARM_CODE_THURSDAY = 4000;
	public static final int ALARM_CODE_FRIDAY = 5000;
	public static final int ALARM_CODE_SATURDAY = 6000;
	public static final int ALARM_CODE_SUNDAY = 7000;
	public static final long MILLISECOND_PER_WEEK = 604800000;
	public static final long MILLISECOND_PER_DAY = 86400000;
	public static final String ALARM = "alarm";
	public static final String ALARM_TIME = "time";
	public static final String POSITION = "position";
	public static final String REQUEST_ALARM = "request_alarm";
	public static final String MONDAY = "2 ";
	public static final String TUESDAY = "3 ";
	public static final String WEDNESDAY = "4 ";
	public static final String THURSDAY = "5 ";
	public static final String FRIDAY = "6 ";
	public static final String SATURDAY = "7 ";
	public static final String SUNDAY = "CN";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {  
		View view = inflater.inflate(R.layout.alarm_screen, container, false);
		addAlarm = (ImageView) view.findViewById(R.id.addAlarm);
		listAlarmLayout = (LinearLayout) view.findViewById(R.id.listAlarm);
		nameBarAlarm = (TextView) view.findViewById(R.id.nameBarAlarm);  
		
		intent = new Intent(getActivity().getApplicationContext(), AlarmNotifyActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Activity.ALARM_SERVICE);
		
		adapter = new AlarmAdapter(getActivity().getApplicationContext(), 0, WatchActivity.sysManager.getArrayAlarm());
		listAlarm = new ListView(getActivity().getApplicationContext());
		listAlarm.setAdapter(adapter);
		listAlarmLayout.addView(listAlarm); 
		   
		int heightListView = 0;
		for (int index = 0; index < listAlarm.getAdapter().getCount(); index++) {
			View viewItem = listAlarm.getAdapter().getView(index, null, listAlarm);
			viewItem.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));  
			heightListView += viewItem.getMeasuredHeight();
		}
		
		LayoutParams layoutParamsListView = (LayoutParams) listAlarm.getLayoutParams();
		layoutParamsListView.height = heightListView + listAlarm.getDividerHeight() * (adapter.getCount() - 1);
		listAlarm.setLayoutParams(layoutParamsListView); 
		 
		addAlarm.setOnClickListener(new OnClickListener() {   

			@Override 
			public void onClick(View v) {  
				Intent intent = new Intent(getActivity().getApplicationContext(), AlarmSetting.class);
				intent.putExtra(REQUEST_ALARM, REQUEST_ADD_ALARM);
				startActivityForResult(intent, REQUEST_ADD_ALARM);   
			}  
			
		});
		
		listAlarm.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, 
					long id) {
				Intent intent = new Intent(getActivity().getApplicationContext(), AlarmSetting.class);
				intent.putExtra(POSITION, position);
				intent.putExtra(REQUEST_ALARM, REQUEST_EDIT_ALARM);
				startActivityForResult(intent, REQUEST_EDIT_ALARM);
			}
		});
		
		return view;
	}
	
	public void updateLanguageAlarm() {
		LanguageManager languageManager = WatchActivity.sysManager.getLanguageManager();
		if (languageManager != null) {
			nameBarAlarm.setText(languageManager.getLabel(ALARM).getValue());
		}
	}

	public void updateListAlarmIcon() {
		adapter.notifyDataSetChanged();
		adapter.notifyDataSetInvalidated();
		listAlarm.invalidateViews();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onStart() {
		super.onStart();  
	} 

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ADD_ALARM) {
			Log.e("", "REQUEST_ADD_ALARM");
			if (resultCode == Activity.RESULT_OK) {
				adapter.notifyDataSetChanged();
				listAlarm.invalidateViews();
				int heightListView = 0;
				for (int index = 0; index < listAlarm.getAdapter().getCount(); index++) {
					View viewItem = listAlarm.getAdapter().getView(index, null, listAlarm);
					viewItem.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
							MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
					heightListView += viewItem.getMeasuredHeight();
				}
				
				LayoutParams layoutParamsListView = (LayoutParams) listAlarm.getLayoutParams();
				layoutParamsListView.height = heightListView + listAlarm.getDividerHeight() * (adapter.getCount() - 1);
				listAlarm.setLayoutParams(layoutParamsListView);
			}
		} else if (requestCode == REQUEST_EDIT_ALARM) {
			Log.e("", "REQUEST_EDIT_ALARM");
			if (resultCode == Activity.RESULT_OK) {
				adapter.notifyDataSetChanged();
				listAlarm.invalidateViews();
			}
		}
	}
	
	public void setAlarm(int position) {
		ArrayList<AlarmData> arrayAlarm = WatchActivity.sysManager.getArrayAlarm();
		Calendar now = Calendar.getInstance();
		String[] time = arrayAlarm.get(position).getTime().split(":");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
		calendar.set(Calendar.SECOND, 0);
		int countRepeat = 0;
		for (int index = 0; index < arrayAlarm.get(position).getRepeat().length; index++) {
			if (arrayAlarm.get(position).getRepeat()[index]) {
				countRepeat++;
			}
		}
		if (countRepeat == 0) {
			if (calendar.compareTo(now) <= 0) {
				calendar.add(Calendar.WEEK_OF_MONTH, 1);
			}
			Log.e("time alarm", "" + calendar.getTime());
			pendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), 
					position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					MILLISECOND_PER_DAY, pendingIntent);  
		} else {
			if (arrayAlarm.get(position).getRepeat()[MONDAY_INDEX]) {
				PendingIntent pendingIntentMonday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_MONDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				if (calendar.compareTo(now) <= 0) {
					calendar.add(Calendar.WEEK_OF_MONTH, 1);
				}
				Log.e("time alarm", "" + calendar.getTime());
				alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 
						MILLISECOND_PER_WEEK, pendingIntentMonday);
			}
			if (arrayAlarm.get(position).getRepeat()[TUESDAY_INDEX]) {
				PendingIntent pendingIntentTuesday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_TUEDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
				if (calendar.compareTo(now) <= 0) {
					calendar.add(Calendar.WEEK_OF_MONTH, 1);
				}
				Log.e("time alarm", "" + calendar.getTime());
				alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 
						MILLISECOND_PER_WEEK, pendingIntentTuesday);
			}
			if (arrayAlarm.get(position).getRepeat()[WEDNESDAY_INDEX]) {
				PendingIntent pendingIntentWesdnesday = PendingIntent.getActivity(getActivity().getApplicationContext(),
								ALARM_CODE_WEDNESDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
				if (calendar.compareTo(now) <= 0) {
					calendar.add(Calendar.WEEK_OF_MONTH, 1);
				}
				Log.e("time alarm", "" + calendar.getTime());
				alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 
						MILLISECOND_PER_WEEK, pendingIntentWesdnesday); 
			} 
			if (arrayAlarm.get(position).getRepeat()[THURSDAY_INDEX]) { 
				PendingIntent pendingIntentThurday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_THURSDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
				if (calendar.compareTo(now) <= 0) {   
					calendar.add(Calendar.WEEK_OF_MONTH, 1);   
				} 
				Log.e("time alarm", "" + calendar.getTime());
				alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 
						MILLISECOND_PER_WEEK, pendingIntentThurday);
			}
			if (arrayAlarm.get(position).getRepeat()[FRIDAY_INDEX]) {
				PendingIntent pendingIntentFriday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_FRIDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
				if (calendar.compareTo(now) <= 0) {
					calendar.add(Calendar.WEEK_OF_MONTH, 1);
				}
				Log.e("time alarm", "" + calendar.getTime());
				alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 
						MILLISECOND_PER_WEEK, pendingIntentFriday);
			}
			if (arrayAlarm.get(position).getRepeat()[SATURDAY_INDEX]) {
				PendingIntent pendingIntentSaturday = PendingIntent.getActivity(getActivity().getApplicationContext(),
								ALARM_CODE_SATURDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
				if (calendar.compareTo(now) <= 0) {
					calendar.add(Calendar.WEEK_OF_MONTH, 1);
				}
				Log.e("time alarm", "" + calendar.getTime());
				alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 
						MILLISECOND_PER_WEEK, pendingIntentSaturday);
			}
			if (arrayAlarm.get(position).getRepeat()[SUNDAY_INDEX]) {
				PendingIntent pendingIntentSunday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_SUNDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				if (calendar.compareTo(now) <= 0) {
					calendar.add(Calendar.WEEK_OF_MONTH, 1);
				}
				Log.e("time alarm", "" + calendar.getTime());
				alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 
						MILLISECOND_PER_WEEK, pendingIntentSunday);
			}
		}
	}

	public void cancelAlarm(int position) {
		ArrayList<AlarmData> arrayAlarm = WatchActivity.sysManager.getArrayAlarm();
		int countRepeat = 0;
		for (int index = 0; index < arrayAlarm.get(position).getRepeat().length; index++) {
			if (arrayAlarm.get(position).getRepeat()[index]) {
				countRepeat++;
			}
		}
		if (countRepeat == 0) {
			pendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), position,
					intent, PendingIntent.FLAG_CANCEL_CURRENT);
			alarmManager.cancel(pendingIntent);
		} else {
			if (arrayAlarm.get(position).getRepeat()[MONDAY_INDEX]) {
				PendingIntent pendingIntentMonday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_MONDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				alarmManager.cancel(pendingIntentMonday);
			}
			if (arrayAlarm.get(position).getRepeat()[TUESDAY_INDEX]) {
				PendingIntent pendingIntentTuesday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_TUEDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				alarmManager.cancel(pendingIntentTuesday);
			}
			if (arrayAlarm.get(position).getRepeat()[WEDNESDAY_INDEX]) {
				PendingIntent pendingIntentWesdnesday = PendingIntent.getActivity(getActivity().getApplicationContext(),
								ALARM_CODE_WEDNESDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				alarmManager.cancel(pendingIntentWesdnesday);
			}
			if (arrayAlarm.get(position).getRepeat()[THURSDAY_INDEX]) {
				PendingIntent pendingIntentThurday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_THURSDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				alarmManager.cancel(pendingIntentThurday);
			}
			if (arrayAlarm.get(position).getRepeat()[FRIDAY_INDEX]) {
				PendingIntent pendingIntentFriday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_FRIDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				alarmManager.cancel(pendingIntentFriday);
			}
			if (arrayAlarm.get(position).getRepeat()[SATURDAY_INDEX]) {
				PendingIntent pendingIntentSaturday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_SATURDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				alarmManager.cancel(pendingIntentSaturday);
			}
			if (arrayAlarm.get(position).getRepeat()[SUNDAY_INDEX]) {
				PendingIntent pendingIntentSunday = PendingIntent.getActivity(getActivity().getApplicationContext(),
						ALARM_CODE_SUNDAY + position, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				alarmManager.cancel(pendingIntentSunday);
			}
		}
	}

	private ImageView addAlarm;
	private ListView listAlarm;
	private TextView nameBarAlarm;
	private AlarmAdapter adapter;
	private LinearLayout listAlarmLayout;
	private AlarmManager alarmManager;
	private Intent intent;
	private PendingIntent pendingIntent;
}
