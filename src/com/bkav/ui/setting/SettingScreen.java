package com.bkav.ui.setting;

import com.bkav.home.system.Platform;
import com.bkav.ui.about.AboutActivity;
import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.language.LanguageSettingDialog;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.calls.CallsManager;
import com.bkav.watch.contact.ContactManager;
import com.bkav.watch.email.EmailManager;
import com.bkav.watch.event.EventManager;
import com.bkav.watch.health.HealthManager;
import com.bkav.watch.notification.NotificationManager;
import com.bkav.watch.sms.SmsManager;
import com.example.bwatchdevice.R;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingScreen extends Fragment {

	public static final int SCREEN_TIMEOUT = 1;
	public static final int MIN_BRIGHTNESS = 10;
	public static final int MAX_BRIGHTNESS = 255;
	public static final String TIMEOUT = "timeout";
	public static final String FIFTEEN_SECONDS = "15 seconds";
	public static final String THIRTY_SECONDS = "30 seconds";
	public static final String ONE_MINUTE = "1 minute";
	public static final String TWO_MINUTES = "2 minutes";
	public static final String FIFTEEN_SECONDS_LONG = "fifteen_seconds_long";
	public static final String THIRTY_SECONDS_LONG = "thirty_seconds_long";
	public static final String ONE_MINUTE_LONG = "one_minute_long";
	public static final String TWO_MINUTES_LONG = "two_minutes_long";
	public static final String SETTING = "setting";
	public static final String ON = "on";
	public static final String OFF = "off";
	public static final String BRIGHTNESS = "brightness";
	public static final String SCREEN_OF_TIMEOUT = "screen_of_timeout";
	public static final String LANGUAGE = "language";
	public static final String ABOUT = "about";
	public static final String RESTART = "restart";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater
				.inflate(R.layout.setting, container, false);

		settingBar = (TextView) fragmentView.findViewById(R.id.settingBar);
		titleBluetooth = (TextView) fragmentView
				.findViewById(R.id.titleBluetooth);
		titleBrightness = (TextView) fragmentView
				.findViewById(R.id.titleBrightness);
		reset = (TextView) fragmentView.findViewById(R.id.reset);
		titleScreenTimeout = (TextView) fragmentView
				.findViewById(R.id.titleScreenTimeout);
		choiceTimeout = (TextView) fragmentView
				.findViewById(R.id.choiceTimeout);
		language = (TextView) fragmentView.findViewById(R.id.language);
		about = (TextView) fragmentView.findViewById(R.id.about);
		restart = (TextView) fragmentView.findViewById(R.id.restart);
		stateBluetooth = (ToggleButton) fragmentView
				.findViewById(R.id.stateBluetooth);
		screenTimeout = (LinearLayout) fragmentView
				.findViewById(R.id.screenTimeout);
		settingBrightness = (SeekBar) fragmentView
				.findViewById(R.id.settingBrightness);

		updateLanguageSetting();
		Typeface typeFaceRegular = WatchActivity.sysManager
				.getTypeFaceRegular(getActivity().getAssets());
		Typeface typeFaceLight = WatchActivity.sysManager
				.getTypeFaceLight(getActivity().getAssets());

		if (typeFaceLight != null && typeFaceRegular != null) {
			settingBar.setTypeface(typeFaceRegular);
			titleBluetooth.setTypeface(typeFaceLight);
			titleBrightness.setTypeface(typeFaceLight);
			titleScreenTimeout.setTypeface(typeFaceLight);
			reset.setTypeface(typeFaceLight);
			choiceTimeout.setTypeface(typeFaceLight);
			language.setTypeface(typeFaceLight);
			about.setTypeface(typeFaceLight);
			restart.setTypeface(typeFaceLight);
		}

		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		try {
			int currentBrightnessValue = Settings.System.getInt(getActivity()
					.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
			settingBrightness.setProgress(currentBrightnessValue);
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}

		stateBluetooth.setChecked(bluetoothAdapter.isEnabled());
		stateBluetooth.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (stateBluetooth.isChecked()) {
					bluetoothAdapter.enable();
					if (!bluetoothAdapter.isDiscovering()) {
						Intent discoverableIntent = new Intent(
								BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
						startActivityForResult(discoverableIntent, 99);
					}
				} else {
					bluetoothAdapter.disable();
				}
			}
		});

		screenTimeout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), TimeoutSettingDialog.class);
				startActivityForResult(intent, 5);
			}
		});

		reset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ContactManager contactManager = (ContactManager) Platform.watch
						.getElement("contact");
				CallsManager callManager = (CallsManager) Platform.watch
						.getElement("calls");
				SmsManager smsManager = (SmsManager) Platform.watch
						.getElement("sms");
				EmailManager emailManager = (EmailManager) Platform.watch
						.getElement("email");
				EventManager eventManager = (EventManager) Platform.watch
						.getElement("event");
				HealthManager healthManager = (HealthManager) Platform.watch
						.getElement("health");
				NotificationManager notificationManager = (NotificationManager) Platform.watch
						.getElement("notify");
				synchronized (Platform.thread) {
					Platform.watch.remove(contactManager);
					Platform.watch.remove(callManager);
					Platform.watch.remove(smsManager);
					Platform.watch.remove(emailManager);
					Platform.watch.remove(eventManager);
					Platform.watch.remove(healthManager);
					Platform.watch.remove(notificationManager);
					Platform.watch.resetData();
				}
			}
		});

		settingBrightness.setMax(MAX_BRIGHTNESS);
		settingBrightness
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						Settings.System.putFloat(getActivity()
								.getContentResolver(),
								Settings.System.SCREEN_BRIGHTNESS,
								brightnessValue);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						if (progress < MIN_BRIGHTNESS) {
							brightnessValue = MIN_BRIGHTNESS;
						} else {
							brightnessValue = progress;
						}
						float backLightValue = (float) progress
								/ MAX_BRIGHTNESS;
						WindowManager.LayoutParams layoutParams = getActivity()
								.getWindow().getAttributes();
						layoutParams.screenBrightness = backLightValue;
						getActivity().getWindow().setAttributes(layoutParams);
					}
				});

		language.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), LanguageSettingDialog.class);
				startActivity(intent);
			}
		});

		about.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity().getApplicationContext(),
						AboutActivity.class));
			}
		});

		restart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent("android.intent.action.REBOOT");
				// startActivity(intent);
			}
		});
		return fragmentView;
	}

	public void updateLanguageSetting() {
		LanguageManager languageManager = WatchActivity.sysManager
				.getLanguageManager();
		if (languageManager != null) {
			settingBar.setText(languageManager.getLabel(SETTING).getValue());
			stateBluetooth.setTextOn(languageManager.getLabel(ON).getValue());
			stateBluetooth.setTextOff(languageManager.getLabel(OFF).getValue());
			titleBrightness.setText(languageManager.getLabel(BRIGHTNESS)
					.getValue());
			titleScreenTimeout.setText(languageManager.getLabel(
					SCREEN_OF_TIMEOUT).getValue());
			language.setText(languageManager.getLabel(LANGUAGE).getValue());
			about.setText(languageManager.getLabel(ABOUT).getValue());
			restart.setText(languageManager.getLabel(RESTART).getValue());
			try {
				int currentTimeout = Settings.System.getInt(getActivity()
						.getContentResolver(),
						Settings.System.SCREEN_OFF_TIMEOUT);
				WatchActivity.sysManager.setScreenTimeout(currentTimeout);
				switch (currentTimeout) {
				case 15000:
					choiceTimeout.setText(languageManager.getLabel(
							FIFTEEN_SECONDS_LONG).getValue());
					break;
				case 30000:
					choiceTimeout.setText(languageManager.getLabel(
							THIRTY_SECONDS_LONG).getValue());
					break;
				case 60000:
					choiceTimeout.setText(languageManager.getLabel(
							ONE_MINUTE_LONG).getValue());
					break;
				case 120000:
					choiceTimeout.setText(languageManager.getLabel(
							TWO_MINUTES_LONG).getValue());
					break;
				default:
					choiceTimeout.setText(languageManager.getLabel(
							FIFTEEN_SECONDS_LONG).getValue());
					break;
				}
			} catch (SettingNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		updateChoiceTimeout();
	}

	public void updateChoiceTimeout() {
		LanguageManager languageManager = WatchActivity.sysManager
				.getLanguageManager();
		if (languageManager != null) {
			switch (WatchActivity.sysManager.getScreenTimeout()) {
			case 15000:
				choiceTimeout.setText(languageManager.getLabel(
						FIFTEEN_SECONDS_LONG).getValue());
				break;
			case 30000:
				choiceTimeout.setText(languageManager.getLabel(
						THIRTY_SECONDS_LONG).getValue());
				break;
			case 60000:
				choiceTimeout.setText(languageManager.getLabel(ONE_MINUTE_LONG)
						.getValue());
				break;
			case 120000:
				choiceTimeout.setText(languageManager
						.getLabel(TWO_MINUTES_LONG).getValue());
				break;
			default:
				choiceTimeout.setText(languageManager.getLabel(
						FIFTEEN_SECONDS_LONG).getValue());
				break;
			}
		}
	}

	private int brightnessValue;
	private TextView settingBar;
	private TextView titleBluetooth;
	private TextView titleBrightness;
	private TextView titleScreenTimeout;
	private ToggleButton stateBluetooth;
	private LinearLayout screenTimeout;
	private TextView choiceTimeout;
	private SeekBar settingBrightness;
	private TextView reset;
	private TextView about;
	private TextView language;
	private TextView restart;
	private BluetoothAdapter bluetoothAdapter;

}
