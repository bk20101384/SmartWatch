package com.bkav.ui.timer;

import java.util.concurrent.TimeUnit;

import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class TimerScreen extends Fragment {

	public static final int MIN_HOUR = 0;
	public static final int MAX_HOUR = 12;
	public static final int MIN_MINUTE = 0;
	public static final int MAX_MINUTE = 60;
	public static final int MILISECOND = 1000;
	public static final String TIMER = "timer";
	public static final String STOP = "stop";
	public static final String START = "start";
	public static final int REQUEST_TIMER = 1;
	public static final String TIME_RESULT_CODE = "time_result";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.timer, container, false);
		timerContainer = (TextView) view.findViewById(R.id.timerContainer);
		startTimer = (Button) view.findViewById(R.id.startTimer);
		nameBarTimer = (TextView) view.findViewById(R.id.nameBarTimer);
		timePicker = "00:00:00";
		timerContainer.setText(timePicker);
		updateValueForUI(millis);
		Typeface typeFaceLight = WatchActivity.sysManager.getTypeFaceLight(getActivity().getAssets());
		if (typeFaceLight != null) {
			startTimer.setTypeface(typeFaceLight);
		}
		updateLanguageStopwatch();
		LanguageManager languageManager = WatchActivity.sysManager.getLanguageManager();
		if (languageManager != null) {
			if (!WatchActivity.sysManager.isTimerStart()) {
				startTimer.setText(languageManager.getLabel(START).getValue());
			} else {
				startTimer.setText(languageManager.getLabel(STOP).getValue());
			}
		} 
		
//		if (WatchActivity.sysManager.isTimerStart()) {
//			startTimer.setBackgroundResource(R.drawable.defense_stop);
//		} else {
//			WatchActivity.sysManager.setTimerStart(true);
//			startTimer.setBackgroundResource(R.drawable.defense_start);
//		}
		
		
		timerContainer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), PickerTimerActivity.class);
				startActivityForResult(intent, REQUEST_TIMER);
			}
		});

		
		
		startTimer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (WatchActivity.sysManager.isTimerStart()) {
					WatchActivity.sysManager.setTimerStart(false);
					startTimer.setText(WatchActivity.sysManager.getLanguageManager().getLabel(START).getValue());
					startTimer.setBackgroundResource(R.drawable.defense_start);
					if (counter != null) {
						counter.cancel();
					} 
				} else {
					WatchActivity.sysManager.setTimerStart(true);
					startTimer.setText(STOP);
					startTimer.setText(WatchActivity.sysManager.getLanguageManager().getLabel(STOP).getValue());
					startTimer.setBackgroundResource(R.drawable.defense_stop);
					counter = new Counter(millis, MILISECOND);
					counter.start();
				}
			}
		});

		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_TIMER) {
			if (resultCode == Activity.RESULT_OK) {
				if (counter != null) {
					counter.cancel();
				}
				WatchActivity.sysManager.setTimerStart(false);
				startTimer.setText(WatchActivity.sysManager.getLanguageManager().getLabel(START).getValue());
				startTimer.setBackgroundResource(R.drawable.defense_start);
				timePicker = data.getStringExtra(TIME_RESULT_CODE);
				timerContainer.setText(timePicker);
				String time[] = timePicker.split(":");
				millis = Integer.parseInt(time[0]) * MAX_MINUTE * MAX_MINUTE * MILISECOND + Integer.parseInt(time[1]) * MAX_MINUTE * MILISECOND
						+ Integer.parseInt(time[2]) * MILISECOND;
			}
		}
	}

	public void updateValueForUI(long millis) { 
		
		String value = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		timerContainer.setText(value);
	}

	public void updateLanguageStopwatch() {
		LanguageManager languageManager = WatchActivity.sysManager.getLanguageManager();
		if (languageManager != null) {
			nameBarTimer.setText(languageManager.getLabel(TIMER).getValue());
			if (!WatchActivity.sysManager.isTimerStart()) {
				startTimer.setText(languageManager.getLabel(START).getValue());
			} else {
				startTimer.setText(languageManager.getLabel(STOP).getValue());
			}
		}
	}

	public class Counter extends CountDownTimer {
		public Counter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			Log.e("", "milis: " + millisInFuture);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			millis = millisUntilFinished;
			updateValueForUI(millis);
		}

		@Override
		public void onFinish() { 
			Log.e("TimerScreen", "Finishhhhh");
		}
	}

	private Counter counter;
	private TextView timerContainer;
	private TextView nameBarTimer;
	private Button startTimer;
	private String timePicker;
	private long millis = 0;
}
