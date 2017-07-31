package com.bkav.ui.clock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.bkav.home.system.Platform;
import com.bkav.ui.call.MissCallIcon;
import com.bkav.ui.email.IncomEmailIcon;
import com.bkav.ui.language.LanguageManager;
import com.bkav.ui.sms.IncomSmsIcon;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.calls.CallsManager;
import com.bkav.watch.email.EmailManager;
import com.bkav.watch.sms.SmsManager;
import com.example.bwatchdevice.R;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ClockStylesDigital extends Fragment {
	public Thread updateDateThread;
	public int typeClock = 1;

	public ClockStylesDigital(int type) {
		this.typeClock = type;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.clock_digital, container, false);
		clockDigital = (RelativeLayout) view.findViewById(R.id.clockDigital);
		dateDigitalClock = (TextView) view.findViewById(R.id.dateDigital);
		customDigitalClock = (CustomDigitalClockIcon) view
				.findViewById(R.id.digitalClock);
		notifyLayout = (LinearLayout) view.findViewById(R.id.notifyLayout);
		initIconForWatchs(getActivity().getApplicationContext());
		switch (typeClock) {
		case 1:
			clockDigital.setBackgroundResource(R.drawable.clockbackground1); 
			break;
		case 2:
			clockDigital.setBackgroundResource(R.drawable.clockbackground2);
			break;
		case 3:
			clockDigital.setBackgroundResource(R.drawable.clockbackground3);
			break;
		default:
			break;
		}
		updateLanguageWatch();
		updateIconState();
		currentDate = dateFormat.format(Calendar.getInstance().getTime());
		dateDigitalClock.setText(currentDate);
		Typeface typeFaceLight = WatchActivity.sysManager
				.getTypeFaceLight(getActivity().getAssets());
		if (typeFaceLight != null) {
			dateDigitalClock.setTypeface(typeFaceLight);
			customDigitalClock.setTypeface(typeFaceLight);
		}

		createUpdateDateThread();
		return view;
	}

	public void updateIconState() {
		if (notifyLayout != null) {
			notifyLayout.removeAllViews();
			CallsManager callManager = (CallsManager) Platform.watch
					.getElement("calls");
			SmsManager smsManager = (SmsManager) Platform.watch
					.getElement("sms");
			EmailManager emailManager = (EmailManager) Platform.watch
					.getElement("email");

			missCallIcon.updateMissCall();
			incomeSmsIcon.updateNewSms();
			incomeEmailIcon.updateNewEmail();

			if (!callManager.getMissCallCount().getValue().equals("0")) {
				Log.e("System Manager", "add misscall icon");
				notifyLayout.addView(missCallIcon.getView());
			}

			if (!smsManager.getMissCountSms().getValue().equals("0")) {
				Log.e("System Manager", "add incomeSmsIcon icon");
				notifyLayout.addView(incomeSmsIcon.getView());
			}

			if (!emailManager.getNumberOfUnreadMsg().getValue().equals("0")) {
				Log.e("System Manager", "add incomeEmailIcon icon");
				notifyLayout.addView(incomeEmailIcon.getView());
			}

		}
		notifyLayout.setVisibility(View.GONE);
	}

	public void initIconForWatchs(Context context) {
		missCallIcon = new MissCallIcon(context);
		incomeSmsIcon = new IncomSmsIcon(context);
		incomeEmailIcon = new IncomEmailIcon(context);
	}

	public void updateLanguageWatch() {
		LanguageManager languageManager = WatchActivity.sysManager
				.getLanguageManager();
		if (languageManager != null) {
			if ((ClockScreen.ENGLISH).equals(languageManager.getLabel("locale")
					.getValue())) {
				dateFormat = new SimpleDateFormat(
						ClockScreen.ENGLISH_DATE_FORMAT, Locale.US);
			} else if ((ClockScreen.VIETNAMESE).equals(languageManager
					.getLabel("locale").getValue())) {
				dateFormat = new SimpleDateFormat(
						ClockScreen.VIETNAMESE_DATE_FORMAT, new Locale("VI"));
			} else {
				dateFormat = new SimpleDateFormat(
						ClockScreen.ENGLISH_DATE_FORMAT, Locale.US);
			}
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		createUpdateDateThread();
	}

	@Override
	public void onStop() {
		super.onStop();
		updateDateThread.interrupt();
		notifyLayout.removeAllViews();
	}

	private RelativeLayout clockDigital;
	private TextView dateDigitalClock;
	private LinearLayout notifyLayout;
	private CustomDigitalClockIcon customDigitalClock;
	private SimpleDateFormat dateFormat;
	private String currentDate;
	private View view;
	private MissCallIcon missCallIcon;
	private IncomSmsIcon incomeSmsIcon;
	private IncomEmailIcon incomeEmailIcon;

	private void createUpdateDateThread() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				while (!Thread.interrupted()) {
					try {
						currentDate = dateFormat.format(Calendar.getInstance()
								.getTime());
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								dateDigitalClock.setText(currentDate);
							}
						});
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		if (updateDateThread == null || !updateDateThread.isAlive()) {
			updateDateThread = new Thread(runnable);
			updateDateThread.start();
		}
	}

}
