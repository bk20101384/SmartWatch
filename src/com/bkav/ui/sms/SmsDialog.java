package com.bkav.ui.sms;

import com.bkav.home.system.Platform;
import com.bkav.ui.call.ContactDialog;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.sms.SmsManager;
import com.example.bwatchdevice.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SmsDialog extends Activity {

	public static final String ADD = "add";
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = View.inflate(getApplicationContext(), R.layout.sms_dialog, null);
		setContentView(view);
		
		smsDialog = (LinearLayout) view.findViewById(R.id.smsDialog);
		timeDialogSms = (TextView) view.findViewById(R.id.timeDialogSms);
		nameDialogSms = (TextView) view.findViewById(R.id.nameDialogSms);
		contentDialogSms = (TextView) view.findViewById(R.id.contentDialogSms);
		contentScrollView = (ScrollView) view.findViewById(R.id.contentScrollView);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		Typeface typeFaceRegular = WatchActivity.sysManager.getTypeFaceRegular(getAssets());
		if (typeFaceRegular != null) {
			nameDialogSms.setTypeface(typeFaceRegular);
			timeDialogSms.setTypeface(typeFaceRegular);
			contentDialogSms.setTypeface(typeFaceRegular);
		}
		vibrator.vibrate(pattern, -1);

		threadCloseDialog = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!Thread.interrupted()) { 
					if (count == 5) {
						Log.e("", "threadCloseDialog runnnnnnnn");
						vibrator.cancel(); 
//						Message messageUpdateCall = new Message();
//						messageUpdateCall.what = WatchActivity.UPDATE_NEW_SMS;
//						WatchActivity.sysManager.getMessageHandlerWatch().sendMessage(messageUpdateCall);
						setCount(ADD);
						finish();
					} else {
						setCount(ADD);
//						Log.e("smsdialog", "count 5s: " + count + "s");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		threadCloseDialog.start();

		SmsManager smsManager = (SmsManager) Platform.watch.getElement("sms");
		if (smsManager.getIncomingSms() != null) {
			if (smsManager.getIncomingSms().getTime() != null) {
				String arrayDateTime[] = smsManager.getIncomingSms().getTime().getValue().split(" ");

				if (arrayDateTime.length == 2) {
					String arrayTime[] = arrayDateTime[1].split(":");
					int hours = Integer.parseInt(arrayTime[0]);
					int minutes = Integer.parseInt(arrayTime[1]);
					timeDialogSms.setText(hours + ":" + minutes);
				}
			}
			if (smsManager.getIncomingSms().getContactName() != null) {
				nameCall = smsManager.getIncomingSms().getContactName().getValue();
			}
			if (smsManager.getIncomingSms().getNumber() != null) {
				numbersCall = smsManager.getIncomingSms().getNumber().getValue();
			}
			if (smsManager.getIncomingSms().getContent() != null) {
//				Log.e("", smsManager.getIncomingSms().getContent().getValue());
				content = smsManager.getIncomingSms().getContent().getValue();
			}
		} else {
//			Log.e("", "inComingSms = null");   
		}

		smsDialog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ContactDialog.class);
				intent.putExtra(WatchActivity.NAME, nameCall);
				intent.putExtra(WatchActivity.NUMBER, numbersCall);
				startActivity(intent);
			}
		});
		
		contentDialogSms.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Log.e("smsdialog", "onclick");
				setResult(RESULT_OK);
				finish();
			}
		});
		
		contentScrollView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				Log.e("smsdialog", "ontouch");
				setCount(" ");
				vibrator.cancel();
				return false;
			}
		});

		nameDialogSms.setText(nameCall);
		contentDialogSms.setText(content);

	}

	public synchronized void setCount(String state) {
		if (ADD.equals(state)) {
			count++;
		} else {
			count = 0;
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		threadCloseDialog.interrupt();
		vibrator.cancel();
//		Log.e("smsdialog", "onDestroy");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		threadCloseDialog.interrupt();
		Log.e("smsdialog", "threadCloseDialog onStop");
	}

	private View view;
	private TextView timeDialogSms;
	private TextView nameDialogSms;
	private TextView contentDialogSms;
	private ScrollView contentScrollView;
	private LinearLayout smsDialog;
	private String nameCall;
	private String content;
	private String numbersCall;
	private Vibrator vibrator;
	private int count = 0;
	private Thread threadCloseDialog;
	private long pattern[] = { 0, 900, 100, 900, 100, 900, 100, 900, 100, 900 };
}
