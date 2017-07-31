package com.bkav.ui.clock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.bkav.home.data.Data;
import com.bkav.home.data.DataParser;
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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClockStylesAnalog extends Fragment {
 
	public static final String LOCALE = "locale";
	public View view;
	public Thread drawSecondClockwise;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.clock_analog, container, false);
		dateAnalogClock = (TextView) view.findViewById(R.id.dateAnalogClock);
		iconContainer = (LinearLayout) view.findViewById(R.id.iconContainer);
		initIconForWatchs(getActivity().getApplicationContext());
		updateLanguageWatch();
		updateIconState();
		Runnable runnable = new CountDownRunner();
		if (drawSecondClockwise == null || !drawSecondClockwise.isAlive()) {
			drawSecondClockwise = new Thread(runnable);
			drawSecondClockwise.start();
		}
		return view;
	} 

	public void updateIconState() {
//		if (iconContainer != null) {
//			iconContainer.removeAllViews();
//			CallsManager callManager = (CallsManager) Platform.watch
//					.getElement("calls");
//			SmsManager smsManager = (SmsManager) Platform.watch
//					.getElement("sms");
//			EmailManager emailManager = (EmailManager) Platform.watch
//					.getElement("email");
//
//			missCallIcon.updateMissCall();
//			incomeSmsIcon.updateNewSms();
//			incomeEmailIcon.updateNewEmail();
//
//			if (!callManager.getMissCallCount().getValue().equals("0")) {
//				Log.e("System Manager", "add misscall icon");
//				iconContainer.addView(missCallIcon.getView());
//			}
//
//			if (!smsManager.getMissCountSms().getValue().equals("0")) {
//				Log.e("System Manager", "add incomeSmsIcon icon");
//				iconContainer.addView(incomeSmsIcon.getView());
//			}
//
//			if (!emailManager.getNumberOfUnreadMsg().getValue().equals("0")) {
//				Log.e("System Manager", "add incomeEmailIcon icon");
//				iconContainer.addView(incomeEmailIcon.getView());
//			}
//
//		}
//		iconContainer.setVisibility(View.GONE);
	}
	
	public void initIconForWatchs(Context context) {
		missCallIcon = new MissCallIcon(context);
		incomeSmsIcon = new IncomSmsIcon(context);
		incomeEmailIcon = new IncomEmailIcon(context);
	}

	public void updateLanguageWatch() {
		LanguageManager languageManager = WatchActivity.sysManager.getLanguageManager();
		if (languageManager != null) {
			if ((ClockScreen.ENGLISH).equals(languageManager.getLabel(LOCALE).getValue())) {
				dateFormat = new SimpleDateFormat(ClockScreen.ENGLISH_DATE_FORMAT, Locale.US);
			} else if ((ClockScreen.VIETNAMESE).equals(languageManager.getLabel(LOCALE).getValue())) {
				dateFormat = new SimpleDateFormat(ClockScreen.VIETNAMESE_DATE_FORMAT, new Locale("VI"));
			} else {
				dateFormat = new SimpleDateFormat(ClockScreen.ENGLISH_DATE_FORMAT, Locale.US);
			}
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		Runnable runnable = new CountDownRunner();
		if (drawSecondClockwise == null || !drawSecondClockwise.isAlive()) {
			drawSecondClockwise = new Thread(runnable);
			drawSecondClockwise.start();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		drawSecondClockwise.interrupt();
		iconContainer.removeAllViews();
		Log.e("", "stopppp");
	}

	@SuppressLint("SimpleDateFormat")
	public void drawSecond() {
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				try {
					Calendar calendar = Calendar.getInstance();
					int seconds = calendar.get(Calendar.SECOND);
					imageSecondsClockwise = (ImageView) view.findViewById(R.id.imageSecondsClockwise);
					if (dateFormat != null) {
						String currentDate = dateFormat.format(Calendar.getInstance().getTime());
						dateAnalogClock.setText(currentDate);

						imageSecondsClockwise.setBackgroundResource(R.drawable.seconds);
						RotateAnimation rotateAnimation = new RotateAnimation((seconds - 1) * 6, seconds * 6, Animation.RELATIVE_TO_SELF, 0.5f,
								Animation.RELATIVE_TO_SELF, 0.5f);
						rotateAnimation.setInterpolator(new LinearInterpolator());
						rotateAnimation.setDuration(1000);  
						rotateAnimation.setFillAfter(true);
						imageSecondsClockwise.startAnimation(rotateAnimation);
					} else {
						Log.e("", "nulllllllll");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private ImageView imageSecondsClockwise;
	private TextView dateAnalogClock;
	private SimpleDateFormat dateFormat;
	private LinearLayout iconContainer;
	private MissCallIcon missCallIcon;
	private IncomSmsIcon incomeSmsIcon;
	private IncomEmailIcon incomeEmailIcon;
	
	private class CountDownRunner implements Runnable {
		public void run() {
			while (!Thread.interrupted()) {
				try {
					drawSecond();
					Thread.sleep(1000);
					
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
