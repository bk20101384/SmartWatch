package com.bkav.ui.setting;

import com.bkav.ui.watchactivity.WatchActivity;
import com.example.bwatchdevice.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

public class TimeoutSettingDialog extends Activity {
	
	public static final int FIFTEEN_SECONDS = 15000;
	public static final int THIRTY_SECONDS = 30000;
	public static final int ONE_MINUTE = 60000;
	public static final int TWO_MINUTES = 120000;
	public static final String FIFTEEN_SECONDS_SHORT = "fifteen_seconds_short";
	public static final String THIRTY_SECONDS_SHORT = "thirty_seconds_short";
	public static final String ONE_MINUTE_SHORT = "one_minute_short";
	public static final String TWO_MINUTES_SHORT = "two_minutes_short";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeout);

		messageUpdateTimeout = new Message();
		messageUpdateTimeout.what = WatchActivity.UPDATE_SCREEN_TIMEOUT;
		fifteenSeconds = (RadioButton) findViewById(R.id.fifteenSeconds);
		thirtySeconds = (RadioButton) findViewById(R.id.thirtySeconds);
		oneMinute = (RadioButton) findViewById(R.id.oneMinute);
		twoMinutes = (RadioButton) findViewById(R.id.twoMinutes);

		switch (WatchActivity.sysManager.getScreenTimeout()) {
		case FIFTEEN_SECONDS:
			fifteenSeconds.setChecked(true);
			break;
		case THIRTY_SECONDS:
			thirtySeconds.setChecked(true);
			break;
		case ONE_MINUTE:
			oneMinute.setChecked(true);
			break;
		case TWO_MINUTES:
			twoMinutes.setChecked(true);
			break;
		default:  
			fifteenSeconds.setChecked(true);
			break;
		}
		
		updateLanguageTimeoutSetting();

		oneMinute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WatchActivity.sysManager.setScreenTimeout(ONE_MINUTE);
				Settings.System.putInt(getContentResolver(),
						Settings.System.SCREEN_OFF_TIMEOUT,
						WatchActivity.sysManager.getScreenTimeout());
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageUpdateTimeout);
				onBackPressed();
			}
		});

		twoMinutes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WatchActivity.sysManager.setScreenTimeout(TWO_MINUTES);
				Settings.System.putInt(getContentResolver(),
						Settings.System.SCREEN_OFF_TIMEOUT,
						WatchActivity.sysManager.getScreenTimeout());
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageUpdateTimeout);
				onBackPressed();
			}
		});

		thirtySeconds.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WatchActivity.sysManager.setScreenTimeout(THIRTY_SECONDS);
				Settings.System.putInt(getContentResolver(),
						Settings.System.SCREEN_OFF_TIMEOUT,
						WatchActivity.sysManager.getScreenTimeout());
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageUpdateTimeout);
				onBackPressed();
			}
		});

		fifteenSeconds.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WatchActivity.sysManager.setScreenTimeout(FIFTEEN_SECONDS);
				Settings.System.putInt(getContentResolver(),
						Settings.System.SCREEN_OFF_TIMEOUT,
						WatchActivity.sysManager.getScreenTimeout());
				WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(
						messageUpdateTimeout);
				onBackPressed();
			}
		});
	}
	
	public void updateLanguageTimeoutSetting() {
		fifteenSeconds.setText(WatchActivity.sysManager.getLanguageManager().getLabel(FIFTEEN_SECONDS_SHORT).getValue());
		thirtySeconds.setText(WatchActivity.sysManager.getLanguageManager().getLabel(THIRTY_SECONDS_SHORT).getValue());
		oneMinute.setText(WatchActivity.sysManager.getLanguageManager().getLabel(ONE_MINUTE_SHORT).getValue());
		twoMinutes.setText(WatchActivity.sysManager.getLanguageManager().getLabel(TWO_MINUTES_SHORT).getValue());
	}

	private RadioButton fifteenSeconds;
	private RadioButton thirtySeconds;
	private RadioButton oneMinute;
	private RadioButton twoMinutes;
	private Message messageUpdateTimeout;
}
