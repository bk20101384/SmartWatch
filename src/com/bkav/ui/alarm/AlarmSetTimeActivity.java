package com.bkav.ui.alarm;

import com.example.bwatchdevice.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;

public class AlarmSetTimeActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_set_time);
		Bundle bundle = getIntent().getExtras();
		hoursPicker = (NumberPicker) findViewById(R.id.hoursAlarmCreate);
		minutesPicker = (NumberPicker) findViewById(R.id.minutesAlarmCreate);
		setAlarmTime = (Button) findViewById(R.id.setAlarmTime);
		cancelAlarmTime = (Button) findViewById(R.id.cancelAlarmTime);
		hoursPicker.setMinValue(AlarmScreen.MIN_HOUR);
		hoursPicker.setMaxValue(AlarmScreen.MAX_HOUR);
		minutesPicker.setMinValue(AlarmScreen.MIN_MINUTE);
		minutesPicker.setMaxValue(AlarmScreen.MAX_MINUTE);
		hoursPicker.setValue(bundle.getInt(AlarmSetting.ALARM_HOURS_CODE, AlarmSetting.HOURS_DEFAULT));
		minutesPicker.setValue(bundle.getInt(AlarmSetting.ALARM_MINUTES_CODE, AlarmSetting.MINUTES_DEFAULT));
		
		setAlarmTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(AlarmSetting.ALARM_HOURS_CODE, hoursPicker.getValue());
				intent.putExtra(AlarmSetting.ALARM_MINUTES_CODE, minutesPicker.getValue());
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		cancelAlarmTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private NumberPicker hoursPicker;
	private NumberPicker minutesPicker;
	private Button setAlarmTime;
	private Button cancelAlarmTime;
}
