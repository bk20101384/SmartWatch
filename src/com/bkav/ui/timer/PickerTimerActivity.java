package com.bkav.ui.timer;

import com.example.bwatchdevice.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;

public class PickerTimerActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picker_timer);
		
		hoursPickerTimer = (NumberPicker) findViewById(R.id.hoursPickerTimer);
		minutesPickerTimer = (NumberPicker) findViewById(R.id.minutesPickerTimer);
		secondsPickerTimer = (NumberPicker) findViewById(R.id.secondsPickerTimer);
		setTimer = (Button) findViewById(R.id.setTimer);
		cancelTimer = (Button) findViewById(R.id.cancelTimer);
		
		hoursPickerTimer.setMaxValue(12);
		hoursPickerTimer.setMinValue(0);
		minutesPickerTimer.setMaxValue(60);
		minutesPickerTimer.setMinValue(0);
		secondsPickerTimer.setMaxValue(60);
		secondsPickerTimer.setMinValue(0);
		
		setTimer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String timeResult = String.format("%02d", hoursPickerTimer.getValue())
						+ ":" + String.format("%02d", minutesPickerTimer.getValue())
						+ ":" + String.format("%02d", secondsPickerTimer.getValue());
				Intent intent = new Intent();
				intent.putExtra(TimerScreen.TIME_RESULT_CODE, timeResult);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		cancelTimer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private NumberPicker hoursPickerTimer;
	private NumberPicker minutesPickerTimer;
	private NumberPicker secondsPickerTimer;
	private Button setTimer;
	private Button cancelTimer;
}
