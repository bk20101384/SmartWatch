package com.bkav.ui.alarm;

import com.example.bwatchdevice.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class AlarmRepeatDateActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_date_repeat);
		monday = (CheckBox) findViewById(R.id.monday);
		tuesday = (CheckBox) findViewById(R.id.tuesday);
		wednesday = (CheckBox) findViewById(R.id.wednesday);
		thurday = (CheckBox) findViewById(R.id.thurday);
		friday = (CheckBox) findViewById(R.id.friday);
		saturday = (CheckBox) findViewById(R.id.saturday);
		sunday = (CheckBox) findViewById(R.id.sunday);
		setAlarmRepeat = (Button) findViewById(R.id.setAlarmRepeat);
		cancelAlarmRepeat = (Button) findViewById(R.id.cancelAlarmRepeat);
		
		Bundle bundle = getIntent().getExtras(); 
		repeatAlarm = bundle.getBooleanArray(AlarmSetting.ALARM_REPEAT_CODE);
		monday.setChecked(repeatAlarm[AlarmScreen.MONDAY_INDEX]);
		tuesday.setChecked(repeatAlarm[AlarmScreen.TUESDAY_INDEX]);
		wednesday.setChecked(repeatAlarm[AlarmScreen.WEDNESDAY_INDEX]);
		thurday.setChecked(repeatAlarm[AlarmScreen.THURSDAY_INDEX]);
		friday.setChecked(repeatAlarm[AlarmScreen.FRIDAY_INDEX]);
		saturday.setChecked(repeatAlarm[AlarmScreen.SATURDAY_INDEX]);
		sunday.setChecked(repeatAlarm[AlarmScreen.SUNDAY_INDEX]);

		setAlarmRepeat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				repeatAlarm[AlarmScreen.MONDAY_INDEX] = monday.isChecked();
				repeatAlarm[AlarmScreen.TUESDAY_INDEX] = tuesday.isChecked();
				repeatAlarm[AlarmScreen.WEDNESDAY_INDEX] = wednesday.isChecked();
				repeatAlarm[AlarmScreen.THURSDAY_INDEX] = thurday.isChecked();
				repeatAlarm[AlarmScreen.FRIDAY_INDEX] = friday.isChecked();
				repeatAlarm[AlarmScreen.SATURDAY_INDEX] = saturday.isChecked();
				repeatAlarm[AlarmScreen.SUNDAY_INDEX] = sunday.isChecked();
				intent.putExtra(AlarmSetting.ALARM_REPEAT_CODE, repeatAlarm);
				setResult(RESULT_OK, intent);
				finish();  
			}
		});

		cancelAlarmRepeat.setOnClickListener(new OnClickListener() {  

			@Override    
			public void onClick(View v) {
				finish();
			}
		});
	}

	private CheckBox sunday;
	private CheckBox monday;
	private CheckBox tuesday;
	private CheckBox wednesday;
	private CheckBox thurday;
	private CheckBox friday;
	private CheckBox saturday;
	private Button setAlarmRepeat;
	private Button cancelAlarmRepeat;
	private boolean[] repeatAlarm;
}
