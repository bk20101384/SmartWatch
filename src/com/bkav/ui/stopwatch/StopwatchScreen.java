package com.bkav.ui.stopwatch;

import java.util.concurrent.TimeUnit;

import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class StopwatchScreen extends Fragment {
	public static final long MAX_TIME = 36000000;
	public static final int INTERVAL_TIME = 100;
	public static final int SECONDS = 60;
	public static final int MILISECOND = 1000;
	public static final String START = "start";
	public static final String STOP = "stop";
	public static final String RESTART = "restart";
	public static final String LAP = "lap";
	public static final String STOPWATCH = "stopwatch";
	public static final String TIME_DEFAULT = "00:00:00";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.stopwatch, container, false);

		time = (TextView) view.findViewById(R.id.time);
		nameBarStopWatch = (TextView) view.findViewById(R.id.nameBarStopWatch);
		start = (Button) view.findViewById(R.id.start);
		lap = (Button) view.findViewById(R.id.lap); 
		lapIcon = (ListView) view.findViewById(R.id.lapIcon);

		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(getActivity().getAssets());
	    Typeface typeFaceLight = WatchActivity.sysManager.getTypeFaceLight(getActivity().getAssets());
	    
	    if (typeFaceLight != null && typeFaceRegular != null) {
	    	time.setTypeface(typeFaceRegular);
	    	start.setTypeface(typeFaceLight);
	    	lap.setTypeface(typeFaceLight);
	    }
		updateLanguageStopwatch();
		
//		if (WatchActivity.sysManager.isStopwatchStart()) {
//			start.setBackgroundResource(R.drawable.defense_stop);
//		} else {
//			WatchActivity.sysManager.setStopwatchStart(true);
//			start.setBackgroundResource(R.drawable.defense_start);
//		}
		
		
		adapter = new LapAdapter(getActivity(), WatchActivity.sysManager.getListLap());
		lapIcon.setAdapter(adapter);

		setListLap();

		LanguageManager languageManager = WatchActivity.sysManager.getLanguageManager();
		if (languageManager != null) {
			if (WatchActivity.sysManager.isStopwatchStart()) {
				start.setText("Stop");
				lap.setText("Lap");
				start.setText(languageManager.getLabel(STOP).getValue());
				lap.setText(languageManager.getLabel(LAP).getValue());
			} else {
				start.setText(languageManager.getLabel(START).getValue());
				lap.setText(languageManager.getLabel(RESTART).getValue());
			}
		}

		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LanguageManager languageManager = WatchActivity.sysManager.getLanguageManager();
				if (languageManager != null) {
					if (WatchActivity.sysManager.isStopwatchStart()) {
						WatchActivity.sysManager.setStopwatchStart(false);
						start.setText(languageManager.getLabel(START).getValue());
						start.setBackgroundResource(R.drawable.defense_start);
						lap.setText(languageManager.getLabel(RESTART).getValue());
						if (counter != null) {
							counter.cancel(); 
						}
					} else {
						WatchActivity.sysManager.setStopwatchStart(true);
						start.setText(languageManager.getLabel(STOP).getValue());
						lap.setText(languageManager.getLabel(LAP).getValue());
						start.setBackgroundResource(R.drawable.defense_stop);
						counter = new Counter(MAX_TIME, INTERVAL_TIME);
						counter.start();
					}
				}
			}
		});

		lap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (WatchActivity.sysManager.isStopwatchStart()) {
					String valueLap = String.format(
							"%02d:%02d:%01d",
							TimeUnit.MILLISECONDS.toMinutes(millis),
							TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MILLISECONDS.toMinutes(millis) * SECONDS,
							(TimeUnit.MILLISECONDS.toMillis(millis) - TimeUnit.MILLISECONDS.toSeconds(millis) * MILISECOND)/INTERVAL_TIME);
					WatchActivity.sysManager.addListLap(valueLap);
				} else {
					WatchActivity.sysManager.clearListLap();
					millis = 0;
					time.setText(TIME_DEFAULT);
				}
				adapter.notifyDataSetChanged();
				lapIcon.invalidateViews();
			}
		});
		return view;
	}

	public void setListLap() {
		
	}

	public void updateValue(long millis) {
		String value = String.format(
				"%02d:%02d:%01d",
				TimeUnit.MILLISECONDS.toMinutes(millis),
				TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MILLISECONDS.toMinutes(millis) * SECONDS,
				(TimeUnit.MILLISECONDS.toMillis(millis) - TimeUnit.MILLISECONDS.toSeconds(millis) * MILISECOND) / INTERVAL_TIME);
		time.setText(value);
	}
	
	public void updateLanguageStopwatch() {
		nameBarStopWatch.setText(WatchActivity.sysManager.getLanguageManager().getLabel(STOPWATCH).getValue());
		if (WatchActivity.sysManager.isStopwatchStart()) {
			start.setText(WatchActivity.sysManager.getLanguageManager().getLabel(STOP).getValue());
			lap.setText(WatchActivity.sysManager.getLanguageManager().getLabel(LAP).getValue());
		} else {
			start.setText(WatchActivity.sysManager.getLanguageManager().getLabel(START).getValue());
			lap.setText(WatchActivity.sysManager.getLanguageManager().getLabel(RESTART).getValue());
		}
	}

	private TextView time;
	private TextView nameBarStopWatch;
	private Button start;
	private Button lap; 
	private ListView lapIcon;
	private LapAdapter adapter;
	private long millis;
	private Counter counter;

	class Counter extends CountDownTimer {

		public Counter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			
		}

		@Override
		public void onTick(long millisUntilFinished) {
			millis = millis + INTERVAL_TIME;
			updateValue(millis);
		}
	}
}
