package com.bkav.ui.call;

import com.bkav.home.system.Platform;
import com.bkav.ui.clock.CustomDigitalClockIcon;
import com.bkav.ui.watchactivity.WatchActivity;
import com.bkav.watch.calls.CallsManager;
import com.example.bwatchdevice.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class CallDialog extends Activity {
	
	public static final int END_CALL = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_dialog);
		WatchActivity.sysManager
				.setMessageHandlerCallDialog(createMessageHandle());
		time = (CustomDigitalClockIcon) findViewById(R.id.time);
		reject = (ImageView) findViewById(R.id.reject);
		accept = (ImageView) findViewById(R.id.accept);
		silent = (ImageView) findViewById(R.id.silent);
		nameCallDialog = (TextView) findViewById(R.id.nameCallDialog);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(pattern, 0);
		
		Typeface typeFaceLight = WatchActivity.sysManager.getTypeFaceLight(getAssets());
		if (typeFaceLight != null) {
			time.setTypeface(typeFaceLight);
			nameCallDialog.setTypeface(typeFaceLight);
		}
		reject.setImageResource(R.drawable.reject);
		silent.setImageResource(R.drawable.silent); 

		CallsManager callManager = (CallsManager) Platform.watch
				.getElement("calls");
		nameCallDialog.setText(callManager.getIncomingCall().getContactName()
				.getValue());  
 
		reject.setOnClickListener(new OnClickListener() {       
			@Override 
			public void onClick(View v) {
				Platform.connection.write(WatchActivity.REJECT_CALL);
				setResult(RESULT_OK);
				finish(); 
			}
		});

		accept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Platform.connection.write(WatchActivity.ACCEPT_CALL);
				finish();
			}
		});

		silent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				silent.setImageResource(R.drawable.silented);
				Platform.connection.write(WatchActivity.SILENT_CALL);
				setResult(RESULT_OK);
				vibrator.cancel();
			}
		});
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
 
	public Handler createMessageHandle() {

		Handler messageHandlerCallDialog = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case END_CALL:
					finish();
					break;
				default:  
					break;
				}
			}
		};
		return messageHandlerCallDialog;
	}

	private ImageView reject;
	private ImageView accept;
	private ImageView silent;
	private TextView nameCallDialog;
	private CustomDigitalClockIcon time;
	private Vibrator vibrator;
	private long pattern[] = { 100, 900 };
}
