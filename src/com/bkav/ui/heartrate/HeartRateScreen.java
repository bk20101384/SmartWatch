package com.bkav.ui.heartrate;

import com.bkav.home.system.Platform;
import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HeartRateScreen extends Fragment implements SensorEventListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.heart_rate, container, false);
		textView = (TextView) view.findViewById(R.id.heartRate);
		float heartbeat = WatchActivity.sysManager.getPreferences().getFloat(
				"HEART_BEAT", 0);
		textView.setText(heartbeat + "");
		healthCareBar = (TextView) view.findViewById(R.id.healthCareBar);
		loadingCircle = (ImageView) view.findViewById(R.id.loadingCircle); 
		loadingCircle.setBackgroundResource(R.drawable.start_heart_rate);
		updateLanguageHealthCare();
		manager = (SensorManager) getActivity().getApplicationContext()
				.getSystemService(Context.SENSOR_SERVICE);
		sensor = manager.getDefaultSensor(TYPE_HEART_RATED);
		loadingCircle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				textView.setText("Calculating....");
				manager.registerListener(HeartRateScreen.this, sensor,
						SensorManager.SENSOR_DELAY_NORMAL);
				loadingCircle.setBackgroundResource(R.drawable.heartrate);
				animationDrawable = (AnimationDrawable) loadingCircle
						.getBackground();
				animationDrawable.setCallback(loadingCircle);
				animationDrawable.start();
				toggleButton.setEnabled(false);
				new CountDownTimer(25000, 5000) {

					@Override 
					public void onTick(long millisUntilFinished) {

					}

					@Override
					public void onFinish() {
						animationDrawable.stop();
						toggleButton.setEnabled(true);
						loadingCircle
								.setBackgroundResource(R.drawable.start_heart_rate);

					}
				}.start(); 

			}
		});

		toggleButton = (ToggleButton) view.findViewById(R.id.toggleButton);
		toggleButton.setChecked(WatchActivity.sysManager.getPreferences()
				.getBoolean("IS_ACTIVE", false));

		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Editor editor = WatchActivity.sysManager.getPreferences()
						.edit();
				editor.putBoolean("IS_ACTIVE", isChecked);
				editor.commit();
			}
		});

		return view;
	}

	@Override
	public void onStop() {
		super.onStop();
		manager.unregisterListener(this, sensor);
	}

	public void updateLanguageHealthCare() {
		LanguageManager languageManager = WatchActivity.sysManager
				.getLanguageManager();
		if (languageManager != null) {
			healthCareBar.setText(languageManager.getLabel("health_care_bar")
					.getValue());
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		try {
			if (event.values[0] != 0f) {
				Platform.connection.write("{health:{GetStatusActiveMethod:"
						+ String.valueOf(toggleButton.isChecked()) + "}}");
				Log.e("heartbeat: ", event.values[0]
						+ "-------------------------");
				Editor editor = WatchActivity.sysManager.getPreferences()
						.edit();
				editor.putFloat("HEART_BEAT", event.values[0]);
				editor.commit();
				textView.setText(event.values[0] + "");
				Platform.connection.write("{health:{GetHeartRateMethod:"
						+ String.valueOf(event.values[0]) + "}}");
				manager.unregisterListener(this, sensor);
				animationDrawable.stop();
				toggleButton.setEnabled(true);
				loadingCircle
						.setBackgroundResource(R.drawable.start_heart_rate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ToggleButton toggleButton;
	private TextView textView = null;
	private TextView healthCareBar;
	private ImageView loadingCircle;
	AnimationDrawable animationDrawable;
	private SensorManager manager;
	private Sensor sensor;
	private static final int TYPE_HEART_RATED = 21;
}
