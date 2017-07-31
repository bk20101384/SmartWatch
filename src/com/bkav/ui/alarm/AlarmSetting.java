package com.bkav.ui.alarm;

import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AlarmSetting extends Activity {

	public static final int REQUEST_ALARM_REPEAT = 1;
	public static final int RESULT_ALARM_REPEAT = 2;
	public static final int REQUEST_ALARM_TIME = 3;
	public static final int RESULT_ALARM_TIME = 4;
	public static final int HOURS_DEFAULT = 12;
	public static final int MINUTES_DEFAULT = 0;
	public static final String ALARM_REPEAT_CODE = "alarm_repeat";
	public static final String ALARM_HOURS_CODE = "hours";
	public static final String ALARM_MINUTES_CODE = "minutes";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_setting);
		timeAlarmSettingContent = (TextView) findViewById(R.id.timeAlarmSettingContent);
		repeatAlarmSettingContent = (TextView) findViewById(R.id.repeatAlarmSettingContent);
		cancelAlarm = (Button) findViewById(R.id.cancelAlarm);
		doneAlarm = (Button) findViewById(R.id.doneAlarm);  
		hoursPicker = HOURS_DEFAULT;
		minutesPicker = MINUTES_DEFAULT;
		timeAlarmSettingContent.setText(String.format("%02d", hoursPicker) + ":" + String.format("%02d", minutesPicker));
		repeatAlarm = new boolean[7];
		Bundle bundle = getIntent().getExtras();
		alarmRequest = bundle.getInt(AlarmScreen.REQUEST_ALARM);
		position = bundle.getInt(AlarmScreen.POSITION);
		if(alarmRequest == AlarmScreen.REQUEST_EDIT_ALARM) {
			String timePicker = WatchActivity.sysManager.getArrayAlarm().get(position).getTime();
			repeatAlarm = WatchActivity.sysManager.getArrayAlarm().get(position).getRepeat();
			
			String[] time = timePicker.split(":");
			hoursPicker = Integer.parseInt(time[0]);
			minutesPicker = Integer.parseInt(time[1]);
			timeAlarmSettingContent.setText(timePicker);
			getDateRepeat();
//			Log.e("", "position:" + position);
//			if (position != 1992) {
//				Log.e("", "size:" + WatchActivity.sysManager.getArrayAlarm().size());
//				repeatAlarm = WatchActivity.sysManager.getArrayAlarm().get(position).getRepeat();
//			} 
		}
		
		timeAlarmSettingContent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AlarmSetTimeActivity.class);
				intent.putExtra(ALARM_HOURS_CODE, hoursPicker);
				intent.putExtra(ALARM_MINUTES_CODE, minutesPicker);
				startActivityForResult(intent, REQUEST_ALARM_TIME);
			}
		});

		repeatAlarmSettingContent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AlarmRepeatDateActivity.class);
				intent.putExtra(ALARM_REPEAT_CODE, repeatAlarm);
				startActivityForResult(intent, REQUEST_ALARM_REPEAT);
			}
		});

		doneAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String timePicker = String.format("%02d", hoursPicker) + ":" + String.format("%02d", minutesPicker);
				Log.e("Alarm setting", "" + timePicker);
				if (alarmRequest == AlarmScreen.REQUEST_ADD_ALARM) {
					AlarmData alarmData = new AlarmData(timePicker, repeatAlarm);
					WatchActivity.sysManager.addArrayAlarm(alarmData);
				} else if (alarmRequest == AlarmScreen.REQUEST_EDIT_ALARM) {
					WatchActivity.sysManager.getArrayAlarm().get(position).update(timePicker, repeatAlarm);
				}
				setResult(RESULT_OK);
				finish();
			}
		});

		cancelAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ALARM_REPEAT) {
			if (resultCode == RESULT_OK) {
				boolean[] valueResult = data.getBooleanArrayExtra(ALARM_REPEAT_CODE);
				repeatAlarm = valueResult;
				getDateRepeat();
			}
		} else if (requestCode == REQUEST_ALARM_TIME) {
			if (resultCode == RESULT_OK) {
				hoursPicker = data.getIntExtra(ALARM_HOURS_CODE, HOURS_DEFAULT);
				minutesPicker = data.getIntExtra(ALARM_MINUTES_CODE, MINUTES_DEFAULT);
				timeAlarmSettingContent.setText(String.format("%02d", hoursPicker) + ":" + String.format("%02d", minutesPicker));
			}
		}
	}

	private int hoursPicker;
	private int minutesPicker;
	private Button doneAlarm;
	private Button cancelAlarm;
	private TextView repeatAlarmSettingContent;
	private TextView timeAlarmSettingContent;
	private boolean[] repeatAlarm;
	private int alarmRequest = 0;
	private int position = 1992;

	private void getDateRepeat() {
		StringBuffer dateRepeat = new StringBuffer();
		int countRepeat = 0;
		for (int index = 0; index < repeatAlarm.length; index++) {
			if (repeatAlarm[index]) {
				countRepeat++;
			}
		}
		if (countRepeat == 7) {
			repeatAlarmSettingContent.setText("Every day");
		} else if (countRepeat == 0) {
			repeatAlarmSettingContent.setText("Never");
		} else {
			if (repeatAlarm[AlarmScreen.MONDAY_INDEX]) {
				dateRepeat.append(AlarmScreen.MONDAY);
			}
			if (repeatAlarm[AlarmScreen.TUESDAY_INDEX]) {
				dateRepeat.append(AlarmScreen.TUESDAY);
			}
			if (repeatAlarm[AlarmScreen.WEDNESDAY_INDEX]) {
				dateRepeat.append(AlarmScreen.WEDNESDAY);
			}
			if (repeatAlarm[AlarmScreen.THURSDAY_INDEX]) {
				dateRepeat.append(AlarmScreen.THURSDAY);
			}
			if (repeatAlarm[AlarmScreen.FRIDAY_INDEX]) {
				dateRepeat.append(AlarmScreen.FRIDAY);
			}
			if (repeatAlarm[AlarmScreen.SATURDAY_INDEX]) {
				dateRepeat.append(AlarmScreen.SATURDAY);
			}
			if (repeatAlarm[AlarmScreen.SUNDAY_INDEX]) {
				dateRepeat.append(AlarmScreen.SUNDAY);
			}
			repeatAlarmSettingContent.setText(dateRepeat.toString());
		}
	}
}
