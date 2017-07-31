package com.bkav.ui.pedometer;

import com.bkav.home.system.Platform;
import com.bkav.test.HealthCare;
import com.bkav.test.Person;
import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.setting.NumberPickerDialog;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PedometerScreen extends Fragment {

	public static final String STEP_DISTANCE = "step_distance";
	public static final String STEPS = "steps";
	public static final String DISTANCE = "distance";
	public static final String CALORIES = "calories";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pedometer, container, false);
		stepDistanceBar = (TextView) view.findViewById(R.id.stepDistanceBar);
		stepIcon = (ImageView) view.findViewById(R.id.stepIcon);
		stepCount = (TextView) view.findViewById(R.id.stepCount);
		distance = (TextView) view.findViewById(R.id.distance);
		calories = (TextView) view.findViewById(R.id.calories);
		stepCountLabel = (TextView) view.findViewById(R.id.stepCountLabel);
		distanceLabel = (TextView) view.findViewById(R.id.distanceLabel);
		caloriesLabel = (TextView) view.findViewById(R.id.caloriesLabel);

		person = new Person(height, weight);
		healthCare = new HealthCare(getActivity(), person);
		healthCare.HealthCareListener();
		backupStep = WatchActivity.sysManager.getPreferences()
				.getInt("step", 0);
		backupDistance = Double.parseDouble(WatchActivity.sysManager
				.getPreferences().getString("distance", "" + 0));
		backupCalories = Double.parseDouble(WatchActivity.sysManager
				.getPreferences().getString("calories", "" + 0));

		stepIcon.setBackgroundResource(R.drawable.loading);
		updateLanguageHealth();
		AnimationDrawable animationDrawable = (AnimationDrawable) stepIcon
				.getBackground();
		animationDrawable.setCallback(stepIcon);
		animationDrawable.start();

		Typeface typeFaceRegular = WatchActivity.sysManager
				.getTypeFaceRegular(getActivity().getAssets());
		Typeface typeFaceBold = WatchActivity.sysManager
				.getTypeFaceBold(getActivity().getAssets());

		if (typeFaceBold != null && typeFaceRegular != null) {
			stepDistanceBar.setTypeface(typeFaceRegular);

			stepCount.setTypeface(typeFaceBold);
			distance.setTypeface(typeFaceBold);
			calories.setTypeface(typeFaceBold);

			stepCountLabel.setTypeface(typeFaceRegular);
			distanceLabel.setTypeface(typeFaceRegular);
			caloriesLabel.setTypeface(typeFaceRegular);
		}
		countDownTimer = new CountDownTimer(1000000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				currentStep = backupStep + healthCare.getStepCounter();
				currentDistance = backupDistance + healthCare.getDistance();
				currentCalories = backupCalories + healthCare.getCalories();

				if (currentStep != lastStep) {
					Platform.connection.write("{health:{CountDistanceMethod:"
							+ currentDistance + "}}");
					Platform.connection.write("{health:{CountStepMethod:"
							+ currentStep + "}}");
					Platform.connection.write("{health:{CountCaloriesMethod:"
							+ currentCalories + "}}");
				}
				Editor editor = WatchActivity.sysManager.getPreferences()
						.edit(); 
				editor.putInt("step", currentStep);
				editor.putString("calories", "" + currentCalories);
				editor.putString("distance", "" + currentDistance);
				editor.commit();
				if (currentStep >= WatchActivity.sysManager.getPreferences()
						.getInt("target", 2000)) {
					Log.e("HealthCareProcess",
							"Ban da dat duoc target 2000 buoc");
					editor.putInt("target", (currentStep / 2000 + 1) * 2000);
					editor.commit();
					Log.e("",
							"target:"
									+ WatchActivity.sysManager.getPreferences()
											.getInt("target", 0));
					
					Message msg = new Message();
					msg.what = WatchActivity.MOTION_COMPLETE_TARGET;
					WatchActivity.sysManager.getMessageHandlerWatch()
							.sendMessage(msg);
				}
				
				stepCount.setText("" + currentStep);   
				distance.setText( "" + (int)currentDistance); 
				calories.setText("" + (int)currentCalories);
				lastStep = currentStep;
			}

			@Override
			public void onFinish() {
				countDownTimer.start();
			}
		};
		
		countDownTimer.start();
		return view;
	}

	public void updatePerson() {
		person = new Person(height, weight);
	}

	public void updateLanguageHealth() {
		LanguageManager languageManager = WatchActivity.sysManager
				.getLanguageManager();
		if (languageManager != null) {
			stepDistanceBar.setText(languageManager.getLabel(STEP_DISTANCE)
					.getValue());
			stepCountLabel.setText(languageManager.getLabel(STEPS).getValue());
			distanceLabel
					.setText(languageManager.getLabel(DISTANCE).getValue());
			caloriesLabel
					.setText(languageManager.getLabel(CALORIES).getValue());
		}
	}

	private TextView stepDistanceBar;
	private TextView stepCount;
	private TextView distance;
	private TextView calories;
	private TextView stepCountLabel;
	private TextView distanceLabel;
	private TextView caloriesLabel;
	private ImageView stepIcon;

	public static double height = 1.8;
	public static double weight = 70;
	public Person person;
	public HealthCare healthCare;
	public int backupStep = 0;
	public int currentStep = 0;
	public int lastStep = 0;
	public double backupCalories = 0;
	public double currentCalories = 0;
	public double backupDistance = 0;
	public double currentDistance = 0;
	public CountDownTimer countDownTimer;
}
