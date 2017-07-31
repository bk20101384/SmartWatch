package com.bkav.ui.pedometer;

import com.bkav.ui.clock.CustomDigitalClockIcon;
import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class MotionDialog extends Activity {
	public String contentMotion = "warning";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.motion_dialog);
		
		Bundle bundle = getIntent().getExtras();
		contentMotion = bundle.getString("contentMotion");
		
		time = (CustomDigitalClockIcon) findViewById(R.id.timeMotionDialog);
		contentMotionDialog = (TextView) findViewById(R.id.contentMotionDialog);
		
		contentMotionDialog.setText(contentMotion);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(pattern, 0);
		contentMotionDialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Typeface typeFaceLight = WatchActivity.sysManager.getTypeFaceLight(getAssets());
		if (typeFaceLight != null) {
			time.setTypeface(typeFaceLight);
			contentMotionDialog.setTypeface(typeFaceLight);
		}

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		WatchActivity.sysManager.setActiveCallActivity(true);
	}
	@Override
	protected void onStop() { 
		super.onStop();
		WatchActivity.sysManager.setActiveCallActivity(false);
		vibrator.cancel();
	}
 
	private TextView contentMotionDialog;
	private CustomDigitalClockIcon time;
	private Vibrator vibrator;
	private long pattern[] = { 100, 900 };
}
